package edu.hm.webtech.domination.model;

import java.util.Collection;

/**
 * Defines methods for a object representing a game in domination.
 * 
 * @author Marco Wolff
 * 
 */
public interface IGame {

	/**
	 * @return {@link IGameConfiguration} of this game object.
	 */
	public IGameConfiguration getGameConfiguration();

	/**
	 * Sets the {@link IGameConfiguration} to the given object.
	 * 
	 * @param gameConfiguration
	 *            {@link IGameConfiguration} containing the new game
	 *            configurations.
	 */
	public void setGameConfiguration(IGameConfiguration gameConfiguration);

	/**
	 * @return {@link Collection} containing all currently {@link IPlayer}s in
	 *         the game.
	 */
	public Collection<IPlayer> getPlayers();

	/**
	 * Adds given {@link IPlayer} to the game.
	 * 
	 * @param player
	 *            {@link IPlayer} which will be added to the game.
	 */
	public void addPlayer(IPlayer player);

	/**
	 * @return {@link Collection} containing all currently {@link ITeam}s in
	 *         this game.
	 */
	public Collection<ITeam> getTeams();

	/**
	 * Adds given {@link ITeam} to the game.
	 * 
	 * @param team
	 *            {@link ITeam} which will be added to the game.
	 */
	public void addTeam(ITeam team);

	/**
	 * @return {@link Collection} containing all {@link IDominationPoint}s of
	 *         the game.
	 */
	public Collection<IDominationPoint> getDominationPoints();

	/**
	 * Adds given {@link IDominationPoint} to the game.
	 * 
	 * @param dominationPoint
	 *            {@link IDominationPoint} which will be added to the game.
	 */
	public void addDominationPoint(IDominationPoint dominationPoint);

	/**
	 * @return {@link IMap} of this game.
	 */
	public IMap getMap();

	/**
	 * Sets the {@link IMap} of the game.
	 * 
	 * @param map
	 *            {@link IMap} which will be played on in the game.
	 */
	public void setMap(IMap map);

	/**
	 * @return {@link IPlayer} owning this game, which means he created it.
	 */
	public IPlayer getOwner();

	/**
	 * Sets the {@link IPlayer} owning this game object.
	 * 
	 * @param player
	 *            {@link IPlayer} which is owning this game object.
	 */
	public void setOwner(IPlayer player);
}
