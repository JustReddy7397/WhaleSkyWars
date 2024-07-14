package ga.justreddy.wiki.whaleskywars.model.kits;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.util.ItemStackBuilder;
import ga.justreddy.wiki.whaleskywars.util.NumberUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JustReddy
 */
@Getter
@Setter
public class Kit {

    private String name;
    private List<ItemStack> kitItems;
    private List<ItemStack> armorItems;
    private ItemStack guiKitItem;
    private boolean isDefault;

    private Kit() {
    }

    private Kit(String name) {
        this.name = name;
    }

    public Kit(String name, List<ItemStack> kitItems, List<ItemStack> armorItems, XMaterial kitItemType) {
        this(name, kitItems, armorItems, kitItemType, false);
    }

    public Kit(String name, List<ItemStack> kitItems, List<ItemStack> armorItems, XMaterial kitItemType, boolean isDefault) {
        this.name = name;
        this.kitItems = kitItems;
        this.armorItems = armorItems;
        this.isDefault = isDefault;
        setGuiKitItem(kitItemType);
    }

    public void equipKit(IGamePlayer player) {

        // TODO

    }

    public void setGuiKitItem(XMaterial kitItemType) {
        ItemStackBuilder builder = new ItemStackBuilder(kitItemType.parseItem());
        builder.withFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        // TODO
        builder.withName("&b" + TextUtil.uppercaseFirstLetter(name));
        List<String> lore = new ArrayList<>();
        if (!armorItems.isEmpty()) {
            for (ItemStack itemStack : armorItems) {
                List<String> itemNameList = new ArrayList<>();
                XMaterial material = XMaterial.matchXMaterial(itemStack);
                itemNameList.add(TextUtil.nameOf(material));
                if (itemStack.hasItemMeta()) {
                    ItemMeta meta = itemStack.getItemMeta();
                    if (meta.hasEnchants()) {
                        meta.getEnchants().forEach((enchantment, integer) -> itemNameList.add(
                                "● " + TextUtil.nameOf(enchantment) + " " + integer));
                    }
                }
                itemNameList.forEach(string -> lore.add("&7" + string));
            }
        }

        if (!kitItems.isEmpty()) {

            for (ItemStack item : kitItems) {
                int amount = item.getAmount();
                List<String> itemNameList = new ArrayList<>();
                XMaterial material = XMaterial.matchXMaterial(item);
                String itemName = amount > 1 ?
                        TextUtil.nameOf(material) + " x" + amount : TextUtil.nameOf(material);

                itemNameList.add(itemName);
                if (item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta.hasEnchants()) {
                        meta.getEnchants().forEach((enchantment, integer) ->
                                itemNameList.add("● " + TextUtil.nameOf(enchantment) + " " + integer));
                    }
                }

                if (item.getType() == Material.ENCHANTED_BOOK) {
                    if (item.hasItemMeta()) {
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                        if (meta.hasStoredEnchants()) {
                            meta.getStoredEnchants().forEach((enchantment, integer) ->
                                    itemNameList.add("● " + TextUtil.nameOf(enchantment) + " " + integer));
                        }
                    }
                }

                if (item.getType() == Material.POTION && item.hasItemMeta()) {
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    if (meta.hasCustomEffects()) {
                        meta.getCustomEffects().forEach(potionEffect -> {
                            itemNameList.add(
                                    "● " +
                                            TextUtil.uppercaseFirstLetter(
                                                    potionEffect.getType().getName()
                                            )
                            );
                            itemNameList.add("● Level: " + (potionEffect.getAmplifier() + 1));
                            itemNameList.add("● Duration: " + NumberUtil.toFormat(potionEffect.getDuration() / 20));
                        });
                    }
                }
                itemNameList.forEach(string -> lore.add("&7" + string));
            }
        }

        builder.withLore(lore);
        guiKitItem = builder.build();
    }

    public static Kit fromPlayerInventory(Player player, ItemStack kitItem, String kitName) {
        Kit kit = new Kit(kitName);
        PlayerInventory inv = player.getInventory();

        kit.setArmorItems(new ArrayList<>(Arrays.asList(inv.getArmorContents())));

        List<ItemStack> kitItems = new ArrayList<>();
        for (int i = 0; i <= 35; i++) {
            ItemStack item = inv.getItem(i);
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    kitItems.add(item);
                }
            }
        }
        kit.setKitItems(kitItems);
        kit.setGuiKitItem(XMaterial.matchXMaterial(kitItem));
        return kit;
    }

}
