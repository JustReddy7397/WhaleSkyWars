package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author JustReddy
 */
public class PlayerManager {

    private final Map<UUID, IGamePlayer> players;

    public PlayerManager() {
        this.players = new HashMap<>();
    }

    public IGamePlayer add(UUID uuid, String name) {
        IGamePlayer player = get(uuid);
        if (player != null) return player;
        player = new GamePlayer(uuid, name);
        players.put(uuid, player);
        return player;
    }

    public IGamePlayer get(UUID uuid) {
        return players.getOrDefault(uuid, null);
    }

    public IGamePlayer get(String name) {
        try (Stream<IGamePlayer> playerStream =  players.values().stream()) {
            try (Stream<IGamePlayer> filteredStream = playerStream.filter(player -> player.getName().equals(name))) {
                return filteredStream.findFirst().orElse(null);
            }
        }
    }

    public void remove(UUID uuid) {
        players.remove(uuid);
    }

    public void die() {
        players.clear();
    }

}
