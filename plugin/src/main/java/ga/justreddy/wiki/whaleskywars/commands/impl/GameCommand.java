package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JustReddy
 */
public class GameCommand implements SkyWarsCommand {

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public String getDescription() {
        return "Commands to create/edit games";
    }

    @Override
    public String getUsage() {
        return "/ws game";
    }

    @Override
    public String getPermission() {
        return "whaleskywars.admin.game";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>(Collections.singletonList("arena"));
    }

    @Override
    public void execute(CommandRunner runner, String[] args) {
        IGamePlayer gamePlayer = runner.asGamePlayer();

        if (args.length == 1) {
            gamePlayer.sendMessages(
                    "&7&m----------------------------------------",
                    "&bWhaleSkyWars &7v1.0.0 &8- &7Made by &bJustReddy",
                    "&b/ws game create <name> &7- &3Create a new game",
                    "&b/ws game setminimun <amount> &7- &3Set the minimum amount of players",
                    "&b/ws game setteamsize <amount> &7- &3Set the team size",
                    "&b/ws game setwaiting &7- &3Set the waiting spawn",
                    "&b/ws game setspectator &7- &3Set the spectator spawn",
                    "&b/ws game bounds <lobby/game> <high/low> &7- &3Set the bounds",
                    "&b/ws addisland &7- &3Add an island",
                    "&b/ws addballoon &7- &3Add a balloon (should be done after adding an island)",
                    "&b/ws removeisland &7- &3Remove an island",
                    "&7&m----------------------------------------"
            );
            return;
        }
    }

    private void createGame(IGamePlayer gamePlayer, String[] args) {
        if (args.length != 3) {
            gamePlayer.sendMessage("&cUsage: /ws game create <name>");
            return;
        }

        String name = args[2];

    }

}
