package ga.justreddy.wiki.whaleskywars.support.bungee.socket;

import ga.justreddy.wiki.whaleskywars.support.IMessenger;
import ga.justreddy.wiki.whaleskywars.support.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.support.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.support.bungee.Bungee;
import ga.justreddy.wiki.whaleskywars.support.packets.PacketType;
import ga.justreddy.wiki.whaleskywars.support.packets.SocketConnection;
import ga.justreddy.wiki.whaleskywars.support.packets.packets.StringPacket;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
@Getter
public class SocketServerMessenger implements IMessenger<Bungee> {

    private final Bungee bungee;
    private final int port;
    private final Map<String, SocketConnection> spigotSocket = new HashMap<>();
    private final IMessengerSender sender;
    private final IMessengerReceiver receiver;
    private ServerSocket socket;

    public SocketServerMessenger(int port) {
        this.port = port;
        bungee = Bungee.getInstance();
        this.sender = new SocketServerMessengerSender(this);
        this.receiver = new SocketServerMessengerReceiver(this);
    }

    @Override
    public void connect() {

        ProxyServer.getInstance().getScheduler()
                .runAsync(bungee, () -> {
                    try {
                        socket = new ServerSocket(port);

                        ProxyServer.getInstance().getScheduler().runAsync(bungee, () -> ((SocketServerMessengerSender) sender).send(socket));

                        acceptConnection(socket);

                    } catch (IOException ex) {
                        TextUtil.errorBungee(ex, "Couldn't start the socket server.", false);
                    }
                });
    }

    @Override
    public void close() {
        try {
            socket.close();
            for (SocketConnection socketConnection : spigotSocket.values()) {
                socketConnection.getSocket().close();
            }
        } catch (IOException ex) {
            TextUtil.errorBungee(ex, "Couldn't close the socket server.", false);
        }
    }

    @Override
    public Bungee getPlugin() {
        return Bungee.getInstance();
    }

    @Override
    public IMessengerSender getSender() {
        return sender;
    }

    @Override
    public IMessengerReceiver getReceiver() {
        return receiver;
    }

    private void acceptConnection(ServerSocket serverSocket) {
        try {
            Socket socket = serverSocket.accept();

            ProxyServer.getInstance().getScheduler().runAsync(bungee, () -> acceptConnection(serverSocket));

            onClientConnect(socket);
        } catch (IOException ex) {
            TextUtil.errorBungee(ex, "Couldn't accept the connection.", false);
        }
    }

    private void onClientConnect(Socket socket) {
        if (socket.isClosed()) return;

        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            String server = inputStream.readUTF();

            SocketConnection connection = new SocketConnection(socket, outputStream);
            spigotSocket.put(server, connection);

            // TODO send games to server

            onServerReceive(server, inputStream);

        } catch (IOException ex) {
            TextUtil.errorBungee(ex, "Couldn't connect to the client.", false);
        }
    }

    private void onServerReceive(String server, ObjectInputStream stream) {
        try {
            ((SocketServerMessengerReceiver) receiver).onServerMessageReader(socket, server, stream);
        } catch (IOException | ClassNotFoundException ex) {
            if (ex instanceof ClassNotFoundException) {
                TextUtil.errorBungee(ex, "Class not found :(", false);
            } else {
                TextUtil.errorBungee(ex, "Server: " + server + " has closed the connection.", false);
            }

            for (String game : new ArrayList<>(bungee.getGames().keySet())) {
                if (bungee.getGames().get(game).getServer().equals(server)) {
                    bungee.getGames().remove(game);
                }
            }

            sender.sendPacketToAllExcept(new StringPacket(PacketType.SERVER_GAMES_REMOVE, server), server);

            spigotSocket.remove(server);
        }
    }

}
