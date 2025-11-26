package ga.justreddy.wiki.whaleskywars.support.socket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.shared.*;
import ga.justreddy.wiki.whaleskywars.shared.json.GsonHelper;
import ga.justreddy.wiki.whaleskywars.shared.json.JsonSerializable;
import ga.justreddy.wiki.whaleskywars.shared.packet.ServerType;
import ga.justreddy.wiki.whaleskywars.shared.packet.TargetPacket;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.server.ServerIdentity;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.server.ServerInfo;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author JustReddy
 */
@Slf4j
public class SpigotSocketListener extends SkyWarsListener {

    private final Socket socket;
    private final PacketEmitter emitter;

    public SpigotSocketListener(String host, int port) throws UnknownHostException, URISyntaxException {
        IO.Options options = IO.Options.builder()
                .setTransports(new String[]{"websocket"})
                .setQuery("token=super_secret_token")
                .build();
        if (host == null) {
            this.socket = IO.socket("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port, options);
        } else if (port != -1) {
            this.socket = IO.socket("http://" + host + ":" + port, options);
        } else {
            this.socket = IO.socket(URI.create(host), options);
        }
        this.emitter = new PacketEmitter(this);
    }

    @Override
    public void init() {
        socket.connect();
        socket.on(Socket.EVENT_CONNECT, (objects) -> {
            sendIdentityPacket();
            WhaleSkyWars.getInstance().getLogger().info( "[SOCKET] Connected to the server.");
        });
        socket.on(Socket.EVENT_CONNECT_ERROR, (objects) -> {
            WhaleSkyWars.getInstance().getLogger().warning("[SOCKET] Connection error: " + Arrays.toString(objects));
        });
    }

    @Override
    public void dispose() {
        socket.disconnect();
    }

    @Override
    public void emit(TargetPacket packet) {

    }

    @Override
    public <T> CompletableFuture<T> emitAck(TargetPacket packet) {
        CompletableFuture<T> future = new CompletableFuture<>();
        final Logger logger = WhaleSkyWars.getInstance().getLogger();

        final PacketType event = packet.getPacket().getPacketType();

        if (!event.isReceiveAck()) {
            logger.warning("[SOCKET] EmitWithAck \"" + event + "\" is not configured as an event that returns an acknowledgement, but was called with emitWithAck.");
            future.completeExceptionally(new RuntimeException("[SOCKET] EmitWithAck \"" + event + "\" is not configured as an event that returns an acknowledgement, but was called with emitWithAck."));
            return future;
        }

        String json;
        if (packet.getPacket() instanceof JsonSerializable) {
            final JsonSerializable jsonSerializable = packet.getPacket();
            json = jsonSerializable.toJson();
        } else {
            json = GsonHelper.GSON.toJson(packet.getPacket());
        }

        System.out.println(json);

        socket.send().emit(event.getName(), new String[]{json}, (response) -> {
            try {
                if (response.length < 1) {
                    future.completeExceptionally(new IllegalArgumentException("No response received"));
                    return;
                }

                logger.info("[SOCKET] EmitWithAckResponse \"" + event + "\" with data: " + response[0].toString());

                try {
                    JsonObject jsonObject = GsonHelper.GSON.fromJson(response[0].toString(), JsonObject.class);

                    if (jsonObject.has("error"))
                        future.completeExceptionally(new Exception(jsonObject.get("error").getAsString()));
                    else if (jsonObject.has("data")) {
                        System.out.println("it has data WOWWW");
                        JsonElement dataElement = jsonObject.get("data");
                        if (dataElement.isJsonObject() || dataElement.isJsonArray() || dataElement.isJsonPrimitive()) {
                            try {
                                T result;
                                if (dataElement.isJsonPrimitive())
                                    result = GsonHelper.GSON.fromJson(dataElement.getAsJsonPrimitive(), event.getType());
                                else
                                    result = GsonHelper.GSON.fromJson(dataElement.isJsonObject() ? dataElement.getAsJsonObject() : dataElement.getAsJsonArray(), event.getType());
                                future.complete(result);
                            } catch (JsonSyntaxException ex) {
                                throw new JsonParseException("Failed to parse JSON for " + event.getType().getTypeName(), ex);
                            }
                        } else if (dataElement.isJsonNull())
                            future.complete(null);
                        else
                            future.completeExceptionally(new IllegalArgumentException("Unable to deserialize data"));
                    } else
                        future.completeExceptionally(new IllegalArgumentException("Missing data field"));
                } catch (JsonSyntaxException ex) {
                    throw new JsonParseException("Failed to parse JSON for " + JsonObject.class.getName(), ex);
                }
            } catch (Exception ex) {
                future.completeExceptionally(ex);
            }
        });

        if (System.getProperty("DEBUG_SOCKET_EMIT") != null)
            logger.info("[SOCKET] EmitWithAckRequest \"" + event + "\" with data: " + json);

        return future;
    }

    @Override
    protected <T> void sendPacketWithRetry(TargetPacket packet, CompletableFuture<T> future, int attempt) {

    }

    @Override
    public void registerListener(PacketListener listener) {
        final Class<?> clazz = listener.getClass();
        final Method[] methods = clazz.getMethods();
        Arrays.stream(methods).forEach(method -> {
            if (!method.isAnnotationPresent(PacketEventHandler.class)) return;

            PacketEventHandler handler = method.getAnnotation(PacketEventHandler.class);
            final PacketType packetType = handler.value();
            Type type = packetType.getType();

            Class<?>[] parameterTypes = method.getParameterTypes();

            if (parameterTypes.length != 1) return;

            if (type != null) {
                Class<?> paramClass = parameterTypes[0];
                Type genType = method.getGenericParameterTypes()[0];
                processListener(listener, method, packetType, type, paramClass, genType);
                return;
            }

            try {
                listen(listener, method, packetType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public void processListener(PacketListener listener, Method method, PacketType packetType, Type returnType, Class<?> paramClass, Type type) {
        if (type instanceof ParameterizedType && returnType instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            Type[] returnParameterizedType = ((ParameterizedType) returnType).getActualTypeArguments();

            if (parameterizedType.length == returnParameterizedType.length) {
                for (int i = 0; i < parameterizedType.length; i++) {
                    if (parameterizedType[i].equals(returnParameterizedType[i])) {
                        try {
                            listen(listener, method, packetType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (returnType instanceof Class<?>) {
                Class<?> expectedClass = (Class<?>) returnType;
                if (!expectedClass.isAssignableFrom(paramClass)) return;
                try {
                    listen(listener, method, packetType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void listen(PacketListener listener, Method method, PacketType type) throws IOException {
        WhaleSkyWars.getInstance().getLogger().log(Level.INFO, "Listening for packet type: " + type.getName());
        listenFor(type, (args) -> {
            try {

                method.setAccessible(true);

                Object arg = null;
                Ack ackCallback = null;

                if (type.getType() != null) {
                    if (String.class.equals(type.getType())) {
                        arg = args[0].toString();
                    } else {
                        try {
                            arg = GsonHelper.GSON.fromJson(args[0].toString(), type.getType());
                        } catch (JsonSyntaxException ex) {
                            throw new JsonParseException("Failed to parse JSON for " + type.getType().getTypeName(), ex);
                        }
                    }
                }

                if (type.getType() != null) {
                    method.invoke(listener, arg);
                } else {
                    method.invoke(listener);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public PacketEmitter getEmitter() {
        return emitter;
    }

    private void listenFor(PacketType type, Emitter.Listener listener) {
        socket.on(type.getName(), listener);
    }

    @Override
    public void close() {
        dispose();
    }


    public void sendIdentityPacket() {

        InetAddress inetAddress = null;

        if (inetAddress == null)
            try {
                inetAddress = getPublicInetAddress();
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

        CompletableFuture<ServerInfo> serverInfoFuture = this.getEmitter().sendIdentity(new ServerIdentity(
                ServerType.LOBBY,
                UUID.fromString(socket.id()),
                inetAddress.getHostAddress(),
                Bukkit.getServer().getPort(),
                100
        ));

        serverInfoFuture.thenAccept(info -> {
            authenticate();
            Bukkit.getLogger().info("[SOCKET] info wowow");
            WhaleSkyWars.getInstance().setInfo(info);
        }).exceptionally(ex -> {
            Bukkit.getLogger().warning("[SOCKET] Failed to verify identity: " + ex.getMessage());
            if (checkReconnectAttempts()) {
                return null;
            }

            Bukkit.getServer().getScheduler().runTaskLater(WhaleSkyWars.getInstance(), this::sendIdentityPacket, 20L * 5);
            return null;
        });

    }

    private InetAddress getPublicInetAddress() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress())
                    return inetAddress;
            }
        }

        return InetAddress.getLocalHost();
    }
}
