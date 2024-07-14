package ga.justreddy.wiki.whaleskywars.api;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author JustReddy
 */
public interface SkyWarsAPI {

    IGamePlayer getPlayer(String name);

    IGamePlayer getPlayer(UUID uuid);

    CompletableFuture<IGamePlayer> loadPlayer(String name);

    CompletableFuture<IGamePlayer> loadPlayer(UUID uuid);

    IGame getGame(String name);

    List<IGame> getGamesWithSimilarNames(String name);

    List<IGame> getGames();

    void registerGameEvent(GameEvent event);

    JavaPlugin getPlugin();

}
