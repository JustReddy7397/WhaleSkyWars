package ga.justreddy.wiki.whaleskywars.api.model.entity.data;

import java.util.Set;

/**
 * Represents the cosmetics of a player in the game.
 * @author JustReddy
 */
public interface IPlayerCosmetics {

    /**
     * Returns the selected cage of the player.
     * @return the selected cage of the player.
     */
    int getSelectedCage();

    /**
     * Sets the selected cage of the player.
     * @param selectedCage the selected cage of the player.
     */
    void setSelectedCage(int selectedCage);

    /**
     * Adds a cached cage to the player.
     * @param cage the cached cage to add.
     * @return true if the cached cage was added; otherwise, false.
     */
    boolean addCachedCage(int cage);

    /**
     * Removes a cached cage from the player.
     * @param cage the cached cage to remove.
     * @return true if the cached cage was removed; otherwise, false.
     */
    boolean removeCachedCage(int cage);

    /**
     * Returns true if the player has the cached cage; otherwise, false.
     * @param cage the cached cage to check.
     * @return true if the player has the cached cage; otherwise, false.
     */
    boolean hasCachedCage(int cage);

    /**
     * Returns the cached cages of the player.
     * @return the cached cages of the player.
     */
    Set<Integer> getOwnedCages();

    /**
     * Returns the selected victory dance of the player.
     * @return the selected victory dance of the player.
     */
    int getSelectedVictoryDance();

    /**
     * Sets the selected victory dance of the player.
     * @param selectedVictoryDance the selected victory dance of the player.
     */
    void setSelectedVictoryDance(int selectedVictoryDance);

    /**
     * Adds a cached victory dance to the player.
     * @param dance the cached victory dance to add.
     * @return true if the cached victory dance was added; otherwise, false.
     */
    boolean addCachedVictoryDance(int dance);

    /**
     * Removes a cached victory dance from the player.
     * @param dance the cached victory dance to remove.
     * @return true if the cached victory dance was removed; otherwise, false.
     */
    boolean removeCachedVictoryDance(int dance);

    /**
     * Returns true if the player has the cached victory dance; otherwise, false.
     * @param dance the cached victory dance to check.
     * @return true if the player has the cached victory dance; otherwise, false.
     */
    boolean hasCachedVictoryDance(int dance);

    /**
     * Returns the cached victory dances of the player.
     * @return the cached victory dances of the player.
     */
    Set<Integer> getOwnedVictoryDances();

    /**
     * Returns the selected kit of the player.
     * @return the selected kit of the player.
     */
    String getSelectedKit();


    /**
     * Sets the selected kit of the player.
     * @param selectedKit the selected kit of the player.
     */
    void setSelectedKit(String selectedKit);

    /**
     * Adds a cached kit to the player.
     * @param kit the cached kit to add.
     * @return true if the cached kit was added; otherwise, false.
     */
    boolean addCachedKit(String kit);

    /**
     * Removes a cached kit from the player.
     * @param kit the cached kit to remove.
     * @return true if the cached kit was removed; otherwise, false.
     */
    boolean removeCachedKit(String kit);

    /**
     * Returns true if the player has the cached kit; otherwise, false.
     * @param kit the cached kit to check.
     * @return true if the player has the cached kit; otherwise, false.
     */
    boolean hasCachedKit(String kit);

    /**
     * Returns the cached kits of the player.
     * @return the cached kits of the player.
     */
    Set<String> getOwnedKits();

    /**
     * Returns the selected perk of the player.
     * @return the selected perk of the player.
     */
    int getSelectedPerk();

    /**
     * Sets the selected perk of the player.
     * @param selectedPerk the selected perk of the player.
     */
    void setSelectedPerk(int selectedPerk);

    /**
     * Adds a cached perk to the player.
     * @param perk the cached perk to add.
     * @return true if the cached perk was added; otherwise, false.
     */
    boolean addCachedPerk(int perk);

    /**
     * Removes a cached perk from the player.
     * @param perk the cached perk to remove.
     * @return true if the cached perk was removed; otherwise, false.
     */
    boolean removeCachedPerk(int perk);

    /**
     * Returns true if the player has the cached perk; otherwise, false.
     * @param perk the cached perk to check.
     * @return true if the player has the cached perk; otherwise, false.
     */
    boolean hasCachedPerk(int perk);

    /**
     * Returns the cached perks of the player.
     * @return the cached perks of the player.
     */
    Set<Integer> getOwnedPerks();

    /**
     * Returns the selected balloon of the player.
     * @return the selected balloon of the player.
     */
    int getSelectedBalloon();

    /**
     * Sets the selected balloon of the player.
     * @param selectedBalloon the selected balloon of the player.
     */
    void setSelectedBalloon(int selectedBalloon);

    /**
     * Adds a cached balloon to the player.
     * @param balloon the cached balloon to add.
     * @return true if the cached balloon was added; otherwise, false.
     */
    boolean addCachedBalloon(int balloon);

    /**
     * Removes a cached balloon from the player.
     * @param balloon the cached balloon to remove.
     * @return true if the cached balloon was removed; otherwise, false.
     */
    boolean removeCachedBalloon(int balloon);

    /**
     * Returns true if the player has the cached balloon; otherwise, false.
     * @param balloon the cached balloon to check.
     * @return true if the player has the cached balloon; otherwise, false.
     */
    boolean hasCachedBalloon(int balloon);

    /**
     * Returns the cached balloons of the player.
     * @return the cached balloons of the player.
     */
    Set<Integer> getOwnedBalloons();

}
