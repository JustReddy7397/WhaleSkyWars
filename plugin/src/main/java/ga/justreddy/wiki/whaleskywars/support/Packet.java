package ga.justreddy.wiki.whaleskywars.support;

import ga.justreddy.wiki.whaleskywars.model.entity.BasePlayer;
import ga.justreddy.wiki.whaleskywars.support.json.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JustReddy
 */
@Getter
@Setter
public class Packet {

    private final BasePlayer basePlayer;
    private final PacketType packetType;
    private boolean isFromBungee;

    public Packet(BasePlayer basePlayer, PacketType packetType) {
        this.basePlayer = basePlayer;
        this.packetType = packetType;
    }

    public Packet(PacketType packetType) {
        this(null, packetType);
    }

}
