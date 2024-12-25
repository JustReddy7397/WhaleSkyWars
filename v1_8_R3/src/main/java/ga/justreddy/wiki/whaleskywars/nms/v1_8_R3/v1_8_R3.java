package ga.justreddy.wiki.whaleskywars.nms.v1_8_R3;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.handler.NBTHandlers;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.model.faketeams.FakeTeam;
import ga.justreddy.wiki.whaleskywars.model.faketeams.FakeTeamManager;
import ga.justreddy.wiki.whaleskywars.util.PrefixUtil;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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

    @Override
    public Block getRelativeBlock(Location location) {
        if (!isSign(location.getBlock())) return null;
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
        return player.getTargetBlock((HashSet<Material>) null, range);
    }

    @Override
    public void setTeamName(IGameTeam team) {
        Map<UUID, List<FakeTeam>> teams = FakeTeamManager.getPlayerTeams();

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
            List<FakeTeam> playerTeams = teams.getOrDefault(player.getUniqueId(), new ArrayList<>());
            if (!playerTeams.isEmpty()) playerTeams.clear();
            greenTeam.addMember(player.getName());
            redTeam.addMember(player.getName());
            playerTeams.add(greenTeam);
            playerTeams.add(redTeam);
            teams.put(player.getUniqueId(), playerTeams);
            player.getPlayer().ifPresent(bukkitPlayer -> {
                FakeTeamManager.sendTeam(bukkitPlayer, greenTeam);
                for (IGamePlayer otherPlayer : team.getGame().getPlayers()) {
                    if (otherPlayer.getUniqueId().equals(player.getUniqueId())) continue;
                    List<FakeTeam> fakeTeams = teams.getOrDefault(otherPlayer.getUniqueId(), new ArrayList<>());
                    if (fakeTeams.isEmpty()) continue;
                    if (fakeTeams.size() < 2) continue;
                    FakeTeam fakeTeam = fakeTeams.get(1);
                    otherPlayer.getPlayer().ifPresent(bukkitPlayer1 -> {
                        FakeTeamManager.sendTeam(bukkitPlayer, fakeTeam);
                    });
                }
            });
        }

        for (IGamePlayer otherPlayer : team.getGame().getPlayers()) {
            if (skip.contains(otherPlayer.getUniqueId())) continue;
            otherPlayer.getPlayer().ifPresent(bukkitPlayer -> {
                FakeTeamManager.sendTeam(bukkitPlayer, redTeam);
            });
        }

    }

    @Override
    public void setTeamName(IGamePlayer player) {
        // Getting all current teams
        Map<UUID, List<FakeTeam>> teams = FakeTeamManager.getPlayerTeams();

        // Getting the priority of team, used for sorting in tab
        int priority = player.getGameTeam().getPriority();

        // Check if the game is a team game
        boolean isTeamGame = player.getGame().getTeamSize() > 1;
        String prefix = "";
        if (isTeamGame) {
            // If it is a team game, we set the prefix to the team id
            prefix = "[" + player.getGameTeam().getId() + "] ";
        }

        // Creating the green team
        FakeTeam greenTeam = new FakeTeam(
                ChatColor.GREEN + prefix, "", 0);
        // Creating the red team
        FakeTeam redTeam = new FakeTeam(
                ChatColor.RED + prefix, "", priority + 1);
        // Getting all teams for this specific player
        List<FakeTeam> playerTeams = teams.getOrDefault(player.getUniqueId(),
                new ArrayList<>());
        // If the player teams are not empty, we clear them
        if (!playerTeams.isEmpty()) playerTeams.clear();
        // Adding the player to the green team
        greenTeam.addMember(player.getName());
        // Adding the player to the red team
        redTeam.addMember(player.getName());
        // Adding the teams to the player
        playerTeams.add(greenTeam);
        playerTeams.add(redTeam);
        // Putting the teams back into the map
        teams.put(player.getUniqueId(), playerTeams);
        player.getPlayer().ifPresent(bukkitPlayer -> {
            // Sending the created green team to the player
            FakeTeamManager.sendTeam(bukkitPlayer, greenTeam);
            // Iterating over all players in the game
            for (IGamePlayer otherPlayer : player.getGame().getPlayers()) {
                // If the player is the same as the current player, we skip
                if (otherPlayer.getUniqueId().equals(player.getUniqueId())) continue;
                // Getting the fake teams of the other player
                List<FakeTeam> fakeTeams = teams.getOrDefault(otherPlayer.getUniqueId(), new ArrayList<>());
                // If the fake teams are empty, we skip
                if (fakeTeams.isEmpty()) continue;
                // If the fake teams size is less than 2, we skip
                if (fakeTeams.size() < 2) continue;
                // Getting the green fake team from the OTHER player(s)
                FakeTeam greenFakeTeam = fakeTeams.get(0);
                // Getting the red fake team from the OTHER player(s)
                FakeTeam redFakeTeam = fakeTeams.get(1);
                otherPlayer.getPlayer().ifPresent(bukkitPlayer1 -> {
                    // If the player is in the same team as the other player
                    if (player.getGameTeam().hasPlayer(otherPlayer)) {
                        // Send the other green fake team to the player
                        FakeTeamManager.sendTeam(bukkitPlayer, greenFakeTeam);
                        // Send the created green fake team to the other player
                        FakeTeamManager.sendTeam(bukkitPlayer1, greenTeam);
                        return;
                    }
                    // Send the other red fake team to the player
                    FakeTeamManager.sendTeam(bukkitPlayer, redFakeTeam);
                    // Send the created red fake team to the other player
                    FakeTeamManager.sendTeam(bukkitPlayer1, redTeam);
                });
            }
        });
    }

    @Override
    public void removeTeamNames(IGame game) {
        for (IGamePlayer player : game.getPlayers()) {
            removeTeamName(player);
        }
    }

    @Override
    public void removeTeamName(IGame game, IGamePlayer player) {
        Map<UUID, List<FakeTeam>> teams = FakeTeamManager.getPlayerTeams();
        List<FakeTeam> playerTeams = teams.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (playerTeams.isEmpty()) return;
        for (FakeTeam fakeTeam : playerTeams) {
            for (String member : fakeTeam.getMembers()) {
                if (member.equals(player.getName())) {
                    fakeTeam.removeMember(member);
                }
            }
        }
        teams.put(player.getUniqueId(), playerTeams);
        player.getPlayer().ifPresent(bukkitPlayer -> {
            FakeTeamManager.sendTeam(bukkitPlayer, new FakeTeam("", "", 0));
        });
    }

    @Override
    public void removeTeamName(IGameTeam team) {
        for (IGamePlayer player : team.getPlayers()) {
            removeTeamName(player);
        }
    }

    @Override
    public void removeTeamName(IGamePlayer player) {
        Map<UUID, List<FakeTeam>> teams = FakeTeamManager.getPlayerTeams();
        List<FakeTeam> playerTeams = teams.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (playerTeams.isEmpty()) return;
        for (FakeTeam fakeTeam : playerTeams) {
            for (String member : fakeTeam.getMembers()) {
                if (member.equals(player.getName())) {
                    fakeTeam.removeMember(member);
                }
            }
        }
        teams.put(player.getUniqueId(), playerTeams);
        player.getPlayer().ifPresent(bukkitPlayer -> {
            FakeTeamManager.sendTeam(bukkitPlayer, new FakeTeam("", "", 0));
        });
    }

    @Override
    public void setWaitingLobbyName(IGamePlayer player) {
        // Check if the player is in a game
        if (player.getGame() == null) return;
        // Getting all teams
        Map<UUID, List<FakeTeam>> TEAMS = FakeTeamManager.getPlayerTeams();
        // Resetting the teams of the player
        for (FakeTeam team : TEAMS.getOrDefault(player.getUniqueId(), new ArrayList<>())) {
            player.getPlayer().ifPresent(bukkitPlayer -> {
                FakeTeamManager.reset(bukkitPlayer, team);
            });
        }

        // Removing player from the map
        TEAMS.remove(player.getUniqueId());


        Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {

            // Getting all teams for this player
            List<FakeTeam> teams = TEAMS.getOrDefault(player.getUniqueId(), new ArrayList<>());

            // If the teams are not empty, we clear them
            if (!teams.isEmpty()) teams.clear();



            player.getPlayer().ifPresent(bukkitPlayer -> {
                // Getting the prefix of the player
                String prefix = PrefixUtil.getColorByRank(bukkitPlayer);
                // Getting the priority of the player
                int priority = PrefixUtil.getPriority(bukkitPlayer);
                // Creating a new fake team
                FakeTeam fakeTeam = new FakeTeam(prefix, "", priority);
                fakeTeam.addMember(bukkitPlayer.getName());
                teams.add(fakeTeam);
                TEAMS.put(player.getUniqueId(), teams);

                // Sending the fake team to the player
                System.out.println("Sending ");
                FakeTeamManager.sendTeam(bukkitPlayer, fakeTeam);

                // Sending the fake team to all other players
                for (IGamePlayer otherPlayer : player.getGame().getPlayers()) {
                    if (otherPlayer.getUniqueId().equals(player.getUniqueId())) continue;
                    otherPlayer.getPlayer().ifPresent(otherBukkitPlayer -> {
                        FakeTeamManager.sendTeam(otherBukkitPlayer, fakeTeam);
                    });
                }

                // Sending the others faketeam to the player
                for (IGamePlayer otherPlayer : player.getGame().getPlayers()) {
                    otherPlayer.getPlayer().ifPresent(otherBukkitPlayer -> {
                        FakeTeam otherFakeTeam = new FakeTeam(
                                PrefixUtil.getColorByRank(otherBukkitPlayer),
                                "",
                                PrefixUtil.getPriority(otherBukkitPlayer));
                        otherFakeTeam.addMember(otherBukkitPlayer.getName());
                        FakeTeamManager.sendTeam(bukkitPlayer, otherFakeTeam);
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
        Map<UUID, List<FakeTeam>> TEAMS = FakeTeamManager.getPlayerTeams();
        List<FakeTeam> teams = TEAMS.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (teams.isEmpty()) return;
        for (FakeTeam team : teams) {
            player.getPlayer().ifPresent(bukkitPlayer -> {
                FakeTeamManager.reset(bukkitPlayer, team);
            });
        }
        TEAMS.remove(player.getUniqueId());
    }

    @Override
    public void respawn(Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
    }

    @Override
    public void sendComponent(Player player, TextComponent component) {
        player.spigot().sendMessage(component);
    }

    @Override
    public TextComponent setHoverText(TextComponent component, String text) {
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(text)));
        return component;
    }

    private boolean isSign(Block block) {
        if (block == null) return false;
        return block.getState() instanceof Sign;
    }

}
