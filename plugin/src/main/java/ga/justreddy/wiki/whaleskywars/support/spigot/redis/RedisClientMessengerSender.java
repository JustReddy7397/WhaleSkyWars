package ga.justreddy.wiki.whaleskywars.support.spigot.redis;

import ga.justreddy.wiki.whaleskywars.support.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author JustReddy
 */
public class RedisClientMessengerSender implements IMessengerSender {

    private final RedisClientMessenger messenger;

    public RedisClientMessengerSender(RedisClientMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void close() {

    }

    @Override
    public void sendPacket(Packet packet) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(packet);
            objectOutputStream.flush();
            byte[] data = outputStream.toByteArray();
            messenger.getJedis().publish(RedisClientMessenger.CHANNEL_BYTES, data);
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
}
