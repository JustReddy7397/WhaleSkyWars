package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.kits.exceptions.KitRequestException;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.KitRequest;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.requests.KitCreationRequest;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.requests.KitLayoutRequest;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.requests.KitModificationRequest;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.GameMode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class KitRequestManager {

    private final Map<UUID, KitRequest> requests;

    public KitRequestManager() {
        this.requests = new HashMap<>();
    }

    public KitRequest getKitRequest(IGamePlayer player) {
        return requests.getOrDefault(player.getUniqueId(), null);
    }

    public void makeRequest(IGamePlayer player, KitRequest request) throws KitRequestException {
        if (requests.containsKey(player.getUniqueId())) throw new KitRequestException(
                "Player " + player.getName() + "(" + player.getUniqueId() + ") already has a request"
        );
        requests.put(player.getUniqueId(), request);
        if (request instanceof KitModificationRequest) {
            ((KitModificationRequest) request).getKit().equipKit(player);
            player.getPlayer().ifPresent(p -> p.setGameMode(GameMode.CREATIVE));
            player.sendMessage(TextUtil.color("&cYou are now in the modification state of the kit editor, Please edit your inventory and when done run the command /ws kit savekit or /ws kit cancelkit to cancel your modification request."));
        } else if (request instanceof KitCreationRequest) {
            player.getPlayer().ifPresent(p -> {
                p.getInventory().clear();
                p.setGameMode(GameMode.CREATIVE);
            });
            player.sendMessage(TextUtil.color("&cYou are now in the creation state of the kit editor, Please setup your inventory and when done run the command /ws kit savekit or /ws kit cancelkit to cancel your creation request."));
        } else if (request instanceof KitLayoutRequest) {
            throw new KitRequestException("Kit layout request is not supported yet");
        }
    }

    public void removeKitRequest(IGamePlayer player) {
        requests.remove(player.getUniqueId());
    }

}
