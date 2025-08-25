package ga.justreddy.wiki.whaleskywars.support.spigot;

import com.github.simplenet.Client;
import ga.justreddy.wiki.whaleskywars.support.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author JustReddy
 */
public class SpigotSimpleListener extends SkyWarsPacketListener {

    private final Client client;
    private final SimplePacketEmitter emitter;

    public SpigotSimpleListener(Client client) {
        this.client = client;
        this.emitter = new SimplePacketEmitter( client);
    }

    @Override
    public void init() {
        client.connect("localhost", 3000);
    }

    @Override
    public void registerListener(PacketListener listener) {

    }

    @Override
    public void processListener(PacketListener listener, Method method, PacketType packetType, Type returnType, Class<?> paramClass, Type type) {

    }

    @Override
    public void listen(PacketListener listener, Method method, PacketType type) throws IOException {

    }

    @Override
    public PacketEmitter getEmitter() {
        return null;
    }

    @Override
    public void close() {

    }
}
