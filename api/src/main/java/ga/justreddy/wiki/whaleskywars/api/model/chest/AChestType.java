package ga.justreddy.wiki.whaleskywars.api.model.chest;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The AChestType abstract class defines the structure
 * for different types of chests in the game.
 * It allows for specifying the minimum
 * and maximum number of items a chest can contain,
 * and includes methods for populating
 * and randomizing the items within a chest.
 * @author JustReddy
 */
public abstract class AChestType {

    private final Map<String, List<ILootItem>> lootItemsPerChestType;
    private final List<String> chestTypes;

    public AChestType() {
        this.lootItemsPerChestType = new HashMap<>();
        this.chestTypes = new ArrayList<>();
    }

    /**
     * Gets the unique identifier for the chest type.
     * @return A String representing the identifier for this chest type.
     */
    public abstract String getIdentifier();

    /**
     * Gets a list of all possible chest types for this chest.
     * @return A list of Strings representing all possible chest types for this chest.
     */
    public List<String> getChestTypes() {
        return chestTypes;
    }

    /**
     * Gets the minimum number of items this chest type should contain.
     * @return An int specifying the minimum number of items.
     */
    public abstract int getMinAmount();

    /**
     * Gets the maximum number of items this chest type can contain.
     * @return An int specifying the maximum number of items.
     */
    public abstract int getMaxAmount();

    /**
     * Gets a map of all items that can be placed in the chest based on the chest type.
     * @return A map of Strings to a list of ILootItem representing all items that can be placed in the chest.
     */
    public Map<String, List<ILootItem>> getItemsForChestTypes() {
        return lootItemsPerChestType;
    }

    /**
     * Prepares the inventory of the chest based on the chest type.
     * This typically involves clearing the inventory and setting up any necessary properties.
     * @param inventory The inventory of the chest to be populated.
     * @param chestType The type of the chest being populated.
     */
    public void populate(Inventory inventory, String chestType) {
        inventory.clear();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<ILootItem> items = lootItemsPerChestType.get(chestType);
        if (items == null) return;
        fillRandomizer(inventory, random, items);
    }

    /**
     * Fills the chest with a randomized selection of items based on the specified list.
     * Ensures that the number of items is within the defined min and max limits.
     * @param inventory The inventory of the chest to fill.
     * @param random An instance of Random for generating random numbers.
     * @param refill A list of ILootItem representing potential items to fill the chest with.
     */
    public void fillRandomizer(Inventory inventory, Random random, List<ILootItem> refill) {
        Set<ILootItem> used = new HashSet<>();

        int given = random.nextInt(getMaxAmount() - getMaxAmount()) + getMaxAmount();

        int placed = 0;

        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            if (placed == given) break;
            ILootItem item = refill.get(
                    random.nextInt(refill.size())
            );
            if (used.contains(item)) continue;
            used.add(item);
            if(item.shouldFill(random)){
                ItemStack itemStack = item.getItemStack();
                inventory.setItem(slotIndex, itemStack);
                placed++;
            }
        }

    }

    /**
     * Adds a loot item to the chest type.
     * @param chestType The type of chest to add.
     */
    public final void addChestType(String chestType) {
        this.chestTypes.add(chestType);
    }

}
