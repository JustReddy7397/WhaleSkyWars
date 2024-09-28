package ga.justreddy.wiki.whaleskywars.model.entity.data;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillEffect;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillMessage;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk.Perk;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IBalloon;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ICage;

import java.util.HashSet;
import java.util.Set;

/**
 * @author JustReddy
 */
public class PlayerCosmetics implements IPlayerCosmetics {

    private int selectedCage;
    private int selectedVictoryDance;
    private String selectedKit;
    private int selectedPerk;
    private int selectedBalloon;
    private int selectedKillMessage;
    private int selectedKillEffect;

    private Set<Integer> ownedCages;
    private Set<Integer> ownedVictoryDances;
    private Set<String> ownedKits;
    private Set<Integer> ownedPerks;
    private Set<Integer> ownedBalloons;
    private Set<Integer> ownedKillMessages;
    private Set<Integer> ownedKillEffects;

    public PlayerCosmetics() {
        this.selectedCage = 0;
        this.selectedVictoryDance = 0;
        this.selectedKit = "default";
        this.selectedPerk = 0;
        this.selectedBalloon = 0;
        this.selectedKillMessage = 0;
        this.selectedKillEffect = 0;
        this.ownedCages = new HashSet<>();
        this.ownedVictoryDances = new HashSet<>();
        this.ownedKits = new HashSet<>();
        this.ownedPerks = new HashSet<>();
        this.ownedBalloons = new HashSet<>();
        this.ownedKillMessages = new HashSet<>();
        this.ownedKillEffects = new HashSet<>();
    }

    @Override
    public int getSelectedCageId() {
        return selectedCage;
    }

    @Override
    public ICage getSelectedCage() {
        return WhaleSkyWars.getInstance().getCageManager().getById(selectedCage);
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
    public int getSelectedVictoryDanceId() {
        return selectedVictoryDance;
    }

    @Override
    public VictoryDance getSelectedVictoryDance() {
        return WhaleSkyWars.getInstance().getVictoryDanceManager().copyOf(selectedVictoryDance);
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
        return selectedKit;
    }

    @Override
    public void setSelectedKit(String selectedKit) {
        this.selectedKit = selectedKit;
    }

    @Override
    public boolean addCachedKit(String kit) {
        if (hasCachedKit(kit)) {
            return false;
        }
        ownedKits.add(kit);
        return true;
    }

    @Override
    public boolean removeCachedKit(String kit) {
        if (!hasCachedKit(kit)) {
            return false;
        }
        ownedKits.remove(kit);
        return true;
    }

    @Override
    public boolean hasCachedKit(String kit) {
        return ownedKits.contains(kit);
    }

    @Override
    public Set<String> getOwnedKits() {
        return ownedKits;
    }

    @Override
    public int getSelectedPerkId() {
        return selectedPerk;
    }

    @Override
    public Perk getSelectedPerk() {
        return WhaleSkyWars.getInstance().getPerkManager().of(selectedPerk);
    }

    @Override
    public void setSelectedPerk(int selectedPerk) {
        this.selectedPerk = selectedPerk;
    }

    @Override
    public boolean addCachedPerk(int perk) {
        if (hasCachedPerk(perk)) {
            return false;
        }
        ownedPerks.add(perk);
        return true;
    }

    @Override
    public boolean removeCachedPerk(int perk) {
        if (!hasCachedPerk(perk)) {
            return false;
        }
        ownedPerks.remove(perk);
        return true;
    }

    @Override
    public boolean hasCachedPerk(int perk) {
        return ownedPerks.contains(perk);
    }

    @Override
    public Set<Integer> getOwnedPerks() {
        return ownedPerks;
    }

    @Override
    public int getSelectedBalloonId() {
        return selectedBalloon;
    }

    @Override
    public IBalloon getSelectedBalloon() {
        return WhaleSkyWars.getInstance().getBalloonManager().getById(selectedBalloon);
    }

    @Override
    public void setSelectedBalloon(int selectedBalloon) {
        this.selectedBalloon = selectedBalloon;
    }

    @Override
    public boolean addCachedBalloon(int balloon) {
        if (hasCachedBalloon(balloon)) {
            return false;
        }
        ownedBalloons.add(balloon);
        return true;
    }

    @Override
    public boolean removeCachedBalloon(int balloon) {
        if (!hasCachedBalloon(balloon)) {
            return false;
        }
        ownedBalloons.remove(balloon);
        return true;
    }

    @Override
    public boolean hasCachedBalloon(int balloon) {
        return ownedBalloons.contains(balloon);
    }

    @Override
    public Set<Integer> getOwnedBalloons() {
        return ownedBalloons;
    }

    @Override
    public int getSelectedKillMessageId() {
        return selectedKillMessage;
    }

    @Override
    public KillMessage getSelectedKillMessage() {
        return WhaleSkyWars.getInstance().getKillMessageManager().of(selectedKillMessage);
    }

    @Override
    public void setSelectedKillMessage(int selectedKillMessage) {
        this.selectedKillMessage = selectedKillMessage;
    }

    @Override
    public boolean addCachedKillMessage(int message) {
        if (hasCachedKillMessage(message)) {
            return false;
        }
        ownedKillMessages.add(message);
        return true;
    }

    @Override
    public boolean removeCachedKillMessage(int message) {
        if (!hasCachedKillMessage(message)) {
            return false;
        }
        ownedKillMessages.remove(message);
        return true;
    }

    @Override
    public boolean hasCachedKillMessage(int message) {
        return ownedKillMessages.contains(message);
    }

    @Override
    public Set<Integer> getOwnedKillMessages() {
        return ownedKillMessages;
    }

    @Override
    public int getSelectedKillEffectId() {
        return selectedKillEffect;
    }

    @Override
    public KillEffect getSelectedKillEffect() {
        return WhaleSkyWars.getInstance().getKillEffectManager().of(selectedKillEffect);
    }

    @Override
    public void setSelectedKillEffect(int selectedKillEffect) {
        this.selectedKillEffect = selectedKillEffect;
    }

    @Override
    public boolean addCachedKillEffect(int effect) {
        if (hasCachedKillEffect(effect)) {
            return false;
        }
        ownedKillEffects.add(effect);
        return true;
    }

    @Override
    public boolean removeCachedKillEffect(int effect) {
        if (!hasCachedKillEffect(effect)) {
            return false;
        }
        ownedKillEffects.remove(effect);
        return true;
    }

    @Override
    public boolean hasCachedKillEffect(int effect) {
        return ownedKillEffects.contains(effect);
    }

    @Override
    public Set<Integer> getOwnedKillEffects() {
        return ownedKillEffects;
    }
}
