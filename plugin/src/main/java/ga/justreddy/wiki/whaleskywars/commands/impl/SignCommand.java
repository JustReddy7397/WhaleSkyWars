package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSign;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;
import ga.justreddy.wiki.whaleskywars.model.game.GameSign;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class SignCommand implements SkyWarsCommand {
    @Override
    public String getName() {
        return "sign";
    }

    @Override
    public String getDescription() {
        return "Commands to manage signs";
    }

    @Override
    public String getUsage() {
        return "/ws sign <create/delete>";
    }

    @Override
    public String getPermission() {
        return "whaleskywars.admin.sign";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public void execute(CommandRunner runner, String[] args) {
        IGamePlayer gamePlayer = runner.asGamePlayer();
        if (args.length == 1) {
            gamePlayer.sendMessages(
                    "&7&m----------------------------------------",
                    "&bWhaleSkyWars &7v1.0.0 &8- &7Made by &bJustReddy",
                    "&b/ws sign create <gameName> &7- &3Create a new sign",
                    "&b/ws sign delete &7- &3Delete a sign",
                    "&7&m----------------------------------------"
            );
        }
        Player bukkitPlayer = runner.asPlayer();
        switch (args[1].toLowerCase()) {
            case "create":
                createSign(gamePlayer, bukkitPlayer, args);
                break;
            case "delete":
                break;
            default:
                gamePlayer.sendMessages("&cUnknown subcommand.");
                break;
        }
    }

    private void createSign(IGamePlayer gamePlayer, Player bukkitPlayer, String[] args) {
        // TODO

        if (args.length != 3) {
            gamePlayer.sendMessages("&cUsage: /ws sign create <gameName>");
            return;
        }

        String gameName = args[2];

        Block block = WhaleSkyWars.getInstance().getNms().getTargetBlock(bukkitPlayer, 5);

        if (block == null) {
            gamePlayer.sendMessages("&cYou are not looking at a sign!");
            return;
        }

        IGame game = WhaleSkyWars.getInstance().getGameManager().getGameByName(gameName);

        if (game == null) {
            gamePlayer.sendMessages("&cGame not found!");
            return;
        }

        Location location = block.getLocation();
        IGameSign sign = new GameSign(gameName, location);
        WhaleSkyWars.getInstance().getSignManager().addSign(sign);
        gamePlayer.sendMessages("&aSign created!");
    }

    @Override
    public List<String> onTabComplete(CommandRunner commandRunner, String[] args) {
        List<String> completions = List.of();
        if (args.length == 2) {
            completions = List.of("create", "delete");
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("create")) {
                completions = WhaleSkyWars.getInstance().getGameManager().getGames().stream()
                        .map(IGame::getName)
                        .collect(Collectors.toList());
            } else if (args[1].equalsIgnoreCase("delete")) {
                completions = WhaleSkyWars.getInstance().getSignManager().copyOf().stream()
                        .map(IGameSign::getId)
                        .collect(Collectors.toList());
            }
        }
        return completions;
    }
}
