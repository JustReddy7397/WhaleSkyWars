package ga.justreddy.wiki.whaleskywars.commands;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author JustReddy
 */
public class CommandRunner {

    private final CommandSender sender;

    public CommandRunner(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender asSender() {
        return sender;
    }

    public Player asPlayer() {
        return (Player) sender;
    }

    public IGamePlayer asGamePlayer() {
        return GamePlayer.get(asPlayer().getUniqueId());
    }

}
