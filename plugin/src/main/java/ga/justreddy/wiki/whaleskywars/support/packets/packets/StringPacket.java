package ga.justreddy.wiki.whaleskywars.support.packets.packets;

import com.avaje.ebeaninternal.server.cluster.SerialiseTransactionHelper;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.support.packets.PacketType;
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
