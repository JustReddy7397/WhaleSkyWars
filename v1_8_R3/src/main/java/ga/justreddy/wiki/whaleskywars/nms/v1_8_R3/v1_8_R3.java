package ga.justreddy.wiki.whaleskywars.nms.v1_8_R3;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.handler.NBTHandlers;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

@SuppressWarnings("unused")
public final class v1_8_R3 implements INms {

    @Override
    public boolean isLegacy() {
        return true;
    }

    @Override
    public ChunkGenerator getChunkGenerator() {
        return new ChunkGenerator() {
            @Override
            public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
                return createChunkData(world);
            }
        };
    }

    @Override
    public void setJsonMessage(Player player, String message) {
        final IChatBaseComponent component = IChatBaseComponent
                .ChatSerializer.a(message);
        ((CraftPlayer) player).getHandle()
                .playerConnection
                .sendPacket(new PacketPlayOutChat(component));
    }

    @Override
    public void sendTitle(Player player, String title, String subTitle) {
        PlayerConnection connection = ((CraftPlayer) player)
                .getHandle().playerConnection;
        PacketPlayOutTitle titleInfo = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.TIMES,
                null,
                20,
                60,
                20
        );
        connection.sendPacket(titleInfo);
        if (title != null) {
            IChatBaseComponent component = IChatBaseComponent.ChatSerializer
                    .a("{\"text\": \"" +
                            title +
                            "\"}");
            connection.sendPacket(new PacketPlayOutTitle(
                    PacketPlayOutTitle.EnumTitleAction.TITLE,
                    component
            ));
        }

        if (subTitle != null) {
            IChatBaseComponent component = IChatBaseComponent.ChatSerializer
                    .a("{\"text\": \"" +
                            subTitle +
                            "\"}");
            connection.sendPacket(new PacketPlayOutTitle(
                    PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                    component
            ));
        }
    }

    @Override
    public void sendActionBar(Player player, String action) {
        if (action == null) return;
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer
                .a("{\"text\": \"" +
                        action +
                        "\"}");
        PacketPlayOutChat chat = new PacketPlayOutChat(
                component, (byte) 2
        );
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);

    }

    @Override
    public void spawnParticle(Location location, String type, int count, float offSetX, float offSetY, float offSetZ, float data) {
        if (type == null) return;
        EnumParticle particle = EnumParticle.valueOf(type);
        float x = (float) location.getBlockX();
        float y = (float) location.getBlockY();
        float z = (float) location.getBlockZ();
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle, true, x, y, z, offSetX, offSetY, offSetX, data, count, 1);
        for (final Player player : location.getWorld().getPlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
        }
    }

    @Override
    public boolean isParticleCorrect(String particle) {
        try {
            EnumParticle.valueOf(particle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void setUnbreakable(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(meta);
        }
    }

    @Override
    public void setCollideWithEntities(Entity entity, boolean collide) {
        if (!(entity instanceof Player)) return;
        ((Player) entity).spigot().setCollidesWithEntities(collide);
    }

    @Override
    public void removeAi(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        tag.setBoolean("Silent", true);
        nmsEntity.f(tag);
    }

    @Override
    public String nameOfEnchantment(Enchantment enchantment) {
        switch (enchantment.getId()) {
            case 0:
                return "Protection";
            case 1:
                return "Fire Protection";
            case 2:
                return "Feather Falling";
            case 3:
                return "Blast Protection";
            case 4:
                return "Projectile Protection";
            case 5:
                return "Respiration";
            case 6:
                return "Aqua Affinity";
            case 7:
                return "Thorns";
            case 8:
                return "Depth Strider";

            case 16:
                return "Sharpness";
            case 17:
                return "Smite";
            case 18:
                return "Bane of Arthropods";
            case 19:
                return "Knockback";
            case 20:
                return "Fire Aspect";
            case 21:
                return "Looting";

            case 32:
                return "Efficiency";
            case 33:
                return "Silk touch";
            case 34:
                return "Unbreaking";
            case 35:
                return "Fortune";

            case 48:
                return "Power";
            case 49:
                return "Punch";
            case 50:
                return "Flame";
            case 51:
                return "Infinity";
            case 61:
                return "Luck of the Sea";
            case 62:
                return "Lure";
        }
        return null;
    }

    @Override
    public Enchantment valueOfEnchantment(String value) {
        if (value.equalsIgnoreCase("protection")) {
            return Enchantment.PROTECTION_ENVIRONMENTAL;
        }
        if (value.equalsIgnoreCase("fire protection")) {
            return Enchantment.PROTECTION_FIRE;
        }
        if (value.equalsIgnoreCase("feather falling")) {
            return Enchantment.PROTECTION_FALL;
        }
        if (value.equalsIgnoreCase("blast protection")) {
            return Enchantment.PROTECTION_EXPLOSIONS;
        }
        if (value.equalsIgnoreCase("projectile protection")) {
            return Enchantment.PROTECTION_PROJECTILE;
        }
        if (value.equalsIgnoreCase("respiration")) {
            return Enchantment.OXYGEN;
        }
        if (value.equalsIgnoreCase("aqua affinity")) {
            return Enchantment.WATER_WORKER;
        }
        if (value.equalsIgnoreCase("thorns")) {
            return Enchantment.THORNS;
        }
        if (value.equalsIgnoreCase("sharpness")) {
            return Enchantment.DAMAGE_ALL;
        }
        if (value.equalsIgnoreCase("smite")) {
            return Enchantment.DAMAGE_UNDEAD;
        }
        if (value.equalsIgnoreCase("bane of arthropods")) {
            return Enchantment.DAMAGE_ARTHROPODS;
        }
        if (value.equalsIgnoreCase("knockback")) {
            return Enchantment.KNOCKBACK;
        }
        if (value.equalsIgnoreCase("fire aspect")) {
            return Enchantment.FIRE_ASPECT;
        }
        if (value.equalsIgnoreCase("looting")) {
            return Enchantment.LOOT_BONUS_MOBS;
        }
        if (value.equalsIgnoreCase("efficiency")) {
            return Enchantment.DIG_SPEED;
        }
        if (value.equalsIgnoreCase("silk touch")) {
            return Enchantment.SILK_TOUCH;
        }
        if (value.equalsIgnoreCase("unbreaking")) {
            return Enchantment.DURABILITY;
        }
        if (value.equalsIgnoreCase("fortune")) {
            return Enchantment.LOOT_BONUS_BLOCKS;
        }
        if (value.equalsIgnoreCase("power")) {
            return Enchantment.ARROW_DAMAGE;
        }
        if (value.equalsIgnoreCase("punch")) {
            return Enchantment.ARROW_KNOCKBACK;
        }
        if (value.equalsIgnoreCase("flame")) {
            return Enchantment.ARROW_FIRE;
        }
        if (value.equalsIgnoreCase("infinity")) {
            return Enchantment.ARROW_INFINITE;
        }
        if (value.equalsIgnoreCase("luck of the sea")) {
            return Enchantment.LUCK;
        }
        if (value.equalsIgnoreCase("lure")) {
            return Enchantment.LURE;
        }
        return null;
    }

    @Override
    public void seWorldRule(World world, String rule, boolean value) {
        String stringValue = "false";
        if (value) stringValue = "true";
        world.setGameRuleValue(rule, stringValue);
    }

    @Override
    public <V> V setNbtValue(V object, String key, Object value) {
        if (object instanceof ItemStack) {
            NBTItem item = new NBTItem((ItemStack) object);
            // Ugly start
            if (value instanceof Integer) {
                item.setInteger(key, (Integer) value);
            }
            if (value instanceof String) {
                item.setString(key, (String) value);
            }
            if (value instanceof Double) {
                item.setDouble(key, (Double) value);
            }
            if (value instanceof Float) {
                item.setFloat(key, (Float) value);
            }
            if (value instanceof Long) {
                item.setLong(key, (Long) value);
            }
            if (value instanceof Short) {
                item.setShort(key, (Short) value);
            }
            if (value instanceof Byte) {
                item.setByte(key, (Byte) value);
            }
            if (value instanceof Boolean) {
                item.setBoolean(key, (Boolean) value);
            }
            if (value instanceof Enum<?>) {
                item.setEnum(key, (Enum<?>) value);
            }
            return (V) item.getItem();
            // Ugly End
        }
        if (object instanceof Entity) {
            NBTEntity entity = new NBTEntity((Entity) object);
            if (value instanceof Integer) {
                entity.setInteger(key, (Integer) value);
            }
            if (value instanceof String) {
                entity.setString(key, (String) value);
            }
            if (value instanceof Double) {
                entity.setDouble(key, (Double) value);
            }
            if (value instanceof Float) {
                entity.setFloat(key, (Float) value);
            }
            if (value instanceof Long) {
                entity.setLong(key, (Long) value);
            }
            if (value instanceof Short) {
                entity.setShort(key, (Short) value);
            }
            if (value instanceof Byte) {
                entity.setByte(key, (Byte) value);
            }
            if (value instanceof Boolean) {
                entity.setBoolean(key, (Boolean) value);
            }
            if (value instanceof Enum<?>) {
                entity.setEnum(key, (Enum<?>) value);
            }
            return object;
            // Ugly End
        }
        return null;
    }

    @Override
    public boolean hasNbtValue(Object object, String key) {
        if (object instanceof ItemStack) {
            NBTItem item = new NBTItem((ItemStack) object);
            return item.hasTag(key);
        }
        if (object instanceof Entity) {
            NBTEntity entity = new NBTEntity((Entity) object);
            return entity.hasTag(key);
        }
        return false;
    }

    @Override
    public Object getNbtValue(Object value, String key) {
        if (value instanceof ItemStack) {
            NBTItem item = new NBTItem((ItemStack) value);
            return item.get(key, NBTHandlers.STORE_READABLE_TAG);
        }
        if (value instanceof Entity) {
            NBTEntity entity = new NBTEntity((Entity) value);
            return entity.get(key, NBTHandlers.STORE_READABLE_TAG);
        }
        return null;
    }

    @Override
    public ItemStack getItemInHand(Player player) {
        return player.getItemInHand();
    }


}
