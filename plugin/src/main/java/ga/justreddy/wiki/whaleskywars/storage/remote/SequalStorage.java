package ga.justreddy.wiki.whaleskywars.storage.remote;

import com.cryptomorin.xseries.XMaterial;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ItemStackAdapter;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ListItemStackAdapter;
import ga.justreddy.wiki.whaleskywars.storage.entities.KitEntity;
import ga.justreddy.wiki.whaleskywars.storage.entities.PlayerEntity;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class SequalStorage implements IStorage {

    private Dao<PlayerEntity, String> playerDao;
    private Dao<KitEntity, String> kitDao;

    public SequalStorage(String type, String host, String database, String username, String password, int port) {
        try {
            ConnectionSource connectionSource = null;

            switch (type) {
                case "mysql":
                    connectionSource = new JdbcConnectionSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&characterEncoding=utf8", username, password);
                    break;
                case "postgresql":
                    connectionSource = new JdbcConnectionSource("jdbc:postgresql://" + host + ":" + port + "/" + database + "?autoReconnect=true&characterEncoding=utf8", username, password);
                    break;
                case "mariadb":
                    connectionSource = new JdbcConnectionSource("jdbc:mariadb://" + host + ":" + port + "/" + database + "?autoReconnect=true&characterEncoding=utf8", username, password);
                    break;
                default:
                    TextUtil.error(new IllegalArgumentException("Invalid storage type: " + type), "Failed to connect to the database: Invalid database type: " + type, true);
                    return;
            }

            TableUtils.createTableIfNotExists(connectionSource, PlayerEntity.class);
            playerDao = DaoManager.createDao(connectionSource, PlayerEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, KitEntity.class);
            kitDao = DaoManager.createDao(connectionSource, KitEntity.class);
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

    @Override
    public void saveKit(Kit kit) {
        KitEntity entity = new KitEntity(kit);
        try {
            kitDao.create(entity);
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to save kit: " + kit.getName(), false);
        }
    }

    @Override
    public List<Kit> getKits() {
        List<Kit> kits = new ArrayList<>();
        try {

            List<KitEntity> entities = kitDao.queryForAll();
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(List.class, new ListItemStackAdapter());
            builder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
            for (KitEntity entity : entities) {
                Kit kit = new Kit(
                        entity.name,
                        builder.create().fromJson(entity.kitItems, List.class),
                        builder.create().fromJson(entity.kitArmor, List.class),
                        XMaterial.matchXMaterial(builder.create().fromJson(entity.guiKitItem, ItemStack.class)),
                        entity.isDefault
                );
                kits.add(kit);
            }
        }catch (SQLException ex) {
            TextUtil.error(ex, "Failed to load kits", false);
            return new ArrayList<>();
        }
        return kits;
    }

    @Override
    public void deleteKit(Kit kit) {
        try {
            kitDao.delete(new KitEntity(kit));
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateKit(Kit kit) {
        try {
            KitEntity entity = new KitEntity(kit);
            kitDao.update(entity);
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to update kit: " + kit.getName(), false);
        }
    }

    @Override
    public void saveKits(List<Kit> kits) {

    }

}
