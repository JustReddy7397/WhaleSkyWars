package ga.justreddy.wiki.whaleskywars.storage.remote;

import com.cryptomorin.xseries.XMaterial;
import com.google.gson.GsonBuilder;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.model.entity.data.PlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.model.entity.data.PlayerRanked;
import ga.justreddy.wiki.whaleskywars.model.entity.data.PlayerStats;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ItemStackAdapter;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ListItemStackAdapter;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class SequalStorage implements IStorage {

    private Map<String, List<String>> customColumns = new HashMap<>();
    private final String type;
    private final String host;
    private final String username;
    private final String password;
    private final int port;
    private final String database;

    public SequalStorage(String type, String host, String database, String username, String password, int port) {
        this.type = type;
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.database = database;
    }

    Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:"
                    + type + "://" + host + ":" + port + "/" + database
                    + "?useSSL=false&autoReconnect=true" +
                    "&useUnicode=true&characterEncoding=utf8", username, password);
        } catch (SQLException e) {
            TextUtil.error(e, "Failed to get connection", false);
            return null;
        }
    }

    @Override
    public void createData() {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            String SQL = "CREATE TABLE IF NOT EXISTS wsw_player_data (" +
                    "uuid VARCHAR(36) PRIMARY KEY," +
                    "name VARCHAR(16)," +
                    "cosmetics LONGTEXT," +
                    "`stats` LONGTEXT)";
            statement.execute(SQL);
        } catch (SQLException e) {
            TextUtil.error(e, "Failed to create data", false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
    }

    @Override
    public void createPlayer(IGamePlayer player) {
        Connection connection = getConnection();
        StringBuilder builder = new StringBuilder("INSERT INTO wsw_player_data (uuid,name,cosmetics,stats");
        for (String column : WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomColumns()) {
            builder.append(",").append(column);
        }
        builder.append(") VALUES (?,?,?,?");
        builder.append(",?".repeat(customColumns.get("wsw_player_data").size()));
        builder.append(")");
        try (PreparedStatement statement = connection.prepareStatement(builder.toString())) {
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, player.getName());
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(List.class, new ListItemStackAdapter());
            gsonBuilder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
            for (ICustomPlayerData customPlayerData : WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomPlayerData().values()) {
                gsonBuilder.registerTypeAdapter(ICustomPlayerData.class, customPlayerData);
            }
            statement.setString(3, gsonBuilder.create().toJson(player.getCosmetics(), PlayerCosmetics.class));
            statement.setString(4, gsonBuilder.create().toJson(player.getStats(), PlayerStats.class));
            int current = 5;
            for (String id : WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomColumns()) {
                ICustomPlayerData playerData = player.getCustomPlayerData(id);
                if (playerData != null) {
                    statement.setString(current, gsonBuilder.create().toJson(playerData, playerData.getClass()));
                } else {
                    statement.setString(current, null);
                }
                current++;
            }
            statement.executeUpdate();
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to create player: " + player.getName() + " (" + player.getUniqueId() + ")", false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }

    }

    @Override
    public boolean doesPlayerExist(IGamePlayer player) {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT uuid FROM wsw_player_data WHERE uuid=?")) {
            statement.setString(1, player.getUniqueId().toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to check if player exists: " + player.getName() + " (" + player.getUniqueId() + ")", false);
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
    }

    @Override
    public void savePlayer(IGamePlayer player) {
        Connection connection = getConnection();
        StringBuilder builder = new StringBuilder("UPDATE wsw_player_data SET name=?,cosmetics=?,stats=?");
        int currentIndex = 4;
        for (String column : WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomColumns()) {
            builder.append(",").append(column).append("=?");
        }
        builder.append(" WHERE uuid=?");
        try (PreparedStatement statement = connection.prepareStatement(builder.toString())) {
            statement.setString(1, player.getName());
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(List.class, new ListItemStackAdapter());
            gsonBuilder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
            for (ICustomPlayerData customPlayerData : WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomPlayerData().values()) {
                gsonBuilder.registerTypeAdapter(ICustomPlayerData.class, customPlayerData);
            }
            statement.setString(2, gsonBuilder.create().toJson(player.getCosmetics(), PlayerCosmetics.class));
            statement.setString(3, gsonBuilder.create().toJson(player.getStats(), PlayerStats.class));
            for (String id : WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomColumns()) {
                ICustomPlayerData playerData = player.getCustomPlayerData(id);
                if (playerData != null) {
                    statement.setString(currentIndex, gsonBuilder.create().toJson(playerData, playerData.getClass()));
                } else {
                    statement.setString(currentIndex, null);
                }
                ++currentIndex;
            }
            statement.setString(currentIndex, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to save player: " + player.getName() + " (" + player.getUniqueId() + ")", false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
    }

    @Override
    public void deletePlayer(IGamePlayer player) {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM wsw_player_data WHERE uuid=?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to delete player: " + player.getName() + " (" + player.getUniqueId() + ")", false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
    }

    @Override
    public IGamePlayer loadPlayer(UUID uuid) {
        IGamePlayer player = WhaleSkyWars.getInstance().getPlayerManager().get(uuid);
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM wsw_player_data WHERE uuid=?")) {
            statement.setString(1, uuid.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (player == null) player = new GamePlayer(uuid, resultSet.getString("name"));
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(List.class,
                            new ListItemStackAdapter());
                    gsonBuilder.registerTypeAdapter(ItemStack.class
                            , new ItemStackAdapter());
                    for (ICustomPlayerData customPlayerData :
                            WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomPlayerData().values()) {
                        gsonBuilder.registerTypeAdapter(
                                ICustomPlayerData.class, customPlayerData);
                    }
                    player.setCosmetics(gsonBuilder.create().
                            fromJson(resultSet.getString("cosmetics"),
                                    PlayerCosmetics.class));
                    player.setStats(gsonBuilder.create().
                            fromJson(resultSet.getString("stats"),
                                    PlayerStats.class));
                    for (String id : WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomColumns()) {
                        ICustomPlayerData playerData = (ICustomPlayerData) gsonBuilder.
                                create().fromJson(resultSet.getString(id),
                                        WhaleSkyWars.getInstance()
                                                .getCustomPlayerDataManager()
                                                .getCustomPlayerDataObj(id).getClass());
                        if (playerData != null) {
                            ((GamePlayer) player).addCustomPlayerData(playerData);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to load player: " + uuid, false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
        return player;
    }

    @Override
    public CompletableFuture<IGamePlayer> loadOfflinePlayer(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> loadPlayer(uuid));
    }

    @Override
    public CompletableFuture<IGamePlayer> loadPlayer(String name) {
        return CompletableFuture.supplyAsync(() -> {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM wsw_player_data WHERE name=?")) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return loadPlayer(UUID.fromString(resultSet.getString("uuid")));
                    }
                }
            } catch (SQLException ex) {
                TextUtil.error(ex, "Failed to load player: " + name, false);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    TextUtil.error(e, "Failed to close connection", false);
                }
            }
            return null;
        });
    }

    @Override
    public void saveKit(Kit kit) {

        Connection connection = getConnection();
        String SQL = "INERT INTO wsw_kits (name, kitItems, kitArmor, guiKitItem, isDefault) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE kitItems=?, kitArmor=?, guiKitItem=?, isDefault=?";
        try (PreparedStatement statement = connection.prepareStatement(
                SQL
        )) {
            statement.setString(1, kit.getName());
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(List.class, new ListItemStackAdapter());
            builder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
            statement.setString(2, builder.create().toJson(kit.getKitItems(), List.class));
            statement.setString(3, builder.create().toJson(kit.getArmorItems(), List.class));
            statement.setString(4, builder.create().toJson(kit.getGuiKitItem(), ItemStack.class));
            statement.setBoolean(5, kit.isDefault());
            statement.setString(6, builder.create().toJson(kit.getKitItems(), List.class));
            statement.setString(7, builder.create().toJson(kit.getArmorItems(), List.class));
            statement.setString(8, builder.create().toJson(kit.getGuiKitItem(), ItemStack.class));
            statement.setBoolean(9, kit.isDefault());
            statement.executeUpdate();
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to save kit: " + kit.getName(), false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
    }

    @Override
    public List<Kit> getKits() {
        List<Kit> kits = new ArrayList<>();

        Connection connection = getConnection();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM wsw_kits)");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Kit kit = new Kit(resultSet.getString("name"),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        XMaterial.matchXMaterial(Material.STONE));
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(List.class, new ListItemStackAdapter());
                builder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
                kit.setKitItems(builder.create().fromJson(resultSet.getString("kitItems"), List.class));
                kit.setArmorItems(builder.create().fromJson(resultSet.getString("kitArmor"), List.class));
                kit.setGuiKitItem(XMaterial.matchXMaterial(builder.create().fromJson(resultSet.getString("guiKitItem"), ItemStack.class)));
                kit.setDefault(resultSet.getBoolean("isDefault"));
                kits.add(kit);
            }
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to load kits", false);
            return new ArrayList<>();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
        return kits;
    }

    @Override
    public void deleteKit(Kit kit) {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM wsw_kits WHERE name=?"
        )) {
            statement.setString(1, kit.getName());
            statement.executeUpdate();
        } catch (SQLException ex) {
            TextUtil.error(ex, "Failed to delete kit: " + kit.getName(), false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                TextUtil.error(e, "Failed to close connection", false);
            }
        }
    }

    @Override
    public void updateKit(Kit kit) {
        saveKit(kit);
    }

    @Override
    public void saveKits(List<Kit> kits) {
        for (Kit kit : kits) {
            saveKit(kit);
        }
    }

    @Override
    public boolean doesColumnExist(String table, String column) {
        String SQL;
        if (type.equalsIgnoreCase("postgresql")) {
            SQL = "SELECT column_name FROM information_schema.columns WHERE table_name = '" + table + "' AND column_name = '" + column + "'";
        } else {
            SQL = "SELECT * FROM information_schema.COLUMNS WHERE TABLE_NAME = '" + table + "' AND COLUMN_NAME = '" + column + "'";
        }
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(SQL)) {
            return result.next();
        } catch (SQLException e) {
            TextUtil.error(e, "Failed to check if column exists: " + column + " in table: " + table, false);
            return false;
        }
    }

    @Override
    public boolean doesTableExist(String table) {
        String SQL = "SHOW TABLES LIKE '" + table + "'";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(SQL)) {
            return result.next();
        } catch (SQLException e) {
            TextUtil.error(e, "Failed to check if table exists: " + table, false);
            return false;
        }
    }

    @Override
    public List<String> getCustomColumns(String table) {
        return customColumns.getOrDefault(table, new ArrayList<>());
    }

    @Override
    public void addCustomColumn(String table, String column) {
        List<String> columns = customColumns.getOrDefault(table, new ArrayList<>());
        columns.add(column);
        customColumns.put(table, columns);
    }

    @Override
    public boolean hasCustomColumn(String table, String column) {
        String query = "SELECT " + column + " FROM " + table;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void removeCustomColumn(String table, String column) {
        List<String> columns = customColumns.getOrDefault(table, new ArrayList<>());
        columns.remove(column);
        customColumns.put(table, columns);
    }

    @Override
    public void createCustomColumn(String column) {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("ALTER TABLE wsw_player_data ADD COLUMN " + column + " LONGTEXT");
        } catch (SQLException e) {
            TextUtil.error(e, "Failed to create custom column: " + column, false);
        }
    }

}
