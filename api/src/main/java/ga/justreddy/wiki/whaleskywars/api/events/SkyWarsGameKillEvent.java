package ga.justreddy.wiki.whaleskywars.api.events;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * @author JustReddy
 */
public class SkyWarsGameKillEvent extends SkyWarsEvent {

    private final IGamePlayer killer;
    private final IGamePlayer killed;

    public SkyWarsGameKillEvent(IGamePlayer killer, IGamePlayer killed) {
        this.killer = killer;
        this.killed = killed;
    }

    public IGamePlayer getKiller() {
        return killer;
    }

    public IGamePlayer getKilled() {
        return killed;
    }

}
