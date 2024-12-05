package ga.justreddy.wiki.whaleskywars.model.game.team;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.ChestType;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameSpawn;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.model.chests.CustomChest;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Balloon;
import ga.justreddy.wiki.whaleskywars.model.game.GameSpawn;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GameTeam implements IGameTeam {

    private final String id;
    private final List<IGamePlayer> players;
    private final Map<Location, String> chests;
    private final IGame game;
    private final IGameSpawn gameSpawn;
    private final Location spawnLocation;
    private final Location balloonLocation;

    public GameTeam(String id, IGame game, Location spawnLocation, Location balloonLocation) {
        this.id = id;
        this.game = game;
        this.players = new ArrayList<>();
        this.chests = new HashMap<>();
        // The cage will be set once the game starts
        this.gameSpawn = new GameSpawn(spawnLocation, false, null);
        this.balloonLocation = balloonLocation;
        this.spawnLocation = spawnLocation;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<IGamePlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public List<IGamePlayer> getAlivePlayers() {
        return players.stream()
                .filter(player -> !player.isDead())
                .collect(Collectors.toList());
    }

    @Override
    public List<IGamePlayer> getSpectatorPlayers() {
        return players.stream().filter(IGamePlayer::isDead).collect(Collectors.toList());
    }

    @Override
    public IGameSpawn getGameSpawn() {
        return gameSpawn;
    }

    @Override
    public int getSize() {
        return players.size();
    }

    @Override
    public void addPlayer(IGamePlayer player) {
        if (hasPlayer(player)) {
            return;
        }
        players.add(player);
    }

    @Override
    public boolean hasPlayer(IGamePlayer player) {
        return players.stream()
                .anyMatch(p -> p.getUniqueId().equals(player.getUniqueId()));
    }

    @Override
    public void removePlayer(IGamePlayer player) {
        players.removeIf(p -> p.getUniqueId().equals(player.getUniqueId()));
    }

    @Override
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public IGame getGame() {
        return game;
    }

    @Override
    public int getPriority() {
        return Integer.parseInt(id);
    }

    @Override
    public void spawnBalloon() {
        List<Integer> balloons = new ArrayList<>();
        for (IGamePlayer player : getPlayers()) {
            Balloon balloon = (Balloon) player.getCosmetics().getSelectedBalloon();
            if (balloon == null) {
                continue;
            }

            if (balloons.contains(balloon.getId())) {
                continue;
            }
            balloons.add(balloon.getId());
        }

        if (balloons.isEmpty()) return;

        int id = balloons.get(ThreadLocalRandom.current().nextInt(balloons.size()));

        Balloon balloon = WhaleSkyWars.getInstance().getBalloonManager().getById(id);

        if (balloon == null) return;

        balloon.spawn(balloonLocation);

    }

    @Override
    public void addChest(Location location, String type) {
        chests.put(location, type);
    }

    @Override
    public boolean isChest(Location location) {
        return chests.keySet().stream().anyMatch(
                chest -> chest.getBlockX() == location.getBlockX() &&
                        chest.getBlockY() == location.getBlockY() &&
                        chest.getBlockZ() == location.getBlockZ()
        );
    }

    @Override
    public void removeChest(Location location) {
        chests.remove(location);
    }

    @Override
    public void fill(IGame game, ChestType chestType) {
        chests.forEach((loc, id) -> {
            if (loc == null) return;
            Block block = loc.getBlock();
            if (block == null) return;
            if (!(block.getState() instanceof Chest)) return;
            Chest chest = (Chest) block.getState();
            CustomChest customChest = WhaleSkyWars.getInstance().
                    getChestManager().getById(id);
            if (customChest == null) return;
            customChest.populateChest(chest.getBlockInventory(),
                    chestType, game.getGameChestType());
        });
    }
}
