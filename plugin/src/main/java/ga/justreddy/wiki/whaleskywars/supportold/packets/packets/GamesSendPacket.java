package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.shared.packet.packets.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * @author JustReddy
 */
@Getter
public class GamesSendPacket extends Packet implements Serializable {

    private final String server;
    private final List<BungeeGame> games;

    public GamesSendPacket(String server, List<BungeeGame> games) {
        super(PacketType.SERVER_GAMES_SEND);
        this.server = server;
        this.games = games;
    }

}
