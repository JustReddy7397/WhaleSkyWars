package ga.justreddy.wiki.whaleskywars.api.model.game;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.ChestType;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Interface representing a game in WhaleSkyWars.
 * Provides methods to manage game state, players, teams,
 * and various game events. <br>
 * Author: JustReddy
 */
public interface IGame {

    /**
     * Returns the name of the game.
     * @return the name of the game.
     */
    String getName();

    /**
     * Returns the display name of the game.
     * @return the display name of the game.
     */
    String getDisplayName();

    /**
     * Returns the minimum number of players required to start the game.
     * @return the minimum number of players.
     */
    int getMinimumPlayers();

    /**
     * Returns the maximum number of players allowed in the game.
     * @return the maximum number of players.
     */
    int getMaximumPlayers();

    /**
     * Returns the team size for the game.
     * @return the team size.
     */
    int getTeamSize();

    /**
     * Returns the current number of players in the game.
     * @return the current number of players.
     */
    int getPlayerCount();

    /**
     * Returns a list of all players in the game.
     * @return a list of all players.
     */
    List<IGamePlayer> getPlayers();

    /**
     * Returns a list of all living players in the game.
     * @return a list of all living players.
     */
    List<IGamePlayer> getAlivePlayers();

    /**
     * Returns a list of all spectators in the game.
     * @return a list of all spectators.
     */
    List<IGamePlayer> getSpectators();

    /**
     * Returns a list of all teams in the game.
     * @return a list of all teams.
     */
    List<IGameTeam> getTeams();

    /**
     * Returns a set of random teams in the game.
     * @return a set of random teams.
     */
    Set<IGameTeam> getRandomTeams();

    /**
     * Returns a list of all living teams in the game.
     * @return a list of all living teams.
     */
    List<IGameTeam> getAliveTeams();

    /**
     * Returns a list of all spectator teams in the game.
     * @return a list of all spectator teams.
     */
    List<IGameTeam> getSpectatorTeams();

    /**
     * Returns a map of players and their kill counts.
     * @return a map of players and their kill counts.
     */
    Map<IGamePlayer, Integer> getKills();

    /**
     * Returns the number of kills for a specific player.
     * @param player the player whose kills are to be retrieved.
     * @return the number of kills for the player.
     */
    int getKills(IGamePlayer player);

    /**
     * Returns the waiting spawn location.
     * @return the waiting spawn location.
     */
    Location getWaitingSpawn();

    /**
     * Returns the spectator spawn location.
     * @return the spectator spawn location.
     */
    Location getSpectatorSpawn();

    /**
     * Returns the waiting cuboid area.
     * @return the waiting cuboid area.
     */
    ICuboid getWaitingCuboid();

    /**
     * Returns the game cuboid area.
     * @return the game cuboid area.
     */
    ICuboid getGameCuboid();

    /**
     * Returns the current game state.
     * @return the current game state.
     */
    GameState getGameState();

    /**
     * Sets the game state.
     * @param gameState the game state to set.
     */
    void setGameState(GameState gameState);

    /**
     * Checks if the game is in a specific state.
     * @param gameState the game state to check.
     * @return true if the game is in the specified state, false otherwise.
     */
    boolean isGameState(GameState gameState);

    /**
     * Returns the game mode.
     * @return the game mode.
     */
    GameMode getGameMode();

    /**
     * Checks if the game mode matches the specified identifier.
     * @param identifier the identifier to check.
     * @return true if the game mode matches, false otherwise.
     */
    boolean isGameMode(String identifier);

    /**
     * Returns the starting timer for the game.
     * @return the starting timer.
     */
    AbstractTimer getStartingTimer();

    /**
     * Returns the pre-game timer for the game.
     * @return the pre-game timer.
     */
    AbstractTimer getPreGameTimer();

    /**
     * Returns the ending timer for the game.
     * @return the ending timer.
     */
    AbstractTimer getEndingTimer();

    /**
     * Returns the current game event.
     * @return the current game event.
     */
    GameEvent getCurrentEvent();

    /**
     * Returns the world in which the game is being played.
     * @return the world.
     */
    World getWorld();

    /**
     * Returns the default chest type for the game.
     * @return the default chest type.
     */
    ChestType getDefaultChestType();

    /**
     * Sets the default chest type for the game.
     * @param chestType the chest type to set.
     */
    void setDefaultChestType(ChestType chestType);

    /**
     * Returns the game chest type.
     * @return the game chest type.
     */
    ChestType getGameChestType();

    /**
     * Returns a list of the top three killers in the game.
     * @return a list of the top three killers.
     */
    List<Map.Entry<String, Integer>> getTopThreeKillers();

    /**
     * Returns a map of players and their voted chest types.
     * @return a map of players and their voted chest types.
     */
    Map<UUID, ChestType> getVotedChestTypes();

    /**
     * Allows a player to vote for a chest type.
     * @param player the player voting.
     * @param chestType the chest type to vote for.
     */
    void voteChestType(IGamePlayer player, ChestType chestType);

    /**
     * Returns the chest type voted by a specific player.
     * @param player the player whose vote is to be retrieved.
     * @return the chest type voted by the player.
     */
    ChestType getVotedChestType(IGamePlayer player);

    /**
     * Adds a chest at a specific location.
     * @param location the location of the chest.
     * @param type the type of the chest.
     */
    void addChest(Location location, String type);

    /**
     * Checks if there is a chest at a specific location.
     * @param location the location to check.
     * @return true if there is a chest, false otherwise.
     */
    boolean isChest(Location location);

    /**
     * Removes a chest from a specific location.
     * @param location the location of the chest to remove.
     */
    void removeChest(Location location);

    /**
     * Clears all votes for chest types.
     */
    void clearVotes();

    /**
     * Initializes the game with a specific world.
     * @param world the world to initialize the game with.
     */
    void init(World world);

    /**
     * Sends a message to a list of players.
     * @param players the players to send the message to.
     * @param message the message to send.
     */
    void sendMessage(List<IGamePlayer> players, String message);

    /**
     * Sends multiple messages to a list of players.
     * @param players the players to send the messages to.
     * @param messages the messages to send.
     */
    void sendMessages(List<IGamePlayer> players, List<String> messages);

    /**
     * Sends multiple messages to a list of players.
     * @param players the players to send the messages to.
     * @param messages the messages to send.
     */
    void sendMessages(List<IGamePlayer> players, String... messages);

    /**
     * Sends a title to a list of players.
     * @param players the players to send the title to.
     * @param title the title to send.
     * @param subtitle the subtitle to send.
     * @param fadeIn the fade-in time.
     * @param stay the stay time.
     * @param fadeOut the fade-out time.
     */
    void sendTitle(List<IGamePlayer> players, String title, String subtitle, int fadeIn, int stay, int fadeOut);

    /**
     * Sends an action bar message to a list of players.
     * @param players the players to send the message to.
     * @param message the message to send.
     */
    void sendActionBar(List<IGamePlayer> players, String message);

    /**
     * Sends a sound to a list of players.
     * @param players the players to send the sound to.
     * @param sound the sound to send.
     * @param volume the volume of the sound.
     * @param pitch the pitch of the sound.
     */
    void sendSound(List<IGamePlayer> players, String sound, float volume, float pitch);

    /**
     * Called when a player joins the game.
     * @param player the player joining the game.
     */
    void onGamePlayerJoin(IGamePlayer player);

    /**
     * Called when a player joins the game with a specific team.
     * @param player the player joining the game.
     * @param team the team the player is joining.
     */
    void onGamePlayerJoin(IGamePlayer player, IGameTeam team);

    /**
     * Called when a player starts spectating the game.
     * @param player the player starting to spectate.
     */
    void onGamePlayerSpectate(IGamePlayer player);

    /**
     * Called when a player leaves the game.
     * @param player the player leaving the game.
     * @param isSilent whether the leave is silent.
     * @param kick whether the player is being kicked.
     */
    void onGamePlayerLeave(IGamePlayer player, boolean isSilent, boolean kick);

    /**
     * Called when a player dies in the game.
     * @param killer the player who killed the victim.
     * @param victim the player who died.
     * @param path the type of kill.
     */
    void onGamePlayerDeath(IGamePlayer killer, IGamePlayer victim, KillPath path);

    /**
     * Called during the countdown phase of the game.
     */
    void onCountDown();

    /**
     * Advances the game to the next phase.
     */
    void goToNextPhase();

    /**
     * Assigns teams for the game.
     */
    void assignTeams();

    /**
     * Resets the game.
     */
    void reset();
}

