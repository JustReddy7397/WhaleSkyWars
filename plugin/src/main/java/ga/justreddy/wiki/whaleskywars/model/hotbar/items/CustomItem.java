package ga.justreddy.wiki.whaleskywars.model.hotbar.items;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.manager.HotBarManager;
import ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.model.hotbar.HotBarItem;
import ga.justreddy.wiki.whaleskywars.model.hotbar.HotBarType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author JustReddy
 */
public class CustomItem extends HotBarItem {

    public CustomItem(HotBarManager manager, ConfigurationSection section, ItemStack item, int slot, String key, HotBarType type) {
        super(manager, section, item, slot, key, type, section.getStringList("actions"));
    }

    @Override
    public void onInteract(Player player) {
        IGamePlayer gamePlayer = GamePlayer.get(player.getUniqueId());
        if (gamePlayer == null) return;
        WhaleSkyWars.getInstance().
                getActionManager()
                .onAction(gamePlayer, actions);
    }
}
