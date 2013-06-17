package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;

/**
 * Marker interface for managers that are managing {@link IDominationPoint}s
 * based on game ticks by {@link IGameManager}.
 * 
 * @author Marco Wolff
 * 
 */
public interface IDominationManager {

	/**
	 * Uses given {@link IGame} object in order to calculate the capturing
	 * progresses on the {@link IDominationPoint}s in relation to team players
	 * in reach of said point.
	 * 
	 * @param game
	 *            {@link IGame} object which will provide the model of
	 *            consideration.
	 */
	public void updateCapturing(IGame game);
}
