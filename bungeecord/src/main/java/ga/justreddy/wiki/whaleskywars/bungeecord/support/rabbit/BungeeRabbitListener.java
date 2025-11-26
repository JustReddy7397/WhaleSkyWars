package ga.justreddy.wiki.whaleskywars.bungeecord.support.rabbit;

import ga.justreddy.wiki.whaleskywars.shared.PacketEmitter;
import ga.justreddy.wiki.whaleskywars.shared.PacketListener;
import ga.justreddy.wiki.whaleskywars.shared.PacketType;
import ga.justreddy.wiki.whaleskywars.shared.SkyWarsListener;
import ga.justreddy.wiki.whaleskywars.shared.packet.TargetPacket;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class BungeeRabbitListener extends SkyWarsListener {

    private final PacketEmitter emitter;

    public BungeeRabbitListener() {
        this.emitter = new PacketEmitter(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void emit(TargetPacket packet) {

    }

    @Override
    public <T> CompletableFuture<T> emitAck(TargetPacket packet) {
        return null;
    }

    @Override
    protected <T> void sendPacketWithRetry(TargetPacket packet, CompletableFuture<T> future, int attempt) {

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
        return emitter;
    }

    @Override
    public void close() {

    }
}
