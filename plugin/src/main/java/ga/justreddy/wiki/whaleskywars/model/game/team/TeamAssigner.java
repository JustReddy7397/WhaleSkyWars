package ga.justreddy.wiki.whaleskywars.model.game.team;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ITeamAssigner;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class TeamAssigner implements ITeamAssigner {

    private final Set<UUID> skip = new HashSet<>();

    @Override
    public void assign(IGame game) {



    }
}
