package ga.justreddy.wiki.whaleskywars.storage;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

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


}
