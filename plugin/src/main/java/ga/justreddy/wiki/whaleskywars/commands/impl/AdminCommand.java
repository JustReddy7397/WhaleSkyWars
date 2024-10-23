package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;
import ga.justreddy.wiki.whaleskywars.manager.cache.CacheManager;
import ga.justreddy.wiki.whaleskywars.model.Messages;
import ga.justreddy.wiki.whaleskywars.model.menu.Menu;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
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
            return;
        }

        switch (args[1].toLowerCase()) {
            case "setlobby":
                setLobbyCommand(player);
                return;
            case "build":
                buildCommand(player);
                return;
            case "menu":
                // TODO
                String menu = args[2];
                Menu m = WhaleSkyWars.getInstance().getMenuManager().of(menu);
                if (m == null) {
                    TextUtil.error(null, "Menu not found!", false);
                    return;
                }
                m.open(player);
                return;
            case "reload":
                reloadCommand(player);
                return;
        }
    }

    private void setLobbyCommand(IGamePlayer player) {
        player.getPlayer().ifPresent(bukkitPlayer -> {
            Location location = bukkitPlayer.getLocation();
            WhaleSkyWars.getInstance().setSpawn(location);
            player.sendMessage(Messages.GENERAL_LOBBY_SET.toString());
        });
    }

    private void buildCommand(IGamePlayer player) {
        CacheManager cacheManager = WhaleSkyWars.getInstance().getCacheManager();

        if (cacheManager.isBuilding(player.getUniqueId())) {
            cacheManager.removeBuilding(player.getUniqueId());
            player.sendMessage(Messages.GENERAL_BUILDING_DISABLED.toString());
        } else {
            cacheManager.addBuilding(player.getUniqueId());
            player.sendMessage(Messages.GENERAL_BUILDING_ENABLED.toString());
        }
    }

    private void reloadCommand(IGamePlayer player) {
        WhaleSkyWars.getInstance().reload();
        player.sendMessage(Messages.GENERAL_RELOADED.toString());
    }

}
