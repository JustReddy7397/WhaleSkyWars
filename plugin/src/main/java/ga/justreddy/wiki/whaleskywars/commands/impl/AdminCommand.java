package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
public class AdminCommand implements SkyWarsCommand {
    @Override
    public String getName() {
        return "admin";
    }

    @Override
    public String getDescription() {
        return "Admin commands";
    }

    @Override
    public String getUsage() {
        return "/ws admin";
    }

    @Override
    public String getPermission() {
        return "whaleskywars.admin";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public void execute(CommandRunner runner, String[] args) {
        IGamePlayer player = runner.asGamePlayer();

        if (args.length == 1) {
            // TODO
        }

        switch (args[1].toLowerCase()) {
            case "setlobby":
                setLobbyCommand(player);
                return;
        }
    }

    private void setLobbyCommand(IGamePlayer player) {
        player.getPlayer().ifPresent(bukkitPlayer -> {
            Location location = bukkitPlayer.getLocation();
            WhaleSkyWars.getInstance().setSpawn(location);
            // TODO
            player.sendMessage("Lobby set successfully");
        });
    }

}
