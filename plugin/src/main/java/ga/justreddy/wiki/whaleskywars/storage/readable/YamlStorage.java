package ga.justreddy.wiki.whaleskywars.storage.readable;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;

/**
 * @author JustReddy
 */
public class YamlStorage implements IStorage {
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
    public IGamePlayer loadPlayer(IGamePlayer player) {
        return null;
    }
}
