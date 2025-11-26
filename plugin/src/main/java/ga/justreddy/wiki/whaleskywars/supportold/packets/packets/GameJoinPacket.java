package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.shared.packet.packets.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author JustReddy
 */
@Getter
public class GameJoinPacket extends Packet implements Serializable {

    private final BungeeGame game;
    private final UUID uuid;
    private final String player;
    private final boolean firstJoin;
    private final boolean localJoin;

    public GameJoinPacket(BungeeGame game, UUID uuid, String player, boolean firstJoin, boolean localJoin) {
        super(PacketType.CLIENT_GAME_JOIN);
        this.game = game;
        this.uuid = uuid;
        this.player = player;
        this.firstJoin = firstJoin;
        this.localJoin = localJoin;
    }

}
