package ga.justreddy.wiki.whaleskywars.api.model.entity;

/**
 * @author JustReddy
 */
public interface ICombatLog {

    IGamePlayer getAttacker();

    IGamePlayer getTarget();

    boolean hasTarget();;

    void setTarget(IGamePlayer target);

    boolean isTimeCorrect();

}
