package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;

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
                    "&b/ws game setdisplayname <name> &7- &3Set the display name",
                    "&b/ws game setminimun <amount> &7- &3Set the minimum amount of players",
                    "&b/ws game setteamsize <amount> &7- &3Set the team size",
                    "&b/ws game setwaiting &7- &3Set the waiting spawn",
                    "&b/ws game setspectator &7- &3Set the spectator spawn",
                    "&b/ws game island create &7- &3Creates an island",
                    "&b/ws game island set <id> balloon &7- &3Sets the balloon spawn for the specified island",
                    "&b/ws game island add <id> chest <type> &7- &3Add the chest for the specified island",
                    "&b/ws game island remove <id> chest <chestId> &7- &3Removes the chest for the specified island",
                    "&b/ws game island set <id> spawn &7- &3Sets the spawn for the specified island",
                    "&b/ws game island remove <id> &7- &3Removes the specified island",
                    "&b/ws game chest add <type> &7- &3Adds a chest to the game",
                    "&b/ws game chest remove <type> &7- &3Removes a chest from the game",
                    "&b/ws game save &7- &3Saves the game",
                    "&7&m----------------------------------------"
            );
            return;
        }

        switch (args[1].toLowerCase()) {
            case "join":
                IAction action = WhaleSkyWars.getInstance().getActionManager().of("JOIN");
                if (action == null) return;
                action.onAction(WhaleSkyWars.getInstance(), gamePlayer, "");
                break;
            case "create":
                createGame(gamePlayer, args);
                break;
            case "setdisplayname":
                setDisplayName(gamePlayer, args);
                break;
            case "setminimum":
                setMinimumPlayers(gamePlayer, args);
                break;
            case "setteamsize":
                setTeamSize(gamePlayer, args);
                break;
            case "setwaiting":
                setWaitingSpawn(gamePlayer);
                break;
            case "setspectator":
                setSpectatorSpawn(gamePlayer);
                break;
            case "island":
                setIslandStuff(gamePlayer, args);
                break;
            case "save":
                save(gamePlayer, args);
                break;
            case "chest":
                chest(gamePlayer, args);
                break;
            case "status":
                status(gamePlayer);
            case "next":
                // TODO fix
                IGame game = gamePlayer.getGame();
                game.goToNextPhase();
                break;
            case "leave":
                IGame game2 = gamePlayer.getGame();
                game2.onGamePlayerLeave(gamePlayer, false, true);
                break;
            default:
                gamePlayer.sendMessage("&cUnknown sub-command");
                break;
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
                    "&b/ws game island create <id> &7- &3Creates an island",
                    "&b/ws game island set <id> balloon &7- &3Sets the balloon spawn for the specified island",
                    "&b/ws game island add <id> chest <type> &7- &3Add the chest for the specified island",
                    "&b/ws game island remove <id> chest <chestId> &7- &3Removes the chest for the specified island",
                    "&b/ws game island set <id> spawn &7- &3Sets the spawn for the specified island",
                    "&b/ws game island remove <id> &7- &3Removes the specified island",
                    "&7&m----------------------------------------"
            );
            return;
        }

        if (args[2].equalsIgnoreCase("create")) {

            if (args.length != 4) {
                player.sendMessage("&cUsage: /ws game island create <id>");
                return;
            }

            int id = -1;

            try {
                id = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                player.sendMessage("&cUsage: /ws game island create <id>");
                return;
            }

            if (id < 1) {
                // TODO
                player.sendMessage("&cUsage: /ws game island create <id>");
                return;
            }

            if (id > 100) {
                // TODO
                player.sendMessage("&cUsage: /ws game island create <id>");
                return;
            }

            WhaleSkyWars.getInstance().getGameCreator()
                    .createIsland(player, id);

            return;
        }

        if (args[2].equalsIgnoreCase("set")) {
            if (args.length < 5) {
                player.sendMessage("&cUsage: /ws game island set <id> <balloon/spawn>");
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

            player.sendMessage("&cUsage: /ws island set <id> <balloon/spawn>");
            return;
        }

        if (args[2].equalsIgnoreCase("remove")) {
            if (args.length < 4) {
                player.sendMessage("&cUsage: /ws island remove <id> [chest]");
                return;
            }
            if (args.length > 4) {
                if (!args[4].equalsIgnoreCase("chest")) {
                    player.sendMessage("&cUsage: /ws island remove <id> chest <chestId>");
                    return;
                }

                int id;

                try {
                    id = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    player.sendMessage("&cInvalid number");
                    return;
                }

                int chestId;

                try {
                    chestId = Integer.parseInt(args[5]);
                } catch (NumberFormatException e) {
                    player.sendMessage("&cInvalid number");
                    return;
                }

                WhaleSkyWars.getInstance().getGameCreator()
                        .removeIslandChest(player, id, chestId);

            } else {

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

    }

    private void save(IGamePlayer player, String[] args) {

        if (args.length != 3) {
            player.sendMessage("&cUsage: /ws game save <enable=true/false>");
            return;
        }

        boolean enable = Boolean.parseBoolean(args[2]);

        WhaleSkyWars.getInstance().getGameCreator()
                .save(player, enable);
    }

    private void chest(IGamePlayer player, String[] args) {

        if (args.length != 4) {
            player.sendMessage("&cUsage: /ws game chest <add/remove> <type>");
            return;
        }

        if (args[2].equalsIgnoreCase("add")) {
            WhaleSkyWars.getInstance().getGameCreator()
                    .addChest(player, args[3]);
            return;
        } else if (args[2].equalsIgnoreCase("remove")) {
            WhaleSkyWars.getInstance().getGameCreator()
                    .removeChest(player);
            return;
        } else {
            player.sendMessage("&cUsage: /ws game chest <add/remove> <type>");
        }

    }

    private void status(IGamePlayer player) {
        WhaleSkyWars.getInstance().getGameCreator()
                .status(player);
    }


}
