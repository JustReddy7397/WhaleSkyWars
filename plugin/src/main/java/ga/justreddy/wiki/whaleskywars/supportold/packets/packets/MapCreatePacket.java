package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * @author JustReddy
 */
@Getter
public class MapCreatePacket extends Packet implements Serializable {

    private final String gameName;
    private final Map<String, Object> data;
    private final File worldFile;

    public MapCreatePacket(String gameName, Map<String, Object> data, File worldFile) {
        super(PacketType.CLIENT_MAP_CREATE);
        this.gameName = gameName;
        this.data = data;
        this.worldFile = worldFile;
    }
}
