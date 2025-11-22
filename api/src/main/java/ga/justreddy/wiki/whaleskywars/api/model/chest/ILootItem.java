package ga.justreddy.wiki.whaleskywars.api.model.chest;

import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author JustReddy
 */
public interface ILootItem {

    ItemStack getItemStack();

    double getChance();

    int getAmount();

    default boolean shouldFill(ThreadLocalRandom random) {
        return random.nextDouble(100) <= getChance();
    }
}
