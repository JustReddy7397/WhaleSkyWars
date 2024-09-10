package ga.justreddy.wiki.whaleskywars.model.game.modes;

import ga.justreddy.wiki.whaleskywars.api.model.game.GameMode;

/**
 * @author JustReddy
 */
public class TeamGameMode extends GameMode {
    @Override
    public String getIdentifier() {
        return "team";
    }

    @Override
    public String getDisplayName() {
        return "Team";
    }

}
