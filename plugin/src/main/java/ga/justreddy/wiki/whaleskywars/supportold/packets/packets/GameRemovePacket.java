package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.shared.packet.packets.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author JustReddy
 */
@Getter
public class GameRemovePacket extends Packet implements Serializable {

    private final BungeeGame game;

    public GameRemovePacket(BungeeGame game) {
        super(PacketType.CLIENT_GAME_REMOVE);
        this.game = game;
    }
}
