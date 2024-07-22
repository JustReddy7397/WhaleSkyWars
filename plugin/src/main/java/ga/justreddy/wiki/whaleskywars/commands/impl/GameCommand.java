package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
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
                    " &b/ws game setdisplayname <name> &7- &3Set the display name",
                    "&b/ws game setminimun <amount> &7- &3Set the minimum amount of players",
                    "&b/ws game setteamsize <amount> &7- &3Set the team size",
                    "&b/ws game setwaiting &7- &3Set the waiting spawn",
                    "&b/ws game setspectator &7- &3Set the spectator spawn",
                    "&b/ws game bounds <lobby/game> <high/low> &7- &3Set the bounds",
                    "&b/ws island create &7- &3Creates an island",
                    "&b/ws island add <id> balloon &7- &3Sets the balloon spawn for the specified island",
                    "&b/ws island add <id> spawn &7- &3Sets the spawn for the specified island",
                    "&b/ws island remove <id> &7- &3Removes the specified island",
                    "&b/ws save &7- &3Saves the game",
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
        WhaleSkyWars.getInstance().getGameCreator()
                .createGame(gamePlayer, name);
    }

    private void setDisplayName(IGamePlayer gamePlayer, String[] args) {
        if (args.length != 3) {
            gamePlayer.sendMessage("&cUsage: /ws game setdisplayname <name>");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            buffer.append(args[i]).append(" ");
        }
        WhaleSkyWars.getInstance().getGameCreator()
                .setDisplayName(gamePlayer, buffer.toString());
    }

    private void setMinimumPlayers(IGamePlayer gamePlayer, String[] args) {
        if (args.length != 3) {
            gamePlayer.sendMessage("&cUsage: /ws game setminimum <amount>");
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            // TODO
            gamePlayer.sendMessage("&cInvalid number");
            return;
        }

        WhaleSkyWars.getInstance().getGameCreator()
                .setMinimumPlayers(gamePlayer, amount);
    }

    private void setTeamSize(IGamePlayer gamePlayer, String[] args) {
        if (args.length != 3) {
            gamePlayer.sendMessage("&cUsage: /ws game setteamsize <amount>");
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            // TODO
            gamePlayer.sendMessage("&cInvalid number");
            return;
        }

        WhaleSkyWars.getInstance().getGameCreator()
                .setTeamSize(gamePlayer, amount);
    }

    private void setWaitingSpawn(IGamePlayer gamePlayer) {
        WhaleSkyWars.getInstance().getGameCreator()
                .setWaitingSpawn(gamePlayer);
    }

    private void setSpectatorSpawn(IGamePlayer gamePlayer) {
        WhaleSkyWars.getInstance().getGameCreator()
                .setSpectatorSpawn(gamePlayer);
    }

    private void setIslandStuff(IGamePlayer player, String[] args) {
        if (args.length == 2) {
            player.sendMessages(
                    "&7&m----------------------------------------",
                    "&bWhaleSkyWars &7v1.0.0 &8- &7Made by &bJustReddy",
                    "&b/ws island create &7- &3Creates an island",
                    "&b/ws island add <id> balloon &7- &3Sets the balloon spawn for the specified island",
                    "&b/ws island add <id> spawn &7- &3Sets the spawn for the specified island",
                    "&b/ws island remove <id> &7- &3Removes the specified island",
                    "&7&m----------------------------------------"
            );
            return;
        }

        if (args[2].equalsIgnoreCase("create")) {
            WhaleSkyWars.getInstance().getGameCreator()
                    .createIsland(player);
            return;
        }

        if (args[2].equalsIgnoreCase("add")) {
            if (args.length < 5) {
                player.sendMessage("&cUsage: /ws island add <id> <balloon/spawn>");
                return;
            }

            int id;

            try {
                id = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                player.sendMessage("&cInvalid number");
                return;
            }

            if (args[4].equalsIgnoreCase("balloon")) {
                WhaleSkyWars.getInstance().getGameCreator()
                        .setIslandBalloon(player, id);
                return;
            }

            if (args[4].equalsIgnoreCase("spawn")) {
                WhaleSkyWars.getInstance().getGameCreator()
                        .setIslandSpawn(player, id);
                return;
            }

            player.sendMessage("&cUsage: /ws island add <id> <balloon/spawn>");
            return;
        }

        if (args[2].equalsIgnoreCase("remove")) {
            if (args.length != 4) {
                player.sendMessage("&cUsage: /ws island remove <id>");
                return;
            }

            int id;

            try {
                id = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                player.sendMessage("&cInvalid number");
                return;
            }

            WhaleSkyWars.getInstance().getGameCreator()
                    .clearIsland(player, id);
            return;
        }

    }

    private void save(IGamePlayer player) {
        WhaleSkyWars.getInstance().getGameCreator()
                .save(player);
    }

}
