package ga.justreddy.wiki.whaleskywars.api.model.chest;

import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * @author JustReddy
 */
public interface ILootItem {

    ItemStack getItemStack();

    double getChance();

    int getAmount();

    default boolean shouldFill(Random random) {
        return random.nextDouble(100) <= getChance();
    }
}
