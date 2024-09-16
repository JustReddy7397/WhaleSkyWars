package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameSpawn;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author JustReddy
 */
public class PlayingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.PLAYING);

        game.getTeams().forEach(team -> {
            IGameSpawn spawn = team.getGameSpawn();
            if (spawn.getCage() == null) return;
            spawn.getCage().remove(team.getSpawnLocation());
        });
        game.getAlivePlayers().forEach(player -> {
            player.getPlayer().ifPresent(bukkitPlayer -> {
                Kit kit = WhaleSkyWars.getInstance().getKitManager().getKitByName(player.getCosmetics().getSelectedKit());
                if (kit == null) {
                    kit = WhaleSkyWars.getInstance().getKitManager().getDefaultKit();
                }
                kit.equipKit(player);
                bukkitPlayer.setGameMode(GameMode.SURVIVAL);
                bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 5));
            });
        });

    }

    @Override
    public void onTick(IGame game) {

        /*if (game.getAliveTeams().size() == 1) {
            ((Game)game).getPhaseHandler().setPhase(new EndingPhase(game.getAliveTeams().get(0)));
            return;
        } else if (game.getAliveTeams().isEmpty()) {
            game.goToNextPhase();
            return;
        }

        GameEvent event = game.getCurrentEvent();

        if (event == null) {
            if (game.getAliveTeams().size() == 1) {
                ((Game)game).getPhaseHandler().setPhase(new EndingPhase(game.getAliveTeams().get(0)));
            } else if (game.getAliveTeams().isEmpty()) {
                game.goToNextPhase();
            }
            return;
        }*/
        GameEvent event = game.getCurrentEvent();

        if (event == null) return;

        if (event.isEnabled()) event.update();

        if (event.isEnded()) {
            event.onEnable(game);
        }
    }

    @Override
    public IPhase getNextPhase() {
        return new EndingPhase(null);
    }
}
