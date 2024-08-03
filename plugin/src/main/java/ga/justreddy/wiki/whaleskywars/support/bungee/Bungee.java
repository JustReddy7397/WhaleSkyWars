package ga.justreddy.wiki.whaleskywars.support.bungee;

import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.support.IMessenger;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    private IMessenger<Bungee> messenger;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        this.config = new BungeeTomlConfig("bungee-config.toml");

    }

}
