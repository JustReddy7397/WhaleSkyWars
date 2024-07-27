package ga.justreddy.wiki.whaleskywars.support.spigot.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.support.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.support.IMessengerSender;
import ga.justreddy.wiki.whaleskywars.support.ISpigotMessenger;
import ga.justreddy.wiki.whaleskywars.support.packets.Packet;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * @author JustReddy
 */
@Getter
@Setter
public class RabbitClientMessenger implements ISpigotMessenger {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private String vhost = "/";

    private Connection connection;
    private Channel channel;

    private final String tag = "WhaleSkyWars";

    private final IMessengerSender sender;
    private final IMessengerReceiver receiver;

    public RabbitClientMessenger(String host, int port, String username, String password, String vhost) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(vhost);

        this.sender = new RabbitClientMessengerSender(this);
        this.receiver = new RabbitClientMessengerReceiver(this);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException ex) {
            TextUtil.error(ex, "Error while creating RabbitMQ connection", true);
        }


    }

    public RabbitClientMessenger(String host, int port, String username, String password) {
        this(host, port, username, password, "/");
    }

    @Override
    public void connect() {
        try {
            channel.exchangeDeclare(tag, "direct", true);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, tag, "info");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                handle(tag, delivery.getBody());
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

        } catch (IOException ex) {
            TextUtil.error(ex, "Error while trying to establish a queue with the RabbitMQ server", true);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
            channel.close();
        } catch (IOException | TimeoutException ex) {
            TextUtil.error(ex, "Error while closing RabbitMQ connection", false);
        }
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

    private void handle(String channel, byte[] data) {
        if (!Objects.equals(channel, tag)) return;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            Object obj = objectInputStream.readObject();
            Packet packet = (Packet) obj;
            receiver.handlePacket(packet);
        } catch (IOException | ClassNotFoundException ex) {
            TextUtil.error(ex, "Error while handling packet", false);
        }
    }

}
