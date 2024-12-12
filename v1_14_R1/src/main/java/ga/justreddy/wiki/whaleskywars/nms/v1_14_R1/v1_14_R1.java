package ga.justreddy.wiki.whaleskywars.nms.v1_14_R1;


import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.model.faketeams.FakeTeam;
import ga.justreddy.wiki.whaleskywars.nms.v1_14_R1.teams.NmsTeam;
import ga.justreddy.wiki.whaleskywars.nms.v1_14_R1.teams.NmsTeamManager;
import ga.justreddy.wiki.whaleskywars.util.PrefixUtil;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public final class v1_14_R1 implements INms {

    @Override
    public boolean isLegacy() {
        return true;
    }

    @Override
    public ChunkGenerator getChunkGenerator() {
        return new ChunkGenerator() {
            @Override
            public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
                ChunkData data = createChunkData(world);
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        biome.setBiome(i, j, Biome.PLAINS);
                    }
                }
                return data;
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
        player.sendTitle(title, subTitle, 20, 60, 20);
    }

    @Override
    public void sendActionBar(Player player, String action) {
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer
                .a("{\"text\":\"" + action + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(component,
                ChatMessageType.GAME_INFO);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

    }

    @Override
    public void spawnParticle(Location location, String type, int count, float offSetX, float offSetY, float offSetZ, float data) {
        if (type == null) return;
        Particle particle = Particle.valueOf(type);
        float x = (float) location.getBlockX();
        float y = (float) location.getBlockY();
        float z = (float) location.getBlockZ();
        for (Player player : location.getWorld().getPlayers()) {
            player.spawnParticle(particle, x, y, z, count, offSetX, offSetY, offSetZ, data);
        }
    }

    @Override
    public boolean isParticleCorrect(String particle) {
        try {
            Particle.valueOf(
                    particle.toUpperCase()
            );
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void setUnbreakable(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setUnbreakable(true);
        itemStack.setItemMeta(meta);
    }

    @Override
    public void setCollideWithEntities(Entity entity, boolean collide) {
        if (!(entity instanceof Player)) return;
        ((Player) entity).setCollidable(collide);
    }

    @Override
    public void removeAi(Entity entity) {
        entity.setSilent(true);
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setAI(false);
        }
    }

    @Override
    public String nameOfEnchantment(Enchantment enchantment) {
        switch (enchantment.getKey().getKey()) {
            case "protection":
                return "Protection";
            case "fire_protection":
                return "Fire Protection";
            case "feather_falling":
                return "Feather Falling";
            case "blast_protection":
                return "Blast Protection";
            case "projectile_protection":
                return "Projectile Protection";
            case "respiration":
                return "Respiration";
            case "aqua_affinity":
                return "Aqua Affinity";
            case "thorns":
                return "Thorns";
            case "depth_strider":
                return "Depth Strider";
            case "frost_walker":
                return "Frost Walker";
            case "binding_curse":
                return "Curse of Binding";
            case "sharpness":
                return "Sharpness";
            case "smite":
                return "Smite";
            case "bane_of_arthropods":
                return "Bane of Arthropods";
            case "knockback":
                return "Knockback";
            case "fire_aspect":
                return "Fire Aspect";
            case "looting":
                return "Looting";
            case "sweeping":
                return "Sweeping Edge";
            case "efficiency":
                return "Efficiency";
            case "silk_touch":
                return "Silk Touch";
            case "unbreaking":
                return "Unbreaking";
            case "fortune":
                return "Fortune";
            case "power":
                return "Power";
            case "punch":
                return "Punch";
            case "flame":
                return "Flame";
            case "infinity":
                return "Infinity";
            case "luck_of_the_sea":
                return "Luck of the Sea";
            case "lure":
                return "Lure";
            case "loyalty":
                return "Loyalty";
            case "impaling":
                return "Impaling";
            case "riptide":
                return "Riptide";
            case "channeling":
                return "Channeling";
            case "multishot":
                return "Multishot";
            case "quick_charge":
                return "Quick Charge";
            case "piercing":
                return "Piercing";
            case "mending":
                return "Mending";
            case "vanishing_curse":
                return "Curse of Vanishing";
            default:
                return "Unknown";
        }
    }

    @Override
    public Enchantment valueOfEnchantment(String enchantment) {
        return Enchantment.getByKey(new NamespacedKey(WhaleSkyWars.getInstance(), enchantment));
    }

    @Override
    public void seWorldRule(World world, String rule, boolean value) {
        world.setGameRule((GameRule<Boolean>) GameRule.getByName(rule), value);
    }

    @Override
    public <V> V setNbtValue(V object, String key, Object value) {
        if (object instanceof ItemStack) {
            ItemStack itemStack = (ItemStack) object;
            ItemMeta meta = itemStack.getItemMeta();
            if (meta == null) return null;
            meta.getPersistentDataContainer().set(new NamespacedKey(WhaleSkyWars.getInstance(), key), PersistentDataType.STRING, value.toString());
            itemStack.setItemMeta(meta);
            return (V) itemStack;
        }

        if (object instanceof Entity) {
            Entity entity = (Entity) object;
            entity.getPersistentDataContainer().set(new NamespacedKey(WhaleSkyWars.getInstance(), key), PersistentDataType.STRING, value.toString());
            return (V) entity;
        }

        return null;
    }

    @Override
    public boolean hasNbtValue(Object object, String key) {
        if (object instanceof ItemStack) {
            ItemStack itemStack = (ItemStack) object;
            ItemMeta meta = itemStack.getItemMeta();
            if (meta == null) return false;
            return meta.getPersistentDataContainer().has(new NamespacedKey(WhaleSkyWars.getInstance(), key), PersistentDataType.STRING);
        }
        if (object instanceof Entity) {
            Entity entity = (Entity) object;
            return entity.getPersistentDataContainer().has(new NamespacedKey(WhaleSkyWars.getInstance(), key), PersistentDataType.STRING);
        }
        return false;
    }

    @Override
    public Object getNbtValue(Object value, String key) {
        if (value instanceof ItemStack) {
            ItemStack itemStack = (ItemStack) value;
            ItemMeta meta = itemStack.getItemMeta();
            if (meta == null) return null;
            return meta.getPersistentDataContainer().get(new NamespacedKey(WhaleSkyWars.getInstance(), key), PersistentDataType.STRING);
        }

        if (value instanceof Entity) {
            Entity entity = (Entity) value;
            return entity.getPersistentDataContainer().get(new NamespacedKey(WhaleSkyWars.getInstance(), key), PersistentDataType.STRING);
        }
        return null;
    }

    @Override
    public ItemStack getItemInHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    @Override
    public Block getRelativeBlock(Location location) {
        Block block = location.getBlock();
        byte data = block.getData();
        switch (data) {
            case 2:
                return block.getRelative(BlockFace.SOUTH);
            case 3:
                return block.getRelative(BlockFace.NORTH);
            case 4:
                return block.getRelative(BlockFace.EAST);
            case 5:
                return block.getRelative(BlockFace.WEST);
        }
        return null;
    }

    @Override
    public Block getTargetBlock(Player player, int range) {
        return player.getTargetBlock(null, range);
    }

    @Override
    public void setTeamName(IGameTeam team) {
        int priority = team.getPriority();

        Set<UUID> skip = new HashSet<>();

        boolean isTeamGame = team.getGame().getTeamSize() > 1;
        String prefix = "";
        if (isTeamGame) {
            prefix = "[" + team.getId() + "] ";
        }

        FakeTeam greenTeam = new FakeTeam(
                ChatColor.GREEN + prefix, "", 0);
        FakeTeam redTeam = new FakeTeam(
                ChatColor.RED + prefix, "", priority + 1);


        for (IGamePlayer player : team.getPlayers()) {
            skip.add(player.getUniqueId());
            player.getPlayer().ifPresent(bukkitPlayer -> {
                NmsTeam green = new NmsTeam(bukkitPlayer, greenTeam);
                NmsTeam red = new NmsTeam(bukkitPlayer, redTeam);
                NmsTeamManager.sendTeam(bukkitPlayer, green);
                NmsTeamManager.TEAMS.put(player.getUniqueId(), List.of(green, red));
                for (IGamePlayer otherPlayer : team.getGame().getPlayers()) {
                    if (skip.contains(otherPlayer.getUniqueId())) continue;
                    otherPlayer.getPlayer().ifPresent(otherBukkitPlayer -> {
                        NmsTeamManager.sendTeam(otherBukkitPlayer, red);
                    });
                }
            });
        }
    }

    @Override
    public void setTeamName(IGamePlayer player) {
        IGameTeam team = player.getGameTeam();
        if (team == null) return;
        setTeamName(team);
    }

    @Override
    public void removeTeamNames(IGame game) {
        for (IGameTeam team : game.getTeams()) {
            removeTeamName(team);
        }
    }

    @Override
    public void removeTeamName(IGame game, IGamePlayer player) {
        removeTeamName(player);
    }

    @Override
    public void removeTeamName(IGameTeam team) {
        for (IGamePlayer player : team.getPlayers()) {
            removeTeamName(player);
        }
    }

    @Override
    public void removeTeamName(IGamePlayer player) {
        Map<UUID, List<NmsTeam>> TEAMS = NmsTeamManager.TEAMS;
        List<NmsTeam> teams = TEAMS.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (teams.isEmpty()) return;
        for (NmsTeam team : teams) {
            player.getPlayer().ifPresent(team::reset);
        }
        TEAMS.remove(player.getUniqueId());
    }

    @Override
    public void setWaitingLobbyName(IGamePlayer player) {
        if (player.getGame() == null) return;
        Map<UUID, List<NmsTeam>> TEAMS = NmsTeamManager.TEAMS;

        for (NmsTeam team : TEAMS.getOrDefault(player.getUniqueId(), new ArrayList<>())) {
            player.getPlayer().ifPresent(team::reset);
        }

        TEAMS.remove(player.getUniqueId());

        Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
            List<NmsTeam> teams = TEAMS.getOrDefault(player.getUniqueId(), new ArrayList<>());
            if (!teams.isEmpty()) teams.clear();

            player.getPlayer().ifPresent(bukkitPlayer -> {
                String prefix = PrefixUtil.getColorByRank(bukkitPlayer);
                int priority = PrefixUtil.getPriority(bukkitPlayer);

                FakeTeam fakeTeam = new FakeTeam(prefix, "", priority);
                NmsTeam nmsTeam = new NmsTeam(bukkitPlayer, fakeTeam);
                teams.add(nmsTeam);
                TEAMS.put(player.getUniqueId(), teams);
                nmsTeam.send(bukkitPlayer);

                for (IGamePlayer otherPlayer : player.getGame().getPlayers()) {
                    if (otherPlayer.getUniqueId().equals(player.getUniqueId())) continue;
                    otherPlayer.getPlayer().ifPresent(nmsTeam::send);
                }

                for (IGamePlayer otherPlayer : player.getGame().getPlayers()) {
                    otherPlayer.getPlayer().ifPresent(otherBukkitPlayer -> {
                        FakeTeam otherFakeTeam = new FakeTeam(PrefixUtil.getColorByRank(otherBukkitPlayer), "", PrefixUtil.getPriority(otherBukkitPlayer));
                        NmsTeam otherNmsTeam = new NmsTeam(otherBukkitPlayer, otherFakeTeam);
                        otherNmsTeam.send(bukkitPlayer);
                    });
                }



            });

        });

    }

    @Override
    public void removeWaitingLobbyName(IGame game) {
        for (IGamePlayer player : game.getPlayers()) {
            removeWaitingLobbyName(game, player);
        }
    }

    @Override
    public void removeWaitingLobbyName(IGame game, IGamePlayer player) {
        Map<UUID, List<NmsTeam>> TEAMS = NmsTeamManager.TEAMS;
        List<NmsTeam> teams = TEAMS.getOrDefault(player.getUniqueId(), new ArrayList<>());
        for (NmsTeam team : teams) {
            player.getPlayer().ifPresent(team::reset);
        }
        TEAMS.remove(player.getUniqueId());
    }

    @Override
    public void respawn(Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
    }
}
