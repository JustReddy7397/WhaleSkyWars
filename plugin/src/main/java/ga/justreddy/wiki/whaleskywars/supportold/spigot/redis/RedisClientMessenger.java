package ga.justreddy.wiki.whaleskywars.supportold.spigot.redis;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.supportold.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.supportold.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.supportold.ISpigotMessenger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.charset.StandardCharsets;

/**
 * @author JustReddy
 */
public class RedisClientMessenger implements ISpigotMessenger {

    private final String channel = "WhaleSkyWars";

    public static final byte[] CHANNEL_BYTES = "WhaleSkyWars".getBytes(StandardCharsets.UTF_8);

    private final JedisPool pool;
    private final Jedis jedis;

    private final RedisClientMessengerSender sender;
    private final RedisClientMessengerReceiver receiver;

    public RedisClientMessenger(String host, int port, String password) {
        this.pool = new JedisPool(host, port);
        this.jedis = pool.getResource();

        /*if (password != null && !password.isEmpty()) {
            jedis.auth(password);
        }*/ // Not sure if i need this TODO

        this.sender = new RedisClientMessengerSender(this);
        this.receiver = new RedisClientMessengerReceiver(this);
    }

    @Override
    public void connect() {
        jedis.connect();
    }

    @Override
    public void close() {
        jedis.close();
        pool.close();
    }

    @Override
    public WhaleSkyWars getPlugin() {
        return WhaleSkyWars.getInstance();
    }

    @Override
    public IMessengerSender getSender() {
        return sender;
    }

    @Override
    public IMessengerReceiver getReceiver() {
        return receiver;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public String getChannel() {
        return channel;
    }
}
