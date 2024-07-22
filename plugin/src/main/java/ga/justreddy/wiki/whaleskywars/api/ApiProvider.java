package ga.justreddy.wiki.whaleskywars.api;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public class ApiProvider implements SkyWarsAPI {

    @Override
    public IGamePlayer getPlayer(String name) {
        return WhaleSkyWars.getInstance().getPlayerManager().get(name);
    }

    @Override
    public IGamePlayer getPlayer(UUID uuid) {
        return GamePlayer.get(uuid);
    }

    @Override
    public CompletableFuture<IGamePlayer> loadPlayer(String name) {
        return null;
    }

    @Override
    public CompletableFuture<IGamePlayer> loadPlayer(UUID uuid) {
        return null;
    }

    @Override
    public IGame getGame(String name) {
        return WhaleSkyWars.getInstance().getGameManager().getGameByName(name);
    }

    @Override
    public List<IGame> getGamesWithSimilarNames(String name) {
        return WhaleSkyWars.getInstance().getGameManager().getGamesBySimilarName(name);
    }

    @Override
    public List<IGame> getGames() {
        return new ArrayList<>(WhaleSkyWars.getInstance().getGameManager().getGames());
    }

    @Override
    public void registerGameEvent(GameEvent event) {
        WhaleSkyWars.getInstance().getGameEventManager().register(event);
    }

    @Override
    public JavaPlugin getPlugin() {
        return WhaleSkyWars.getInstance();
    }
}
