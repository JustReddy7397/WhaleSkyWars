package ga.justreddy.wiki.whaleskywars.api.model.game.team;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;

/**
 * Represents a TeamAssigner in the WhaleSkyWars game.
 * A TeamAssigner is responsible for assigning teams in the game.
 * Each TeamAssigner has a method to assign teams for a game.
 *
 * @author JustReddy
 */
public interface ITeamAssigner {

    /**
     * Assigns teams for a game.
     *
     * @param game the game to assign teams for
     */
    void assign(IGame game);

    /**
     * Assigns a player to a team in a game.
     * @param game the game to assign the player to
     * @param player the player to assign to a team
     */
    void assign(IGame game, IGamePlayer player);

}
