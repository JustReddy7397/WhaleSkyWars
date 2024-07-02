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

}
