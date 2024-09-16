package ga.justreddy.wiki.whaleskywars.model.action;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * An action that can be executed
 * This is used for the action system
 * The action system is used to execute actions based on a string
 * @author JustReddy
 */
public interface IAction {

    /**
     * Get the identifier of the action
     * @return the identifier
     */
    String getIdentifier();

    /**
     * Execute the action
     * @param plugin the plugin
     * @param player the player to execute the action for
     * @param data the data to execute
     */
    void onAction(WhaleSkyWars plugin, IGamePlayer player, String data);

}
