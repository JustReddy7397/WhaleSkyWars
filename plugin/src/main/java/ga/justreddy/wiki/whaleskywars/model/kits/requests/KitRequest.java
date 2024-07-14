package ga.justreddy.wiki.whaleskywars.model.kits.requests;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * @author JustReddy
 */

@FunctionalInterface
public interface KitRequest {

    IGamePlayer getGamePlayer();

}
