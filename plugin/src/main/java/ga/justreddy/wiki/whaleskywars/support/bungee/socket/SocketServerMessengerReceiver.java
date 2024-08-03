package ga.justreddy.wiki.whaleskywars.support.bungee.socket;

import ga.justreddy.wiki.whaleskywars.support.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.support.bungee.Bungee;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;

/**
 * @author JustReddy
 */
public class SocketServerMessengerReceiver implements IMessengerReceiver {

    private final Bungee plugin;
    private final SocketServerMessenger socketServer;
    private final SocketServerMessengerSender sender;

    public SocketServerMessengerReceiver(SocketServerMessenger messenger) {
        this.plugin = messenger.getPlugin();
        this.socketServer = messenger;
        this.sender = (SocketServerMessengerSender) messenger.getSender();
    }

    public void onServerMessageReader(ServerSocket socket, String server, ObjectInputStream stream) throws IOException, ClassNotFoundException {
        while (!socket.isClosed()) {
            Object object = stream.readObject();
            if (!(object instanceof Packet)) return;
            Packet packet = (Packet) object;
            handlePacket(packet);
        }
    }

    @Override
    public void handlePacket(Packet packet) {

    }
}
