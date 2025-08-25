package ga.justreddy.wiki.whaleskywars.support.bungee.listeners;

import com.corundumstudio.socketio.AckRequest;
import ga.justreddy.wiki.whaleskywars.support.PacketListener;
import ga.justreddy.wiki.whaleskywars.support.PacketType;
import ga.justreddy.wiki.whaleskywars.support.ServerIdentity;
import ga.justreddy.wiki.whaleskywars.support.SkyWarsEventHandler;
import ga.justreddy.wiki.whaleskywars.support.bungee.Bungee;
import ga.justreddy.wiki.whaleskywars.support.packets.IdentifyPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class GenericSocketListener extends PacketListener {

    private final Bungee bungee = Bungee.getInstance();

    @SkyWarsEventHandler(PacketType.CLIENT_IDENTIFY)
    public void onIdentify(IdentifyPacket packet, AckRequest request) {
        List<String> servers = bungee.getServers().computeIfAbsent(packet.getType(), k -> new ArrayList<>());
        String serverName = String.format("%s-%d", packet.getType().name(), servers.size() + 1);
        servers.add(serverName);
        ServerIdentity identity = new ServerIdentity(
                UUID.randomUUID(),
                packet.getType(),
                packet.getIp(),
                packet.getPort(),
                packet.getMaxPlayers()
        );
        request.sendAckData(identity);
    }

}
