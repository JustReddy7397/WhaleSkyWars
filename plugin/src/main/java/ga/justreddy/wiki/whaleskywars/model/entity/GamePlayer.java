package ga.justreddy.wiki.whaleskywars.model.entity;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class GamePlayer implements IGamePlayer {

    private final UUID uniqueId;
    private final String name;
    private final Optional<Player> player;
    private IPlayerCosmetics cosmetics;
    private IGame game;
    private IGameTeam gameTeam;
    private boolean dead;

    public GamePlayer(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.player = Optional.ofNullable(Bukkit.getPlayer(uniqueId));
        this.cosmetics = null;
        this.game = null;
        this.gameTeam = null;
        this.dead = false;
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
        player.ifPresent(player -> player.sendMessage(message));
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

    public static IGamePlayer get(UUID uuid) {
        return WhaleSkyWars.getInstance().getPlayerManager().get(uuid);
    }

}
