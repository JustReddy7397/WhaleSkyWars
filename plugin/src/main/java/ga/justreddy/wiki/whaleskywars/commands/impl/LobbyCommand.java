package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JustReddy
 */
public class LobbyCommand implements SkyWarsCommand {
    @Override
    public String getName() {
        return "lobby";
    }

    @Override
    public String getDescription() {
        return "Go to the lobby";
    }

    @Override
    public String getUsage() {
        return "/ws lobby";
    }

    @Override
    public String getPermission() {
        return "whaleskywars.spawn";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>(Collections.singletonList("hub"));
    }

    @Override
    public void execute(CommandRunner runner, String[] args) {
        IGamePlayer player = runner.asGamePlayer();
        Player bukkitPlayer = runner.asPlayer();
        Location spawn = WhaleSkyWars.getInstance().getSpawn();
        if (spawn == null) {
            player.sendMessages("&cThe spawn has not been set yet.");
            return;
        }
        bukkitPlayer.teleport(spawn);
    }
}
