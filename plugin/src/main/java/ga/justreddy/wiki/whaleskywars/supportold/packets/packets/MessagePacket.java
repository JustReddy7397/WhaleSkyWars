package ga.justreddy.wiki.whaleskywars.supportold.packets.packets;

import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
@Getter
public class MessagePacket extends Packet implements Serializable {

    private final UUID uuid;
    private final List<String> messages;

    public MessagePacket(UUID uuid, List<String> messages) {
        super(PacketType.CLIENT_MESSAGE);
        this.uuid = uuid;
        this.messages = messages;
    }

    public MessagePacket(UUID uuid, String... messages) {
        this(uuid, Arrays.asList(messages));
    }
}
