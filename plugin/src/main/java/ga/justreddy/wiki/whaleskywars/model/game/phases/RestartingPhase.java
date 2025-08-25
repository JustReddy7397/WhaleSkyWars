package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author JustReddy
 */
public class RestartingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.RESTORING);

        World world = game.getWorld();
        WhaleSkyWars.getInstance().getNms().removeTeamNames(game);
        game.getPlayers().forEach(player -> {
            game.onGamePlayerLeave(player, true, true);
        });
        if (world != null) {



            world.getEntities().forEach(entity -> {
                if (entity instanceof Player) return;
                if (entity != null) {
                    entity.remove();
                }
            });


            game.reset();

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
