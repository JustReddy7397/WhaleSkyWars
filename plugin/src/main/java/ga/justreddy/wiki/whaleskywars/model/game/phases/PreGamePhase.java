package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameSpawn;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Cage;
import ga.justreddy.wiki.whaleskywars.model.game.Game;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author JustReddy
 */
public class PreGamePhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.PREGAME);
        game.getPreGameTimer().start();
        game.assignTeams();

        if (game.getWaitingSpawn() != null) {
            game.getWaitingCuboid().clear();

            game.getTeams().forEach(team -> {
                IGameSpawn gameSpawn = team.getGameSpawn();
                if (gameSpawn.isUsed()) return;
                gameSpawn.setUsed(true);

                if (!team.getPlayers().isEmpty()) {
                    IGamePlayer player = team.getPlayers().get(ThreadLocalRandom.current().nextInt(team.getPlayers().size()));
                    Cage cage = WhaleSkyWars.getInstance().getCageManager().getById(player.getCosmetics().getSelectedCage());
                    gameSpawn.setCage(cage);
                    team.spawnBalloon();
                    if (cage == null) return;
                    if (game.getGameMode().isTeamGame()) {
                        cage.createBig(team.getSpawnLocation());
                    } else {
                        cage.createSmall(team.getSpawnLocation());
                    }

                    team.getPlayers().forEach(gamePlayer -> {
                        gamePlayer.getPlayer().ifPresent(player1 -> {
                            player1.teleport(team.getSpawnLocation());
                        });
                    });
                }
            });
        }
    }

    @Override
    public void onTick(IGame game) {

        AbstractTimer timer = game.getPreGameTimer();

        if (game.getPlayerCount() <= 0) {
            timer.stop();
            ((Game) game).getPhaseHandler().setPhase(new RestartingPhase());
            return;
        }


        if (timer.getTicksExceed() <= 0) {
            timer.stop();
            game.goToNextPhase();
        }
    }

    @Override
    public IPhase getNextPhase() {
        return new PlayingPhase();
    }
}
