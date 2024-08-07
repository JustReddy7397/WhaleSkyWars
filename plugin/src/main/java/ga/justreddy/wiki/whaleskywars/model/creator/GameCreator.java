package ga.justreddy.wiki.whaleskywars.model.creator;

import com.cryptomorin.xseries.XMaterial;
import com.moandjiezana.toml.Toml;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameMode;
import ga.justreddy.wiki.whaleskywars.model.config.CustomTomlReader;
import ga.justreddy.wiki.whaleskywars.model.config.CustomTomlWriter;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class GameCreator implements Listener {

    private final Map<UUID, String> setup;
    private static final File GAMES_FOLDER = WhaleSkyWars.getInstance().getGameManager().getGamesFolder();

    public GameCreator() {
        this.setup = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, WhaleSkyWars.getInstance());
    }

    public void createGame(IGamePlayer player, String name) {

        if (setup.containsKey(player.getUniqueId())) {
            // TODO
            player.sendMessage("You are already in the setup process");
            return;
        }

        File file = getFile(name);

        if (file.exists()) {
            // TODO
            player.sendMessage("Game already exists");
            return;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Toml toml = load(file);
        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);
        writer.set("settings.displayName", name);
        writer.set("settings.teamSize", 1);
        writer.set("settings.gameMode", GameMode.SOLO.toString());
        writer.set("settings.minimumPlayers", 2);
        writer.set("settings.enabled", false);
        writer.write();
        setup.put(player.getUniqueId(), name);
        World world = WhaleSkyWars.getInstance().getWorldManager()
                .createNewWorld(name);
        // :D
        world.getSpawnLocation().getBlock().setType(XMaterial.BEDROCK.parseMaterial());
        player.getPlayer().ifPresent(bukkitPlayer -> bukkitPlayer.teleport(world.getSpawnLocation()));
        // TODO
        player.sendMessage("You are now in the setup process");
    }

    public void setDisplayName(IGamePlayer player, String displayName) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);

        writer.set("settings", "displayName", displayName);

        writer.write();

        // TODO
        player.sendMessage("Display name set to: " + displayName);

    }

    public void setTeamSize(IGamePlayer player, int teamSize) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);

        writer.set("settings", "teamSize", teamSize);

        writer.write();

        // TODO
        player.sendMessage("Team size set to: " + teamSize);

    }

    public void setMinimumPlayers(IGamePlayer player, int minimumPlayers) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);

        writer.set("settings", "minimumPlayers", minimumPlayers);

        writer.write();

        // TODO
        player.sendMessage("Minimum players set to: " + minimumPlayers);

    }

    public void setWaitingSpawn(IGamePlayer player) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);

        // Player can never be null :D
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();

        writer.set("waiting-location", LocationUtil.toLocation(location));
        writer.write();
        // TODO
        player.sendMessage("Waiting spawn set");
    }

    public void setSpectatorSpawn(IGamePlayer player) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);

        // Player can never be null :D
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();

        writer.set("spectator-location", LocationUtil.toLocation(location));
        writer.write();
        // TODO
        player.sendMessage("Spectator spawn set");

    }

    public void createIsland(IGamePlayer player) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlReader reader = CustomTomlReader.of(toml);
        CustomTomlReader islands = reader.getTable("islands");
        int island = getCurrentIsland(toml);
        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);
        writer.set("islands." + island + ".spawn", "");
        writer.set("islands." + island + ".balloon", "");
        writer.write(file);
        player.sendMessage("Island " + island + " created");
    }

    public void setIslandSpawn(IGamePlayer player, int islandId) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlReader reader = CustomTomlReader.of(toml);
        CustomTomlReader islands = reader.getTable("islands");
        Map<String, Object> values = islands.getTable();

        if (values == null || !values.containsKey(islandId + "")) {
            // TODO
            player.sendMessage("Island " + islandId + " does not exist");
            return;
        }
        // Player can never be null :D
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();
        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);
        // TODO
        writer.set("islands." + islandId + ".spawn", LocationUtil.toLocation(location));
        writer.write();
        player.sendMessage("Island " + islandId + " spawn set");
    }

    public void setIslandBalloon(IGamePlayer player, int islandId) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlReader reader = CustomTomlReader.of(toml);
        CustomTomlReader islands = reader.getTable("islands");
        Map<String, Object> values = islands.getTable();
        if (!values.containsKey(islandId + "")) {
            // TODO
            player.sendMessage("Island " + islandId + " does not exist");
            return;
        }
        // Player can never be null :D
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();
        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);
        // TODO
        writer.set("islands." + islandId + ".balloon", LocationUtil.toLocation(location));
        writer.write();
        player.sendMessage("Island " + islandId + " balloon set");
    }

    public void clearIsland(IGamePlayer player, int islandId) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlReader reader = CustomTomlReader.of(toml);
        CustomTomlReader islands = reader.getTable("islands");
        Map<String, Object> values = islands.getTable();
        if (!values.containsKey(islandId + "")) {
            // TODO
            player.sendMessage("Island " + islandId + " does not exist");
            return;
        }
        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);
        writer.remove("islands", islandId + "");
        writer.write(file);
        player.sendMessage("Island " + islandId + " cleared");
    }

    public void save(IGamePlayer player, boolean enable) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        Toml toml = load(file);

        CustomTomlReader reader = CustomTomlReader.of(toml);

        if (!isEverythingSettedUp(reader)) {
            // TODO
            player.sendMessage("Not everything is set up");
            return;
        }

        World world = Bukkit.getServer().getWorld(name);


        // TODO
        player.sendMessage("Game saved");

        setup.remove(player.getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player bukkitPlayer = event.getPlayer();
        if (!setup.containsKey(bukkitPlayer.getUniqueId())) {
            return;
        }

        IGamePlayer player = GamePlayer.get(bukkitPlayer.getUniqueId());
        if (player == null) {
            return;
        }

        ItemStack itemStack = event.getItem();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;

        String name = setup.get(player.getUniqueId());
        File file = getFile(name);
        Toml toml = load(file);
        CustomTomlReader reader = CustomTomlReader.of(toml);
        CustomTomlWriter writer = CustomTomlWriter.of(toml, file);
        if (event.getClickedBlock() == null) return;
        if (itemStack.getType() == Material.STICK) {
            String location = LocationUtil.toLocation(event.getClickedBlock().getLocation());
            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK:
                    writer.set("waiting-cuboid", "high", location);
                    writer.write();
                    player.sendMessage("High location set for waiting cuboid");
                    event.setCancelled(true);
                    break;
                case RIGHT_CLICK_BLOCK:
                    writer.set("waiting-cuboid", "low", location);
                    writer.write();
                    player.sendMessage("Low location set for waiting cuboid");
                    event.setCancelled(true);
                    break;
            }

        } else if (itemStack.getType() == Material.BLAZE_ROD) {
            String location = LocationUtil.toLocation(event.getClickedBlock().getLocation());
            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK:
                    writer.set("game-cuboid", "high", location);
                    writer.write();
                    player.sendMessage("High location set for game cuboid");
                    event.setCancelled(true);
                    break;
                case RIGHT_CLICK_BLOCK:
                    writer.set("waiting-cuboid", "low", location);
                    writer.write();
                    player.sendMessage("game location set for game cuboid");
                    event.setCancelled(true);
                    break;
            }
        }

    }

    private boolean isEverythingSettedUp(CustomTomlReader reader) {
        return reader.isSet("game-cuboid.high")
                && reader.isSet("game-cuboid.low")
                && reader.isSet("spectator-location")
                && reader.isSet("islands");
    }

    private int getCurrentIsland(Toml toml) {
        CustomTomlReader reader = CustomTomlReader.of(toml);

        if (!reader.isSet("islands")) {
            return 1;
        }

        int count = 1;

        CustomTomlReader islands = reader.getTable("islands");

        Set<String> keys = islands.keys();

        return keys.size() + 1;
    }

    private File getFile(String name) {
        return new File(GAMES_FOLDER, name + ".toml");
    }

    private Toml load(File file) {
        return new Toml().read(file);
    }

    private boolean isSettingUp(IGamePlayer player) {
        UUID uniqueId = player.getUniqueId();

        if (!setup.containsKey(uniqueId)) {
            // TODO
            player.sendMessage("You are not in the setup process");
            return false;
        }

        String name = setup.get(uniqueId);
        File file = getFile(name);
        if (!file.exists()) {
            // TODO
            player.sendMessage("Game does not exist");
            return false;
        }
        return true;
    }

}
