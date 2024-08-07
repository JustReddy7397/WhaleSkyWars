package ga.justreddy.wiki.whaleskywars.storage.readable;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class JsonStorage implements IStorage {
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

    @Override
    public void saveKit(Kit kit) {

    }

    @Override
    public List<Kit> getKits() {
        return new ArrayList<>();
    }

    @Override
    public void deleteKit(Kit kit) {

    }

    @Override
    public void updateKit(Kit kit) {

    }

    @Override
    public void saveKits(List<Kit> kits) {

    }

}
