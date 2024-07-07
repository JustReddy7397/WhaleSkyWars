package ga.justreddy.wiki.whaleskywars.model.entity.data;

import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerCosmetics;

import java.util.HashSet;
import java.util.Set;

/**
 * @author JustReddy
 */
public class PlayerCosmetics implements IPlayerCosmetics {

    private int selectedCage;
    private int selectedVictoryDance;

    private Set<Integer> ownedCages;
    private Set<Integer> ownedVictoryDances;

    public PlayerCosmetics() {
        this.selectedCage = 0;
        this.selectedVictoryDance = 0;
        this.ownedCages = new HashSet<>();
        this.ownedVictoryDances = new HashSet<>();
    }

    @Override
    public int getSelectedCage() {
        return selectedCage;
    }

    @Override
    public void setSelectedCage(int selectedCage) {
        this.selectedCage = selectedCage;
    }

    @Override
    public boolean addCachedCage(int cage) {
        if (hasCachedCage(cage)) {
            return false;
        }
        ownedCages.add(cage);
        return true;
    }

    @Override
    public boolean removeCachedCage(int cage) {
        if (!hasCachedCage(cage)) {
            return false;
        }
        ownedCages.remove(cage);
        return true;
    }

    @Override
    public boolean hasCachedCage(int cage) {
        return ownedCages.contains(cage);
    }

    @Override
    public Set<Integer> getOwnedCages() {
        return ownedCages;
    }

    @Override
    public int getSelectedVictoryDance() {
        return selectedVictoryDance;
    }

    @Override
    public void setSelectedVictoryDance(int selectedVictoryDance) {
        this.selectedVictoryDance = selectedVictoryDance;
    }

    @Override
    public boolean addCachedVictoryDance(int dance) {
        if (hasCachedVictoryDance(dance)) {
            return false;
        }
        ownedVictoryDances.add(dance);
        return true;
    }

    @Override
    public boolean removeCachedVictoryDance(int dance) {
        if (!hasCachedVictoryDance(dance)) {
            return false;
        }
        ownedVictoryDances.remove(dance);
        return true;
    }

    @Override
    public boolean hasCachedVictoryDance(int dance) {
        return ownedVictoryDances.contains(dance);
    }

    @Override
    public Set<Integer> getOwnedVictoryDances() {
        return ownedVictoryDances;
    }
}
