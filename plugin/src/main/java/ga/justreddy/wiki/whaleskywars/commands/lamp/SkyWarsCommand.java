package ga.justreddy.wiki.whaleskywars.commands.lamp;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSign;
import ga.justreddy.wiki.whaleskywars.commands.CommandRunner;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommandHolder;
import ga.justreddy.wiki.whaleskywars.manager.GameManager;
import ga.justreddy.wiki.whaleskywars.manager.cache.CacheManager;
import ga.justreddy.wiki.whaleskywars.model.creator.CageCreator;
import ga.justreddy.wiki.whaleskywars.model.creator.GameCreator;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.model.game.GameSign;
import ga.justreddy.wiki.whaleskywars.model.lamp.suggestions.GameNameSuggestionProvider;
import ga.justreddy.wiki.whaleskywars.util.CommandGrabber;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import ga.justreddy.wiki.whaleskywars.util.pages.InteractiveHelpMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.w3c.dom.Text;
import revxrsal.commands.Lamp;
import revxrsal.commands.LampVisitor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.help.Help;
import revxrsal.commands.process.MessageSender;

import java.util.List;

/**
 * @author JustReddy
 */
@Command("wsw")

public class SkyWarsCommand implements LampVisitor<BukkitCommandActor>, MessageSender<BukkitCommandActor, String> {

    private static final int ENTRIES_PER_PAGE = 8;

    @Dependency
    private GameManager gameManager;
    @Dependency
    private CacheManager cacheManager;
    @Dependency
    private GameCreator<Hologram> gameCreator;
    @Dependency
    private CageCreator cageCreator;

    @Command("wsw help")
    @Description("Shows the help menu")
    @Usage("/ws help")
    public void help(BukkitCommandActor actor, @Range(min = 1) @Default("1") @Named("page") int page) {
        if (!actor.isPlayer()) {
            TextUtil.sendConsoleMessage("&cYou must be a player to use this command!");
            return;
        }

        List<SkyWarsCommandHolder> holders = CommandGrabber.grabCommands();
        SkyWarsCommandHolder helpCommandHolder = holders.stream().filter(holder -> holder.getName().orElse("").equalsIgnoreCase("wsw help")).findFirst().orElse(null);
        InteractiveHelpMenu.builder().build().sendInteractiveMenu(actor, holders, page, helpCommandHolder);
    }

    @Command("wsw reload")
    @Description("Reloads the plugin")
    @Usage("/ws reload")
    public void reload(BukkitCommandActor actor) {
        TextUtil.sendMessage(actor.sender(), "&aPlugin reloaded!");
    }


    @Override
    public void visit(@NotNull Lamp<BukkitCommandActor> lamp) {
        lamp.register(new SignCommand());
    }

    @Override
    public void send(@NotNull BukkitCommandActor actor, @NotNull String string) {
        CommandSender sender = actor.sender();
        TextUtil.sendMessage(sender,
                "&l&dWhaleSkyWars &8» &7v" + WhaleSkyWars.getInstance().getDescription().getVersion());
        TextUtil.sendMessage(sender, "&7Made by &dJustReddy");
        TextUtil.sendMessage(sender, "&7Use &d/ws help &7for a list of commands");
    }

    @Command("wsw sign")
    @Description("Manage game signs")
    @Usage("/ws sign <create/remove>")
    public static class SignCommand {

        @CommandPlaceholder
        public void placeholder(BukkitCommandActor actor) {
            CommandSender sender = actor.sender();
            TextUtil.sendMessage(sender,
                    "&l&dWhaleSkyWars &8» &7v" + WhaleSkyWars.getInstance().getDescription().getVersion());
            TextUtil.sendMessage(sender, "&7Made by &dJustReddy");
            TextUtil.sendMessage(sender, "&7Use &d/ws sign help &7for a list of sign commands");

        }

        @Subcommand("help")
        @Description("Shows the help menu for signs")
        @Usage("/ws sign help")
        public void help(BukkitCommandActor actor, @Range(min = 1) @Default("1") @Named("page") int page) {
            if (!actor.isPlayer()) {
                TextUtil.sendConsoleMessage("&cYou must be a player to use this command!");
                return;
            }

            List<SkyWarsCommandHolder> holders = CommandGrabber.grabFromSpecificInnerClass(SignCommand.class);
            holders.forEach(holder -> {
                String name = holder.getName().orElse("");
                holder.setName("wsw sign " + name);
            });
            SkyWarsCommandHolder helpCommandHolder = holders.stream().filter(holder -> holder.getName().orElse("").equalsIgnoreCase("help")).findFirst().orElse(null);
            InteractiveHelpMenu.builder().build().sendInteractiveMenu(actor, holders, page, helpCommandHolder);
        }

        @Subcommand("create")
        public void createSign(Player player,
                               @SuggestWith(GameNameSuggestionProvider.class) IGame game) {
            IGamePlayer gamePlayer = GamePlayer.get(player.getUniqueId());
            Block block = WhaleSkyWars.getInstance().getNms().getTargetBlock(player, 5);

            if (block == null || !(block.getState() instanceof Sign)) {
                gamePlayer.sendMessages("&cYou are not looking at a sign!");
                return;
            }

            Location location = block.getLocation();
            IGameSign sign = new GameSign(game.getName(), location);
            WhaleSkyWars.getInstance().getSignManager().addSign(sign);
            gamePlayer.sendMessages("&aSign created!");
        }

        @Subcommand("remove")
        public void removeSign(Player player) {
            player.sendMessage("test");
        }

    }
}
