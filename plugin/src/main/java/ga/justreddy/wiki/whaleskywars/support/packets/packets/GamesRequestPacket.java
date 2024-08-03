package ga.justreddy.wiki.whaleskywars.support.packets.packets;

import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.support.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author JustReddy
 */
@Getter
public class GamesRequestPacket extends Packet implements Serializable {

    private final String server;

    public GamesRequestPacket(String server) {
        super(PacketType.CLIENT_GAMES_REQUEST);
        this.server = server;
    }
}
