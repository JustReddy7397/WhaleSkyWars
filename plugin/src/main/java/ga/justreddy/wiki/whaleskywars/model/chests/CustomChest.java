package ga.justreddy.wiki.whaleskywars.model.chests;

import ga.justreddy.wiki.whaleskywars.api.model.chest.AChestType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

/**
 * @author JustReddy
 */
public class CustomChest extends AChestType {

    private final String identifier;
    private final int minAmount;
    private final int maxAmount;

    public CustomChest(String identifier, ConfigurationSection section, int minAmount, int maxAmount) {
        this.identifier = identifier;
        for (String key : section.getKeys(false)) {
            addChestType(key);

        }
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int getMinAmount() {
        return minAmount;
    }

    @Override
    public int getMaxAmount() {
        return maxAmount;
    }
}
