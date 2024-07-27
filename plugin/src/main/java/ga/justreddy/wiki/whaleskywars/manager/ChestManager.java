package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.chest.AChestType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class ChestManager {

    private final Map<String, AChestType> chests;
    private final File folder;

    public ChestManager() {
        this.chests = new HashMap<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "chests");
        if (!folder.exists()) folder.mkdir();
    }

    public void start() {
        File[] chests = folder.listFiles();
        if (chests == null) return;
        
    }



}
