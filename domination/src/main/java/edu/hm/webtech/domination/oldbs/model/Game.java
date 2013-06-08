package edu.hm.webtech.domination.oldbs.model;

import java.util.ArrayList;
import java.util.Collection;

import edu.hm.webtech.domination.model.TeamIdentifier;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 13:32
 * <h1>Das Datenobjekt für ein Spiel.</h1>
 */
@Deprecated
public class Game {
    /**
     * Die Spielerliste.
     */
    private Collection<Player> players;
    /**
     * Die Liste der Teams.
     */
    private Collection<Team> teams;
    /**
     * Die Liste der Domination Points.
     */
    private Collection<DominationPoint> dominationPoints;
    /**
     * Die Spielkarte.
     */
    private Map map;

    /**
     * Konstruktor.
     *
     * @param map Die Spielkarte.
     */
    public Game(final Map map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null.");
        }
        this.players = new ArrayList<Player>();
        this.teams = new ArrayList<Team>();
        this.dominationPoints = new ArrayList<DominationPoint>();
        this.map = map;

    }

    /**
     * Einen Spieler zur Spielerliste hinzufügen.
     *
     * @param player Der Spieler.
     * @return True, wenn Spielerliste verändert wurde.
     */
    public boolean addPlayer(final Player player) {
        return this.players.add(player);
    }

    /**
     * Einen Spieler aus der Spielerliste löschen.
     *
     * @param player Der Spieler.
     * @return True, wenn Spielerliste verändert wurde.
     */
    public boolean removePlayer(final Player player) {
        return this.players.remove(player);
    }

    /**
     * Einen Spieler anhand seines Spielernamens aus der Spielerliste bekommen.
     *
     * @param playerName Der Spielername.
     * @return Der Spieler oder null, wenn nicht in der Liste.
     */
    public Player getPlayer(final String playerName) {
        Player player = null;
        for (Player temp : players) {
            if (temp.getName() == playerName) {
                player = temp;
                break;
            }
        }
        return player;
    }

    /**
     * Ein Team zur Teamliste hinzufügen.
     *
     * @param team Das Team.
     * @return True, wenn Liste verändert wurde.
     */
    public boolean addTeam(final Team team) {
        return this.teams.add(team);
    }

    /**
     * Ein Team aus der Teamliste löschen.
     *
     * @param team Das Team.
     * @return True, wenn Liste verändert wurde.
     */
    public boolean removeTeam(final Team team) {
        return this.teams.remove(team);
    }

    /**
     * Ein Team anhand seines Identifiers aus der Teamliste bekommen.
     *
     * @param teamIdentifier Der Identifier.
     * @return Das Team oder null, wenn nicht in der Liste.
     */
    public Team getTeam(final TeamIdentifier teamIdentifier) {
        Team team = null;
        for (Team temp : teams) {
            if (temp.getColor() == teamIdentifier) {
                team = temp;
                break;
            }
        }
        return team;
    }

    /**
     * Einen Domination Point zur Liste der Domination Points hinzufügen.
     *
     * @param dominationPoint Der Domination Point.
     * @return True, wenn Liste verändert wurde.
     */
    public boolean addDominationPoint(final DominationPoint dominationPoint) {
        return this.dominationPoints.add(dominationPoint);
    }

    /**
     * Einen Domination Point aus der Liste der Domination Points löschen.
     *
     * @param dominationPoint Der Domination Point.
     * @return True, wenn Liste verändert wurde.
     */
    public boolean removeDominationPoint(final DominationPoint dominationPoint) {
        return this.dominationPoints.remove(dominationPoint);
    }

    /**
     * Setter für die Spielkarte.
     *
     * @param map Die Spielkarte.
     */
    public void setMap(Map map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null.");
        }
        this.map = map;
    }

    /**
     * Getter für die Spielkarte.
     *
     * @return Die Spielkarte.
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Den Spieler mit playerName zum durch teamIdentifier gekennzeichneten Team hinzufügen.
     *
     * @param playerName     Der Spielername.
     * @param teamIdentifier Der Identifier bzw Farbe des Teams.
     * @return True, wenn der Spieler zum Team hinzugefügt werden konnte oder bereits im Team war.<br />
     *         False, wenn Spieler oder Team nicht in der Spieler, bzw. Teamliste des Spiels waren, oder der Spieler bereits einem anderen Team zugeordnet war.<br />
     *         Spieler bitte vorher aus Team löschen.
     */
    public boolean addPlayerToTeam(final String playerName, final TeamIdentifier teamIdentifier) {
        Player player = this.getPlayer(playerName);
        Team team = this.getTeam(teamIdentifier);
        if (team == null || player == null) {
            return false;
        }
        if (player.getTeam() != null) {
            return false;
        }
        team.addPlayer(player);
        player.setTeam(team);
        return true;
    }

    /**
     * Den Spieler player zu einem Team team hinzufügen.
     *
     * @param player Der Spieler.
     * @param team   Das Team.
     * @return True, wenn der Spieler zum Team hinzugefügt werden konnte oder bereits im Team war.<br />
     *         False, wenn Spieler oder Team nicht in der Spieler, bzw. Teamliste des Spiels waren, oder der Spieler bereits einem anderen Team zugeordnet war.<br />
     *         Spieler bitte vorher aus Team löschen.
     */
    public boolean addPlayerToTeam(final Player player, final Team team) {
        if (team == null || player == null) {
            return false;
        }
        if (!players.contains(player) || !teams.contains(team)) {
            return false;
        }
        if (player.getTeam() != null) {
            return false;
        }
        team.addPlayer(player);
        player.setTeam(team);
        return true;
    }

    /**
     * Den Spieler mit playerName aus seinem Team löschen.
     *
     * @param playerName Der Spielername.
     * @return True, wenn der Spieler gelöscht werden konnte oder keinem Team zugeordnet war.<br />
     *         False, wenn Spieler nicht in der Spielerliste war.
     */
    public boolean removePlayerFromTeam(final String playerName) {
        Player player = this.getPlayer(playerName);
        if (player == null) {
            return false;
        }
        Team team = player.getTeam();
        if (team == null) {
            return true;
        }
        team.removePlayer(player);
        player.setTeam(null);
        return true;
    }

    /**
     * Den Spieler player aus seinem Team löschen.
     *
     * @param player Der Spieler.
     * @return True, wenn der Spieler gelöscht werden konnte oder keinem Team zugeordnet war.<br />
     *         False, wenn Spieler nicht in der Spielerliste war.
     */
    public boolean removePlayerFromTeam(final Player player) {
        if (!players.contains(player)) {
            return false;
        }
        Team team = player.getTeam();
        if (team == null) {
            return true;
        }
        team.removePlayer(player);
        player.setTeam(null);
        return true;
    }

    /**
     * Getter für die Spielerliste.
     *
     * @return Die Spielerliste.
     */
    public Collection<Player> getPlayers() {
        return players;
    }

    /**
     * Getter für die Teamliste.
     *
     * @return Die Teamliste.
     */
    public Collection<Team> getTeams() {
        return teams;
    }

    /**
     * Getter für die Liste der Domination Points.
     *
     * @return Die Liste der Domination Points.
     */
    public Collection<DominationPoint> getDominationPoints() {
        return dominationPoints;
    }
}
