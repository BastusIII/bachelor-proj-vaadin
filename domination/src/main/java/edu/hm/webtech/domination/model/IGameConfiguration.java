package edu.hm.webtech.domination.model;

/**
 * Defines methods which are used in order to acces the configuration of an
 * {@link IGame} object.
 *
 * @author Marco Wolff
 */
public interface IGameConfiguration {

    // TODO EXPAND ME IF NECESSARY!

    /**
     * @return score limit of the game for a {@link ITeam} in order to win!
     */
    public int getScoreLimit();

    /**
     * @return zoom factor of the game map.
     */
    int getZoomFactor();

    /**
     * @return number of teams.
     */
    int getNumberOfTeams();

    /**
     * @return max players per team.
     */
    int getMaxPlayersPerTeam();

    /**
     * @return number of domination points.
     */
    int getNumberOfDominationPoints();

    /**
     * @return owner of the created game.
     */
    IPlayer getOwner();

    /**
     * @return the type of the game.
     */
    GameType getGameType();
}
