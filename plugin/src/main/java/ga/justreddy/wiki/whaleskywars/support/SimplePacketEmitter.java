package ga.justreddy.wiki.whaleskywars.support;

import com.github.simplenet.Client;
import com.github.simplenet.Server;
import com.github.simplenet.packet.Packet;
import com.google.common.io.ByteArrayDataInput;
import ga.justreddy.wiki.whaleskywars.support.bungee.Bungee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author JustReddy
 */
public class SimplePacketEmitter {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    private static final int TIMEOUT_SECONDS = 5;
    private static final int MAX_ATTEMPTS = 2; // 1 original + 1 retry

    private final Server server;
    private final Client client;

    private SimplePacketEmitter(Server server, Client client) {
        this.server = server;
        this.client = client;
    }

    public SimplePacketEmitter(Server server) {
        this(server, null);
    }

    public SimplePacketEmitter(Client client) {
        this(null, client);
    }

    public void emit(TargetPacket packet) {
        if (server != null) {
            Set<ServerType> servers = packet.getTargetServers();
            List<ServerType> excludedServers = new ArrayList<>(servers);
            excludedServers.removeIf(servers::contains);
            List<Client> clients = Bungee.getInstance().getClientsExcept(excludedServers);

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
            ) {
                objectOutputStream.writeObject(packet);
                objectOutputStream.flush();
                server.queueAndFlushToAllExcept(Packet.builder().putBytes(byteArrayOutputStream.toByteArray()), clients);
            } catch (Exception exception) {
                
            }

        } else if (client != null) {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
            ) {
                objectOutputStream.writeObject(packet);
                objectOutputStream.flush();
                Packet.builder().putBytes(byteArrayOutputStream.toByteArray()).queueAndFlush(client);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
