package edu.hm.webtech.domination.manager.lobby;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IGameConfiguration;
import edu.hm.webtech.domination.model.Player;

import java.util.Collection;

/**
 * Defines methods which are necessary for handling a lobby which allows
 * creating and joining of {@link IGameManager}s.
 * 
 * @author Marco Wolff, Daniel Brielbeck
 * 
 */
public interface ILobbyManager {

	/**
	 * Creates a new {@link IGameManager} containing a new {@link IGame} with
	 * given {@link IGameConfiguration}.
	 * 
	 * @param gameConfiguration
	 *            {@link IGameConfiguration} for the new {@link IGame} object.
	 * @return {@link IGameManager} handling the created {@link IGame}.
	 */
	public IGameManager createGame(IGameConfiguration gameConfiguration);

	/**
	 * @return {@link Collection} containing all {@link IGameManager}s which are
	 *         representing currently existing {@link IGame}s.
	 */
	public Collection<IGameManager> getGames();
		
	/**
	 * Adds a new Player to the list of waiting players
	 * @param player
	 * 			username to check, if already exists
	 * @author Daniel Brielbeck
	 */
	public void addWaitingPlayer(Player player) throws ModelException;
	
}
