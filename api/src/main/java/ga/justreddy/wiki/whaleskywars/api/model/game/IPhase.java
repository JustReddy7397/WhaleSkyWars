package ga.justreddy.wiki.whaleskywars.api.model.game;

/**
 * @author JustReddy
 */
public interface IPhase {

    /**
     * Called when the phase is enabled
     * @param game the game
     */
    void onEnable(IGame game);

    /**
     * Called every second
     * @param game the game
     */
    void onTick(IGame game);

    /**
     * Gets the next phase
     */
    IPhase getNextPhase();

}
