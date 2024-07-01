package ga.justreddy.wiki.whaleskywars.model.game.team;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSpawn;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.model.game.GameSpawn;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameTeam implements IGameTeam {

    private final String id;
    private final List<IGamePlayer> players;
    private final IGameSpawn gameSpawn;
    private final Location spawnLocation;

    public GameTeam(String id, Location spawnLocation) {
        this.id = id;
        this.players = new ArrayList<>();
        // The cage will be set once the game starts
        this.gameSpawn = new GameSpawn(spawnLocation, false, null);
        this.spawnLocation = spawnLocation;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<IGamePlayer> getPlayers() {
        return players;
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
}
