package ga.justreddy.wiki.whaleskywars.model.entity;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsAPI;
import ga.justreddy.wiki.whaleskywars.api.model.entity.ICombatLog;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerStats;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.model.entity.data.CustomPlayerDataExample;
import ga.justreddy.wiki.whaleskywars.model.entity.data.PlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.model.entity.data.PlayerStats;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author JustReddy
 */
public class GamePlayer implements IGamePlayer {

    private final UUID uniqueId;
    private final String name;
    private final Optional<Player> player;
    private IPlayerCosmetics cosmetics;
    private IPlayerStats stats;
    private IGame game;
    private IGameTeam gameTeam;
    private ICombatLog combatLog;
    private boolean dead;
    private Map<String, ICustomPlayerData> customPlayerData;

    public GamePlayer(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.player = Optional.ofNullable(Bukkit.getPlayer(uniqueId));
        this.cosmetics = new PlayerCosmetics();
        this.stats = new PlayerStats();
        this.customPlayerData = new HashMap<>();
        this.customPlayerData.putAll(WhaleSkyWars.getInstance().getCustomPlayerDataManager().getCustomPlayerData());
        this.combatLog = new CombatLog(this);
        this.game = null;
        this.gameTeam = null;
        this.dead = false;
    }

    public static IGamePlayer get(UUID uuid) {
        return WhaleSkyWars.getInstance().getPlayerManager().get(uuid);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<Player> getPlayer() {
        return player;
    }

    @Override
    public IPlayerCosmetics getCosmetics() {
        return cosmetics;
    }

    @Override
    public void setCosmetics(IPlayerCosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    @Override
    public IPlayerStats getStats() {
        return stats;
    }

    @Override
    public void setStats(IPlayerStats stats) {
        this.stats = stats;
    }

    @Override
    public IGame getGame() {
        return game;
    }

    @Override
    public void setGame(IGame game) {
        this.game = game;
    }

    @Override
    public IGameTeam getGameTeam() {
        return gameTeam;
    }

    @Override
    public void setGameTeam(IGameTeam team) {
        this.gameTeam = team;
    }

    @Override
    public ICombatLog getCombatLog() {
        return combatLog;
    }

    @Override
    public void setCombatLog(ICombatLog combatLog) {
        this.combatLog = combatLog;
    }

    @Override
    public boolean isPlaying() {
        return game != null;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void sendMessage(String message) {
        player.ifPresent(player -> player.sendMessage(TextUtil.color(message)));
    }

    @Override
    public void sendMessages(List<String> messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public void sendMessages(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {

    }

    @Override
    public void sendActionBar(String message) {

    }

    @Override
    public void sendSound(String sound, float volume, float pitch) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ICustomPlayerData> T getCustomPlayerData(String id) {
        if (!customPlayerData.containsKey(id)) {
            return null;
        }
        ICustomPlayerData playerData = customPlayerData.get(id);

        return (T) playerData;
    }


}
