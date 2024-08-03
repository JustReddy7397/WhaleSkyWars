package ga.justreddy.wiki.whaleskywars.support.packets.packets;

import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.support.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author JustReddy
 */
@Getter
public class JoinPacket extends Packet implements Serializable {

    private final BungeeGame game;
    private final UUID uuid;
    private final String player;
    private final boolean firstJoin;
    private final boolean localJoin;

    public JoinPacket(BungeeGame game, UUID uuid, String player, boolean firstJoin, boolean localJoin) {
        super(PacketType.CLIENT_GAME_JOIN);
        this.game = game;
        this.uuid = uuid;
        this.player = player;
        this.firstJoin = firstJoin;
        this.localJoin = localJoin;
    }

}
