package ga.justreddy.wiki.whaleskywars.model.entity;

import ga.justreddy.wiki.whaleskywars.api.model.entity.ICombatLog;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * @author JustReddy
 */
public class CombatLog implements ICombatLog {

    private final IGamePlayer attacker;
    private IGamePlayer target;
    private long time;

    public CombatLog(IGamePlayer attacker) {
        this.attacker = attacker;
        this.target = null;
    }

    @Override
    public IGamePlayer getAttacker() {
        return attacker;
    }

    @Override
    public IGamePlayer getTarget() {
        return target;
    }

    @Override
    public boolean hasTarget() {
        return target != null;
    }

    @Override
    public void setTarget(IGamePlayer target) {
        this.target = target;
        this.time = System.currentTimeMillis();
    }

    @Override
    public boolean isTimeCorrect() {
        return (System.currentTimeMillis() - this.time) / 1000L < 8L;
    }
}
