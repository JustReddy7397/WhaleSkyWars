package ga.justreddy.wiki.whaleskywars.bungeecord.support.socket;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.google.gson.JsonParseException;
import ga.justreddy.wiki.whaleskywars.bungeecord.Bungeecord;
import ga.justreddy.wiki.whaleskywars.bungeecord.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.bungeecord.support.socket.listeners.GenericSocketPacketListener;
import ga.justreddy.wiki.whaleskywars.bungeecord.support.tasks.SocketHeartbeatTask;
import ga.justreddy.wiki.whaleskywars.shared.*;
import ga.justreddy.wiki.whaleskywars.shared.json.GsonHelper;
import ga.justreddy.wiki.whaleskywars.shared.packet.ServerType;
import ga.justreddy.wiki.whaleskywars.shared.packet.TargetPacket;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author JustReddy
 */
public class BungeeSocketListener extends SkyWarsListener {

    private final Map<UUID, SocketIOClient> clients;
    private final Map<SocketIOClient, Long> heartbeats;

    private final SocketIOServer server;
    private final PacketEmitter emitter;
    private final String token;

    private final int taskId;

    public BungeeSocketListener(TomlConfig tomlConfig) throws UnknownHostException {
        this.token = tomlConfig.getString("super_secret_token");
        this.heartbeats = new ConcurrentHashMap<>();
        this.clients = new ConcurrentHashMap<>();
        final Configuration config = new Configuration();
        config.setJsonSupport(new JacksonJsonSupport());
        config.setHostname(tomlConfig.getString("bungee.socket.host"));
        config.setPort(tomlConfig.getInteger("bungee.socket.port"));
        config.setAuthorizationListener(data -> {
            final String token = data.getSingleUrlParam("token");
            if (token == null || !token.equals(this.token)) {
                Bungeecord.getInstance().getLogger().info("[SOCKET] Unauthorized connection attempt rejected.");
                return new AuthorizationResult(false);
            } else {
                return new AuthorizationResult(true);
            }
        });
        this.server = new SocketIOServer(config);
        this.emitter = new PacketEmitter(this);
        new GenericSocketPacketListener(this);
        this.taskId = Bungeecord.getInstance().getProxy().getScheduler()
                .schedule(Bungeecord.getInstance(), new SocketHeartbeatTask(this), 5, TimeUnit.SECONDS).getId();
    }

    @Override
    public void init() {
        server.addConnectListener(onConnect());
        server.addDisconnectListener(onDisconnect());
        server.start();
    }

    @Override
    public void dispose() {
        server.stop();
    }

    @Override
    public void emit(TargetPacket packet) {
        Set<ServerType> servers = packet.getTargetServers();
        servers.stream().map(server -> "servers-" + server.name()).forEach(mappedServer -> {
            server.getRoomOperations(mappedServer).sendEvent(packet.getPacket().getPacketType().getName(), packet.getPacket());
        });
    }

    @Override
    public <T> CompletableFuture<T> emitAck(TargetPacket packet) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        sendPacketWithRetry(packet, future, 1);
        return future;
    }

    @Override
    protected <T> void sendPacketWithRetry(TargetPacket packet, CompletableFuture<T> future, int attempt) {
        Set<ServerType> servers = packet.getTargetServers();
        for (ServerType targetServer : servers) {
            String mappedServer = "servers-" + targetServer.name();
            server.getRoomOperations(mappedServer).sendEvent(packet.getPacket().getPacketType().getName(), GsonHelper.GSON.toJson(packet.getPacket(), packet.getPacket().getPacketType().getType()), new BroadcastAckCallback<>(String.class) {

                @Override
                protected void onClientSuccess(SocketIOClient client, String result) {
                    if (future.isDone()) return;
                    T arg = GsonHelper.GSON.fromJson(result, packet.getPacket().getPacketType().getType());
                    future.complete(arg);
                }

                @Override
                public void onClientTimeout(SocketIOClient client) {
                    if (attempt < 5) {
                        sendPacketWithRetry(packet, future, attempt + 1);
                    } else {
                        future.completeExceptionally(new IOException("Acknowledgment timeout after 3 attempts"));
                    }
                }
            });
        }
    }

    @Override
    public void registerListener(PacketListener listener) {
        final Class<?> clazz = listener.getClass();
        final Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(PacketEventHandler.class)) {
                continue;
            }

            final PacketEventHandler annotation = method.getAnnotation(PacketEventHandler.class);
            final PacketType event = annotation.value();
            final Type expectedType = event.getType();

            final Class<?>[] parameterTypes = method.getParameterTypes();

            if (expectedType != null && parameterTypes.length != 3 && !AckRequest.class.isAssignableFrom(parameterTypes[1]) && !SocketIOClient.class.isAssignableFrom(parameterTypes[2])) {
                throw new IllegalArgumentException("Method " + method.getName() + " must have three parameters: (T data, AckRequest ack, SocketIOClient client).");
            } else if (expectedType == null && parameterTypes.length == 2 && !AckRequest.class.isAssignableFrom(parameterTypes[0]) && !SocketIOClient.class.isAssignableFrom(parameterTypes[1])) {
                throw new IllegalArgumentException("Method " + method.getName() + " must have two parameters: (AckRequest ack, SocketIOClient client).");
            } else if (expectedType == null && parameterTypes.length == 1 && !SocketIOClient.class.isAssignableFrom(parameterTypes[0])) {
                throw new IllegalArgumentException("Method " + method.getName() + " must have one parameter: (SocketIOClient client).");
            }

            if (expectedType != null) {
                final Class<?> parameterType = parameterTypes[0];
                final Type genericType = method.getGenericParameterTypes()[0];
                processListener(listener, method, event, expectedType, parameterType, genericType);
                continue;
            }

            try {
                listen(listener, method, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void processListener(PacketListener listener, Method method, PacketType packetType, Type expectedType, Class<?> paramClass, Type genericType) {
        if (genericType instanceof ParameterizedType && expectedType instanceof ParameterizedType) {
            final ParameterizedType methodPt = (ParameterizedType) genericType;
            final ParameterizedType expectedPt = (ParameterizedType) expectedType;

            final Type[] methodArgs = methodPt.getActualTypeArguments();
            final Type[] expectedArgs = expectedPt.getActualTypeArguments();

            if (methodArgs.length == expectedArgs.length) {
                for (int i = 0; i < methodArgs.length; i++) {
                    if (!methodArgs[i].equals(expectedArgs[i])) {
                        throw new IllegalArgumentException("Method " + method.getName() + " parameterized type argument " + i + " does not match expected type.");
                    }
                }
                try {
                    listen(listener, method, packetType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }

        if (expectedType instanceof Class<?>) {
            final Class<?> expectedClass = (Class<?>) expectedType;
            if (!expectedClass.isAssignableFrom(paramClass)) {
                throw new IllegalArgumentException("Method " + method.getName() + " parameter type " + paramClass.getName() + " does not match expected type " + expectedClass.getName() + ".");
            }
            try {
                listen(listener, method, packetType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void listen(PacketListener listener, Method method, PacketType type) throws IOException {
        Bungeecord.getInstance().getLogger().log(Level.INFO, "Listening for packet type: " + type.getType());
        server.addEventListener(type.getName(), String.class, (client, data, ackSender) -> {
            method.setAccessible(true);

            if (type.getType() == null) {
                try {
                    method.invoke(listener, client);
                } catch (Exception e) {
                    Bungeecord.getInstance().getLogger().log(Level.SEVERE, "Error invoking method", e);
                }
                return;
            }

            Object object = null;

            try {
                object = GsonHelper.GSON.fromJson(data, type.getType());
            } catch (JsonParseException exception) {
            }


            if (type.isReceiveAck() && ackSender != null) {
                method.invoke(listener, object, ackSender, client);
            } else {
                method.invoke(listener, object, client);
            }
        });
    }

    @Override
    public PacketEmitter getEmitter() {
        return emitter;
    }

    @Override
    public void close() {

    }

    private ConnectListener onConnect() {
        return (client) -> {
            clients.put(client.getSessionId(), client);
            Bungeecord.getInstance().getLogger().info("[SOCKET] Client connected: " + client.getSessionId());
        };
    }

    private DisconnectListener onDisconnect() {
        return (client -> {
            clients.remove(client.getSessionId());
            Bungeecord.getInstance().getLogger().info("[SOCKET] Client disconnected: " + client.getSessionId());
        });
    }

    public SocketIOServer getServer() {
        return server;
    }

    public SocketIOClient getClient(UUID sessionId) {
        return clients.getOrDefault(sessionId, null);
    }

    public long getLastHeartbeat(UUID sessionId) {
        return getLastHeartbeat(getClient(sessionId));
    }

    public long getLastHeartbeat(SocketIOClient client) {
        return heartbeats.getOrDefault(client, 0L);
    }

    public Map<SocketIOClient, Long> getHeartbeats() {
        return heartbeats;
    }
}
