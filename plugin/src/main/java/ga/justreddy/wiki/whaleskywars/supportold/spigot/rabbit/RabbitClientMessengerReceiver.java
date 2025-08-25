package ga.justreddy.wiki.whaleskywars.supportold.spigot.rabbit;

import ga.justreddy.wiki.whaleskywars.supportold.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;

/**
 * @author JustReddy
 */
public class RabbitClientMessengerReceiver implements IMessengerReceiver {

    private final RabbitClientMessenger messenger;

    public RabbitClientMessengerReceiver(RabbitClientMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void handlePacket(Packet packet) {
        PacketType type = packet.getPacketType();
        switch (type) {
        }
    }
}
