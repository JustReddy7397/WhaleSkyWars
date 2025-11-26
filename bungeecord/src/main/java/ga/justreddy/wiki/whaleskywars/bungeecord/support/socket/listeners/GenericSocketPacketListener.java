package ga.justreddy.wiki.whaleskywars.bungeecord.support.socket.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import ga.justreddy.wiki.whaleskywars.bungeecord.Bungeecord;
import ga.justreddy.wiki.whaleskywars.bungeecord.support.BungeePacketListener;
import ga.justreddy.wiki.whaleskywars.bungeecord.support.socket.BungeeSocketListener;
import ga.justreddy.wiki.whaleskywars.shared.PacketEventHandler;
import ga.justreddy.wiki.whaleskywars.shared.PacketType;
import ga.justreddy.wiki.whaleskywars.shared.SkyWarsListener;
import ga.justreddy.wiki.whaleskywars.shared.json.GsonHelper;
import ga.justreddy.wiki.whaleskywars.shared.packet.Data;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.server.ServerIdentity;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.server.ServerInfo;

/**
 * @author JustReddy
 */
public class GenericSocketPacketListener extends BungeePacketListener {

    public GenericSocketPacketListener(SkyWarsListener listener) {
        super(listener);
        listener.registerListener(this);
    }

    @PacketEventHandler(PacketType.SERVER_IDENTIFY)
    public void onIdentity(ServerIdentity identity, AckRequest request, SocketIOClient client) {
        if (client == null) {
            return;
        }
        Bungeecord.getInstance().getLogger().info("Received identity: " + identity.toJson());
        ServerInfo info = new ServerInfo(
                identity.getServerType(),
                identity.getUniqueIdentifier(),
                identity.getIp(),
                identity.getPort(),
                identity.getMaxPlayers()
        );
        request.sendAckData(GsonHelper.GSON.toJson(new Data(info)));
        client.joinRoom("servers-" + identity.getServerType().name());
        Bungeecord.getInstance().getLogger().info("[SOCKET] Registered server " + identity.getServerType().name() + " with UUID " + identity.getUniqueIdentifier());
    }

    @PacketEventHandler(PacketType.CLIENT_HEARTBEAT)
    public void onHeartBeat(SocketIOClient client) {
        BungeeSocketListener socketListener = (BungeeSocketListener) getListener();
        if (socketListener.getClient(client.getSessionId()) != null) {
            socketListener.getHeartbeats().put(client, System.currentTimeMillis());
        }
    }

}
