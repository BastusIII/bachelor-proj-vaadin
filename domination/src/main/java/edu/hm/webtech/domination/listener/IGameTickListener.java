package edu.hm.webtech.domination.listener;

import edu.hm.webtech.domination.manager.game.IGameManager;

/**
 * Defines methods for objects which react to the game tick of the
 * {@link IGameManager}.
 * 
 * @author Marco Wolff
 * 
 */
public interface IGameTickListener {

	/**
	 * Is called when a game tick happens. This is the place where the logic of
	 * the game tick reacting objects should be placed.
	 */
	public void tick();
}
