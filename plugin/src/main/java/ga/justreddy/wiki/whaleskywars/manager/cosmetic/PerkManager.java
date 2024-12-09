package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk.Perk;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class PerkManager {

    private final Map<Integer, Perk> perks;
    private final File folder = WhaleSkyWars.ADDONS_FOLDER;

    public PerkManager() {
        this.perks = new HashMap<>();
    }

    public void start() {
        // TODO load perks
    }

    public void register(File file) {
        try {
            List<Class<? extends Addon>> perks = ClassUtil.findAddons(file);
            for (Class<? extends Addon> classPerks : perks) {
                Addon perk = classPerks.getConstructor().newInstance();
                if (!(perk instanceof Perk)) continue;
                register((Perk) perk);
            }
        } catch (Exception e) {
            TextUtil.error(e, "Failed to load perks from: " + file.getName(), false);
        }
    }

    public void register(Perk perk) {
        perks.put(perk.getId(), perk);
    }

    public void die() {
        perks.clear();
    }

    public boolean exists(int id) {
        return perks.containsKey(id);
    }

    public Perk of(int id) {
        return perks.getOrDefault(id, null);
    }

    public Map<Integer, Perk> getPerks() {
        return perks;
    }
}
