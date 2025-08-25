package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author JustReddy
 */
@Getter
public class BackToServerPacket extends Packet implements Serializable {

    private final UUID player;
    private final String lobby;
    private final ServerPriority serverPriority;


    public BackToServerPacket(UUID player, String lobby, ServerPriority serverPriority) {
        super(PacketType.CLIENT_BACK_TO_SERVER);
        this.player = player;
        this.lobby = lobby;
        this.serverPriority = serverPriority;
    }

    public enum ServerPriority {LOBBY, PREVIOUS}


}
