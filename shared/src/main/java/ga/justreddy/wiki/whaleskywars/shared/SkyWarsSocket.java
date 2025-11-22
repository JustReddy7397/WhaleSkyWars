package ga.justreddy.wiki.whaleskywars.shared;

import ga.justreddy.wiki.whaleskywars.shared.packet.TargetPacket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public abstract class SkyWarsSocket {

    protected boolean authenticated;
    private boolean shouldTerminate;
    private int reconnectAttempts;

    public abstract void init();

    public abstract void dispose();

    protected final boolean checkReconnectAttempts() {
        this.reconnectAttempts++;
        return this.reconnectAttempts >= 5;
    }

    public final void authenticate() {
        this.reconnectAttempts = 0;
        this.authenticated = true;
    }

    public abstract void emit(TargetPacket packet);

    public abstract  <T> CompletableFuture<T> emitAck(TargetPacket packet);

    protected abstract <T> void sendPacketWithRetry(TargetPacket packet, CompletableFuture<T> future, int attempt);

    public abstract void registerListener(PacketListener listener);

    public abstract void processListener(PacketListener listener,
                                         Method method,
                                         PacketType packetType,
                                         Type returnType,
                                         Class<?> paramClass, Type type);

    public abstract void listen(PacketListener listener, Method method, PacketType type) throws IOException;

    public abstract PacketEmitter getEmitter();

    public abstract void close();

    private <T> void handleAckResponse(CompletableFuture<T> future, Object[] args) {
        if (future.isDone()) {
            return;
        }

        if (args.length == 0) {
            future.complete(null);
            return;
        }

        Object arg = args[0];

        if (arg instanceof byte[]) {
            byte[] array = (byte[]) arg;
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                @SuppressWarnings("unchecked")
                T deserializedObject = (T) objectInputStream.readObject();
                future.complete(deserializedObject);

            } catch (IOException | ClassNotFoundException e) {
                future.completeExceptionally(e);
            }
        } else {
            future.completeExceptionally(new IllegalArgumentException("Expected byte[] but got " + arg.getClass()));
        }
    }

}
