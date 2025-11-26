package ga.justreddy.wiki.whaleskywars.model.creator;

import com.cryptomorin.xseries.XMaterial;
import com.grinderwolf.swm.api.exceptions.InvalidWorldException;
import com.grinderwolf.swm.api.exceptions.WorldAlreadyExistsException;
import com.grinderwolf.swm.api.exceptions.WorldLoadedException;
import com.grinderwolf.swm.api.exceptions.WorldTooBigException;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.Messages;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.shared.config.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.supportold.packets.packets.MapCreatePacket;
import ga.justreddy.wiki.whaleskywars.util.FileUtil;
import ga.justreddy.wiki.whaleskywars.util.LocationUtil;
import ga.justreddy.wiki.whaleskywars.util.Replaceable;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class GameCreator<H> implements Listener {

    private final Map<UUID, String> setup;
    private final Map<String, Map<String, H>> holograms;
    private static final File GAMES_FOLDER = WhaleSkyWars.getInstance().getGameManager().getGamesFolder();

    public GameCreator() {
        this.setup = new HashMap<>();
        holograms = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, WhaleSkyWars.getInstance());
    }

    public void createGame(IGamePlayer player, String name) {

        if (WhaleSkyWars.getInstance().getSpawn() == null) {
            player.sendMessage(Messages.ERROR_NO_LOBBY.toString());
            return;
        }

        if (setup.containsKey(player.getUniqueId())) {
            player.sendMessage(Messages.SETUP_ALREADY_IN_PROGRESS.toString());
            return;
        }

        File file = getFile(name);

        if (file.exists()) {
            player.sendMessage(Messages.SETUP_ALREADY_CREATED.toString());
            return;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");
        config.set("settings.displayName", name);
        config.set("settings.teamSize", 1);
        config.set("settings.gameMode", "solo");
        config.set("settings.minimumPlayers", 2);
        config.set("settings.enabled", false);
        config.save();

        setup.put(player.getUniqueId(), name);
        World world = WhaleSkyWars.getInstance().getWorldManager()
                .createNewWorld(name);
        // :D
        world.getSpawnLocation().getBlock().setType(XMaterial.BEDROCK.parseMaterial());
        player.getPlayer().ifPresent(bukkitPlayer -> bukkitPlayer.teleport(world.getSpawnLocation()));
        player.sendMessage(Messages.SETUP_CREATED.toString(Replaceable.of("<name>", name)));
    }

    public void setDisplayName(IGamePlayer player, String displayName) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");
        config.set("settings.displayName", displayName);
        config.save();

        // TODO
        player.sendMessage(Messages.SETUP_DISPLAYNAME_SET.toString(Replaceable.of("<displayname>", displayName)));
    }

    public void setTeamSize(IGamePlayer player, int teamSize) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        config.set("settings.teamSize", teamSize);

        config.save();

        // TODO
        player.sendMessage(Messages.SETUP_TEAMSIZE_SET.toString(Replaceable.of("<teamsize>", String.valueOf(teamSize))));

    }

    public void setMinimumPlayers(IGamePlayer player, int minimumPlayers) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        config.set("settings.minimumPlayers", minimumPlayers);

        config.save();

        // TODO
        player.sendMessage(Messages.SETUP_MIN_PLAYERS_SET.toString(Replaceable.of("<min-players>", String.valueOf(minimumPlayers))));

    }

    public void setWaitingSpawn(IGamePlayer player) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        // Player can never be null :D

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();

        config.set("waiting-location", LocationUtil.toLocation(location));

        config.save();
        // TODO
        player.sendMessage(Messages.SETUP_WAITINGSPAWN_SET.toString());
    }

    public void setSpectatorSpawn(IGamePlayer player) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        // Player can never be null :D

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();

        config.set("spectator-location", LocationUtil.toLocation(location));

        config.save();

        player.sendMessage(Messages.SETUP_SPECTATORSPAWN_SET.toString());

    }

    public void createIsland(IGamePlayer player, int islandId) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        Set<Integer> islands = config.getSection("islands").keys().stream().map(Integer::parseInt).collect(Collectors.toSet());

        if (islands.contains(islandId)) {
            player.sendMessage(Messages.SETUP_ISLAND_ALREADY_EXISTS.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return;
        }

        config.set("islands." + islandId + ".spawn", "");
        config.set("islands." + islandId + ".balloon", "");
        config.save();

        player.sendMessage(Messages.SETUP_ISLAND_CREATED.toString(Replaceable.of("<island>", String.valueOf(islandId))));
    }

    public void setIslandSpawn(IGamePlayer player, int islandId) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("islands." + islandId);

        if (section == null) {
            player.sendMessage(Messages.SETUP_ISLAND_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return;
        }

        // Player can never be null :D
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();

        section.set("spawn", LocationUtil.toLocation(location));

        config.save();

        player.sendMessage(Messages.SETUP_ISLAND_SPAWN_SET.toString(Replaceable.of("<island>", String.valueOf(islandId))));
    }

    public void setIslandBalloon(IGamePlayer player, int islandId) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("islands." + islandId);

        if (section == null) {
            // TODO
            player.sendMessage(Messages.SETUP_ISLAND_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return;
        }

        // Player can never be null :D

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Location location = player.getPlayer().get().getLocation();

        section.set("balloon", LocationUtil.toLocation(location));

        config.save();


        player.sendMessage(Messages.SETUP_ISLAND_BALLOON_SET.toString(Replaceable.of("<island>", String.valueOf(islandId))));
    }

    public void clearIsland(IGamePlayer player, int islandId) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("islands." + islandId);

        if (section == null) {
            player.sendMessage(Messages.SETUP_ISLAND_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return;
        }

        config.set("islands." + islandId, null);

        config.save();

        player.sendMessage(Messages.SETUP_ISLAND_DELETED.toString(Replaceable.of("<island>", String.valueOf(islandId))));
        if (WhaleSkyWars.getInstance().isHooked("DecentHolograms")) {
            Map<String, ?> hologramMap = holograms.getOrDefault(name, new HashMap<>());
            if (hologramMap.isEmpty()) return;
            for (Map.Entry<String, ?> entry : hologramMap.entrySet()) {
                if (entry.getKey().contains("island_" + islandId)) {
                    ((Hologram) entry.getValue()).delete();
                    hologramMap.remove(entry.getKey());
                }
            }
        }
    }

    public void addIslandChest(IGamePlayer player, int islandId, String chestType) {
        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("islands." + islandId);

        if (section == null) {
            player.sendMessage(Messages.SETUP_ISLAND_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return;
        }

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return;
        }

        Block block = WhaleSkyWars.getInstance().getNms().getTargetBlock(bukkitPlayer, 5);

        if (block == null || !(block.getState() instanceof Chest)) {
            player.sendMessages("&cYou are not looking at a chest!");
            return;
        }

        if (WhaleSkyWars.getInstance().getChestManager().getById(chestType) == null) {
            player.sendMessage(Messages.SETUP_CHEST_NOT_FOUND.toString(Replaceable.of("<type>", chestType)));
            return;
        }

        if (isIslandChest(player, islandId)) {
            player.sendMessage(Messages.SETUP_ISLAND_CHEST_ALREADY_ADDED.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return;
        }

        int currentChests = section.getSection("chests").keys().size();
        section.set("chests." + currentChests + ".type", chestType);
        section.set("chests." + currentChests + ".location", LocationUtil.toLocation(block.getLocation()));
        config.save();

        player.sendMessage(Messages.SETUP_ISLAND_CHEST_ADDED.toString(Replaceable.of("<island>", String.valueOf(islandId)), Replaceable.of("<type>", chestType), Replaceable.of("<id>", String.valueOf(currentChests))));
        if (WhaleSkyWars.getInstance().isHooked("DecentHolograms")) {
            List<String> lines = new ArrayList<>();
            lines.add("&a&lIsland Chest &d&l" + islandId + " &a&l" + currentChests);
            Hologram hologram = DHAPI.createHologram("islandchest_" + islandId + "_" + currentChests, block.getLocation().clone().add(0.5, 1.5, 0.5));
            hologram.setDefaultVisibleState(false);
            hologram.setShowPlayer(bukkitPlayer);
            Map<String, H> hologramMap = holograms.getOrDefault(name, new HashMap<>());
            if (hologramMap.isEmpty()) {
                holograms.put(name, hologramMap);
            }
            hologramMap.put("island_" + islandId + "_" + currentChests, (H) hologram);
            holograms.put(name, hologramMap);
        }
    }

    private boolean isIslandChest(IGamePlayer player, int islandId, int chestId) {
        if (!isSettingUp(player)) {
            return false;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("islands." + islandId);

        if (section == null) {
            player.sendMessage(Messages.SETUP_ISLAND_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return false;
        }

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return false;
        }

        return section.isSet("chests." + chestId);
    }

    private boolean isIslandChest(IGamePlayer player, int islandId) {
        if (!isSettingUp(player)) {
            return false;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("islands." + islandId);

        if (section == null) {
            player.sendMessage(Messages.SETUP_ISLAND_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return false;
        }

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return false;
        }

        Block block = WhaleSkyWars.getInstance().getNms().getTargetBlock(bukkitPlayer, 5);

        if (block == null || !(block.getState() instanceof Chest)) {
            player.sendMessages("&cYou are not looking at a chest!");
            return false;
        }

        boolean isChest = false;

        for (String key : section.getSection("chests").keys()) {
            Location location = LocationUtil.getLocation(section.getString("chests." + key + ".location"));
            if (location == null) continue;
            if (location.equals(block.getLocation())) {
                isChest = true;
                break;
            }
        }

        return isChest;
    }

    public void removeIslandChest(IGamePlayer player, int islandId, int chestId) {
        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("islands." + islandId);

        if (section == null) {
            player.sendMessage(Messages.SETUP_ISLAND_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId))));
            return;
        }

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return;
        }

        if (!isIslandChest(player, islandId, chestId)) {
            player.sendMessage(Messages.SETUP_ISLAND_CHEST_NOT_FOUND.toString(Replaceable.of("<island>", String.valueOf(islandId)), Replaceable.of("<id>", String.valueOf(chestId))));
            return;
        }

        section.set("chests." + chestId, null);
        config.save();
        player.sendMessage(Messages.SETUP_ISLAND_CHEST_REMOVED.toString(Replaceable.of("<island>", String.valueOf(islandId)), Replaceable.of("<id>", String.valueOf(chestId))));
        if (WhaleSkyWars.getInstance().isHooked("DecentHolograms")) {
            Map<String, H> hologramMap = holograms.getOrDefault(name, new HashMap<>());
            if (hologramMap.isEmpty()) return;
            Hologram hologram = (Hologram) hologramMap.getOrDefault("island_"
                    + islandId + "_" + chestId, null);
            if (hologram == null) return;
            hologram.delete();
            hologramMap.remove("island_" + islandId + "_" + chestId);
            holograms.put(name, hologramMap);
        }
    }

    public void addChest(IGamePlayer player, String chestType) {
        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("chests");

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return;
        }

        Block block = WhaleSkyWars.getInstance().getNms().getTargetBlock(bukkitPlayer, 5);

        if (block == null || !(block.getState() instanceof Chest)) {
            player.sendMessages("&cYou are not looking at a chest!");
            return;
        }

        if (WhaleSkyWars.getInstance().getChestManager().getById(chestType) == null) {
            player.sendMessage(Messages.SETUP_CHEST_NOT_FOUND.toString(Replaceable.of("<type>", chestType)));
            return;
        }

        if (isChest(player)) {
            player.sendMessage(Messages.SETUP_CHEST_ALREADY_ADDED.toString());
            return;
        }

        int currentChests = section.keys().size();

        section.set(currentChests + ".type", chestType);
        section.set(currentChests + ".location", LocationUtil.toLocation(block.getLocation()));
        config.save();
        player.sendMessage(Messages.SETUP_CHEST_ADDED.toString(Replaceable.of("<type>", chestType), Replaceable.of("<id>", String.valueOf(currentChests))));
        if (WhaleSkyWars.getInstance().isHooked("DecentHolograms")) {
            List<String> lines = new ArrayList<>();
            lines.add("&a&lGame Chest &d&l" + currentChests);
            Hologram hologram = DHAPI.createHologram("gamechest_" + currentChests, block.getLocation().clone().add(0.5, 1.5, 0.5));
            hologram.setDefaultVisibleState(false);
            hologram.setShowPlayer(bukkitPlayer);
            Map<String, H> hologramMap = holograms.getOrDefault(name, new HashMap<>());
            if (hologramMap.isEmpty()) {
                holograms.put(name, hologramMap);
            }
            hologramMap.put("game_" + currentChests, (H) hologram);
            holograms.put(name, hologramMap);
        }
    }

    /*public boolean isChest(IGamePlayer player) {
        if (!isSettingUp(player)) {
            return false;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("chests");

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return false;
        }

        return section.isSet(String.valueOf(chestId));
    }*/

    public boolean isChest(IGamePlayer player) {
        if (!isSettingUp(player)) {
            return false;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("chests");

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return false;
        }

        Block block = WhaleSkyWars.getInstance().getNms().getTargetBlock(bukkitPlayer, 5);

        if (block == null || !(block.getState() instanceof Chest)) {
            player.sendMessages("&cYou are not looking at a chest!");
            return false;
        }

        boolean isChest = false;

        for (String key : section.keys()) {
            Location location = LocationUtil.getLocation(section.getString(key + ".location"));
            if (location == null) continue;
            if (location.equals(block.getLocation())) {
                isChest = true;
                break;
            }
        }

        return isChest;
    }

    public void removeChest(IGamePlayer player) {
        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        ConfigurationSection section = config.getSection("chests");

        Player bukkitPlayer = player.getPlayer().orElse(null);
        if (bukkitPlayer == null) {
            return;
        }

        Block block = WhaleSkyWars.getInstance().getNms().getTargetBlock(bukkitPlayer, 5);

        if (block == null || !(block.getState() instanceof Chest)) {
            player.sendMessages("&cYou are not looking at a chest!");
            return;
        }

        if (!isChest(player)) {
            player.sendMessage(Messages.SETUP_CHEST_NOT_CHEST.toString());
            return;
        }

        int chestId = -1;
        for (String key : section.keys()) {
            Location location = LocationUtil.getLocation(section.getString(key + ".location"));
            if (location == null) continue;
            if (!LocationUtil.equalsBlock(location, block.getLocation())) continue;
            chestId = Integer.parseInt(key);
        }

        if (chestId == -1) {
            player.sendMessage(Messages.SETUP_CHEST_NOT_FOUND.toString(Replaceable.of("<id>", String.valueOf(chestId))));
            return;
        }

        section.set(String.valueOf(chestId), null);
        config.save();
        player.sendMessage(Messages.SETUP_CHEST_REMOVED.toString(Replaceable.of("<id>", String.valueOf(chestId))));
        if (WhaleSkyWars.getInstance().isHooked("DecentHolograms")) {
            Map<String, H> hologramMap = holograms.getOrDefault(name, new HashMap<>());
            if (hologramMap.isEmpty()) return;
            Hologram hologram = (Hologram) hologramMap.getOrDefault("game_" + chestId, null);
            if (hologram == null) return;
            hologram.delete();
            hologramMap.remove("game_" + chestId);
            holograms.put(name, hologramMap);
        }
    }

    public void save(IGamePlayer player, boolean enable) {

        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        if (!isEverythingSettedUp(config)) {
            player.sendMessage(Messages.SETUP_MISSING_COMPONENTS.toString());
            return;
        }

        if (WhaleSkyWars.getInstance().isHooked("DecentHolograms")) {
            holograms.getOrDefault(name, new HashMap<>()).forEach((key, value) -> ((Hologram) value).delete());
            holograms.remove(name);
        }

        World world = Bukkit.getServer().getWorld(name);

        world.save();

        for (Player players : world.getPlayers()) {
            players.teleport(WhaleSkyWars.getInstance().getSpawn());
        }

        Bukkit.unloadWorld(world, false);

        config.set("settings.enabled", enable);

        config.save();

        boolean mapSync = WhaleSkyWars.getInstance().getSettingsConfig().getBoolean("modules.map-sync");

        if (WhaleSkyWars.getInstance().isHooked("SlimeWorldManager")) {
            Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
                try {
                    WhaleSkyWars.getInstance().getSlimePlugin()
                            .importWorld(world.getWorldFolder(),
                                    world.getName(),
                                    WhaleSkyWars.getInstance().getSlimeLoader());
                } catch (InvalidWorldException | WorldLoadedException
                         | IOException | WorldAlreadyExistsException |
                         WorldTooBigException ex) {
                    setup.remove(player.getUniqueId());
                    TextUtil.error(ex, "Failed to save game " + name, false);
                    return;
                }

                Bukkit.getScheduler().runTask(WhaleSkyWars.getInstance(), () -> {
                    WhaleSkyWars.getInstance().getWorldManager().removeWorld(world);
                    WhaleSkyWars.getInstance().getWorldManager().copySlimeWorld(world.getName());
                    if (mapSync && WhaleSkyWars.getInstance().getServerMode() == ServerMode.LOBBY) {
                        // TODO message
                        File worldFile = new File("slime_worlds/" + world.getName() + ".slime");
                        Map<String, Object> data = new TempConfig(GAMES_FOLDER, name + ".toml").data();
                        MapCreatePacket packet = new MapCreatePacket(name, data, worldFile);
                        /*WhaleSkyWars.getInstance()
                                .getMessenger().getSender().sendPacket(
                                        packet
                                );*/
                    } else {
                        FileUtil.delete(new File("slime_worlds/" + world.getName() + ".slime"));
                    }
                });

            });
        } else {
            WhaleSkyWars.getInstance().getWorldManager().copyWorld(world.getName());
            if (mapSync && WhaleSkyWars.getInstance().getServerMode() == ServerMode.LOBBY) {
                // TODO message
                File worldFile = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/worlds/" + name);
                Map<String, Object> data = new TempConfig(GAMES_FOLDER, name + ".toml").data();
                MapCreatePacket packet = new MapCreatePacket(name, data, worldFile);
                /*WhaleSkyWars.getInstance()
                        .getMessenger().getSender().sendPacket(
                                packet
                        );*/
            } else {
                FileUtil.delete(world.getWorldFolder());
            }
        }

        if (enable) {
            WhaleSkyWars.getInstance().getGameManager().register(name, config);
        }

        // TODO
        player.sendMessage(Messages.SETUP_SUCCESS.toString(Replaceable.of("<game>", name)));

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
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");
        if (event.getClickedBlock() == null) return;
        if (itemStack.getType() == Material.STICK) {
            String location = LocationUtil.toLocation(event.getClickedBlock().getLocation());
            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK:
                    config.set("waiting-cuboid.high", location);
                    config.save();
                    player.sendMessage(Messages.SETUP_BOUNDS_WAITING_HIGH.toString());
                    event.setCancelled(true);
                    break;
                case RIGHT_CLICK_BLOCK:
                    config.set("waiting-cuboid.low", location);
                    config.save();
                    player.sendMessage(Messages.SETUP_BOUNDS_WAITING_LOW.toString());
                    event.setCancelled(true);
                    break;
            }

        } else if (itemStack.getType() == Material.BLAZE_ROD) {
            String location = LocationUtil.toLocation(event.getClickedBlock().getLocation());
            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK:
                    config.set("game-cuboid.high", location);
                    config.save();
                    player.sendMessage(Messages.SETUP_BOUNDS_GAME_HIGH.toString());
                    event.setCancelled(true);
                    break;
                case RIGHT_CLICK_BLOCK:
                    config.set("game-cuboid.low", location);
                    config.save();
                    player.sendMessage(Messages.SETUP_BOUNDS_GAME_LOW.toString());
                    event.setCancelled(true);
                    break;
            }
        }

    }

    private boolean isEverythingSettedUp(TempConfig config) {
        return config.isSet("game-cuboid.high")
                && config.isSet("game-cuboid.low")
                && config.isSet("spectator-location")
                && config.isSet("islands");
    }

    private int getCurrentIsland(TempConfig config) {

        if (!config.isSet("islands")) {
            return 1;
        }

        int count = 1;

        ConfigurationSection islands = config.getSection("islands");

        Set<String> keys = islands.keys();

        return keys.size() + 1;
    }

    private File getFile(String name) {
        return new File(GAMES_FOLDER, name + ".toml");
    }


    private boolean isSettingUp(IGamePlayer player) {
        UUID uniqueId = player.getUniqueId();

        if (!setup.containsKey(uniqueId)) {
            // TODO
            player.sendMessage(Messages.SETUP_NOT_IN_PROGRESS.toString());
            return false;
        }

        String name = setup.get(uniqueId);
        File file = getFile(name);
        if (!file.exists()) {
            // TODO
            player.sendMessage(Messages.SETUP_NOT_EXISTS.toString());
            return false;
        }
        return true;
    }

    public void status(IGamePlayer player) {
        if (!isSettingUp(player)) {
            return;
        }

        String name = setup.get(player.getUniqueId());

        File file = getFile(name);

        // TODO
        TempConfig config = new TempConfig(GAMES_FOLDER, name + ".toml");

        boolean isDisplayNameSet = config.isSet("settings.displayName");
        boolean isTeamSizeSet = config.isSet("settings.teamSize");
        boolean isMinimumPlayersSet = config.isSet("settings.minimumPlayers");
        boolean isWaitingSpawnSet = config.isSet("waiting-location");
        boolean isSpectatorSpawnSet = config.isSet("spectator-location");
        boolean isIslandsSet = config.isSet("islands");
        boolean isGameCuboidSet = config.isSet("game-cuboid.high") && config.isSet("game-cuboid.low");
        boolean isWaitingCuboidSet = config.isSet("waiting-cuboid.high") && config.isSet("waiting-cuboid.low");
        String yes = "&a✔";
        String no = "&c✘";

        player.sendMessages(Messages.SETUP_STATUS.toList(
                Replaceable.of("<displayname>", isDisplayNameSet ? yes : no),
                Replaceable.of("<teamsize>", isTeamSizeSet ? yes : no),
                Replaceable.of("<min-players>", isMinimumPlayersSet ? yes : no),
                Replaceable.of("<waiting-spawn>", isWaitingSpawnSet ? yes : no),
                Replaceable.of("<spectator-spawn>", isSpectatorSpawnSet ? yes : no),
                Replaceable.of("<islands>", isIslandsSet ? yes : no),
                Replaceable.of("<game-bounds>", isGameCuboidSet ? yes : no),
                Replaceable.of("<waiting-bounds>", isWaitingCuboidSet ? yes : no)
        ));

    }

    public void kill() {
        setup.clear();
        if (WhaleSkyWars.getInstance().isHooked("DecentHolograms")) {
            holograms.forEach((key, value) -> value.forEach((k, v) -> ((Hologram) v).delete()));
        }
        holograms.clear();
    }
}
