package ga.justreddy.wiki.whaleskywars.model.menu;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author JustReddy
 */
public abstract class Menu implements InventoryHolder {

    protected final TempConfig config;
    private BukkitTask task = null;

    public Menu(TempConfig config) {
        this.config = config;
    }

    protected Inventory inventory;

    public String getTitle() {
        return config.getString("options.title");
    }

    public String getIdentifier() {
        return config.getString("options.identifier");
    }

    public int getRows() {
        return config.getInteger("options.rows");
    }

    public int getRefreshRate() {
        return config.getInteger("options.refreshRate");
    }

    public boolean isRefreshable() {
        return config.getBoolean("options.refreshable");
    }

    public abstract String setPlaceholders(String text, IGamePlayer player);

    public abstract void setMenuItems(IGamePlayer player);

    public abstract void onClick(IGamePlayer player, InventoryClickEvent event);

    public void onClose() {
        if (task != null) {
            task.cancel();
        }
    }

    public void open(IGamePlayer player) {
        inventory = Bukkit.createInventory(this, getRows() * 9, setPlaceholders(getTitle(), player));
        player.getPlayer().ifPresent(bukkitPlayer -> bukkitPlayer.openInventory(inventory));
        if (!Bukkit.isPrimaryThread()) {
            setMenuItems(player);
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
                setMenuItems(player);
            });
        }
        if (isRefreshable()) {
            task = Bukkit.getScheduler().runTaskTimer(WhaleSkyWars.getInstance(), () -> {
                if (player.getPlayer().isPresent()) {
                    setMenuItems(player);
                }
            }, 0, getRefreshRate() * 20L);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
