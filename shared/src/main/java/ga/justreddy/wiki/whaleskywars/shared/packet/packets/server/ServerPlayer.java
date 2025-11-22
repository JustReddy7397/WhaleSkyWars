package ga.justreddy.wiki.whaleskywars.shared.packet.packets.server;

import ga.justreddy.wiki.whaleskywars.shared.json.JsonSerializable;

import java.util.UUID;

/**
 * @author JustReddy
 */
public class ServerPlayer extends JsonSerializable {

    private final UUID uniqueId;
    private final String cachedName;
    private final UUID serverIdentifier;
    private final UUID proxyIdentifier;

    public ServerPlayer(UUID uniqueId, String cachedName, UUID serverIdentifier, UUID proxyIdentifier) {
        this.uniqueId = uniqueId;
        this.cachedName = cachedName;
        this.serverIdentifier = serverIdentifier;
        this.proxyIdentifier = proxyIdentifier;
    }

}
