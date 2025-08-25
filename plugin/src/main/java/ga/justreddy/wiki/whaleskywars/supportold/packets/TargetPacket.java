package ga.justreddy.wiki.whaleskywars.supportold.packets;

import lombok.Getter;

import java.util.Set;

/**
 * @author JustReddy
 */
@Getter
public class TargetPacket {

    private final Packet packet;
    private final Set<String> servers;

    public TargetPacket(Packet packet, Set<String> servers) {
        this.packet = packet;
        this.servers = servers;
    }

}
