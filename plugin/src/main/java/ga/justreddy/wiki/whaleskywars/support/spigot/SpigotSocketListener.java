package ga.justreddy.wiki.whaleskywars.support.spigot;

import ga.justreddy.wiki.whaleskywars.support.*;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author JustReddy
 */
public class SpigotSocketListener extends SkyWarsPacketListener {

    private final Socket socket;
    private final PacketEmitter emitter;

    public SpigotSocketListener(String host, int port) throws UnknownHostException, URISyntaxException {
        IO.Options options = IO.Options
                .builder()
                .setPath("/socket")
                .build();

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

    }

    @Override
    public void processListener(PacketListener listener, Method method, PacketType packetType, Type returnType, Class<?> paramClass, Type type) {

    }

    @Override
    public void listen(PacketListener listener, Method method, PacketType type) throws IOException {
        listenFor(type, (args) -> {

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
