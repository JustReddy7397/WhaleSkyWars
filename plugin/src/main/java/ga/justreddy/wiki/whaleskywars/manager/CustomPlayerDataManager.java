package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class CustomPlayerDataManager {

    private final Map<String, ICustomPlayerData> customPlayerData;

    public CustomPlayerDataManager() {
        this.customPlayerData = new HashMap<>();
    }

    public void addCustomPlayerData(ICustomPlayerData customPlayerData) {
        this.customPlayerData.put(customPlayerData.getId(), customPlayerData);
    }

    public void removeCustomPlayerData(ICustomPlayerData customPlayerData) {
        this.customPlayerData.remove(customPlayerData.getId());
    }

    public ICustomPlayerData getCustomPlayerData(String id) {
        return this.customPlayerData.get(id);
    }

    public Map<String, ICustomPlayerData> getCustomPlayerData() {
        return customPlayerData;
    }
}