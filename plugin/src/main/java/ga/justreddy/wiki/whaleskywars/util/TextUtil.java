package ga.justreddy.wiki.whaleskywars.util;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.util.iridium.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author JustReddy
 */
public class TextUtil {

    public static final String CHAT_LINE = "&m-----------------------------------------------------";
    public static final String CONSOLE_LINE = "*-----------------------------------------------------*";
    public final static String LORE_LINE = "&m--------------------------";


    public static String color(String text) {
        return IridiumColorAPI.process(text);
    }

    public static List<String> color(List<String> text) {
        return IridiumColorAPI.process(text);
    }

    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(color(message));
    }

    public static void sendMessages(CommandSender player, List<String> messages) {
        messages.forEach(message -> sendMessage(player, message));
    }

    public static void sendMessages(CommandSender player, String... messages) {
        for (String message : messages) {
            sendMessage(player, message);
        }
    }

    public static void sendMessages(List<CommandSender> players, String message) {
        players.forEach(player -> sendMessage(player, message));
    }

    public static void sendMessages(List<CommandSender> players, List<String> messages) {
        players.forEach(player -> sendMessages(player, messages));
    }

    public static void sendMessages(List<CommandSender> players, String... messages) {
        players.forEach(player -> sendMessages(player, messages));
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message.replaceAll("%line%", CONSOLE_LINE)));
    }

    public static void sendConsoleMessages(List<String> messages) {
        messages.forEach(TextUtil::sendConsoleMessage);
    }

    public static void sendConsoleMessages(String... messages) {
        for (String message : messages) {
            sendConsoleMessage(message);
        }
    }

    public static void error(Throwable throwable, String description, boolean disable) {
        if (throwable != null) throwable.printStackTrace();

        sendConsoleMessages(
                "&4%line%",
                "&cAn internal error has occurred in " + WhaleSkyWars.getInstance().getDescription().getName() + "!",
                "&cContact the plugin author if you cannot fix this error.",
                "&cDescription: &6" + description,
                "&4%line%"
        );

        if (disable && Bukkit.getPluginManager().isPluginEnabled(WhaleSkyWars.getInstance())) {
            Bukkit.getPluginManager().disablePlugin(WhaleSkyWars.getInstance());
        }
    }

    public static void errorCommand(CommandSender sender, String description) {
        sendMessages(sender, "&4%line%", "&cAn error occurred while running this command", "&cDescription: &6" + description, "&4%line%");
    }

}
