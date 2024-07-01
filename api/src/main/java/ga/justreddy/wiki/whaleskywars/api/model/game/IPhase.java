package ga.justreddy.wiki.whaleskywars.api.model.game;

/**
 * @author JustReddy
 */
public interface IPhase {

    void onEnable(IGame game);

    void onTick(IGame game);

    IPhase getNextPhase();

}
