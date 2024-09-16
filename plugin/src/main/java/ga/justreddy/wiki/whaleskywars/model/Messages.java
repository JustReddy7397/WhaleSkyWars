package ga.justreddy.wiki.whaleskywars.model;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;

/**
 * @author JustReddy
 */
public enum Messages {

    GENERAL_BUILDING_ENABLED("general.building.enabled"),
    GENERAL_BUILDING_DISABLED("general.building.disabled"),
    GENERAL_NO_PERMISSION("general.no-permission"),
    GENERAL_PLAYER_ONLY("general.player-only"),
    GENERAL_LOBBY_SET("general.lobby-set"),
    GENERAL_RELOADED("general.reloaded");

    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        String prefix = WhaleSkyWars.getInstance().getMessagesConfig().getString("prefix");
        return prefix.isEmpty() ? WhaleSkyWars.getInstance().getMessagesConfig().getString(path) : prefix + WhaleSkyWars.getInstance().getMessagesConfig().getString(path);
    }

}
