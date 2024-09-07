package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
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

        File defaultCageFolder = new File(WhaleSkyWars.getInstance().getDataFolder(), "cages/default");
        if (!defaultCageFolder.exists()) {
            defaultCageFolder.mkdirs();
        }
        File defaultCage = new File(WhaleSkyWars.getInstance().getDataFolder(), "cages/default/default.toml");
        File defaultSmallCage = new File(WhaleSkyWars.getInstance().getDataFolder(), "cages/default/small.schematic");
        File defaultBigCage = new File(WhaleSkyWars.getInstance().getDataFolder(), "cages/default/big.schematic");
        if (!defaultCage.exists()) {
            WhaleSkyWars.getInstance().saveResource("cages/default/default.toml", false);
        }
        if (!defaultSmallCage.exists()) {
            WhaleSkyWars.getInstance().saveResource("cages/default/small.schematic", false);
        }
        if (!defaultBigCage.exists()) {
            WhaleSkyWars.getInstance().saveResource("cages/default/big.schematic", false);
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
                TempConfig config = new TempConfig(folder, file.getName());
                register(file.getName().replace(".toml", ""), config);
            }
        }
    }

    public void register(String name, TempConfig config) {
        String actualname = config.getString("name", name);
        int id = config.getInteger("id");
        int cost = config.getInteger("cost");
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
