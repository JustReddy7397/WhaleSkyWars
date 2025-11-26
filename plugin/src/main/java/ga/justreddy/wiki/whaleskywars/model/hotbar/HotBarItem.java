package ga.justreddy.wiki.whaleskywars.model.hotbar;

import de.tr7zw.changeme.nbtapi.NBTItem;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.manager.HotBarManager;
import ga.justreddy.wiki.whaleskywars.shared.config.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

/**
 * @author JustReddy
 */
public abstract class HotBarItem implements Listener {

    protected final HotBarManager manager;
    protected final ItemStack itemStack;
    protected final ConfigurationSection section;
    protected final String key;
    protected final int slot;
    protected final HotBarType type;
    protected final boolean allowMovement;
    protected final List<String> actions;

    public HotBarItem(HotBarManager manager, ConfigurationSection section, ItemStack item, int slot, String key, HotBarType type, List<String> actions) {
        this.manager = manager;
        this.section = section;
        this.slot = slot;
        this.key = key;
        this.type = type;
        this.actions = actions;
        ItemStack nbtItem = WhaleSkyWars.getInstance().getNms()
                .setNbtValue(item, "hotbar", key);
        this.allowMovement = section.getBoolean("allow-movement");
        this.itemStack = nbtItem;
        Bukkit.getPluginManager().registerEvents(this, WhaleSkyWars.getInstance());
    }

    public void giveItem(Player player) {
        ItemStack newItem = itemStack.clone();
        if (section != null && section.isSet("username")) {
            newItem = new ItemStackBuilder(newItem).setSkullOwner(player.getName()).build();
        }

        player.getInventory().setItem(slot, newItem);
    }

    public void removeItem(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack item = inventory.getItem(slot);

        if (item != null && new NBTItem(item).getString("hotbarItem").equals(key)) {
            inventory.remove(inventory.getItem(slot));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (allowMovement) return;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;
        String value = (String) WhaleSkyWars.getInstance().getNms().getNbtValue(clicked, "hotbar");
        if (event.getSlot() == slot && value != null && value.equals(key)){
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onHotBarItemInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item.getType() == Material.AIR) return;
        String value = (String) WhaleSkyWars.getInstance().getNms().getNbtValue(item, "hotbar");
        if (value == null || !value.equals(key)) return;

        onInteract(player);
    }

    public abstract void onInteract(Player player);

}
