package ga.justreddy.wiki.whaleskywars.storage;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public interface IStorage {

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

}
