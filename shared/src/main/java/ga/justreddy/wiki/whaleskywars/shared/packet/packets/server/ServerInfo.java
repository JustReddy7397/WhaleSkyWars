package ga.justreddy.wiki.whaleskywars.shared.packet.packets.server;

import ga.justreddy.wiki.whaleskywars.shared.packet.ServerType;

import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class ServerInfo extends ServerIdentity {

    public ServerInfo(ServerType serverType, UUID uniqueIdentifier, String ip, int port, int maxPlayers) {
        super(serverType, uniqueIdentifier, ip, port, maxPlayers);
    }

}
