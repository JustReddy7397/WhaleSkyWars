package ga.justreddy.wiki.whaleskywars.model.menu.menus;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.shared.config.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.model.menu.Menu;
import ga.justreddy.wiki.whaleskywars.util.ItemStackBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

/**
 * @author JustReddy
 */
public class OtherMenu extends Menu {

    public OtherMenu(TempConfig config) {
        super(config);
    }

    @Override
    public String setPlaceholders(String text, IGamePlayer player) {

        if (WhaleSkyWars.getInstance().isHooked("PlaceholderAPI") && player.getPlayer().isPresent()) {
            text = PlaceholderAPI.setPlaceholders(player.getPlayer().get(), text);
        }

        return text;
    }

    @Override
    public void setMenuItems(IGamePlayer player) {
        ConfigurationSection section = config.getSection("buttons");
        System.out.println(section.data());
        for (String path : section.keys()) {
            ConfigurationSection button = section.getSection(path);
            System.out.println(button.data());
            ItemStackBuilder builder = ItemStackBuilder.getItemStack(button, player)
                    .clearLore();
            List<String> lore = button.getStringList("lore");
            lore.stream().map(s -> setPlaceholders(s, player)).forEach(builder::addLore);
            if (button.get("position") instanceof Number) {
                inventory.setItem(button.getInteger("position"), builder.build());
            } else if (button.get("position") instanceof List) {
                button.getIntegerList("position").forEach(i -> {
                    inventory.setItem(i, builder.build());
                });
            }
        }
    }

    @Override
    public void onClick(IGamePlayer player, InventoryClickEvent event) {
        player.getPlayer().ifPresent(bukkitPlayer -> {
            ConfigurationSection section = config.getSection("buttons");
            for (String path : section.keys()) {
                ConfigurationSection button = section.getSection(path);

                if (button.get("position") instanceof Number) {
                    if (button.isSet("permission")) {
                        if (!bukkitPlayer.hasPermission(button.getString("permission"))) {
                            return;
                        }
                    }
                    List<String> actions = button.getStringList("actions");
                    WhaleSkyWars.getInstance().getActionManager().onAction(player, actions);
                    break;
                }
            }
        });
    }

}
