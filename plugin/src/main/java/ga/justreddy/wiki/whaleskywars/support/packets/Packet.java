package ga.justreddy.wiki.whaleskywars.support.packets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JustReddy
 */
@RequiredArgsConstructor
@Getter
@Setter
public class Packet implements Serializable {

    protected String server;
    private PacketType packetType;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

}
