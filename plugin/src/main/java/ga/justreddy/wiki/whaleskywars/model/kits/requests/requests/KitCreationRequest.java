package ga.justreddy.wiki.whaleskywars.model.kits.requests.requests;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.KitRequest;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
@Getter
@Setter
public class KitCreationRequest implements KitRequest {

    private final IGamePlayer player;
    private final String kitName;
    private final ItemStack material;

    private List<ItemStack> armorItems;
    private List<ItemStack> kitItems;

    private boolean isDefault;

    public KitCreationRequest(IGamePlayer player, String kitName, ItemStack kitMaterial) {
        this.player = player;
        this.kitName = kitName;
        this.material = kitMaterial;
    }

    public Kit toKit() {
        return new Kit(kitName, kitItems != null ? kitItems : new ArrayList<>(), armorItems != null ? armorItems : new ArrayList<>(), XMaterial.matchXMaterial(material), isDefault);
    }

    @Override
    public IGamePlayer getGamePlayer() {
        return player;
    }

    public String getKitName() {
        return kitName;
    }
}
