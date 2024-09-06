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

    @Override
    public String getSelectedKit() {
        return "";
    }

    @Override
    public void setSelectedKit(String selectedKit) {

    }

    @Override
    public boolean addCachedKit(String kit) {
        return false;
    }

    @Override
    public boolean removeCachedKit(String kit) {
        return false;
    }

    @Override
    public boolean hasCachedKit(String kit) {
        return false;
    }

    @Override
    public Set<String> getOwnedKits() {
        return new HashSet<>();
    }

    @Override
    public int getSelectedPerk() {
        return 0;
    }

    @Override
    public void setSelectedPerk(int selectedPerk) {

    }

    @Override
    public boolean addCachedPerk(int perk) {
        return false;
    }

    @Override
    public boolean removeCachedPerk(int perk) {
        return false;
    }

    @Override
    public boolean hasCachedPerk(int perk) {
        return false;
    }

    @Override
    public Set<Integer> getOwnedPerks() {
        return new HashSet<>();
    }

    @Override
    public int getSelectedBalloon() {
        return 0;
    }

    @Override
    public void setSelectedBalloon(int selectedBalloon) {

    }

    @Override
    public boolean addCachedBalloon(int balloon) {
        return false;
    }

    @Override
    public boolean removeCachedBalloon(int balloon) {
        return false;
    }

    @Override
    public boolean hasCachedBalloon(int balloon) {
        return false;
    }

    @Override
    public Set<Integer> getOwnedBalloons() {
        return new HashSet<>();
    }
}
