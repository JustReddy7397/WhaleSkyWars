package ga.justreddy.wiki.whaleskywars.api.model.entity.data;

import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillEffect;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillMessage;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk.Perk;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IBalloon;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ICage;

import java.util.Set;

/**
 * Represents the cosmetics of a player in the game.
 * @author JustReddy
 */
public interface IPlayerCosmetics {

    /**
     * Returns the selected cage id of the player.
     * @return the selected cage id of the player.
     */
    int getSelectedCageId();

    /**
     * Returns the selected cage of the player.
     * @return the selected cage of the player.
     */
    ICage getSelectedCage();

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
     * Returns the selected victory dance id of the player.
     * @return the selected victory dance id of the player.
     */
    int getSelectedVictoryDanceId();

    /**
     * Returns the selected victory dance of the player.
     * @return the selected victory dance of the player.
     */
    VictoryDance getSelectedVictoryDance();

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
     * Returns the selected perk id of the player.
     * @return the selected perk id of the player.
     */
    int getSelectedPerkId();

    /**
     * Returns the selected perk of the player.
     * @return the selected perk of the player.
     */
    Perk getSelectedPerk();

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
     * Returns the selected balloon id of the player.
     * @return the selected balloon id of the player.
     */
    int getSelectedBalloonId();

    /**
     * Returns the selected balloon of the player.
     * @return the selected balloon of the player.
     */
    IBalloon getSelectedBalloon();

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

    /**
     * Returns the selected kill message id of the player.
     * @return the selected kill message id of the player.
     */
    int getSelectedKillMessageId();

    /**
     * Returns the selected kill message of the player.
     * @return the selected kill message of the player.
     */
    KillMessage getSelectedKillMessage();

    /**
     * Sets the selected kill message of the player.
     * @param selectedKillMessage the selected kill message of the player.
     */
    void setSelectedKillMessage(int selectedKillMessage);

    /**
     * Adds a cached kill message to the player.
     * @param message the cached kill message to add.
     * @return true if the cached kill message was added; otherwise, false.
     */
    boolean addCachedKillMessage(int message);

    /**
     * Removes a cached kill message from the player.
     * @param message the cached kill message to remove.
     * @return true if the cached kill message was removed; otherwise, false.
     */

    boolean removeCachedKillMessage(int message);

    /**
     * Returns true if the player has the cached kill message; otherwise, false.
     * @param message the cached kill message to check.
     * @return true, if the player has the cached kill message; otherwise, false.
     */
    boolean hasCachedKillMessage(int message);

    /**
     * Returns the cached kill messages of the player.
     * @return the cached kill messages of the player.
     */
    Set<Integer> getOwnedKillMessages();

    /**
     * Returns the selected kill effect id of the player.
     * @return the selected kill effect id of the player.
     */
    int getSelectedKillEffectId();

    /**
     * Returns the selected kill effect of the player.
     * @return the selected kill effect of the player.
     */
    KillEffect getSelectedKillEffect();

    /**
     * Sets the selected kill effect of the player.
     * @param selectedKillEffect the selected kill effect of the player.
     */
    void setSelectedKillEffect(int selectedKillEffect);

    /**
     * Adds a cached kill effect to the player.
     * @param effect the cached kill effect to add.
     * @return true if the cached kill effect was added; otherwise, false.
     */
    boolean addCachedKillEffect(int effect);

    /**
     * Removes a cached kill effect from the player.
     * @param effect the cached kill effect to remove.
     * @return true if the cached kill effect was removed; otherwise, false.
     */
    boolean removeCachedKillEffect(int effect);

    /**
     * Returns true if the player has the cached kill effect; otherwise, false.
     * @param effect the cached kill effect to check.
     * @return true if the player has the cached kill effect; otherwise, false.
     */
    boolean hasCachedKillEffect(int effect);

    /**
     * Returns the cached kill effects of the player.
     * @return the cached kill effects of the player.
     */
    Set<Integer> getOwnedKillEffects();

}
