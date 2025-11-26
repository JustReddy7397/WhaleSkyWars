package ga.justreddy.wiki.whaleskywars.shared;

import ga.justreddy.wiki.whaleskywars.shared.packet.ServerType;
import ga.justreddy.wiki.whaleskywars.shared.packet.TargetPacket;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.server.ServerIdentity;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.server.ServerInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class PacketEmitter {

    private final SkyWarsListener listener;

    public PacketEmitter(SkyWarsListener listener) {
        this.listener = listener;
    }

    public CompletableFuture<ServerInfo> sendIdentity(ServerIdentity identity) {
        return listener.emitAck(new TargetPacket(identity, Set.of(ServerType.BUNGEE)));
    }

    public void sendHeartbeat() {
        listener.emit(new TargetPacket(null, Set.of(ServerType.BUNGEE)));
    }

}
