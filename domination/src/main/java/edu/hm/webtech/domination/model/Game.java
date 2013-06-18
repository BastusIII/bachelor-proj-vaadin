package edu.hm.webtech.domination.model;

import edu.hm.webtech.domination.exception.ModelException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>Das Datenobjekt für ein Spiel.</h1>
 *
 * @author Sebastian Stumpf
 */
public class Game implements IGame {
    /**
     * Die Spielerliste.
     */
    private Collection<IPlayer> players;
    /**
     * Die Liste der Teams.
     */
    private Collection<ITeam> teams;
    /**
     * Die Liste der Domination Points.
     */
    private Collection<IDominationPoint> dominationPoints;
    /**
     * Die Spielkarte.
     */
    private IMap map;

    /**
     * Spiel Konfiguration.
     */
    private IGameConfiguration config;

    /**
     * Konstruktor. Konfigurationsdatei wird nicht verwendet um das model zu
     * erstellen!!.
     *
     * @param config Die Konfigurationsdatei
     */
    public Game(final IGameConfiguration config) {
        this.players = new ArrayList<IPlayer>();
        this.teams = new ArrayList<ITeam>();
        this.dominationPoints = new ArrayList<IDominationPoint>();
        this.config = config;
    }

    /**
     * Konstruktor. Konfigurationsdatei wird nicht verwendet um das model zu
     * erstellen!!.
     *
     * @param config Die Konfigurationsdatei
     * @param map    The Map
     */
    public Game(final IMap map, final IGameConfiguration config) {
        this.players = new ArrayList<IPlayer>();
        this.teams = new ArrayList<ITeam>();
        this.dominationPoints = new ArrayList<IDominationPoint>();
        this.map = map;
        this.config = config;
    }

    /**
     * Copy constructor, deep copying given {@link IGame}.
     *
     * @param game {@link IGame} object that will be copied.
     * @author Marco Wolff
     */
    public Game(IGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Game may not be null!");
        }
        IMap gamesMap = game.getMap();
        if (gamesMap == null) {
            this.map = null;
        } else {
            this.map = new Map(gamesMap);
        }
        IGameConfiguration gamesConfig = game.getGameConfiguration();
        if (gamesConfig == null) {
            this.config = null;
        } else {
            this.config = new GameConfiguration(gamesConfig);
        }
        this.players = new ArrayList<IPlayer>();
        for (IPlayer player : game.getPlayers()) {
            this.players.add(new Player(player));
        }
        this.teams = new ArrayList<ITeam>();
        for (ITeam team : game.getTeams()) {
            this.teams.add(new Team(team));
        }
        this.dominationPoints = new ArrayList<IDominationPoint>();
        for (IDominationPoint dominationPoint : game.getDominationPoints()) {
            this.dominationPoints.add(new DominationPoint(dominationPoint));
        }
    }

    @Override
    public void addPlayer(final IPlayer player) throws ModelException {
        if (!this.players.add(player))
            throw new ModelException("Player could not be added.");
    }

    @Override
    public void removePlayer(final IPlayer player) throws ModelException {
        if (!this.players.remove(player))
            throw new ModelException("Player could not be removed.");
    }

    @Override
    public void addTeam(final ITeam team) throws ModelException {
        if (!this.teams.add(team))
            throw new ModelException("Team could not be added.");
    }

    @Override
    public void removeTeam(final ITeam team) throws ModelException {
        if (!this.teams.remove(team))
            throw new ModelException("Team could not be removed.");
    }

    @Override
    public void addDominationPoint(final IDominationPoint dominationPoint)
            throws ModelException {
        if (!this.dominationPoints.add(dominationPoint))
            throw new ModelException("Domination Point could not be added.");
    }

    @Override
    public void removeDominationPoint(final IDominationPoint dominationPoint)
            throws ModelException {
        if (!this.dominationPoints.remove(dominationPoint))
            throw new ModelException("Domination Point could not be removed.");
    }

    @Override
    public void setMap(final IMap map) {
        this.map = map;
    }

    @Override
    public IMap getMap() {
        return this.map;
    }

    @Override
    public IPlayer getOwner() {
        return this.config.getOwner();
    }

    @Override
    public IGameConfiguration getGameConfiguration() {
        return this.config;
    }

    @Override
    public void setGameConfiguration(final IGameConfiguration gameConfiguration) {
        this.config = gameConfiguration;
    }

    @Override
    public Collection<IPlayer> getPlayers() {
        return this.players;
    }

    @Override
    public Collection<ITeam> getTeams() {
        return this.teams;
    }

    @Override
    public Collection<IDominationPoint> getDominationPoints() {
        return this.dominationPoints;
    }
}
