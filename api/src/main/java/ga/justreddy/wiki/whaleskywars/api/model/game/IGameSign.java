package ga.justreddy.wiki.whaleskywars.api.model.game;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * @author JustReddy
 */
public interface IGameSign {

    String getId();

    Location getLocation();

    void update(IGame game);

    void updateBungee(Object bungeeGame);

    Block getRelativeBlock();

}
