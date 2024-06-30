package ga.justreddy.wiki.whaleskywars.api.model.game.team;

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
}
