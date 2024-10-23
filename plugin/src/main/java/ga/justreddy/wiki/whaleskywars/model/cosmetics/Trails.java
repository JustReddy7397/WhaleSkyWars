package ga.justreddy.wiki.whaleskywars.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Cosmetic;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Trail;
import lombok.Getter;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author JustReddy
 */
@Getter
public class Trails extends Trail {

    private final String particle;

    public Trails(String name, int id, int cost, String particle) {
        super(name, id, cost);
        this.particle = particle;
    }

    public void summon(Projectile projectile) {
        if (particle.equalsIgnoreCase("null")) return;

        if (projectile.isOnGround() || projectile.isDead()) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (projectile.isOnGround() || projectile.isDead()) {
                    cancel();
                    return;
                }
                WhaleSkyWars.getInstance().getNms().spawnParticle(projectile.getLocation(), getParticle(), 2, 0.0F, 0.0F, 0.0F, 0.0F);
                summon(projectile);
            }
        }.runTaskLaterAsynchronously(WhaleSkyWars.getInstance(), 3L);
    }

}

