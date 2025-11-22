package ga.justreddy.wiki.whaleskywars.support.spigot.socket;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.support.*;
import ga.justreddy.wiki.whaleskywars.support.json.GsonHelper;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;

/**
 * @author JustReddy
 */
public class SpigotSocketListener extends SkyWarsPacketListener {

    private final Socket socket;
    private final PacketEmitter emitter;

    public SpigotSocketListener(String host, int port) throws UnknownHostException, URISyntaxException {
        IO.Options options = IO.Options.builder().setPath("/socket")
                .setExtraHeaders(Map.of(
                        "Authorization", List.of("your_super_secret_token_here")
                )).build();
        if (host == null) {
            this.socket = IO.socket("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port, options);
        } else if (port != -1) {
            this.socket = IO.socket(host + ":" + port, options);
        } else {
            this.socket = IO.socket(URI.create(host), options);
        }
        this.emitter = new PacketEmitter(socket);
    }

    @Override
    public void init() {
        socket.connect();
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

    }
}
