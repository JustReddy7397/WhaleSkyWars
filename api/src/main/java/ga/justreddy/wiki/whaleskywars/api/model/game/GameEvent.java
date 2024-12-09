package ga.justreddy.wiki.whaleskywars.api.model.game;

import ga.justreddy.wiki.whaleskywars.api.model.Addon;

/**
 * @author JustReddy
 */
public abstract class GameEvent implements Cloneable, Addon {

    protected final String name;
    protected boolean enabled;
    protected int timer;

    public GameEvent(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public abstract void onEnable(IGame game);

    public abstract void onTick(IGame game);

    public abstract void onDisable(IGame game);

    public abstract GameEvent clone();

    public final void update() {
        if (timer > 0) timer--;
    }

    public final boolean isEnded() {
        return timer <= 0;
    }

    public final int getTimer() {
        return timer;
    }

    public final void setTimer(int timer) {
        this.timer = timer;
    }

    public final String getName() {
        return name;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected Object cloneCommon() throws CloneNotSupportedException {
        return super.clone();
    }


}
