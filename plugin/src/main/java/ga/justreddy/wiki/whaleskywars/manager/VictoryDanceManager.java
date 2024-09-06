package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
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
    private final File folder;

    public VictoryDanceManager() {
        this.dances = new HashMap<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "dances");
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
            String name = file.getName().replace(".jar", "");
            register(name, file);
        }
    }

    private void register(String name, File file) {
        try {
            List<Class<? extends VictoryDance>> events = ClassUtil.findClasses(file, VictoryDance.class);
            for (Class<? extends VictoryDance> event : events) {
                VictoryDance victoryDance = event.getConstructor().newInstance();
                register(victoryDance);
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

    public VictoryDance copyOf(int id) {
        VictoryDance event = dances.getOrDefault(id, null);
        if (event == null) return null;
        return event.clone();
    }

}
