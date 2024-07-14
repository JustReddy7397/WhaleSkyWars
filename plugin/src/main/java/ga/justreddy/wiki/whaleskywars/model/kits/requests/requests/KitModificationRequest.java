package ga.justreddy.wiki.whaleskywars.model.kits.requests.requests;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.KitRequest;
import lombok.Getter;

/**
 * @author JustReddy
 */
@Getter
public class KitModificationRequest implements KitRequest {


    private final IGamePlayer player;
    private final Kit kit;

    public KitModificationRequest(IGamePlayer player, Kit kit) {
        this.player = player;
        this.kit = kit;
    }

    @Override
    public IGamePlayer getGamePlayer() {
        return player;
    }
}
