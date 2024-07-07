package ga.justreddy.wiki.whaleskywars.api.model.entity.data;

import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameMode;

/**
 * @author JustReddy
 */
public interface IPlayerStats {

    int getSoloWins();

    int getTeamWins();

    int getTotalWins();

    int getSoloDeaths();

    int getTeamDeaths();

    int getTotalDeaths();

    int getSoloKills();

    int getTeamKills();

    int getTotalKills();

    int getSoloGamesPlayed();

    int getTeamGamesPlayed();

    int getTotalGamesPlayed();

    int getArrowsShot();

    int getArrowsHit();

    int getBlocksPlaced();

    int getBlocksBroken();

    int getTravelledBlocks();

    int getDamageDealt();

    int getDamageTaken();

    void addWin(GameMode mode);

    void addDeath(GameMode mode);

    void addKill(GameMode mode);

    void addGamePlayed(GameMode mode);

    void addArrowsShot(int arrowsShot);

    void addArrowsHit(int arrowsHit);

    void addBlocksPlaced(int blocksPlaced);

    void addBlocksBroken(int blocksBroken);

    void addTravelledBlocks(int travelledBlocks);

    void addDamageDealt(int damageDealt);

    void addDamageTaken(int damageTaken);

    void removeWin(GameMode mode);

    void removeDeath(GameMode mode);

    void removeKill(GameMode mode);

    void removeGamePlayed(GameMode mode);

    void removeArrowsShot(int arrowsShot);

    void removeArrowsHit(int arrowsHit);

    void removeBlocksPlaced(int blocksPlaced);

    void removeBlocksBroken(int blocksBroken);

    void removeTravelledBlocks(int travelledBlocks);

    void removeDamageDealt(int damageDealt);

    void removeDamageTaken(int damageTaken);

    void setSoloWins(int soloWins);

    void setTeamWins(int teamWins);

    void setTotalWins(GameMode mode, int totalWins);

    void setSoloDeaths(int soloDeaths);

    void setTeamDeaths(int teamDeaths);

    void setTotalDeaths(GameMode mode, int totalDeaths);

    void setSoloKills(int soloKills);

    void setTeamKills(int teamKills);

    void setTotalKills(GameMode mode, int totalKills);

    void setSoloGamesPlayed(int soloGamesPlayed);

    void setTeamGamesPlayed(int teamGamesPlayed);

    void setTotalGamesPlayed(GameMode mode, int totalGamesPlayed);

    void setArrowsShot(int arrowsShot);

    void setArrowsHit(int arrowsHit);

    void setBlocksPlaced(int blocksPlaced);

    void setBlocksBroken(int blocksBroken);

    void setTravelledBlocks(int travelledBlocks);

    void setDamageDealt(int damageDealt);

    void setDamageTaken(int damageTaken);

    void resetStats();

    void resetStats(GameMode mode);

}
