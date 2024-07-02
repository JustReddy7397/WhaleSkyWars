package ga.justreddy.wiki.whaleskywars.model.cosmetics;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Cosmetic;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IBalloon;
import ga.justreddy.wiki.whaleskywars.util.ItemStackBuilder;
import ga.justreddy.wiki.whaleskywars.util.LocationUtil;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LeashHitch;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author JustReddy
 */
@Getter
public class Balloon extends Cosmetic implements IBalloon {

    private final INms nms = WhaleSkyWars.getInstance().getNms();

    private final String texture;

    public Balloon(String name, int id, int cost, String texture) {
        super(name, id, cost);
        this.texture = texture;
    }

    @Override
    public void spawn(Location location) {
        // <3

        if (!location.getChunk().isLoaded()) {
            location.getChunk().load();
        }

        Giant giant = location.getWorld().spawn(location, Giant.class);
        giant.getEquipment().setItemInHand((new ItemStackBuilder(XMaterial.PLAYER_HEAD.parseItem())).setTexture(texture).build());
        giant.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0), false);
        giant.setCustomNameVisible(false);
        giant.setCanPickupItems(false);
        giant.setNoDamageTicks(Integer.MAX_VALUE);
        nms.removeAi(giant);

        Bat bat = location.getWorld().spawn(LocationUtil.getFixedSide(giant.getEyeLocation(), 4.0D).subtract(0.0D, 2.0D, 0.0D), Bat.class);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 999));
        bat.setCustomNameVisible(false);
        bat.setCanPickupItems(false);
        bat.setNoDamageTicks(Integer.MAX_VALUE);
        nms.removeAi(bat);

        Block block = location.clone().getBlock();
        block.setType(XMaterial.OAK_FENCE.parseMaterial());
        Block fixed = location.clone().add(0.0D, 0.0D, 1.0D).getBlock();
        BlockState fixedState = fixed.getState();
        XMaterial old = XMaterial.matchXMaterial(fixedState.getData().toItemStack());
        fixed.setType(Material.BARRIER);
        LeashHitch leash = location.getWorld().spawn(location.clone(), LeashHitch.class);
        bat.setLeashHolder(leash);
        fixed.setType(old.parseMaterial());
        if (nms.isLegacy()) {
            fixedState.setData(new MaterialData(old.parseMaterial(), old.getData()));
            fixedState.update();
        }

    }
}
