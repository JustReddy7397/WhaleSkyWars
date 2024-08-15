package ga.justreddy.wiki.whaleskywars.model.kits.requests;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * @author JustReddy
 */

public interface KitRequest {

    IGamePlayer getGamePlayer();

    String getKitName();

}
