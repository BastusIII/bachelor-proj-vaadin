package edu.hm.webtech.domination.manager.lobby;

import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IGameConfiguration;

import java.util.Collection;

/**
 * Defines methods which are necessary for handling a lobby which allows
 * creating and joining of {@link IGameManager}s.
 * 
 * @author Marco Wolff
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
}
