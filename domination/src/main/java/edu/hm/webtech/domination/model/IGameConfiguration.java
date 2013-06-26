package edu.hm.webtech.domination.model;

/**
 * Defines methods which are used in order to acces the configuration of an
 * {@link IGame} object.
 * 
 * @author Marco Wolff
 */
public interface IGameConfiguration {

	/**
	 * @return score limit of the game for a {@link ITeam} in order to win!
	 */
	public int getScoreLimit();

	/**
	 * @return number of teams.
	 */
	public int getNumberOfTeams();

	/**
	 * @return max players per team.
	 */
	public int getMaxPlayersPerTeam();

	/**
	 * @return number of domination points.
	 */
	public int getNumberOfDominationPoints();

	/**
	 * @return owner of the created game.
	 */
	public IPlayer getOwner();

    /**
     * Sets the new owner.
     *
     * @param owner The new owner.
     */
    public void setOwner(IPlayer owner);

	/**
	 * @return the type of the game.
	 */
	public GameType getGameType();

	/**
	 * @return the identifier of the game.
	 */
	public String getName();
}
