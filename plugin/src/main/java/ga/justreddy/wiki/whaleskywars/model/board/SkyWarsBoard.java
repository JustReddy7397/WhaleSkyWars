package ga.justreddy.wiki.whaleskywars.model.board;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.creator.ScoreBoardCreator;
import ga.justreddy.wiki.whaleskywars.util.NumberUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class SkyWarsBoard {

    private final Map<UUID, Integer> data;

    public SkyWarsBoard() {
        this.data = new HashMap<>();
    }

    public void setLobbyBoard(IGamePlayer player) {
        if (player == null) return;
        if (!player.getPlayer().isPresent()) return;
        Player bukkitPlayer = player.getPlayer().get();

        TomlConfig config = WhaleSkyWars.getInstance().getScoreboardConfig();
        if (!config.getBoolean("lobby-board.enabled")) return;

        SimpleDateFormat dateFormat = new SimpleDateFormat(config.getString("date-format"));
        ScoreBoardCreator creator = new ScoreBoardCreator(player) {
            @Override
            public String setPlaceHolders(String line) {
                line = line.replace("{player}", player.getName());
                line = line.replace("{date}", dateFormat.format(System.currentTimeMillis()));
                line = line.replace("{wins}", String.valueOf(player.getStats().getTotalWins()));
                line = line.replace("{soloWins}", String.valueOf(player.getStats().getSoloWins()));
                line = line.replace("{teamWins}", String.valueOf(player.getStats().getTeamWins()));
                line = line.replace("{kills}", String.valueOf(player.getStats().getTotalKills()));
                line = line.replace("{soloKills}", String.valueOf(player.getStats().getSoloKills()));
                line = line.replace("{teamKills}", String.valueOf(player.getStats().getTeamKills()));
                line = line.replace("{deaths}", String.valueOf(player.getStats().getTotalDeaths()));
                line = line.replace("{soloDeaths}", String.valueOf(player.getStats().getSoloDeaths()));
                line = line.replace("{teamDeaths}", String.valueOf(player.getStats().getTeamDeaths()));
                // TODO coins
                line = line.replace("{coins}", "0");
                // TODO dust
                line = line.replace("{dust}", "0");

                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    line = PlaceholderAPI.setPlaceholders(bukkitPlayer, line);
                }
                return line;
            }
        };

        int taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(WhaleSkyWars.getInstance(), () -> {
            String title = config.getString("lobby-board.title");
            creator.setTitle(title);
            List<String> lines = config.getStringList("lobby-board.lines")
                    .stream().map(creator::setPlaceHolders).collect(Collectors.toList());
            creator.setLines(lines);
        }, 0, 100L).getTaskId();
        data.put(player.getUniqueId(), taskId);
    }

    public void setGameBoard(IGamePlayer player) {
        if (player == null) return;
        if (!player.getPlayer().isPresent()) return;
        Player bukkitPlayer = player.getPlayer().get();

        TomlConfig config = WhaleSkyWars.getInstance().getScoreboardConfig();
        if (!config.getBoolean("game-board.enabled")) return;

        SimpleDateFormat dateFormat = new SimpleDateFormat(config.getString("date-format"));

        ScoreBoardCreator creator = new ScoreBoardCreator(player) {
            @Override
            public String setPlaceHolders(String line) {
                IGame game = player.getGame();
                line = line.replace("{player}", player.getName());
                line = line.replace("{date}", dateFormat.format(System.currentTimeMillis()));
                line = line.replace("{kills}", String.valueOf(game.getKills(player)));
                String currentEvent = config.getString("events.no-event");

                switch (game.getGameState()) {
                    case WAITING:
                        currentEvent = config.getString("events.waiting");
                        break;
                    case STARTING:
                        currentEvent = config.getString("events.starting")
                                .replace("{time}", NumberUtil.toFormat(game.getPreGameTimer().getSeconds()));
                        break;
                    case PREGAME:
                        currentEvent = config.getString("events.pregame")
                                .replace("{time}", NumberUtil.toFormat(game.getPreGameTimer().getSeconds()));
                        break;
                    case PLAYING:
                        GameEvent event = game.getCurrentEvent();
                        if (event != null) {
                            currentEvent = config.getString("events." + event.getName())
                                    .replace("{time}", NumberUtil.toFormat(event.getTimer()));
                        }
                        line = line.replace("{team}", player.getGameTeam().getId());
                        break;
                    case ENDING:
                        currentEvent = config.getString("events.ending")
                                .replace("{time}", NumberUtil.toFormat(game.getEndingTimer().getSeconds()));
                        break;
                }
                line = line.replace("{event}", currentEvent);

                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    line = PlaceholderAPI.setPlaceholders(bukkitPlayer, line);
                }
                return line;
            }
        };

        int taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(WhaleSkyWars.getInstance(), () -> {
            String title = config.getString("game-board.title");
            creator.setTitle(title);
            List<String> lines = config.getStringList("game-board.lines")
                    .stream().map(creator::setPlaceHolders).collect(Collectors.toList());
            creator.setLines(lines);
        }, 0, 20L).getTaskId();
        data.put(player.getUniqueId(), taskId);

    }

    public void removeScoreboard(IGamePlayer player) {
        UUID uuid = player.getUniqueId();
        if (!data.containsKey(uuid)) return;
        player.getPlayer().ifPresent(bukkitPlayer -> bukkitPlayer.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard()));
        Bukkit.getServer().getScheduler().cancelTask(data.get(uuid));
        data.remove(uuid);
    }

}
