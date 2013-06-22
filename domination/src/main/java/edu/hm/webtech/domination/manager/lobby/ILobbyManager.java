package edu.hm.webtech.domination.manager.lobby;

import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IGameConfiguration;

import java.util.Collection;

/**
 * Defines methods which are necessary for handling a lobby which allows
 * creating and joining of {@link IGameManager}s.
 * 
 * @author Marco Wolff, Daniel Brielbeck, Sebastian Stumpf
 * 
 */
public interface ILobbyManager {

	/**
	 * Creates a new {@link IGameManager} containing a new {@link IGame} with
	 * given {@link IGameConfiguration}. A player can create exactly one game.
	 * 
	 * @param gameConfiguration
	 *            {@link IGameConfiguration} for the new {@link IGame} object.
	 * @return {@link IGameManager} handling the created {@link IGame}. Null if game name already in use. Null, if player already owns a game.
	 */
	public IGameManager createGame(final IGameConfiguration gameConfiguration);

    /**
     * Removes a game manager from the lobby. Only games with no players can be deleted.
     * @param gameManager The game to remove.
*                    @return true if the game was sucessfully removed
     */
    public boolean removeGame(final IGameManager gameManager);

	/**
	 * @return {@link Collection} containing all {@link IGameManager}s which are
	 *         representing currently existing {@link IGame}s.
	 */
	public Collection<IGameManager> getGames();

    /**
     *
     * @return the counter for all created games.
     */
    public int getMaxGameCounter();
}
