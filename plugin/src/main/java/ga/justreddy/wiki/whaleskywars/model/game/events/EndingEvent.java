package ga.justreddy.wiki.whaleskywars.model.game.events;

import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import ga.justreddy.wiki.whaleskywars.model.game.phases.EndingPhase;

/**
 * @author JustReddy
 */
public class EndingEvent extends GameEvent {

    public EndingEvent(boolean enabled) {
        super("ending", enabled);
    }

    @Override
    public void onEnable(IGame game) {
        ((Game)game).getPhaseHandler()
                .setPhase(new EndingPhase(null));
        setEnabled(false);
    }

    @Override
    public void onTick(IGame game) {}

    @Override
    public void onDisable(IGame game) {}

    @Override
    public GameEvent clone() {
        return new EndingEvent(enabled);
    }
}
