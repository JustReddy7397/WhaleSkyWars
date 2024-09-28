package ga.justreddy.wiki.whaleskywars.commands;

import ga.justreddy.wiki.whaleskywars.commands.impl.*;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class BaseCommand implements TabExecutor {

    private final Map<String, SkyWarsCommand> commands;

    public BaseCommand() {
        this.commands = new HashMap<>();
        registerCommands(new GameCommand(), new CageCommand(), new AdminCommand()
                , new LobbyCommand(), new SignCommand());
    }

    public void registerCommands(SkyWarsCommand... commands) {
        for (SkyWarsCommand command : commands) {
            this.commands.put(command.getName(), command);
            for (String alias : command.getAliases()) {
                this.commands.put(alias, command);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command whoCares, String dumbAndWhoCares, String[] args) {

        if (args.length == 0) {
            TextUtil.sendMessage(sender, "&bWhaleSkyWars &7v1.0.0 &8- &7Made by &bJustReddy");
            if (sender.hasPermission("whaleskywars.admin")) {
                TextUtil.sendMessage(sender, "&7Use &b/ws help &7to see all available commands.");
            }
            return true;
        }

        SkyWarsCommand command = commands.get(args[0]);
        if (command == null) {
            TextUtil.errorCommand(sender, "&cUnknown command.");
            return true;
        }

        if (command.isPlayerOnly() && !(sender instanceof Player)) {
            TextUtil.errorCommand(sender, "&cThis command is only available for players.");
            return true;
        }

        if (!sender.hasPermission(command.getPermission())) {
            TextUtil.errorCommand(sender, "&cYou do not have permission to execute this command.");
            return true;
        }

        command.execute(new CommandRunner(sender), args);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd,
                                      String label, String[] args) {
        List<String> completions = List.of();

        if (args.length == 1) {
            completions = new ArrayList<>(commands.keySet());
        } else if (args.length > 1) {
            SkyWarsCommand command = commands.get(args[0]);
            if (command != null) {
                completions = command.onTabComplete(new CommandRunner(sender), args);
            }
        }

        return completions;
    }
}
