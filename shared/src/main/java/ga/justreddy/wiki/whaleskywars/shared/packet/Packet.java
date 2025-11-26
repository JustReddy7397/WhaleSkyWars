package ga.justreddy.wiki.whaleskywars.shared.packet;

import ga.justreddy.wiki.whaleskywars.shared.PacketType;
import ga.justreddy.wiki.whaleskywars.shared.json.JsonSerializable;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.entity.BasePlayer;
import lombok.Getter;

/**
 * @author JustReddy
 */
@Getter
public class Packet extends JsonSerializable {

    private final BasePlayer basePlayer;
    private final PacketType packetType;

    public Packet(BasePlayer basePlayer, PacketType packetType) {
        this.basePlayer = basePlayer;
        this.packetType = packetType;
    }

    public Packet(PacketType packetType) {
        this(null, packetType);
    }

}
