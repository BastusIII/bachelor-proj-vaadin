package edu.hm.webtech.domination.model;

import edu.hm.webtech.domination.exception.ModelException;

import java.util.Collection;

/**
 * Defines methods for a object representing a game in domination.
 *
 * @author Marco Wolff, Sebastian Stumpf
 */
public interface IGame {

    /**
     * @return {@link IGameConfiguration} of this game object.
     */
    public IGameConfiguration getGameConfiguration();

    /**
     * Sets the {@link IGameConfiguration} to the given object.
     *
     * @param gameConfiguration {@link IGameConfiguration} containing the new game
     *                          configurations.
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
     * @param player {@link IPlayer} which will be added to the game.
     * @throws ModelException if player could not be added.
     */
    public void addPlayer(IPlayer player) throws ModelException;

    /**
     * Removes given {@link IPlayer} from the game.
     *
     * @param player {@link IPlayer} which will be removed from the game.
     * @throws ModelException if player could not be removed.
     */
    public void removePlayer(IPlayer player) throws ModelException;

    /**
     * @return {@link Collection} containing all currently {@link ITeam}s in
     *         this game.
     */
    public Collection<ITeam> getTeams();

    /**
     * Adds given {@link ITeam} to the game.
     *
     * @param team {@link ITeam} which will be added to the game.
     * @throws ModelException if team could not be added.
     */
    public void addTeam(ITeam team) throws ModelException;

    /**
     * Removes given {@link ITeam} from the game.
     *
     * @param team {@link IPlayer} which will be removed from the game.
     * @throws ModelException if team could not be removed.
     */
    public void removeTeam(ITeam team) throws ModelException;

    /**
     * @return {@link Collection} containing all {@link IDominationPoint}s of
     *         the game.
     */
    public Collection<IDominationPoint> getDominationPoints();

    /**
     * Adds given {@link IDominationPoint} to the game.
     *
     * @param dominationPoint {@link IDominationPoint} which will be added to the game.
     * @throws ModelException if domination point could not be added.
     */
    public void addDominationPoint(IDominationPoint dominationPoint) throws ModelException;

    /**
     * Removes given {@link IDominationPoint} from the game.
     *
     * @param dominationPoint {@link IDominationPoint} which will be removed from the game.
     * @throws ModelException if domination point could not be removed.
     */
    public void removeDominationPoint(IDominationPoint dominationPoint) throws ModelException;

    /**
     * @return {@link IMap} of this game.
     */
    public IMap getMap();

    /**
     * Sets the {@link IMap} of the game.
     *
     * @param map {@link IMap} which will be played on in the game.
     */
    public void setMap(IMap map);

    /**
     * @return {@link IPlayer} owning this game, which means he created it.
     */
    public IPlayer getOwner();

    /**
     *
     * @return the name of the game.
     */
    public String getName();
}
