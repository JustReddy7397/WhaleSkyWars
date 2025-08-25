package ga.justreddy.wiki.whaleskywars.support.packets;

import ga.justreddy.wiki.whaleskywars.support.Packet;
import ga.justreddy.wiki.whaleskywars.support.PacketType;
import ga.justreddy.wiki.whaleskywars.support.ServerType;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author JustReddy
 */
@Getter
public class IdentifyPacket extends Packet implements Serializable {

    private final ServerType type;
    private final String ip;
    private final int port;
    private final int maxPlayers;

    public IdentifyPacket(ServerType type, String ip, int port, int maxPlayers) {
        super(PacketType.CLIENT_IDENTIFY);
        this.type = type;
        this.ip = ip;
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

}
