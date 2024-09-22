package ga.justreddy.wiki.whaleskywars.model.action.actions;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import ga.justreddy.wiki.whaleskywars.support.packets.packets.JoinPacket;
import ga.justreddy.wiki.whaleskywars.util.ShuffleUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class JoinAction implements IAction {

    // TODO finish this

    @Override
    public String getIdentifier() {
        return "JOIN";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        if (plugin.getServerMode() == ServerMode.LOBBY) {
            joinFromLobby(plugin, player, data);
        } else if (plugin.getServerMode() == ServerMode.BUNGEE) {
            joinFromBungee(plugin, player, data);
        } else if (plugin.getServerMode() == ServerMode.MULTI_ARENA) {
            IGame playerGame = player.getGame();
            List<IGame> games = plugin.getGameManager()
                    .getGamesBySimilarName(data);
            if (games.isEmpty()) {
                player.sendMessage("&cNo game found with the name " + data);
                // TODO
                return;
            }
            ShuffleUtil.shuffle(games);
            IGame game = games.get(0);
            if (playerGame != null) {
                playerGame.onGamePlayerLeave(player, !playerGame.isGameState(GameState.WAITING) || !playerGame.isGameState(GameState.STARTING), false);
            }
            game.onGamePlayerJoin(player);
        }
    }

    private void joinFromLobby(WhaleSkyWars plugin, IGamePlayer player, String data) {
        if (!data.isEmpty()) {
            List<IGame> games = plugin.getGameManager()
                    .getGamesBySimilarName(data);
            if (games.isEmpty()) {
                player.sendMessage("&cNo game found with the name " + data);
                // TODO
                return;
            }
            List<BungeeGame> bungeeGames = games.stream().map(game -> ((Game) game).getBungeeGame())
                    .collect(Collectors.toList());
            if (bungeeGames.isEmpty()) {
                player.sendMessage("&cNo game found with the name " + data);
                // TODO
                return;
            }

            ShuffleUtil.shuffleBungee(bungeeGames);
            BungeeGame bungeeGame = bungeeGames.get(0);
            if (bungeeGame.isFull()) {
                player.sendMessage("&cThe game " + bungeeGame.getName() + " is full");
                // TODO
                return;
            }
            JoinPacket packet = new JoinPacket(bungeeGame, player.getUniqueId(), player.getName(), true, false);
            plugin.getMessenger().getSender().sendPacket(packet);
        } else {
            IGame game = plugin.getGameManager().getRandomGame();
            if (game == null) {
                player.sendMessage("&cNo game found");
                // TODO
                return;
            }
            BungeeGame bungeeGame = ((Game) game).getBungeeGame();
            if (bungeeGame.isFull()) {
                player.sendMessage("&cThe game " + bungeeGame.getName() + " is full");
                // TODO
                return;
            }
            JoinPacket packet = new JoinPacket(bungeeGame, player.getUniqueId(), player.getName(), true, false);
            plugin.getMessenger().getSender().sendPacket(packet);
        }
    }

    private void joinFromBungee(WhaleSkyWars plugin, IGamePlayer player, String data) {
        IGame playerGame = player.getGame();
        if (!data.isEmpty()) {
            List<IGame> games = plugin.getGameManager()
                    .getGamesBySimilarName(data);
            if (games.isEmpty()) {
                player.sendMessage("&cNo game found with the name " + data);
                // TODO
                return;
            }
            List<BungeeGame> bungeeGames = games.stream().map(game -> ((Game) game).getBungeeGame())
                    .collect(Collectors.toList());
            if (bungeeGames.isEmpty()) {
                player.sendMessage("&cNo game found with the name " + data);
                // TODO
                return;
            }

            ShuffleUtil.shuffleBungee(bungeeGames);
            BungeeGame bungeeGame = bungeeGames.get(0);
            if (bungeeGame.isFull()) {
                player.sendMessage("&cThe game " + bungeeGame.getName() + " is full");
                // TODO
                return;
            }
            if (playerGame != null) {
                playerGame.onGamePlayerLeave(player, !playerGame.isGameState(GameState.WAITING) || !playerGame.isGameState(GameState.STARTING), false);
            }
            JoinPacket packet = new JoinPacket(bungeeGame, player.getUniqueId(), player.getName(), true, false);
            plugin.getMessenger().getSender().sendPacket(packet);
        } else {
            IGame game = plugin.getGameManager().getRandomGame();
            if (game == null) {
                player.sendMessage("&cNo game found");
                // TODO
                return;
            }
            BungeeGame bungeeGame = ((Game) game).getBungeeGame();
            if (bungeeGame.isFull()) {
                player.sendMessage("&cThe game " + bungeeGame.getName() + " is full");
                // TODO
                return;
            }
            if (playerGame != null) {
                playerGame.onGamePlayerLeave(player, !playerGame.isGameState(GameState.WAITING) || !playerGame.isGameState(GameState.STARTING), false);
            }
            JoinPacket packet = new JoinPacket(bungeeGame, player.getUniqueId(), player.getName(), true, false);
            plugin.getMessenger().getSender().sendPacket(packet);
        }
    }

}
