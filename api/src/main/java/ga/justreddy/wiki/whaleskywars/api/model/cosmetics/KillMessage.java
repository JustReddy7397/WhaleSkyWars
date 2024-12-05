package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.jetbrains.annotations.NotNull;

/**
 * @author JustReddy
 */
public abstract class KillMessage extends Cosmetic {

    /**
     * Initializes a new instance of the KillMessage class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the cosmetic.
     * @param id   The unique identifier of the cosmetic.
     * @param cost The cost of the cosmetic.
     */
    public KillMessage(String name, int id, int cost) {
        super(name, id, cost);
    }

    /**
     * Sends a message when a player kills another player with a melee weapon.
     * @param game The game in which the kill occurred.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendMeleeMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player by knocking them into the void.
     * @param game The game in which the kill occurred.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendVoidMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player by knocking into the ground.
     * @param game The game in which the kill occurred.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendFallMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player by setting them on fire.
     * Or when the player dies by fire
     * @param game The game in which the kill occurred.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendFireMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player with an explosion.
     * Or when the player dies by explosion
     * @param game The game in which the kill occurred.
     * @param killer  The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendExplosionMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player by drowning them.
     * Or when the player dies by drowning
     * @param game  The game in which the kill occurred.
     * @param killer  The player who killed the other player.
     * @param victim  The player who was killed.
     */
    public abstract void sendDrowningMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player by suffocating them.
     * @param game The game in which the kill occurred.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendSuffocationMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player with a projectile.
     * @param game The game in which the kill occurred.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendProjectileMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

    /**
     * Sends a message when a player kills another player with an unknown type.
     * @param game The game in which the kill occurred.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void sendUnknownMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

}
