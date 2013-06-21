package edu.hm.webtech.domination.manager.game;

import java.util.List;

import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Default implementation of {@link IScoreManager}.
 * 
 * @author Marco Wolff
 * 
 */
public class ScoreManagerImpl implements IScoreManager {

	/**
	 * The value of score, a team gets for owning a {@link IDominationPoint}.
	 */
	public static int SCORE_INCREMENT = 10;

	@Override
	public void tick(IGame game) {
		updateScore(game);
	}

	/**
	 * Update the scores on given {@link IGame} via the {@link IDominationPoint}
	 * s, using their owning {@link ITeam}.
	 * 
	 * @param game
	 *            {@link IGame} that will be considered.
	 */
	protected void updateScore(IGame game) {
		if (game == null) {
			throw new IllegalArgumentException("Game may not be null!");
		}

		List<IDominationPoint> dominationPoints = (List<IDominationPoint>) game
				.getDominationPoints();
		for (IDominationPoint dominationPoint : dominationPoints) {
			ITeam ownerTeam = dominationPoint.getOwnerTeam();
			if (ownerTeam != null) {
				ownerTeam.addScore(SCORE_INCREMENT);
			}
		}
	}
}
