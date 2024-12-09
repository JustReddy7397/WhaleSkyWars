package ga.justreddy.wiki.whaleskywars.util;

import com.fasterxml.jackson.databind.type.PlaceholderForType;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

/**
 * @author JustReddy
 */
public class PrefixUtil {

    public static String getColorByRank(Player player) {
        List<String> ranks = WhaleSkyWars.getInstance().getSettingsConfig().getStringList("game-options.tablist-ranks");
        String colorToUse = "";
        for (String rank : ranks) {
            String primaryGroup = WhaleSkyWars.getInstance().getPermission()
                    .getPrimaryGroup(player);
            String[] split = rank.split(";");
            if (primaryGroup.equalsIgnoreCase(split[0])) {
                colorToUse = split[1];
                break;
            }
        }
        return colorToUse;
    }

    public static int getPriority(Player player) {
        List<String> ranks = WhaleSkyWars.getInstance().getSettingsConfig().getStringList("game-options.tablist-ranks");
        int priority = 0;
        for (String rank : ranks) {
            String primaryGroup = WhaleSkyWars.getInstance().getPermission()
                    .getPrimaryGroup(player);
            String[] split = rank.split(";");
            if (primaryGroup.equalsIgnoreCase(split[0])) {
                priority = Integer.parseInt(split[2]);
                break;
            }
        }
        return priority;
    }

}
