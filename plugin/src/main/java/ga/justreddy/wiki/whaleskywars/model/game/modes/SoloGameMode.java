package ga.justreddy.wiki.whaleskywars.model.game.modes;

import ga.justreddy.wiki.whaleskywars.api.model.game.GameMode;

/**
 * @author JustReddy
 */
public class SoloGameMode extends GameMode {
    @Override
    public String getIdentifier() {
        return "solo";
    }

    @Override
    public String getDisplayName() {
        return "Solo";
    }

}
