package ga.justreddy.wiki.whaleskywars.model.chests;

import com.cryptomorin.xseries.XMaterial;
import com.moandjiezana.toml.Toml;
import ga.justreddy.wiki.whaleskywars.api.model.chest.ILootItem;
import ga.justreddy.wiki.whaleskywars.model.config.CustomTomlReader;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class LootItem implements ILootItem {

    private final Material material;
    private final Byte id;
    private final Map<Enchantment, Integer> enchants;
    private final double chance;
    private final int amount;

    public LootItem(CustomTomlReader tomlReader) {
        this.material = XMaterial.matchXMaterial(
                tomlReader.getString("material"))
                .orElseGet(() -> XMaterial.AIR)
                .parseMaterial();
        this.id = (byte) tomlReader.getInt("id");
        this.enchants = new HashMap<>();

        CustomTomlReader enchantments = tomlReader.getTable("enchants");
        if (enchantments != null && !enchantments.entries().isEmpty()) {
            for (Map.Entry<String, Object> entry : enchantments.getTable().entrySet()) {
                Enchantment enchantment = Enchantment.getByName(entry.getKey());
                if (enchantment != null) {
                    enchants.put(enchantment, (int) entry.getValue());
                }
            }
        }

        this.chance = tomlReader.getDouble("chance");
        this.amount = tomlReader.getInt("amount");
    }

    public LootItem(ConfigurationSection section) {
        this.material = XMaterial.matchXMaterial(
                section.getString("material"))
                .orElseGet(() -> XMaterial.AIR)
                .parseMaterial();
        this.id = (byte) section.getInt("id");
        this.enchants = new HashMap<>();

        ConfigurationSection enchantments = section.getConfigurationSection("enchants");
        if (enchantments != null && !enchantments.getKeys(false).isEmpty()) {
            for (String key : enchantments.getKeys(false)) {
                Enchantment enchantment = Enchantment.getByName(key);
                if (enchantment != null) {
                    enchants.put(enchantment, enchantments.getInt(key));
                }
            }
        }

        this.chance = section.getDouble("chance");
        this.amount = section.getInt("amount");
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setDurability(id);
        itemStack.addUnsafeEnchantments(enchants);
        return itemStack;
    }

    @Override
    public double getChance() {
        return chance;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}