package ga.justreddy.wiki.whaleskywars.shared.packet;

import ga.justreddy.wiki.whaleskywars.shared.json.JsonSerializable;
import lombok.Getter;

import java.util.Set;

/**
 * @author JustReddy
 */
@Getter
public class TargetPacket extends JsonSerializable {

    private final Packet data;
    private final Set<ServerType> targetServers;

    public TargetPacket(Packet data, Set<ServerType> targetServers) {
        this.data = data;
        this.targetServers = targetServers;
    }

    public Packet getPacket() {
        return data;
    }

}
