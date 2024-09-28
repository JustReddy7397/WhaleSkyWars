package ga.justreddy.wiki.whaleskywars.api.model.game.enums;

import java.io.Serializable;

/**
 * @author JustReddy
 */
public enum GameState implements Serializable {

    WAITING,
    STARTING,
    PREGAME,
    PLAYING,
    ENDING,
    RESTORING,
    DISABLED

}
