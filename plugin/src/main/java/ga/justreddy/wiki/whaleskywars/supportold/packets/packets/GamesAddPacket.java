package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * @author JustReddy
 */
@Getter
public class GamesAddPacket extends Packet implements Serializable {

    private final String server;
    private final List<BungeeGame> games;

    public GamesAddPacket(String server, List<BungeeGame> games) {
        super(PacketType.SERVER_GAMES_ADD);
        this.server = server;
        this.games = games;
    }
}
