package ga.justreddy.wiki.whaleskywars.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ItemStackAdapter;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ListItemStackAdapter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public interface IStorage {

    Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ItemStackAdapter.class, new ItemStackAdapter())
            .registerTypeAdapter(ListItemStackAdapter.class, new ListItemStackAdapter())
            .create();

    void createData();

    void createPlayer(IGamePlayer player);

    boolean doesPlayerExist(IGamePlayer player);

    void savePlayer(IGamePlayer player);

    void deletePlayer(IGamePlayer player);

    IGamePlayer loadPlayer(UUID uuid);

    CompletableFuture<IGamePlayer> loadOfflinePlayer(UUID uuid);

    CompletableFuture<IGamePlayer> loadPlayer(String name);

    void saveKit(Kit kit);

    List<Kit> getKits();

    void deleteKit(Kit kit);

    void updateKit(Kit kit);

    void saveKits(List<Kit> kits);

    boolean doesColumnExist(String table, String column);

    boolean doesTableExist(String table);

    List<String> getCustomColumns(String table);

    void addCustomColumn(String table, String column);

    boolean hasCustomColumn(String table, String column);

    void removeCustomColumn(String table, String column);

    void createCustomColumn(String column);
}
