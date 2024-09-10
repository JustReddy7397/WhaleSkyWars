package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

/**
 * Represents a cosmetic in the game.
 * This class is abstract and cannot be instantiated.
 * It should be extended to create specific cosmetics.
 * Each cosmetic has a name, unique identifier, and cost.
 * The unique identifier is used to identify the cosmetic in the game.
 * The cost is the amount of currency required to purchase the cosmetic.
 * The name is the display name of the cosmetic.
 * @author JustReddy
 */
public abstract class Cosmetic {

    private final String name;
    private final int id;
    private final int cost;

    /**
     * Initializes a new instance of the Cosmetic class with the specified name,
     * ID, and cost.
     * @param name The name of the cosmetic.
     * @param id The unique identifier of the cosmetic.
     * @param cost The cost of the cosmetic.
     */
    public Cosmetic(String name, int id, int cost) {
        this.name = name;
        this.id = id;
        this.cost = cost;
    }

    /**
     * Returns the name of the cosmetic.
     * @return The name of the cosmetic.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique identifier of the cosmetic.
     * @return The unique identifier of the cosmetic.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the cost of the cosmetic.
     * @return The cost of the cosmetic.
     */
    public int getCost() {
        return cost;
    }

}
