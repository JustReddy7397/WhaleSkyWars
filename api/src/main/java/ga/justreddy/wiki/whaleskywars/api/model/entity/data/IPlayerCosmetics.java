package ga.justreddy.wiki.whaleskywars.api.model.entity.data;

import java.util.Set;

/**
 * @author JustReddy
 */
public interface IPlayerCosmetics {

    int getSelectedCage();

    void setSelectedCage(int selectedCage);

    boolean addCachedCage(int cage);

    boolean removeCachedCage(int cage);

    boolean hasCachedCage(int cage);

    Set<Integer> getOwnedCages();

    int getSelectedVictoryDance();

    void setSelectedVictoryDance(int selectedVictoryDance);

    boolean addCachedVictoryDance(int dance);

    boolean removeCachedVictoryDance(int dance);

    boolean hasCachedVictoryDance(int dance);

    Set<Integer> getOwnedVictoryDances();

    String getSelectedKit();

    void setSelectedKit(String selectedKit);

    boolean addCachedKit(String kit);

    boolean removeCachedKit(String kit);

    boolean hasCachedKit(String kit);

    Set<String> getOwnedKits();

    int getSelectedPerk();

    void setSelectedPerk(int selectedPerk);

    boolean addCachedPerk(int perk);

    boolean removeCachedPerk(int perk);

    boolean hasCachedPerk(int perk);

    Set<Integer> getOwnedPerks();

    int getSelectedBalloon();

    void setSelectedBalloon(int selectedBalloon);

    boolean addCachedBalloon(int balloon);

    boolean removeCachedBalloon(int balloon);

    boolean hasCachedBalloon(int balloon);

    Set<Integer> getOwnedBalloons();

}
