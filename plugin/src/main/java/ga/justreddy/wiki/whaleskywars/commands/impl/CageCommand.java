package ga.justreddy.wiki.whaleskywars.commands.impl;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommand;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommandHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
public class CageCommand implements SkyWarsCommand {

    @Override
    public String getName() {
        return "cage";
    }

    @Override
    public String getDescription() {
        return "Command for creating cages";
    }

    @Override
    public String getUsage() {
        return "/ws cage";
    }

    @Override
    public String getPermission() {
        return "whaleskywars.admin.cage";
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
        IGamePlayer gamePlayer = runner.asGamePlayer();

        if (args.length == 1) {
            gamePlayer.sendMessages(
                    "&7&m----------------------------------------",
                    "&bWhaleSkyWars &7v1.0.0 &8- &7Made by &bJustReddy",
                    "&b/ws cage create <name> &7- &3Create a new cage",
                    "&b/ws cage setsmall - &7- &3Sets the small cage for solos",
                    "&b/ws cage setbig - &7- &3Sets the medium cage for teams",
                    "&b/ws cage setcost <cost> - &7- &3Sets the cost of the cage",
                    "&b/ws cage save - &7- &3Saves the cage",
                    "&7&m----------------------------------------"
            );
            return;
        }

        switch (args[1]) {
            case "create":
                if (args.length != 3) {
                    gamePlayer.sendMessage("&cUsage: /ws cage create <name>");
                    return;
                }
                WhaleSkyWars.getInstance().getCageCreator()
                        .create(gamePlayer, args[2]);
                break;
            case "setsmall":
                WhaleSkyWars.getInstance().getCageCreator()
                        .saveSmallStructure(gamePlayer);
                break;
            case "setbig":
                WhaleSkyWars.getInstance().getCageCreator()
                        .saveBigStructure(gamePlayer);
                break;
            case "setcost":
                if (args.length != 3) {
                    gamePlayer.sendMessage("&cUsage: /ws cage setcost <cost>");
                    return;
                }
                WhaleSkyWars.getInstance().getCageCreator()
                        .setCost(gamePlayer, Integer.parseInt(args[2]));
                break;
            case "save":
                WhaleSkyWars.getInstance().getCageCreator()
                        .finish(gamePlayer);
                break;
            default:
                gamePlayer.sendMessage("&cUnknown subcommand.");
                break;
        }

    }
}
