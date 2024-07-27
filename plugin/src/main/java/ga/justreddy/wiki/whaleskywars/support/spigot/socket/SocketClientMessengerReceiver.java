package ga.justreddy.wiki.whaleskywars.support.spigot.socket;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.support.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.Socket;

/**
 * @author JustReddy
 */
public class SocketClientMessengerReceiver implements IMessengerReceiver {

    private final WhaleSkyWars PLUGIN = WhaleSkyWars.getInstance();

    public void onClientMessageRead(Socket socket, ObjectInputStream inputStream) throws IOException {

        while (!socket.isClosed()) {
            try {
                Object object = inputStream.readObject();

                if (!(object instanceof Packet)) return;

                Packet packet = (Packet) object;

                Bukkit.getScheduler().runTaskAsynchronously(PLUGIN, () -> handlePacket(packet));


            } catch (ClassNotFoundException | OptionalDataException
                    | StreamCorruptedException
                    | InvalidClassException ex) {
                TextUtil.error(ex, "Error while reading packet from server", false
                        );
            }
        }

    }

    @Override
    public void handlePacket(Packet packet) {
        // TODO
    }
}
