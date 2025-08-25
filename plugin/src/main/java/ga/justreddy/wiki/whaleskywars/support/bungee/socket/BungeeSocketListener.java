package ga.justreddy.wiki.whaleskywars.support.bungee.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.support.*;
import ga.justreddy.wiki.whaleskywars.support.bungee.BungeeTomlConfig;
import ga.justreddy.wiki.whaleskywars.support.bungee.listeners.GenericSocketListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * @author JustReddy
 */
public class BungeeSocketListener extends SkyWarsPacketListener {

    private final SocketIOServer server;
    private final PacketEmitter emitter;
    private final String token;

    public BungeeSocketListener(BungeeTomlConfig tomlConfig) {
        final Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(4018);
        this.server = new SocketIOServer(
                config);
        this.token = tomlConfig.getString("super_secret_token");
        this.emitter = new PacketEmitter(server);
        new GenericSocketListener();
    }

    @Override
    public void init() {
        server.start();
        server.addConnectListener(onConnect());
    }

    @Override
    public void registerListener(PacketListener listener) {
        final Class<?> clazz = listener.getClass();
        final Method[] methods = clazz.getMethods();
        Arrays.stream(methods).forEach(method -> {
            if (!method.isAnnotationPresent(SkyWarsEventHandler.class)) return;

            SkyWarsEventHandler handler = method.getAnnotation(SkyWarsEventHandler.class);
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
        WhaleSkyWars.getInstance().getLogger()
                .log(Level.INFO, "Listening for packet type: " + type.getName());
        server.addEventListener(type.getName(), String.class, (client, data, ackSender) -> {
            method.setAccessible(true);

            byte[] bytes = data.getBytes();

            if (type.getType() == null) {
                try {
                    method.invoke(listener);
                } catch (Exception e) {
                    WhaleSkyWars.getInstance().getLogger().log(Level.SEVERE, "Error invoking method", e);
                }
                return;
            }

            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                if (type.isAck() && ackSender != null) {
                    method.invoke(listener, objectInputStream.readObject(), ackSender);
                } else {
                    method.invoke(listener, objectInputStream.readObject());
                }

            } catch (Exception e) {
                WhaleSkyWars.getInstance().getLogger().log(Level.SEVERE, "Error processing packet", e);
                if (ackSender != null) {
                    ackSender.sendAckData("Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public PacketEmitter getEmitter() {
        return emitter;
    }

    @Override
    public void close() {
        //  TODO SEND DISCONNECT PACKET
    }

    private ConnectListener onConnect() {
        return (client) -> {
            final String token = client.getHandshakeData().getHttpHeaders().get("Authorization");
            System.out.println("Client trying to connect with token: " + token);
            if (token == null || !token.equals(this.token)) {
                client.disconnect();
            } else {
                System.out.println("Client connected with token: " + token);
            }
        };
    }

}
