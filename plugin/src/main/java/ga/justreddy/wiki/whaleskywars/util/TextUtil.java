package ga.justreddy.wiki.whaleskywars.util;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.support.bungee.Bungee;
import ga.justreddy.wiki.whaleskywars.util.iridium.IridiumColorAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

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

    public static void sendBungeeConsoleMessages(String... messages) {
        for (String message : messages) {
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%line%", CONSOLE_LINE)));
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

    public static void errorBungee(Throwable throwable, String description, boolean disable) {
        if (throwable != null) throwable.printStackTrace();

        sendBungeeConsoleMessages(
                "&4%line%",
                "&cAn internal error has occurred in " + Bungee.getInstance().getDescription().getName() + "!",
                "&cContact the plugin author if you cannot fix this error.",
                "&cDescription: &6" + description,
                "&4%line%"
        );
        if (disable) {
            Bungee.getInstance().onDisable();
        }
    }

    public static void errorCommand(CommandSender sender, String description) {
        sendMessages(sender, "&4%line%", "&cAn error occurred while running this command", "&cDescription: &6" + description, "&4%line%");
    }

    public static String uppercaseFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String nameOf(XMaterial material) {
        String materialName = material.name();

        if (materialName.contains("_")) {
            String[] split = materialName.split("_");

            StringBuilder builder = new StringBuilder();
            for (String inSplit : split) {
                builder.append(uppercaseFirstLetter(inSplit.toLowerCase())).append(" ");
            }

            return builder.substring(0, builder.length() - 1);
        }
        return uppercaseFirstLetter(materialName.toLowerCase());
    }

    public static String nameOf(Enchantment enchantment) {
        return WhaleSkyWars.getInstance().getNms().nameOfEnchantment(enchantment);
    }

    public static Enchantment valueOf(String value) {
        return WhaleSkyWars.getInstance().getNms().valueOfEnchantment(value);
    }

}
