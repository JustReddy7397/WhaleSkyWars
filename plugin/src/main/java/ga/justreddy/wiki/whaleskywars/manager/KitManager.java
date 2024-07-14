package ga.justreddy.wiki.whaleskywars.manager;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class KitManager {

    private final Map<String, Kit> kits;

    public KitManager() {
        this.kits = new HashMap<>();
    }

    public void loadKits() {
        Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
            synchronized (kits) {
                kits.clear();
                WhaleSkyWars.getInstance().getStorage().getKits()
                        .forEach(kit -> {
                            kits.put(kit.getName(), kit);
                        });
            }
        });
        assureDefaultKitExists();
    }

    private void assureDefaultKitExists() {
        if (hasDefaultKit()) return;
        Kit kit = getKitByName("default");
        if (kit == null) {
            kit = new Kit("default",
                    new ArrayList<>(Arrays.asList(
                            XMaterial.IRON_SWORD.parseItem(),
                            XMaterial.IRON_PICKAXE.parseItem(),
                            XMaterial.IRON_AXE.parseItem()
                    )), new ArrayList<>(), XMaterial.IRON_SWORD, true);
            kits.put("default", kit);
            Kit finalKit = kit;
            Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> WhaleSkyWars.getInstance().getStorage().saveKit(finalKit));
            // TODO
        }
    }

    public Kit getKitByName(String name) {
        return kits.getOrDefault(name, null);
    }

    public boolean hasDefaultKit() {
        return kits.values().stream().anyMatch(Kit::isDefault);
    }

    public Kit getDefaultKit() {
        if (!hasDefaultKit()) assureDefaultKitExists();
        return kits.values().stream().filter(Kit::isDefault).findFirst().orElse(kits.values().stream().findFirst().get());
    }

    public Map<String, Kit> getKits() {
        return kits;
    }

}
