package ga.justreddy.wiki.whaleskywars.api.model.entity;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents a GamePlayer in the WhaleSkyWars game.
 * A GamePlayer is a player participating in a game.
 * Each GamePlayer has a unique id, a name, a Player object, a Game object, and states for playing and being dead.
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
     * Returns the Game object of the GamePlayer.
     *
     * @return the Game object of the GamePlayer
     */
    IGame getGame();

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

}
