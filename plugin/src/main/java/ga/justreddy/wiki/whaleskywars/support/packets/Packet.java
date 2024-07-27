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

    private String server;
    private final PacketType packetType;

}
