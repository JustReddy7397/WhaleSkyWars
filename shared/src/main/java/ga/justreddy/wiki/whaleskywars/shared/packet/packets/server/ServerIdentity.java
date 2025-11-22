package ga.justreddy.wiki.whaleskywars.shared.packet.packets.server;

import ga.justreddy.wiki.whaleskywars.shared.PacketType;
import ga.justreddy.wiki.whaleskywars.shared.packet.Packet;
import ga.justreddy.wiki.whaleskywars.shared.packet.ServerType;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
@Getter
public class ServerIdentity extends Packet {

    private final ServerType serverType;
    private final UUID uniqueIdentifier;
    private final String ip;
    private final int port;
    private final int maxPlayers;
    private final List<ServerPlayer> initialPlayers;


    public ServerIdentity(ServerType serverType, UUID uniqueIdentifier, String ip, int port, int maxPlayers, List<ServerPlayer> initialPlayers) {
        super(PacketType.CLIENT_IDENTIFY);
        this.serverType = serverType;
        this.uniqueIdentifier = uniqueIdentifier;
        this.ip = ip;
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.initialPlayers = initialPlayers;
    }


}
