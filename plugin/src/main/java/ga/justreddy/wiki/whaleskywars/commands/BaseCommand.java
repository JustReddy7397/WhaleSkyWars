package ga.justreddy.wiki.whaleskywars.commands;

import ga.justreddy.wiki.whaleskywars.commands.impl.GameCommand;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class BaseCommand implements CommandExecutor {

    private final Map<String, SkyWarsCommand> commands;

    public BaseCommand() {
        this.commands = new HashMap<>();
        registerCommands(new GameCommand());
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
            TextUtil.sendMessage(sender, "&cUnknown command.");
            return true;
        }

        if (command.isPlayerOnly() && !(sender instanceof Player)) {
            TextUtil.sendMessage(sender, "&cThis command is only available for players.");
            return true;
        }

        if (!sender.hasPermission(command.getPermission())) {
            TextUtil.sendMessage(sender, "&cYou do not have permission to execute this command.");
            return true;
        }

        command.execute(new CommandRunner(sender), args);

        return true;
    }
}
