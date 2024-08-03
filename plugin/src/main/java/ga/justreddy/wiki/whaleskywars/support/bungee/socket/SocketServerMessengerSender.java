package ga.justreddy.wiki.whaleskywars.support.bungee.socket;

import ga.justreddy.wiki.whaleskywars.support.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.support.packets.SocketConnection;
import ga.justreddy.wiki.whaleskywars.support.packets.TargetPacket;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author JustReddy
 */
public class SocketServerMessengerSender implements IMessengerSender {

    private final SocketServerMessenger socketServer;
    private final LinkedBlockingQueue<TargetPacket> sendQueue = new LinkedBlockingQueue<>();

    public SocketServerMessengerSender(SocketServerMessenger socketServer) {
        this.socketServer = socketServer;
    }

    public void send(ServerSocket socket) {
        while (!socket.isClosed()) {
            try {

                final TargetPacket targetPacket = sendQueue.take();
                if (socket.isClosed()) return;
                Packet packet = targetPacket.getPacket();
                Set<String> servers = targetPacket.getServers();

                for (String server : servers) {
                    SocketConnection connection = socketServer.getSpigotSocket().getOrDefault(server, null);
                    if (connection == null) continue;
                    ObjectOutputStream outputStream = connection.getOutputStream();
                    outputStream.writeObject(packet);
                    outputStream.flush();
                    outputStream.reset();
                }
            }catch (IOException | InterruptedException ex) {
                TextUtil.errorBungee(ex, "Couldn't send packet to servers.", false);
            }
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void sendPacket(Packet packet) {
        sendQueue.add(new TargetPacket(packet, new HashSet<>(socketServer.getSpigotSocket().keySet())));
    }

    @Override
    public void sendPacketToServer(Packet packet, String server) {
        sendQueue.add(new TargetPacket(packet, Collections.singleton(server)));
    }

    @Override
    public void sendPacketToAllExcept(Packet packet, String exceptServer) {
        Set<String> servers = getServersExcept(exceptServer);
        sendQueue.add(new TargetPacket(packet, servers));
    }

    public Set<String> getServersExcept(String server) {
        Set<String> servers = new HashSet<>(socketServer.getSpigotSocket().keySet());
        servers.remove(server);
        return servers;
    }

}
