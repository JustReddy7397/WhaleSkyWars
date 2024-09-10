package ga.justreddy.wiki.whaleskywars.model.game.events;

import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;

/**
 * @author JustReddy
 */
public class DoomEvent extends GameEvent {

    public DoomEvent(boolean enabled) {
        super("doom", enabled);
    }

    private EnderDragon dragon;

    @Override
    public void onEnable(IGame game) {
        setEnabled(false);
        World world = game.getWorld();
        dragon = world.spawn(game.getSpectatorSpawn(), EnderDragon.class);
    }

    @Override
    public void onTick(IGame game) {
        if (dragon != null && dragon.isDead()) {
            dragon = null;
        }
    }

    @Override
    public void onDisable(IGame game) {
        if (dragon != null) {
            dragon.remove();
        }
    }

    @Override
    public GameEvent clone() {
        return new DoomEvent(enabled);
    }
}
