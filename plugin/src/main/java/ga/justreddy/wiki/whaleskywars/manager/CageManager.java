package ga.justreddy.wiki.whaleskywars.manager;

import com.moandjiezana.toml.Toml;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Cage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class CageManager {

    private final Map<Integer, Cage> data;
    private final File dataFolder;

    public CageManager() {
        this.dataFolder = new File(WhaleSkyWars.getInstance().getDataFolder(), "cages");
        this.data = new HashMap<>();
        // TODO load defaults
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public void start() {
        File[] folders = dataFolder.listFiles();
        if (folders == null) {
            return;
        }

        for (File folder : folders) {
            if (!folder.isDirectory()) continue;
            File[] files = folder.listFiles();
            if (files == null) {
                continue;
            }
            for (File file : files) {
                if (!file.getName().endsWith(".toml")) {
                    continue;
                }
                Toml toml = new Toml().read(file);
                register(folder.getName(), toml);
            }
        }
    }

    public void register(String name, Toml toml) {
        String actualname = toml.getString("name", name);
        int id = toml.getLong("id").intValue();
        int cost = toml.getLong("cost").intValue();
        File smallSchematic = new File(WhaleSkyWars
                .getInstance().getDataFolder(),
                "cages/" + name + "/small.schematic");
        File bigSchematic = new File(WhaleSkyWars.getInstance().getDataFolder(),
                "cages/" + name + "/big.schematic");
        Cage cage = new Cage(actualname, id, cost, smallSchematic, bigSchematic);
        data.put(id, cage);
    }

    public Cage getById(int id) {
        return data.getOrDefault(id, null);
    }

    public void die() {
        data.clear();
    }

    public Map<Integer, Cage> getData() {
        return data;
    }
}
