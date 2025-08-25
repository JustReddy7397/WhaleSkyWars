package ga.justreddy.wiki.whaleskywars.manager;

import com.google.common.collect.ImmutableList;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSign;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.model.game.GameSign;
import ga.justreddy.wiki.whaleskywars.util.LocationUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class SignManager {

    private final Map<String, IGameSign> signs;

    public SignManager() {
        this.signs = new HashMap<>();
    }

    public void start() {
        TomlConfig config = WhaleSkyWars.getInstance().getSignsConfig();
        ConfigurationSection section = config.getSection("signs");
        for (String key : section.keys()) {
            ConfigurationSection signSection = section.getSection(key);
            IGameSign sign = new GameSign(key.toLowerCase(), LocationUtil.getLocation(signSection.getString("location")));
            signs.put(key.toLowerCase(), sign);
        }
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoaded " + signs.size() + " signs.");
    }

    public void die() {
        signs.clear();
    }

    public void addSign(IGameSign sign) {
        signs.put(sign.getId(), sign);
        TomlConfig config = WhaleSkyWars.getInstance().getSignsConfig();
        ConfigurationSection section = config.getSection("signs");
        ConfigurationSection signSection = section.getSection(sign.getId());
        signSection.set("location", LocationUtil.toLocation(sign.getLocation()));
        config.save();
    }

    public boolean hasSign(String id) {
        return signs.containsKey(id);
    }

    public boolean hasSign(Location location) {
        return signs.values().stream().anyMatch(sign -> LocationUtil.equalsBlock(sign.getLocation(), location));
    }

    public ImmutableList<IGameSign> copyOf() {
        return ImmutableList.copyOf(signs.values());
    }


    public void removeSign(Location location) {
        IGameSign sign = signs.values().stream().filter(s -> LocationUtil.equalsBlock(s.getLocation(), location)).findFirst().orElse(null);
        if (sign != null) {
            signs.remove(sign.getId());
            TomlConfig config = WhaleSkyWars.getInstance().getSignsConfig();
            ConfigurationSection section = config.getSection("signs");
            section.set(sign.getId(), null);
            config.save();
        }
        location.getBlock().setType(Material.AIR);
    }
}
