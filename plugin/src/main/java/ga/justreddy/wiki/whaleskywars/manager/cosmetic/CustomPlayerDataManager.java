package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillMessage;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.killmessages.DefaultKillMessage;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class CustomPlayerDataManager {

    private final Map<String, ICustomPlayerData> customPlayerData;
    private final File folder = WhaleSkyWars.ADDONS_FOLDER;

    public CustomPlayerDataManager() {
        this.customPlayerData = new HashMap<>();
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

    public void register(ICustomPlayerData customPlayerData) {
        this.customPlayerData.put(customPlayerData.getId(), customPlayerData);
    }

    public void register(File file) {
        try {
            List<Class<? extends Addon>> customPlayerDatas = ClassUtil.findAddons(file);
            for (Class<? extends Addon> classCustomPlayerData : customPlayerDatas) {
                Addon customPlayerData = classCustomPlayerData.getConstructor().newInstance();
                if (!(customPlayerData instanceof ICustomPlayerData)) continue;
                register((ICustomPlayerData) customPlayerData);
            }
        } catch (Exception ex) {
            TextUtil.error(ex, "Failed to load custom player data from: " + file.getName(), false);
        }
    }

    public void die() {
        customPlayerData.clear();
    }

    public ICustomPlayerData getCustomPlayerData(String id) {
        return this.customPlayerData.get(id);
    }

    public <T> T getCustomPlayerDataObj(String id, Class<T> clazz) {
        if (!clazz.isInstance(customPlayerData.get(id))) return null;
        return (T) this.customPlayerData.get(id);
    }

    public Map<String, ICustomPlayerData> getCustomPlayerData() {
        return customPlayerData;
    }

    public List<String> getCustomColumns() {
        List<String> columns = new ArrayList<>();
        for (ICustomPlayerData customPlayerData : customPlayerData.values()) {
            columns.add(customPlayerData.getId());
        }
        return columns;
    }

}
