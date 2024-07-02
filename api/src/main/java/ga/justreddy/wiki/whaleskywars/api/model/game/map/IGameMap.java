package ga.justreddy.wiki.whaleskywars.api.model.game.map;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.bukkit.World;

/**
 * @author JustReddy
 */
public interface IGameMap {

    void onEnable(IGame game);

    void onRestart(IGame game);

    void onDisable(IGame game);

}
