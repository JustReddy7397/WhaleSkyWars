package ga.justreddy.wiki.whaleskywars.tasks;

import ga.justreddy.wiki.whaleskywars.shared.PacketEmitter;

/**
 * @author JustReddy
 */
public class HeartbeatTask implements Runnable {

    private final PacketEmitter emitter;

    public HeartbeatTask(PacketEmitter emitter) {
        this.emitter = emitter;
    }

    @Override
    public void run() {
        emitter.sendHeartbeat();
    }
}
