package ga.justreddy.wiki.whaleskywars.support;

import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * @author JustReddy
 */
@Getter
public class TargetPacket implements Serializable {

    private final Packet packet;
    private final Set<ServerType> targetServers;

    public TargetPacket(Packet packet, Set<ServerType> targetServers) {
        this.packet = packet;
        this.targetServers = targetServers;
    }

}
