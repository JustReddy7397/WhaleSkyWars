package ga.justreddy.wiki.whaleskywars.supportold.spigot.socket;

import ga.justreddy.wiki.whaleskywars.supportold.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author JustReddy
 */
public class SocketClientMessengerSender implements IMessengerSender {

    private final SocketClientMessenger messenger;

    private final LinkedBlockingQueue<Packet> packetQueue = new LinkedBlockingQueue<>();

    public SocketClientMessengerSender(SocketClientMessenger messenger) {
        this.messenger = messenger;
    }

    private boolean isRunning = false;

    public void send(Socket socket, ObjectOutputStream stream) {
        if (isRunning) return;
        isRunning = true;
        while (!socket.isClosed()) {
            try {
                Packet packet = packetQueue.take();
                if (packet.getPacketType() == null) return;
                stream.writeObject(packet);
                stream.flush();
                stream.reset();
            } catch (IOException | InterruptedException ex) {
                TextUtil.error(ex, "Error while sending packet to server", false);
            }
        }
    }

    @Override
    public void close() {
        packetQueue.add(new Packet(null));
    }

    @Override
    public void sendPacket(Packet packet) {
        packetQueue.add(packet);
    }

    @Override
    public void sendPacketToServer(Packet packet, String server) {
        throw new UnsupportedOperationException("For bungeecord only");
    }

    @Override
    public void sendPacketToAllExcept(Packet packet, String exceptServer) {
        throw new UnsupportedOperationException("For bungeecord only");
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
