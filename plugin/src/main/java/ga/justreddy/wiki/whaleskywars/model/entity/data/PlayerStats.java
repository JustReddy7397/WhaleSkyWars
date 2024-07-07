package ga.justreddy.wiki.whaleskywars.model.entity.data;

import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerStats;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameMode;

/**
 * @author JustReddy
 */
public class PlayerStats implements IPlayerStats {

    private int soloWins = 0;
    private int teamWins = 0;
    private int soloDeaths = 0;
    private int teamDeaths = 0;
    private int soloKills = 0;
    private int teamKills = 0;
    private int soloGamesPlayed = 0;
    private int teamGamesPlayed = 0;
    private int arrowsShot = 0;
    private int arrowsHit = 0;
    private int blocksPlaced = 0;
    private int blocksBroken = 0;
    private int travelledBlocks = 0;
    private int damageDealt = 0;
    private int damageTaken = 0;

    @Override
    public int getSoloWins() {
        return soloWins;
    }

    @Override
    public int getTeamWins() {
        return teamWins;
    }

    @Override
    public int getTotalWins() {
        return soloWins + teamWins;
    }

    @Override
    public int getSoloDeaths() {
        return soloDeaths;
    }

    @Override
    public int getTeamDeaths() {
        return teamDeaths;
    }

    @Override
    public int getTotalDeaths() {
        return soloDeaths + teamDeaths;
    }

    @Override
    public int getSoloKills() {
        return soloKills;
    }

    @Override
    public int getTeamKills() {
        return teamKills;
    }

    @Override
    public int getTotalKills() {
        return soloKills + teamKills;
    }

    @Override
    public int getSoloGamesPlayed() {
        return soloGamesPlayed;
    }

    @Override
    public int getTeamGamesPlayed() {
        return teamGamesPlayed;
    }

    @Override
    public int getTotalGamesPlayed() {
        return soloGamesPlayed + teamGamesPlayed;
    }

    @Override
    public int getArrowsShot() {
        return arrowsShot;
    }

    @Override
    public int getArrowsHit() {
        return arrowsHit;
    }

    @Override
    public int getBlocksPlaced() {
        return blocksPlaced;
    }

    @Override
    public int getBlocksBroken() {
        return blocksBroken;
    }

    @Override
    public int getTravelledBlocks() {
        return travelledBlocks;
    }

    @Override
    public int getDamageDealt() {
        return damageDealt;
    }

    @Override
    public int getDamageTaken() {
        return damageTaken;
    }

    @Override
    public void addWin(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloWins++;
                break;
            case TEAM:
                teamWins++;
                break;
        }
    }

    @Override
    public void addDeath(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloDeaths++;
                break;
            case TEAM:
                teamDeaths++;
                break;
        }
    }

    @Override
    public void addKill(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloKills++;
                break;
            case TEAM:
                teamKills++;
                break;
        }
    }

    @Override
    public void addGamePlayed(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloGamesPlayed++;
                break;
            case TEAM:
                teamGamesPlayed++;
                break;
        }
    }

    @Override
    public void addArrowsShot(int arrowsShot) {
        this.arrowsShot += arrowsShot;
    }

    @Override
    public void addArrowsHit(int arrowsHit) {
        this.arrowsHit += arrowsHit;
    }

    @Override
    public void addBlocksPlaced(int blocksPlaced) {
        this.blocksPlaced += blocksPlaced;
    }

    @Override
    public void addBlocksBroken(int blocksBroken) {
        this.blocksBroken += blocksBroken;
    }

    @Override
    public void addTravelledBlocks(int travelledBlocks) {
        this.travelledBlocks += travelledBlocks;
    }

    @Override
    public void addDamageDealt(int damageDealt) {
        this.damageDealt += damageDealt;
    }

    @Override
    public void addDamageTaken(int damageTaken) {
        this.damageTaken += damageTaken;
    }

    @Override
    public void removeWin(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloWins--;
                break;
            case TEAM:
                teamWins--;
                break;
        }
    }

    @Override
    public void removeDeath(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloDeaths--;
                break;
            case TEAM:
                teamDeaths--;
                break;
        }
    }

    @Override
    public void removeKill(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloKills--;
                break;
            case TEAM:
                teamKills--;
                break;
        }
    }

    @Override
    public void removeGamePlayed(GameMode mode) {
        switch (mode) {
            case SOLO:
                soloGamesPlayed--;
                break;
            case TEAM:
                teamGamesPlayed--;
                break;
        }
    }

    @Override
    public void removeArrowsShot(int arrowsShot) {
        this.arrowsShot -= arrowsShot;
    }

    @Override
    public void removeArrowsHit(int arrowsHit) {
        this.arrowsHit -= arrowsHit;
    }

    @Override
    public void removeBlocksPlaced(int blocksPlaced) {
        this.blocksPlaced -= blocksPlaced;
    }

    @Override
    public void removeBlocksBroken(int blocksBroken) {
        this.blocksBroken -= blocksBroken;
    }

    @Override
    public void removeTravelledBlocks(int travelledBlocks) {
        this.travelledBlocks -= travelledBlocks;
    }

    @Override
    public void removeDamageDealt(int damageDealt) {
        this.damageDealt -= damageDealt;
    }

    @Override
    public void removeDamageTaken(int damageTaken) {
        this.damageTaken -= damageTaken;
    }

    @Override
    public void setSoloWins(int soloWins) {
        this.soloWins = soloWins;
    }

    @Override
    public void setTeamWins(int teamWins) {
        this.teamWins = teamWins;
    }

    @Override
    public void setTotalWins(GameMode mode, int totalWins) {
        switch (mode) {
            case SOLO:
                setSoloWins(totalWins);
                break;
            case TEAM:
                setTeamWins(totalWins);
                break;
        }
    }

    @Override
    public void setSoloDeaths(int soloDeaths) {
        this.soloDeaths = soloDeaths;
    }

    @Override
    public void setTeamDeaths(int teamDeaths) {
        this.teamDeaths = teamDeaths;
    }

    @Override
    public void setTotalDeaths(GameMode mode, int totalDeaths) {
        switch (mode) {
            case SOLO:
                setSoloDeaths(totalDeaths);
                break;
            case TEAM:
                setTeamDeaths(totalDeaths);
                break;
        }
    }

    @Override
    public void setSoloKills(int soloKills) {

    }

    @Override
    public void setTeamKills(int teamKills) {

    }

    @Override
    public void setTotalKills(GameMode mode, int totalKills) {
        switch (mode) {
            case SOLO:
                setSoloKills(totalKills);
                break;
            case TEAM:
                setTeamKills(totalKills);
                break;
        }
    }

    @Override
    public void setSoloGamesPlayed(int soloGamesPlayed) {

    }

    @Override
    public void setTeamGamesPlayed(int teamGamesPlayed) {

    }

    @Override
    public void setTotalGamesPlayed(GameMode mode, int totalGamesPlayed) {
        switch (mode) {
            case SOLO:
                setSoloGamesPlayed(totalGamesPlayed);
                break;
            case TEAM:
                setTeamGamesPlayed(totalGamesPlayed);
                break;
        }
    }

    @Override
    public void setArrowsShot(int arrowsShot) {
        this.arrowsShot = arrowsShot;
    }

    @Override
    public void setArrowsHit(int arrowsHit) {
        this.arrowsHit = arrowsHit;
    }

    @Override
    public void setBlocksPlaced(int blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
    }

    @Override
    public void setBlocksBroken(int blocksBroken) {
        this.blocksBroken = blocksBroken;
    }

    @Override
    public void setTravelledBlocks(int travelledBlocks) {
        this.travelledBlocks = travelledBlocks;
    }

    @Override
    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    @Override
    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    @Override
    public void resetStats() {
        this.soloWins = 0;
        this.teamWins = 0;
        this.soloDeaths = 0;
        this.teamDeaths = 0;
        this.soloKills = 0;
        this.teamKills = 0;
        this.soloGamesPlayed = 0;
        this.teamGamesPlayed = 0;
        this.arrowsShot = 0;
        this.arrowsHit = 0;
        this.blocksPlaced = 0;
        this.blocksBroken = 0;
        this.travelledBlocks = 0;
        this.damageDealt = 0;
        this.damageTaken = 0;
    }

    @Override
    public void resetStats(GameMode mode) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
