package ga.justreddy.wiki.whaleskywars.model.cosmetics.killmessages;

import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillMessage;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.jetbrains.annotations.NotNull;

/**
 * @author JustReddy
 */
public class DefaultKillMessage extends KillMessage {
    /**
     * Initializes a new instance of the KillMessage class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the cosmetic.
     * @param id   The unique identifier of the cosmetic.
     * @param cost The cost of the cosmetic.
     */
    public DefaultKillMessage() {
        super("Default", 0, 0);
    }

    @Override
    public void sendMeleeMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7with a &7melee &7attack.");
    }

    @Override
    public void sendVoidMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7by knocking them into the void.");
    }

    @Override
    public void sendFallMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7by making them fall to their death.");
    }

    @Override
    public void sendFireMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7by setting them on fire.");
    }

    @Override
    public void sendExplosionMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7with an explosion.");
    }

    @Override
    public void sendDrowningMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7by drowning them.");
    }

    @Override
    public void sendSuffocationMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7by suffocating them.");
    }

    @Override
    public void sendProjectileMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7with a projectile.");
    }

    @Override
    public void sendUnknownMessage(@NotNull IGame game, @NotNull IGamePlayer killer, @NotNull IGamePlayer victim) {
        game.sendMessage(game.getPlayers(),
                "&7" + killer.getName() + " &7killed &7" + victim.getName() + " &7in an unknown way.");
    }

    @Override
    public @NotNull KillMessage clone() {
        return new DefaultKillMessage();
    }
}
