package ga.justreddy.wiki.whaleskywars.api;

import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The main API class for the SkyWars plugin.
 * This class is used to get instances of the plugin's classes.
 * Provides methods to retrieve players, games, and register custom data.
 * <br>
 * Author: JustReddy
 */
public interface SkyWarsAPI {

    /**
     * Retrieves a game player by their name.
     *
     * @param name the name of the player.
     * @return the game player.
     */
    IGamePlayer getPlayer(String name);

    /**
     * Retrieves a game player by their UUID.
     *
     * @param uuid the UUID of the player.
     * @return the game player.
     */
    IGamePlayer getPlayer(UUID uuid);

    /**
     * Asynchronously loads a game player by their name.
     *
     * @param name the name of the player.
     * @return a CompletableFuture containing the game player.
     */
    CompletableFuture<IGamePlayer> loadPlayer(String name);

    /**
     * Asynchronously loads a game player by their UUID.
     *
     * @param uuid the UUID of the player.
     * @return a CompletableFuture containing the game player.
     */
    CompletableFuture<IGamePlayer> loadPlayer(UUID uuid);

    /**
     * Retrieves a game by its name.
     *
     * @param name the name of the game.
     * @return the game.
     */
    IGame getGame(String name);

    /**
     * Retrieves a list of games with similar names.
     *
     * @param name the name to search for similar games.
     * @return a list of games with similar names.
     */
    List<IGame> getGamesWithSimilarNames(String name);

    /**
     * Retrieves a list of all games.
     *
     * @return a list of all games.
     */
    List<IGame> getGames();

    /**
     * Registers a custom victory dance.
     *
     * @param dance the victory dance to register.
     */
    void registerVictoryDance(VictoryDance dance);

    /**
     * Registers custom player data.
     *
     * @param playerData the custom player data to register.
     */
    void registerCustomPlayerData(ICustomPlayerData playerData);

    /**
     * Retrieves the plugin instance.
     *
     * @return the plugin instance.
     */
    JavaPlugin getPlugin();
}
