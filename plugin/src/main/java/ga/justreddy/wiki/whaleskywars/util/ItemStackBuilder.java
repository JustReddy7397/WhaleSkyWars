package ga.justreddy.wiki.whaleskywars.util;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.profiles.builder.ProfileInstruction;
import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.ProfileInputType;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.SneakyThrows;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class ItemStackBuilder {

    private ItemStack ITEM_STACK;


    public ItemStackBuilder(ItemStack item) {
        this.ITEM_STACK = item;
    }

    public static ItemStackBuilder getItemStack(ConfigurationSection section, Player player) {
        ItemStack item = XMaterial.matchXMaterial(section.getString("material").toUpperCase()).get().parseItem();

        ItemStackBuilder builder = new ItemStackBuilder(item);

        if (section.contains("amount")) {
            builder.withAmount(section.getInt("amount"));
        }

        if (section.contains("username") && player != null) {
            builder.setSkullOwner(section.getString("username").replace("<player>", player.getName()));
        }

        if (section.contains("texture")) {
            builder.setTexture(section.getString("texture"));
        }

        if (section.contains("displayname")) {
            if (player != null) builder.withName(section.getString("displayname"), player);
            else builder.withName(section.getString("displayname"));
        }

        if (section.contains("lore")) {
            if (player != null) builder.withLore(section.getStringList("lore"), player);
            else builder.withLore(section.getStringList("lore"));
        }

        if (section.contains("glow") && section.getBoolean("glow")) {
            builder.withGlow();
        }

        if (section.contains("item_flags")) {
            List<ItemFlag> flags = new ArrayList<>();
            section.getStringList("item_flags").forEach(text -> {
                try {
                    ItemFlag flag = ItemFlag.valueOf(text);
                    flags.add(flag);
                } catch (IllegalArgumentException ignored) {
                }
            });
            builder.withFlags(flags.toArray(new ItemFlag[0]));
        }

        return builder;
    }

    public static ItemStackBuilder getItemStack(ConfigurationSection section) {
        return getItemStack(section, null);
    }


    public ItemStackBuilder withAmount(int amount) {
        ITEM_STACK.setAmount(amount);
        return this;
    }

    public ItemStackBuilder withFlags(ItemFlag... flags) {
        ItemMeta meta = ITEM_STACK.getItemMeta();
        meta.addItemFlags(flags);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addNBT(String key, String value) {
        NBTItem nbt = new NBTItem(ITEM_STACK);
        nbt.setString(key, value);
        ITEM_STACK = nbt.getItem();
        return this;
    }

    public ItemStackBuilder withName(String name) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        meta.setDisplayName(TextUtil.color(name));
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withName(String name, Player player) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        meta.setDisplayName(TextUtil.color(PlaceholderAPI.setPlaceholders(player, name)));
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) ITEM_STACK.getItemMeta();
            im.setOwner(owner);
            ITEM_STACK.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemStackBuilder withLore(String... lore) {
        return withLore(Arrays.asList(lore));
    }

    public ItemStackBuilder withLore(List<String> lore, Player player) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        List<String> coloredLore = new ArrayList<String>();
        for (String s : lore) {
            coloredLore.add(TextUtil.color(s));
        }
        meta.setLore(coloredLore);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withLore(List<String> lore) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        List<String> coloredLore = new ArrayList<>();
        for (String s : lore) {
            coloredLore.add(TextUtil.color(s));
        }
        meta.setLore(coloredLore);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addLore(String line) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(TextUtil.color(line));
        meta.setLore(lore);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }


    @SuppressWarnings("deprecation")
    public ItemStackBuilder withDurability(int durability) {
        ITEM_STACK.setDurability((short) durability);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStackBuilder withData(int data) {
        ITEM_STACK.setDurability((short) data);
        return this;
    }

    @SneakyThrows
    public ItemStackBuilder setTexture(String texture) {
        if (texture == null) return this;
        ProfileInstruction<ItemStack> skull = XSkull.of(ITEM_STACK);
        skull.profile(new Profileable.StringProfileable(texture, ProfileInputType.BASE64));
        ITEM_STACK = skull.apply();
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment, final int level) {
        ITEM_STACK.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment) {
        ITEM_STACK.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder withGlow() {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ITEM_STACK.setItemMeta(meta);
        ITEM_STACK.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        return this;
    }

    public ItemStackBuilder withType(Material material) {
        ITEM_STACK.setType(material);
        return this;
    }

    public ItemStackBuilder clearLore() {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        meta.setLore(new ArrayList<String>());
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder clearEnchantments() {
        for (Enchantment enchantment : ITEM_STACK.getEnchantments().keySet()) {
            ITEM_STACK.removeEnchantment(enchantment);
        }
        return this;
    }

    public ItemStackBuilder withColor(Color color) {
        Material type = ITEM_STACK.getType();
        if (type == Material.LEATHER_BOOTS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET || type == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) ITEM_STACK.getItemMeta();
            meta.setColor(color);
            ITEM_STACK.setItemMeta(meta);
            return this;
        } else {
            throw new IllegalArgumentException("withColor is only applicable for leather armor!");
        }
    }

    public ItemStackBuilder withPotion(PotionType potionType, int level, boolean splash, boolean hasExtendedDuration) {
        Material type = ITEM_STACK.getType();
        if (type != Material.POTION) {
            throw new IllegalArgumentException("withPotion is only applicable for potions!");
        }
        Potion potion = new Potion(potionType);
        potion.setLevel(level);
        potion.setSplash(splash);
        if (hasExtendedDuration) {
            potion.setHasExtendedDuration(true);
        }
        potion.apply(ITEM_STACK);
        return this;
    }

    public ItemStackBuilder withPotion(PotionType potion, PotionEffect effect) {
        Material type = ITEM_STACK.getType();
        if (type != Material.POTION) {
            throw new IllegalArgumentException("withPotion is only applicable for potions!");
        }
        Potion potion1 = new Potion(potion);
        potion1.setSplash(true);
        potion1.apply(ITEM_STACK);
        PotionMeta meta = (PotionMeta) ITEM_STACK.getItemMeta();
        meta.addCustomEffect(effect, true);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withPotion(PotionType potion, boolean splash, PotionEffect effect) {
        Material type = ITEM_STACK.getType();
        if (type != Material.POTION) {
            throw new IllegalArgumentException("withPotion is only applicable for potions!");
        }
        Potion potion1 = new Potion(potion);
        potion1.setSplash(splash);
        potion1.apply(ITEM_STACK);
        PotionMeta meta = (PotionMeta) ITEM_STACK.getItemMeta();
        meta.addCustomEffect(effect, true);
        if (!potion.getEffectType().equals(effect.getType())) {
            meta.removeCustomEffect(potion.getEffectType());
        }
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withBookEnchantments(Enchantment enchantment, int level) {
        Material type = ITEM_STACK.getType();
        if (type != Material.ENCHANTED_BOOK) {
            throw new IllegalArgumentException("withBookEnchantment is only applicable for enchantment books!");
        }
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) ITEM_STACK.getItemMeta();
        meta.addStoredEnchant(enchantment, level, false);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withBase64(String texture) {
        Material type = ITEM_STACK.getType();
        if (type != XMaterial.PLAYER_HEAD.parseMaterial()) {
            throw new IllegalArgumentException("withBase64 is only applicable for player heads!");
        }

        final SkullMeta meta = (SkullMeta) ITEM_STACK.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", texture));
        meta.setDisplayName("");
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (final IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        ITEM_STACK.setItemMeta(meta);
        return this;
    }


    public ItemStack build() {
        return ITEM_STACK;
    }


}
