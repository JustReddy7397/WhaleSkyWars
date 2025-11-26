package ga.justreddy.wiki.whaleskywars.bungeecord.support.tasks;

import com.corundumstudio.socketio.SocketIOClient;
import ga.justreddy.wiki.whaleskywars.bungeecord.support.BungeePacketListener;
import ga.justreddy.wiki.whaleskywars.bungeecord.support.socket.BungeeSocketListener;

import java.util.Map;

/**
 * @author JustReddy
 */
public class SocketHeartbeatTask implements Runnable {

    private final BungeeSocketListener listener;

    public SocketHeartbeatTask(BungeeSocketListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        for (Map.Entry<SocketIOClient, Long> entry : listener.getHeartbeats().entrySet()) {
            SocketIOClient client = entry.getKey();
            long lastHeartbeat = entry.getValue();
            long currentTime = System.currentTimeMillis();
            // If the last heartbeat was more than 30 seconds ago, disconnect the client
            if (currentTime - lastHeartbeat > 30000) {
                client.disconnect() ;
            }
        }
    }
}
