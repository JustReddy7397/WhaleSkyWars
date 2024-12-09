package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class VictoryDanceManager {

    private final Map<Integer, VictoryDance> dances;
    private final File folder = WhaleSkyWars.ADDONS_FOLDER;

    public VictoryDanceManager() {
        this.dances = new HashMap<>();
    }

    public void start() {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jar")) continue;
            String name = file.getName().replace(".jar", "");
            register(name, file);
        }
    }

    private void register(String name, File file) {
        try {
            List<Class<? extends Addon>> dances = ClassUtil.findAddons(file);
            for (Class<? extends Addon> dance : dances) {
                Addon addon = dance.getConstructor().newInstance();
                if (!(addon instanceof VictoryDance)) continue;
                register((VictoryDance) addon);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void register(VictoryDance dance) {
        dances.put(dance.getId(), dance);
    }

    public void die() {
        dances.clear();
    }

    public boolean exists(int id) {
        return dances.containsKey(id);
    }

    public VictoryDance copyOf(int id) {
        VictoryDance event = dances.getOrDefault(id, null);
        if (event == null) return null;
        return event.clone();
    }

    public Map<Integer, VictoryDance> getDances() {
        return dances;
    }
}
