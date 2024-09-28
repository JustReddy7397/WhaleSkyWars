package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
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
    private final File folder;

    public KillEffectManager() {
        this.killEffects = new HashMap<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "killEffects");
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
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
            List<Class<? extends KillEffect>> killEffects = ClassUtil.findClasses(file, KillEffect.class);
            for (Class<? extends KillEffect> classKillEffects : killEffects) {
                KillEffect killEffect = classKillEffects.getConstructor().newInstance();
                register(killEffect);
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

}
