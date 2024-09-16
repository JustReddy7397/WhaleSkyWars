package ga.justreddy.wiki.whaleskywars.tasks;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;

import java.util.HashMap;
import java.util.List;

/**
 * @author JustReddy
 */
public class CustomColumnCheck implements Runnable {

    private final IStorage storage;

    public CustomColumnCheck(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        // Check if the column is present in the database
        List<String> columns = storage.getCustomColumns("wsw_player_data");
        new HashMap<>(WhaleSkyWars.getInstance().getCustomPlayerDataManager()
                .getCustomPlayerData()).forEach((id, value) -> {
            if (columns.contains(id)) return;
            storage.addCustomColumn("wsw_player_data", id);
            if (!storage.hasCustomColumn("wsw_player_data", id)) {
                storage.createCustomColumn(id);
            }
        });
    }
}
