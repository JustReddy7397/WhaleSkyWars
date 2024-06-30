package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

/**
 * @author JustReddy
 */
public abstract class Cosmetic {

    private final String name;
    private final int id;
    private final int cost;

    public Cosmetic(String name, int id, int cost) {
        this.name = name;
        this.id = id;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

}
