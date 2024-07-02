package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import org.bukkit.World;

/**
 * @author JustReddy
 */
public class RestartingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.RESTORING);

        ServerMode mode = WhaleSkyWars.getInstance().getServerMode();

        World world = game.getWorld();
        if (world != null) {

            world.getPlayers().forEach(player -> {
                if (mode == ServerMode.BUNGEE) {
                    // TODO send back to lobby
                } else if (mode == ServerMode.MULTI_ARENA) {
                    // TODO send back to lobby
                }
            });

            world.getEntities().forEach(entity -> {
                if (entity != null) {
                    entity.remove();
                }
            });

            WhaleSkyWars.getInstance().getGameMap().onRestart(game);

        }

    }

    @Override
    public void onTick(IGame game) {

    }

    @Override
    public IPhase getNextPhase() {
        return null;
    }
}
