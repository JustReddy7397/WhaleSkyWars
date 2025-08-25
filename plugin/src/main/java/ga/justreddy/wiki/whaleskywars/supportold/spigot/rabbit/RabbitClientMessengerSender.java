package ga.justreddy.wiki.whaleskywars.supportold.spigot.rabbit;

import ga.justreddy.wiki.whaleskywars.supportold.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author JustReddy
 */
public class RabbitClientMessengerSender implements IMessengerSender {

    private final RabbitClientMessenger messenger;

    public RabbitClientMessengerSender(RabbitClientMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void close() {
        // Empty
    }

    @Override
    public void sendPacket(Packet packet) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(packet);
            objectOutputStream.flush();
            byte[] data = outputStream.toByteArray();
            publish(data);
        } catch (IOException ex) {
            TextUtil.error(ex, "Error while sending packet "+packet.getPacketType()+" to server", false);
        }
    }

    @Override
    public void sendPacketToServer(Packet packet, String server) {
        throw new UnsupportedOperationException("For bungeecord only");
    }

    @Override
    public void sendPacketToAllExcept(Packet packet, String exceptServer) {
        throw new UnsupportedOperationException("For bungeecord only");
    }

    private void publish(byte[] data) {
        try {
            if (!messenger.getChannel().isOpen()) {
                messenger.setChannel(messenger.getConnection().createChannel());
                messenger.connect();
            }
            messenger.getChannel().basicPublish("WhaleSkyWars", "info", null, data);
        } catch (IOException ex) {
            TextUtil.error(ex, "Error while sending packet to server", false);
        }
    }

}
