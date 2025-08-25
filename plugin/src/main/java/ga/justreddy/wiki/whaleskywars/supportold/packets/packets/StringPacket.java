package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author JustReddy
 */
@Getter
public class StringPacket extends Packet implements Serializable {

    private final String string;

    public StringPacket(PacketType packetType, String string) {
        super(packetType);
        this.string = string;
    }

}
