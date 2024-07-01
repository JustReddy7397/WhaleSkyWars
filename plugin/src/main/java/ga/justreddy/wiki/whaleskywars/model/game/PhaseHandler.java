package ga.justreddy.wiki.whaleskywars.model.game;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;

/**
 * @author JustReddy
 */
public class PhaseHandler {

    private final IGame game;

    private IPhase currentPhase;

    public PhaseHandler(IGame game) {
        this.game = game;
    }

    public boolean isInPhase(Class<? extends IPhase> phaseClass) {
        return currentPhase.getClass().equals(phaseClass);
    }

    public void nextPhase() {
        IPhase phase = currentPhase.getNextPhase();
        if (phase == null) return;
        setPhase(phase);
    }

    public void setPhase(IPhase phase) {
        currentPhase = phase;
        currentPhase.onEnable(game);
    }

    public void onTick() {
        if (currentPhase == null) return;
        currentPhase.onTick(game);
    }

}
