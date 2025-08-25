package ga.justreddy.wiki.whaleskywars.support;

import com.corundumstudio.socketio.SocketIOServer;
import io.socket.client.Ack;
import io.socket.client.Socket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author JustReddy
 */
public class PacketEmitter {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    private static final int TIMEOUT_SECONDS = 5;
    private static final int MAX_ATTEMPTS = 2; // 1 original + 1 retry

    private final SocketIOServer server;
    private final Socket client;

    public PacketEmitter(SocketIOServer server) {
        this(server, null);
    }

    public PacketEmitter(Socket client) {
        this(null, client);
    }

    private PacketEmitter(SocketIOServer server, Socket client) {
        this.server = server;
        this.client = client;
    }

    public void emit(TargetPacket packet) {
        if (server != null) {
            Set<ServerType> servers = packet.getTargetServers();
            servers.stream().map(server -> "servers-" + server.name()).forEach(
                    mappedServer -> {
                        server.getRoomOperations(mappedServer)
                                .sendEvent(packet.getPacket().getPacketType().getName(),
                                        packet);
                    }
            );
        }
        if (client != null) {
            client.emit(packet.getPacket().getPacketType().getName(), packet);
        }
    }

    public <T> CompletableFuture<T> emitAck(TargetPacket packet) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        sendPacketWithRetry(packet, future, 1);
        return future;
    }

    private <T> void sendPacketWithRetry(TargetPacket packet, CompletableFuture<T> future, int attempt) {
        if (server != null) {
            Set<ServerType> servers = packet.getTargetServers();
            for (ServerType targetServer : servers) {
                String mappedServer = "servers-" + targetServer.name();
                server.getRoomOperations(mappedServer)
                        .sendEvent(packet.getPacket().getPacketType().getName(), packet, (Ack) args -> {
                            if (future.isDone()) return;
                            handleAckResponse(future, args);
                        });
            }
        } else {
            client.emit(packet.getPacket().getPacketType().getName(), packet, (Ack) args -> {
                if (future.isDone()) return;
                handleAckResponse(future, args);
            });
        }

        // Schedule timeout
        SCHEDULER.schedule(() -> {
            if (!future.isDone()) {
                if (attempt < MAX_ATTEMPTS) {
                    // Retry
                    sendPacketWithRetry(packet, future, attempt + 1);
                } else {
                    // Fail after max attempts
                    future.completeExceptionally(new TimeoutException("No ACK received after " + MAX_ATTEMPTS + " attempts."));
                }
            }
        }, TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    private <T> void handleAckResponse(CompletableFuture<T> future, Object[] args) {
        if (future.isDone()) return; // Protect against double complete

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
