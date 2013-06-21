package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Defines basic methods which are necessary for handling game logic and the
 * like on a {@link IGame} object.
 * 
 * @author Marco Wolff
 * 
 */
public interface IGameManager {

	/**
	 * Joins given {@link IPlayer} to the {@link IGame}.
	 * 
	 * @param player
	 *            {@link IPlayer} which will be joined to the {@link IGame}.
	 */
	public void joinGame(IPlayer player);

	/**
	 * Leaves given {@link IPlayer} from the {@link IGame}.
	 * 
	 * @param player
	 *            {@link IPlayer} which will leave the {@link IGame}.
	 */
	public void leaveGame(IPlayer player);

	/**
	 * Changes {@link ITeam} of given {@link IPlayer} to the given {@link ITeam}
	 * .
	 * 
	 * @param player
	 *            {@link IPlayer} which will be changing {@link ITeam}s.
	 * @param team
	 *            new {@link ITeam} of the given {@link IPlayer}.
	 */
	public void changeTeam(IPlayer player, ITeam team);

	/**
	 * @return the {@link IGame} object which is managed by this game manager.
	 */
	public IGame getGame();

	/**
	 * Start the {@link IGame} so that the domination may begin.
	 */
	public void startGame();

	/**
	 * Updates the location on given {@link IPlayer} to given value of longitude
	 * and latitude.
	 * 
	 * @param player
	 *            {@link IPlayer} whichs location will be updated.
	 * @param longitude
	 *            longitude of the new location.
	 * @param latitude
	 *            latitude of the new location.
	 */
	public void updateLocation(IPlayer player, double longitude, double latitude);

	/**
	 * @return {@link ITeam} which is the winner of the game, or 'null' if there
	 *         is no winner yet.
	 */
	public ITeam getWinnerTeam();

	/**
	 * Checks if the current {@link IGame} fulfills the starting criteria.
	 * 
	 * @return true, if starting criteria is fulfilled, else false.
	 */
	public boolean isGameReady();
}
