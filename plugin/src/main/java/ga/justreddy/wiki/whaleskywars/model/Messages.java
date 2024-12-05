package ga.justreddy.wiki.whaleskywars.model;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.util.Replaceable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public enum Messages {

    GENERAL_BUILDING_ENABLED("general.building.enabled"),
    GENERAL_BUILDING_DISABLED("general.building.disabled"),
    GENERAL_NO_PERMISSION("general.no-permission"),
    GENERAL_PLAYER_ONLY("general.player-only"),
    GENERAL_LOBBY_SET("general.lobby-set"),
    GENERAL_RELOADED("general.reloaded"),
    GAME_JOINED("game.joined"),
    GAME_LEFT("game.left"),
    GAME_STARTING("game.starting"),
    GAME_STARTED("game.started"),
    GAME_ENDED("game.ended"),
    GAME_WINNERS("game.winners"),
    GAME_DEATH_VOID("game.death.void"),
    GAME_DEATH_FALL("game.death.fall"),
    GAME_DEATH_FIRE("game.death.fire"),
    GAME_DEATH_EXPLOSION("game.death.explosion"),
    GAME_DEATH_DROWNED("game.death.drowned"),
    GAME_DEATH_SUFFOCATION("game.death.suffocation"),
    GAME_DEATH_UNKNOWN("game.death.unknown"),
    ERROR_PLAYER_ONLY("error.player-only"),
    ERROR_PLAYER_NOT_FOUND("error.player-not-found"),
    ERROR_INVALID_COMMAND("error.invalid-command"),
    ERROR_INVALID_PERMISSION("error.invalid-permission"),
    ERROR_NO_LOBBY("error.no-lobby"),
    SETUP_CREATED("setup.created"),
    SETUP_ALREADY_IN_PROGRESS("setup.already-in-progress"),
    SETUP_NOT_IN_PROGRESS("setup.not-in-progress"),
    SETUP_NOT_EXISTS("setup.not-exists"),
    SETUP_ALREADY_CREATED("setup.already-created"),
    SETUP_SUCCESS("setup.success"),
    SETUP_MISSING_COMPONENTS("setup.missing-components"),
    SETUP_DISPLAYNAME_SET("setup.displayname-set"),
    SETUP_TEAMSIZE_SET("setup.teamsize-set"),
    SETUP_MIN_PLAYERS_SET("setup.min-players-set"),
    SETUP_WAITINGSPAWN_SET("setup.waitingspawn-set"),
    SETUP_SPECTATORSPAWN_SET("setup.spectatorspawn-set"),
    SETUP_ISLAND_CREATED("setup.island.created"),
    SETUP_ISLAND_DELETED("setup.island.deleted"),
    SETUP_ISLAND_SPAWN_SET("setup.island.spawn-set"),
    SETUP_ISLAND_BALLOON_SET("setup.island.balloon-set"),
    SETUP_ISLAND_CHEST_ADD("setup.island.chest-add"),
    SETUP_ISLAND_CHEST_REMOVE("setup.island.chest-remove"),
    SETUP_ISLAND_CHEST_ALREADY_EXISTS("setup.island.chest-already-exists"),
    SETUP_ISLAND_NOT_FOUND("setup.island.not-found"),
    SETUP_ISLAND_ALREADY_EXISTS("setup.island.already-exists"),
    SETUP_BOUNDS_WAITING_HIGH("setup.bounds.waiting.high"),
    SETUP_BOUNDS_WAITING_LOW("setup.bounds.waiting.low"),
    SETUP_BOUNDS_GAME_HIGH("setup.bounds.game.high"),
    SETUP_BOUNDS_GAME_LOW("setup.bounds.game.low"),
    SETUP_CHEST_NOT_FOUND("setup.chest.not-found"),
    SETUP_CHEST_ADDED("setup.chest.added"),
    SETUP_CHEST_REMOVED("setup.chest.removed"),
    SETUP_CHEST_ALREADY_ADDED("setup.chest.already-added"),
    SETUP_CHEST_NOT_CHEST("setup.chest.not-chest"),
    SETUP_ISLAND_CHEST_ADDED("setup.island.chest-added"),
    SETUP_ISLAND_CHEST_REMOVED("setup.island.chest-removed"),
    SETUP_ISLAND_CHEST_ALREADY_ADDED("setup.island.chest-already-added"),
    SETUP_ISLAND_CHEST_NOT_FOUND("setup.island.chest-not-found"),
    SETUP_STATUS("setup.satus"),
    SIGN_CHOOSE_GAME("sign.choose-game"),
  ;

    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String toString(Replaceable... replaceables) {
        String prefix = WhaleSkyWars.getInstance().getMessagesConfig().getString("prefix");
        String message = prefix.isEmpty() ? WhaleSkyWars.getInstance().getMessagesConfig().getString(path) : prefix + WhaleSkyWars.getInstance().getMessagesConfig().getString(path);
        for (Replaceable replaceable : replaceables) {
            if (replaceable.getKey() == null || replaceable.getValue() == null) continue;
            message = message.replace(replaceable.getKey(), replaceable.getValue());
        }
        return message;
    }

    public String toString(Player player, Replaceable... replaceables) {
        String prefix = WhaleSkyWars.getInstance().getMessagesConfig().getString("prefix");
        String message = prefix.isEmpty() ? WhaleSkyWars.getInstance().getMessagesConfig().getString(path) : prefix + WhaleSkyWars.getInstance().getMessagesConfig().getString(path);
        for (Replaceable replaceable : replaceables) {
            if (replaceable.getKey() == null || replaceable.getValue() == null) continue;
            message = message.replace(replaceable.getKey(), replaceable.getValue());
        }
        if (WhaleSkyWars.getInstance().isHooked("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

    @Override
    public String toString() {
        return toString(Replaceable.of(null, null));
    }

    public List<String> toList(Replaceable... replaceables) {
        String prefix = WhaleSkyWars.getInstance().getMessagesConfig().getString("prefix");
        List<String> messages = WhaleSkyWars.getInstance().getMessagesConfig().getStringList(path);
        return messages.stream().map(message -> {
            for (Replaceable replaceable : replaceables) {
                message = message.replace(replaceable.getKey(), replaceable.getValue());
            }
            return prefix.isEmpty() ? message : prefix + message;
        }).collect(Collectors.toList());
    }


}
