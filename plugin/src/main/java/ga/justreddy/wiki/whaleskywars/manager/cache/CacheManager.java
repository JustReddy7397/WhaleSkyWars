package ga.justreddy.wiki.whaleskywars.manager.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class CacheManager {

    private final Set<UUID> building;

    public CacheManager() {
        this.building = new HashSet<>();
    }

    public void addBuilding(UUID uuid) {
        building.add(uuid);
    }

    public boolean isBuilding(UUID uuid) {
        return building.contains(uuid);
    }

    public void removeBuilding(UUID uuid) {
        building.remove(uuid);
    }


}
