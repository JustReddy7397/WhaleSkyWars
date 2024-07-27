package ga.justreddy.wiki.whaleskywars.support.spigot.socket;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.support.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.support.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.support.ISpigotMessenger;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * @author JustReddy
 */
public class SocketClientMessenger implements ISpigotMessenger {

    private final String host;
    private final int port;
    private final String server;
    private final IMessengerSender sender;
    private final IMessengerReceiver receiver;
    private Socket socket;

    public SocketClientMessenger(String host, int port, String server) {
        this.host = host;
        this.port = port;
        this.server = server;
        this.sender = new SocketClientMessengerSender();
        this.receiver = new SocketClientMessengerReceiver();
   }

    @Override
    public void connect() {
        new BukkitRunnable() {
            boolean msg = socket != null;
            @Override
            public void run() {
                if (onClientConnect()) {
                    cancel();
                    return;
                }

                if (!msg) {
                    msg = true;
                    TextUtil.error(null, "Couldn't connect to the bungeecord server. Plugin will try to connect until it succeeds.", false);
                }

            }
        }.runTaskTimerAsynchronously(getPlugin(), 0, 20);
    }

    @Override
    public void close() {

    }

    @Override
    public WhaleSkyWars getPlugin() {
        return WhaleSkyWars.getInstance();
    }

    @Override
    public IMessengerSender getSender() {
        return sender;
    }

    @Override
    public IMessengerReceiver getReceiver() {
        return receiver;
    }

    private boolean onClientConnect() {
        SocketFactory factory = SSLSocketFactory.getDefault();
        try {
            socket = factory.createSocket(host, port);

            try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutput outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                outputStream.writeUTF(server);
                outputStream.flush();

                Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> onClientReceive(inputStream));

                TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aConnected to the bungee server on port " + port + "!");

            } catch (IOException ex) {
                return false;
            }

        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private void onClientReceive(ObjectInputStream stream) {
        try {
            ((SocketClientMessengerReceiver) receiver).onClientMessageRead(socket, stream);
        } catch (IOException ex) {
            if (!socket.isClosed()) {
                close();
                connect();
                TextUtil.error(ex, "Failed to read message from the bungee server, reconnecting...", false);
            }
        }
    }

}
