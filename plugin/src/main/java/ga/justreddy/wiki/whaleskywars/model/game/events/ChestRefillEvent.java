package ga.justreddy.wiki.whaleskywars.model.game.events;

import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.ChestType;
import ga.justreddy.wiki.whaleskywars.model.game.Game;

/**
 * @author JustReddy
 */
public class ChestRefillEvent extends GameEvent {

    public ChestRefillEvent(boolean enabled) {
        super("refill", enabled);
    }

    @Override
    public void onEnable(IGame game) {
        setEnabled(false);
        ((Game)game).fillChests(ChestType.REFILL);
    }

    @Override
    public void onTick(IGame game) {
    }

    @Override
    public void onDisable(IGame game) {

    }

    @Override
    public ChestRefillEvent clone() {
        return new ChestRefillEvent(enabled);
    }

}
