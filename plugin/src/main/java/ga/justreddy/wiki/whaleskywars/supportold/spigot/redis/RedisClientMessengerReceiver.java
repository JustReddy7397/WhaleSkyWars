package ga.justreddy.wiki.whaleskywars.supportold.spigot.redis;

import ga.justreddy.wiki.whaleskywars.supportold.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import redis.clients.jedis.BinaryJedisPubSub;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author JustReddy
 */
public class RedisClientMessengerReceiver implements IMessengerReceiver {

    private final RedisClientMessenger messenger;

    private final BinaryJedisPubSub pubSub;

    public RedisClientMessengerReceiver(RedisClientMessenger messenger) {
        this.messenger = messenger;

        this.pubSub = new BinaryJedisPubSub() {
            @Override
            public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
                String channelString = new String(channel, StandardCharsets.UTF_8);
                if (!channelString.equalsIgnoreCase(messenger.getChannel())) return;
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(message);
                     ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
                ) {
                    Object o = objectInputStream.readObject();
                    if (!(o instanceof Packet)) return;
                    Packet packet = (Packet) o;
                    handlePacket(packet);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        messenger.getJedis().subscribe(pubSub, RedisClientMessenger.CHANNEL_BYTES);
    }

    @Override
    public void handlePacket(Packet packet) {

    }
}
