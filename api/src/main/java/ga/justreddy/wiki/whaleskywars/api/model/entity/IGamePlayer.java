package ga.justreddy.wiki.whaleskywars.api.model.entity;

import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerStats;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a GamePlayer in the WhaleSkyWars game.
 * A GamePlayer is a player participating in a game.
 * Each GamePlayer has a unique id, a name, a Player object,
 * a Game object, a GameTeam object
 * and states for playing and being dead.
 *
 * @author JustReddy
 */
public interface IGamePlayer {

    /**
     * Returns the unique id of the GamePlayer.
     *
     * @return the unique id of the GamePlayer
     */
    UUID getUniqueId();

    /**
     * Returns the name of the GamePlayer.
     *
     * @return the name of the GamePlayer
     */
    String getName();

    /**
     * Returns the Player object of the GamePlayer.
     *
     * @return the Player object of the GamePlayer
     */
    Optional<Player> getPlayer();

    /**
     * Gets the PlayerCosmetic object of the GamePlayer.
     *
     * @return the PlayerCosmetic object of the GamePlayer
     */
    IPlayerCosmetics getCosmetics();

    /**
     * Sets the PlayerCosmetic object of the GamePlayer.
     *
     * @param cosmetics the PlayerCosmetic object to set
     */
    void setCosmetics(IPlayerCosmetics cosmetics);

    /**
     * Gets the PlayerStats object of the GamePlayer.
     *
     * @return the PlayerStats object of the GamePlayer
     */
    IPlayerStats getStats();

    /**
     * Sets the PlayerStats object of the GamePlayer.
     *
     * @param stats the PlayerStats object to set
     */
    void setStats(IPlayerStats stats);

    /**
     * Returns the Game object of the GamePlayer.
     *
     * @return the Game object of the GamePlayer
     */
    IGame getGame();

    /**
     * Sets the Game object of the GamePlayer.
     *
     * @param game the Game to set
     */
    void setGame(IGame game);

    /**
     * Returns the GameTeam of the GamePlayer.
     *
     * @return the GameTeam of the GamePlayer
     */
    IGameTeam getGameTeam();

    /**
     * Sets the GameTeam of the GamePlayer.
     *
     * @param team the GameTeam to set
     */
    void setGameTeam(IGameTeam team);

    /**
     * Returns the CombatLog of the GamePlayer.
     * @return the CombatLog of the GamePlayer
     */
    ICombatLog getCombatLog();

    /**
     * Sets the CombatLog of the GamePlayer.
     * @param combatLog the CombatLog to set
     */
    void setCombatLog(ICombatLog combatLog);

    /**
     * Returns whether the GamePlayer is playing.
     *
     * @return true if the GamePlayer is playing, false otherwise
     */
    boolean isPlaying();

    /**
     * Returns whether the GamePlayer is dead.
     *
     * @return true if the GamePlayer is dead, false otherwise
     */
    boolean isDead();

    /**
     * Sets whether the player should be dead or not.
     *
     * @param dead true if the GamePlayer is dead, false otherwise
     */
    void setDead(boolean dead);

    /**
     * Sends a message to the GamePlayer.
     *
     * @param message the message to send
     */
    void sendMessage(String message);

    /**
     * Sends a list of messages to the GamePlayer.
     *
     * @param messages the list of messages to send
     */
    void sendMessages(List<String> messages);

    /**
     * Sends a list of messages to the GamePlayer.
     *
     * @param messages the list of messages to send
     */
    void sendMessages(String... messages);

    /**
     * Sends a title to the GamePlayer.
     *
     * @param title    the title to send
     * @param subtitle the subtitle to send
     * @param fadeIn   the fade in time
     * @param stay     the stay time
     * @param fadeOut  the fade out time
     */
    void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut);

    /**
     * Sends an action bar message to the GamePlayer.
     *
     * @param message the message to send
     */
    void sendActionBar(String message);

    /**
     * Sends a sound to the GamePlayer.
     *
     * @param sound  the sound to send
     * @param volume the volume of the sound
     * @param pitch  the pitch of the sound
     */
    void sendSound(String sound, float volume, float pitch);

    <T extends ICustomPlayerData> T getCustomPlayerData(String id);

}
