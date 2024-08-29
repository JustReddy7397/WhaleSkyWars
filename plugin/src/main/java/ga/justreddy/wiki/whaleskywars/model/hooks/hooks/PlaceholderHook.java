package ga.justreddy.wiki.whaleskywars.model.hooks.hooks;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerStats;
import ga.justreddy.wiki.whaleskywars.manager.PlayerManager;
import ga.justreddy.wiki.whaleskywars.model.hooks.IHook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author JustReddy
 */
public class PlaceholderHook extends PlaceholderExpansion implements IHook {

    private final PlayerManager manager;

    public PlaceholderHook(PlayerManager manager) {
        this.manager = manager;
    }

    @Override
    public String getHookId() {
        return "PlaceholderAPI";
    }

    @Override
    public boolean canHook() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public void hook(WhaleSkyWars plugin) {
        register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "wsw";
    }

    @Override
    public @NotNull String getAuthor() {
        return "JustReddy";
    }

    @Override
    public @NotNull String getVersion() {
        return WhaleSkyWars.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        IGamePlayer gamePlayer = manager.get(player.getUniqueId());
        IPlayerStats stats = gamePlayer.getStats();

        String[] args = params.split("_");
        switch (args[0]) {
            case "kills":
                if (args.length > 1) {
                    switch (args[1]) {
                        case "solo":
                            return String.valueOf(stats.getSoloKills());
                        case "team":
                            return String.valueOf(stats.getTeamKills());
                    }
                }
                return String.valueOf(stats.getTotalKills());
            case "deaths":
            case "losses":
                if (args.length > 1) {
                    switch (args[1]) {
                        case "solo":
                            return String.valueOf(stats.getSoloDeaths());
                        case "team":
                            return String.valueOf(stats.getTeamDeaths());
                    }
                }
                return String.valueOf(stats.getTotalDeaths());
            case "wins":
                if (args.length > 1) {
                    switch (args[1]) {
                        case "solo":
                            return String.valueOf(stats.getSoloWins());
                        case "team":
                            return String.valueOf(stats.getTeamWins());
                    }
                }
                return String.valueOf(stats.getTotalWins());
            case "arrowshit":
                return String.valueOf(stats.getArrowsHit());
            case "arrowsshot":
                return String.valueOf(stats.getArrowsShot());
            case "blocksplaced":
                return String.valueOf(stats.getBlocksPlaced());
            case "blocksbroken":
                return String.valueOf(stats.getBlocksBroken());
            case "damage":
                if (args.length > 1) {
                    switch (args[1]) {
                        case "dealt":
                            return String.valueOf(stats.getDamageDealt());
                        case "taken":
                            return String.valueOf(stats.getDamageTaken());
                    }
                }
                return "";
            case "played":
                if (args.length > 1) {
                    switch (args[1]) {
                        case "solo":
                            return String.valueOf(stats.getSoloGamesPlayed());
                        case "team":
                            return String.valueOf(stats.getTeamGamesPlayed());
                    }
                }
                return String.valueOf(stats.getTotalGamesPlayed());
            case "travelled":
                return String.valueOf(stats.getTravelledBlocks());
            case "name":
                return player.getName();
        }

        return "";
    }
}
