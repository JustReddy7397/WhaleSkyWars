package ga.justreddy.wiki.whaleskywars.api.model.entity.data;

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

    void addWin(boolean teamGame);

    void addDeath(boolean teamGame);

    void addKill(boolean teamGame);

    void addGamePlayed(boolean teamGame);

    void addArrowsShot(int arrowsShot);

    void addArrowsHit(int arrowsHit);

    void addBlocksPlaced(int blocksPlaced);

    void addBlocksBroken(int blocksBroken);

    void addTravelledBlocks(int travelledBlocks);

    void addDamageDealt(int damageDealt);

    void addDamageTaken(int damageTaken);

    void removeWin(boolean teamGame);

    void removeDeath(boolean teamGame);

    void removeKill(boolean teamGame);

    void removeGamePlayed(boolean teamGame);

    void removeArrowsShot(int arrowsShot);

    void removeArrowsHit(int arrowsHit);

    void removeBlocksPlaced(int blocksPlaced);

    void removeBlocksBroken(int blocksBroken);

    void removeTravelledBlocks(int travelledBlocks);

    void removeDamageDealt(int damageDealt);

    void removeDamageTaken(int damageTaken);

    void setSoloWins(int soloWins);

    void setTeamWins(int teamWins);

    void setTotalWins(boolean teamGame, int totalWins);

    void setSoloDeaths(int soloDeaths);

    void setTeamDeaths(int teamDeaths);

    void setTotalDeaths(boolean teamGame, int totalDeaths);

    void setSoloKills(int soloKills);

    void setTeamKills(int teamKills);

    void setTotalKills(boolean teamGame, int totalKills);

    void setSoloGamesPlayed(int soloGamesPlayed);

    void setTeamGamesPlayed(int teamGamesPlayed);

    void setTotalGamesPlayed(boolean teamGame, int totalGamesPlayed);

    void setArrowsShot(int arrowsShot);

    void setArrowsHit(int arrowsHit);

    void setBlocksPlaced(int blocksPlaced);

    void setBlocksBroken(int blocksBroken);

    void setTravelledBlocks(int travelledBlocks);

    void setDamageDealt(int damageDealt);

    void setDamageTaken(int damageTaken);

    void resetStats();

    void resetStats(boolean teamGame);

}
