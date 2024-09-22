package ga.justreddy.wiki.whaleskywars.support.spigot.rabbit;

import ga.justreddy.wiki.whaleskywars.support.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.support.packets.PacketType;

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
