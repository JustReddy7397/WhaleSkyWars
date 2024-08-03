package ga.justreddy.wiki.whaleskywars.support.packets.packets;

import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.support.packets.PacketType;
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
