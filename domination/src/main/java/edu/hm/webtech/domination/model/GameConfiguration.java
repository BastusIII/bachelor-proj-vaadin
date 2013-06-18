package edu.hm.webtech.domination.model;

/**
 * Default implementation of {@link IGameConfiguration}.
 * 
 * @author Marco Wolff
 * 
 */

public class GameConfiguration implements IGameConfiguration {

	/**
	 * Score limit which a team needs to hit in order to win!
	 */
	private final int scoreLimit;

	/**
	 * Constructor, setting the score limit.
	 * 
	 * @param scoreLimit
	 */
	public GameConfiguration(int scoreLimit) {
		this.scoreLimit = scoreLimit;
	}

	/**
	 * Copy constructor, deep copying given {@link IGameConfiguration}.
	 * 
	 * @author Marco Wolff
	 * @param player
	 *            {@link IGameConfiguration} object that will be copied.
	 */
	public GameConfiguration(IGameConfiguration gameConfiguration) {
		if (gameConfiguration == null) {
			throw new IllegalArgumentException("Game configuration may not be null!");
		}
		this.scoreLimit = gameConfiguration.getScoreLimit();
	}

	@Override
	public int getScoreLimit() {
		return scoreLimit;
	}

}
