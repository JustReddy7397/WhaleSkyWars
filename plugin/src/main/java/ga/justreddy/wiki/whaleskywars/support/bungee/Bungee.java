package ga.justreddy.wiki.whaleskywars.support.bungee;

import com.github.simplenet.Client;
import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.support.ServerType;
import ga.justreddy.wiki.whaleskywars.support.bungee.socket.BungeeSocketListener;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.*;

/**
 * @author JustReddy
 */
@Getter
public class Bungee extends Plugin {

    @Getter
    private static Bungee instance;

    private BungeeTomlConfig config;

    private final Map<String, BungeeGame> games = new HashMap<>();

    private final Map<UUID, String> playerServers = new HashMap<>();

    private final Map<ServerType, List<Client>> clients = new HashMap<>();

    private final Map<ServerType, List<String>> servers = new HashMap<>();

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        this.config = new BungeeTomlConfig("bungee-config.toml");
        new BungeeSocketListener(config);
    }

    public List<Client> getClientsExcept(ServerType... type) {
        List<Client> allClients = new ArrayList<>();
        List<ServerType> excludedTypes = Arrays.asList(type);
        for (Map.Entry<ServerType, List<Client>> entry : clients.entrySet()) {
            ServerType serverType = entry.getKey();
            List<Client> clientList = entry.getValue();

            // Skip specified types
            if (excludedTypes.contains(serverType)) {
                continue;
            }

            // Add clients from this server type
            allClients.addAll(clientList);
        }
        return allClients;
    }

    public List<Client> getClientsExcept(List<ServerType> serverTypes) {
        List<Client> allClients = new ArrayList<>();
        for (Map.Entry<ServerType, List<Client>> entry : clients.entrySet()) {
            ServerType serverType = entry.getKey();
            List<Client> clientList = entry.getValue();

            // Skip specified types
            if (serverTypes.contains(serverType)) {
                continue;
            }

            // Add clients from this server type
            allClients.addAll(clientList);
        }
        return allClients;
    }

}
