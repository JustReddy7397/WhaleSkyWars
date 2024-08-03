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
public class GameUpdatePacket extends Packet implements Serializable {

    private final BungeeGame game;

    public GameUpdatePacket(BungeeGame game) {
        super(PacketType.CLIENT_GAME_UPDATE);
        this.game = game;
    }
}
