package ga.justreddy.wiki.whaleskywars.support.packets.packets;

import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.support.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author JustReddy
 */
@Getter
public class KickPacket extends Packet implements Serializable {

    private final BungeeGame game;
    private final String player;
    private final String target;


    public KickPacket(BungeeGame game, String player, String target) {
        super(PacketType.CLIENT_GAME_REMOVE);
        this.game = game;
        this.player = player;
        this.target = target;
    }
}
