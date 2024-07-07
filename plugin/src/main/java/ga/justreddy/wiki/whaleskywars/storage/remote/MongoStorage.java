package ga.justreddy.wiki.whaleskywars.storage.remote;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class MongoStorage implements IStorage {

    @Override
    public void createData() {

    }

    @Override
    public void createPlayer(IGamePlayer player) {

    }

    @Override
    public boolean doesPlayerExist(IGamePlayer player) {
        return false;
    }

    @Override
    public void savePlayer(IGamePlayer player) {

    }

    @Override
    public void deletePlayer(IGamePlayer player) {

    }

    @Override
    public IGamePlayer loadPlayer(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<IGamePlayer> loadOfflinePlayer(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<IGamePlayer> loadPlayer(String name) {
        return null;
    }

}
