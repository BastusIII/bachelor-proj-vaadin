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
     * Ersteller des Spiels.
     */
    private IPlayer owner;

    /**
     * Spiel Konfiguration.
     */
    private IGameConfiguration config;

    /**
     * Konstruktor.
     */
    public Game() {
        this.players = new ArrayList<IPlayer>();
        this.teams = new ArrayList<ITeam>();
        this.dominationPoints = new ArrayList<IDominationPoint>();
        this.map = null;
        this.owner = null;
        this.config = null;
    }

    /**
     * Konstruktor. Konfigurationsdatei wird nicht verwendet um das model zu erstellen!!.
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
     * Konstruktor. Konfigurationsdatei wird nicht verwendet um das model zu erstellen!!.
     *
     * @param config Die Konfigurationsdatei
     * @param owner  The Owner
     * @param map    The Map
     */
    public Game(final IMap map, final IPlayer owner, final IGameConfiguration config) {
        this.players = new ArrayList<IPlayer>();
        this.teams = new ArrayList<ITeam>();
        this.dominationPoints = new ArrayList<IDominationPoint>();
        this.map = map;
        this.owner = owner;
        this.config = config;
    }

    /**
     * Copy constructor, deep copying given {@link IGame}.
     *
     * @param game {@link IGame} object that will be copied.
     * @author Marco Wolff
     */
    public Game(IGame game) {
        this(new Map(game.getMap()), new Player(game.getOwner()),
                new GameConfiguration(game.getGameConfiguration()));
        for (IPlayer player : game.getPlayers()) {
            players.add(new Player(player));
        }
        for (ITeam team : game.getTeams()) {
            teams.add(new Team(team));
        }
        for (IDominationPoint dominationPoint : game.getDominationPoints()) {
            dominationPoints.add(new DominationPoint(dominationPoint));
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
        return this.owner;
    }

    @Override
    public void setOwner(final IPlayer player) {
        this.owner = player;
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
