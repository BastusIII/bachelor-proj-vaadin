package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IScoreObject;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Defines methods for managers that are managing {@link IScoreObject}s in order
 * to determine if there is a winner.
 * 
 * @author Marco Wolff
 * 
 */
public interface IScoreManager {

	/**
	 * Checks if a {@link ITeam} is already a winner considering given
	 * {@link IGame} object returns that {@link ITeam} or in case there is no
	 * winner, null will be returned.
	 * 
	 * @param game
	 *            {@link IGame} object which will probide the data.
	 * @return {@link ITeam} which is the winner of the {@link IGame}, else null
	 *         if there is no winner yet.
	 */
	public ITeam checkForWinner(IGame game);
}
