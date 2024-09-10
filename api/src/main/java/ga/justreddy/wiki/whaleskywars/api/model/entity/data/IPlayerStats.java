package ga.justreddy.wiki.whaleskywars.api.model.entity.data;

/**
 * Interface representing the statistics of a player in the game.
 * Provides methods to get, set, add, and remove various statistics.
 * Also includes methods to reset statistics.
 * <p>
 * Implementations of this interface should handle the storage and retrieval of these statistics.
 * <p>
 * @author JustReddy
 */
public interface IPlayerStats {

    /**
     * Returns the number of solo wins.
     *
     * @return the number of solo wins.
     */
    int getSoloWins();

    /**
     * Returns the number of team wins.
     *
     * @return the number of team wins.
     */
    int getTeamWins();

    /**
     * Returns the total number of wins.
     *
     * @return the total number of wins.
     */
    int getTotalWins();

    /**
     * Returns the number of solo deaths.
     *
     * @return the number of solo deaths.
     */
    int getSoloDeaths();

    /**
     * Returns the number of team deaths.
     *
     * @return the number of team deaths.
     */
    int getTeamDeaths();

    /**
     * Returns the total number of deaths.
     *
     * @return the total number of deaths.
     */
    int getTotalDeaths();

    /**
     * Returns the number of solo kills.
     *
     * @return the number of solo kills.
     */
    int getSoloKills();

    /**
     * Returns the number of team kills.
     *
     * @return the number of team kills.
     */
    int getTeamKills();

    /**
     * Returns the total number of kills.
     *
     * @return the total number of kills.
     */
    int getTotalKills();

    /**
     * Returns the number of solo games played.
     *
     * @return the number of solo games played.
     */
    int getSoloGamesPlayed();

    /**
     * Returns the number of team games played.
     *
     * @return the number of team games played.
     */
    int getTeamGamesPlayed();

    /**
     * Returns the total number of games played.
     *
     * @return the total number of games played.
     */
    int getTotalGamesPlayed();

    /**
     * Returns the number of arrows shot.
     *
     * @return the number of arrows shot.
     */
    int getArrowsShot();

    /**
     * Returns the number of arrows hit.
     *
     * @return the number of arrows hit.
     */
    int getArrowsHit();

    /**
     * Returns the number of blocks placed.
     *
     * @return the number of blocks placed.
     */
    int getBlocksPlaced();

    /**
     * Returns the number of blocks broken.
     *
     * @return the number of blocks broken.
     */
    int getBlocksBroken();

    /**
     * Returns the number of blocks travelled.
     *
     * @return the number of blocks travelled.
     */
    int getTravelledBlocks();

    /**
     * Returns the amount of damage dealt.
     *
     * @return the amount of damage dealt.
     */
    int getDamageDealt();

    /**
     * Returns the amount of damage taken.
     *
     * @return the amount of damage taken.
     */
    int getDamageTaken();

    /**
     * Adds a win to the player's statistics.
     *
     * @param teamGame true if the win is in a team game, false if in a solo game.
     */
    void addWin(boolean teamGame);

    /**
     * Adds a death to the player's statistics.
     *
     * @param teamGame true if the death is in a team game, false if in a solo game.
     */
    void addDeath(boolean teamGame);

    /**
     * Adds a kill to the player's statistics.
     *
     * @param teamGame true if the kill is in a team game, false if in a solo game.
     */
    void addKill(boolean teamGame);

    /**
     * Adds a game played to the player's statistics.
     *
     * @param teamGame true if the game played is in a team game, false if in a solo game.
     */
    void addGamePlayed(boolean teamGame);

    /**
     * Adds arrows shot to the player's statistics.
     *
     * @param arrowsShot the number of arrows shot to add.
     */
    void addArrowsShot(int arrowsShot);

    /**
     * Adds arrows hit to the player's statistics.
     *
     * @param arrowsHit the number of arrows hit to add.
     */
    void addArrowsHit(int arrowsHit);

    /**
     * Adds blocks placed to the player's statistics.
     *
     * @param blocksPlaced the number of blocks placed to add.
     */
    void addBlocksPlaced(int blocksPlaced);

    /**
     * Adds blocks broken to the player's statistics.
     *
     * @param blocksBroken the number of blocks broken to add.
     */
    void addBlocksBroken(int blocksBroken);

    /**
     * Adds travelled blocks to the player's statistics.
     *
     * @param travelledBlocks the number of blocks travelled to add.
     */
    void addTravelledBlocks(int travelledBlocks);

    /**
     * Adds damage dealt to the player's statistics.
     *
     * @param damageDealt the amount of damage dealt to add.
     */
    void addDamageDealt(int damageDealt);

    /**
     * Adds damage taken to the player's statistics.
     *
     * @param damageTaken the amount of damage taken to add.
     */
    void addDamageTaken(int damageTaken);

    /**
     * Removes a win from the player's statistics.
     *
     * @param teamGame true if the win is in a team game, false if in a solo game.
     */
    void removeWin(boolean teamGame);

    /**
     * Removes a death from the player's statistics.
     *
     * @param teamGame true if the death is in a team game, false if in a solo game.
     */
    void removeDeath(boolean teamGame);

    /**
     * Removes a kill from the player's statistics.
     *
     * @param teamGame true if the kill is in a team game, false if in a solo game.
     */
    void removeKill(boolean teamGame);

    /**
     * Removes a game played from the player's statistics.
     *
     * @param teamGame true if the game played is in a team game, false if in a solo game.
     */
    void removeGamePlayed(boolean teamGame);

    /**
     * Removes arrows shot from the player's statistics.
     *
     * @param arrowsShot the number of arrows shot to remove.
     */
    void removeArrowsShot(int arrowsShot);

    /**
     * Removes arrows hit from the player's statistics.
     *
     * @param arrowsHit the number of arrows hit to remove.
     */
    void removeArrowsHit(int arrowsHit);

    /**
     * Removes blocks placed from the player's statistics.
     *
     * @param blocksPlaced the number of blocks placed to remove.
     */
    void removeBlocksPlaced(int blocksPlaced);

    /**
     * Removes blocks broken from the player's statistics.
     *
     * @param blocksBroken the number of blocks broken to remove.
     */
    void removeBlocksBroken(int blocksBroken);

    /**
     * Removes travelled blocks from the player's statistics.
     *
     * @param travelledBlocks the number of blocks travelled to remove.
     */
    void removeTravelledBlocks(int travelledBlocks);

    /**
     * Removes damage dealt from the player's statistics.
     *
     * @param damageDealt the amount of damage dealt to remove.
     */
    void removeDamageDealt(int damageDealt);

    /**
     * Removes damage taken from the player's statistics.
     *
     * @param damageTaken the amount of damage taken to remove.
     */
    void removeDamageTaken(int damageTaken);

    /**
     * Sets the number of solo wins.
     *
     * @param soloWins the number of solo wins to set.
     */
    void setSoloWins(int soloWins);

    /**
     * Sets the number of team wins.
     *
     * @param teamWins the number of team wins to set.
     */
    void setTeamWins(int teamWins);

    /**
     * Sets the total number of wins.
     *
     * @param teamGame  true if the total wins are in a team game, false if in a solo game.
     * @param totalWins the total number of wins to set.
     */
    void setTotalWins(boolean teamGame, int totalWins);

    /**
     * Sets the number of solo deaths.
     *
     * @param soloDeaths the number of solo deaths to set.
     */
    void setSoloDeaths(int soloDeaths);

    /**
     * Sets the number of team deaths.
     *
     * @param teamDeaths the number of team deaths to set.
     */
    void setTeamDeaths(int teamDeaths);

    /**
     * Sets the total number of deaths.
     *
     * @param teamGame    true if the total deaths are in a team game, false if in a solo game.
     * @param totalDeaths the total number of deaths to set.
     */
    void setTotalDeaths(boolean teamGame, int totalDeaths);

    /**
     * Sets the number of solo kills.
     *
     * @param soloKills the number of solo kills to set.
     */
    void setSoloKills(int soloKills);

    /**
     * Sets the number of team kills.
     *
     * @param teamKills the number of team kills to set.
     */
    void setTeamKills(int teamKills);

    /**
     * Sets the total number of kills.
     *
     * @param teamGame   true if the total kills are in a team game, false if in a solo game.
     * @param totalKills the total number of kills to set.
     */
    void setTotalKills(boolean teamGame, int totalKills);

    /**
     * Sets the number of solo games played.
     *
     * @param soloGamesPlayed the number of solo games played to set.
     */
    void setSoloGamesPlayed(int soloGamesPlayed);

    /**
     * Sets the number of team games played.
     *
     * @param teamGamesPlayed the number of team games played to set.
     */
    void setTeamGamesPlayed(int teamGamesPlayed);

    /**
     * Sets the total number of games played.
     *
     * @param teamGame         true if the total games played are in a team game, false if in a solo game.
     * @param totalGamesPlayed the total number of games played to set.
     */
    void setTotalGamesPlayed(boolean teamGame, int totalGamesPlayed);

    /**
     * Sets the number of arrows shot.
     *
     * @param arrowsShot the number of arrows shot to set.
     */
    void setArrowsShot(int arrowsShot);

    /**
     * Sets the number of arrows hit.
     *
     * @param arrowsHit the number of arrows hit to set.
     */
    void setArrowsHit(int arrowsHit);

    /**
     * Sets the number of blocks placed.
     *
     * @param blocksPlaced the number of blocks placed to set.
     */
    void setBlocksPlaced(int blocksPlaced);

    /**
     * Sets the number of blocks broken.
     *
     * @param blocksBroken the number of blocks broken to set.
     */
    void setBlocksBroken(int blocksBroken);

    /**
     * Sets the number of blocks traveled.
     *
     * @param travelledBlocks the number of blocks traveled to set.
     */
    void setTraveledBlocks(int travelledBlocks);

    /**
     * Sets the amount of damage dealt.
     * @param damageDealt the amount of damage dealt to set.
     */
    void setDamageDealt(int damageDealt);

    /**
     * Sets the amount of damage taken.
     *
     * @param damageTaken the amount of damage taken to set.
     */
    void setDamageTaken(int damageTaken);

    /**
     * Resets all statistics.
     */
    void resetStats();

    /**
     * Resets all statistics for the specified game type.
     *
     * @param teamGame true if the statistics to reset are for a team game, false if for a solo game.
     */
    void resetStats(boolean teamGame);

}
