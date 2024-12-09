package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillEffect;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class KillEffectManager {

    private final Map<Integer, KillEffect> killEffects;
    private final File folder = WhaleSkyWars.ADDONS_FOLDER;

    public KillEffectManager() {
        this.killEffects = new HashMap<>();
    }

    public void start() {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jar")) continue;
            register(file);
        }
    }

    public void register(KillEffect killEffect) {
        killEffects.put(killEffect.getId(), killEffect);
    }

    public void register(File file) {
        try {
            List<Class<? extends Addon>> killEffects = ClassUtil.findAddons(file);
            for (Class<? extends Addon> classKillEffects : killEffects) {
                Addon killEffect = classKillEffects.getConstructor().newInstance();
                if (!(killEffect instanceof KillEffect)) continue;
                register((KillEffect) killEffect);
            }
        } catch (Exception e) {
            TextUtil.error(e, "Failed to load kill effects from: " + file.getName(), false);
        }
    }

    public void die() {
        killEffects.clear();
    }

    public boolean exists(int id) {
        return killEffects.containsKey(id);
    }

    public KillEffect of(int id) {
        return killEffects.getOrDefault(id, null);
    }

    public Map<Integer, KillEffect> getKillEffects() {
        return killEffects;
    }
}
