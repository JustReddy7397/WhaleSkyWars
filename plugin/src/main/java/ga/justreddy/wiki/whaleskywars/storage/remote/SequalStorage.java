package ga.justreddy.wiki.whaleskywars.storage.remote;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;
import ga.justreddy.wiki.whaleskywars.storage.entities.PlayerEntity;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class SequalStorage implements IStorage {

    private Dao<PlayerEntity, String> playerDao;

    public SequalStorage(String type, String host, String database, String username, String password, int port) {
        try {
            ConnectionSource connectionSource = null;

            switch (type) {
                case "mysql":
                    connectionSource = new JdbcConnectionSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&characterEncoding=utf8", username, password);
                    break;
                case "postgresql":
                    connectionSource = new JdbcConnectionSource("jdbc:postgresql://" + host + ":" + port + "/" + database, username, password);
                    break;
                case "mariadb":
                    connectionSource = new JdbcConnectionSource("jdbc:mariadb://" + host + ":" + port + "/" + database, username, password);
                    break;
                default:
                    TextUtil.error(new IllegalArgumentException("Invalid storage type: " + type), "Failed to connect to the database: Invalid database type: " + type, true);
                    return;
            }

            TableUtils.createTableIfNotExists(connectionSource, PlayerEntity.class);
            playerDao = DaoManager.createDao(connectionSource, PlayerEntity.class);
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to connect to the database", true);
            return;
        }
    }


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
