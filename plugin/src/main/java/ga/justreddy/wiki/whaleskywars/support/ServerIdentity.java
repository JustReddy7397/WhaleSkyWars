package ga.justreddy.wiki.whaleskywars.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * @author JustReddy
 */
@AllArgsConstructor
@Getter
public class ServerIdentity {

    private final UUID uuid;
    private final ServerType type;
    private final String ip;
    private final int port;
    private final int maxPlayers;

}
