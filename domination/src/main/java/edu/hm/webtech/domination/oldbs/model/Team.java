package edu.hm.webtech.domination.oldbs.model;

import edu.hm.webtech.domination.model.TeamIdentifier;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScorePublisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 13:33
 * <h1>Team ist das Datenobjekt eines Teams, welches die Spieler enthält, eine Farbe, Namen und Punktestand des Teams speichert.</h1>
 * <p>Name und Identifier können nach Erstellung des Teams nicht mehr geändert werden.</p>
 */
@Deprecated
public class Team implements ScorePublisher {
    /**
     * Der Identifier des Teams ist eine Farbe.
     */
    private final TeamIdentifier color;
    /**
     * Der Name des Teams, nicht eindeutig.
     */
    private final String name;
    /**
     * Der Punktestand des Teams.
     */
    private int score;
    /**
     * Die Liste der Spieler des Teams.
     */
    private Collection<Player> players;

    /**
     * Contains components, which are notified when the score changes.
     */
    private List<ScoreListener> scoreListeners = new ArrayList<ScoreListener>();

    /**
     * Konstrutor.
     *
     * @param color Die Farbe.
     * @param name  Der Name.
     * @param score Der Punktestand.
     * @throws IllegalArgumentException if either color or name are null.
     */
    public Team(final TeamIdentifier color, final String name, final int score) {
        if (color == null || name == null) throw new IllegalArgumentException("Color and name must not be null.");
        this.color = color;
        this.name = name;
        this.score = score;
        this.players = new ArrayList<Player>();
    }

    /**
     * Getter für den Identifier.
     *
     * @return Der Identifier.
     */
    public TeamIdentifier getColor() {
        return color;
    }

    /**
     * Getter für den Namen.
     *
     * @return Der Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter für den Punktestand.
     *
     * @return Der Punktestand.
     */
    public int getScore() {
        return score;
    }

    /**
     * Addiert einen Wert zum Punktestand.
     */
    public void addScore(final int score) {
        this.score += score;
    }

    /**
     * Fügt einen Spieler zur Spielerliste hinzu.
     *
     * @param player Der Spieler.
     * @return True, wenn die Spielerliste sich verändert hat, d.h. der Spieler war noch nicht in der Liste.
     * @throws IllegalArgumentException if player is null.
     */
    public boolean addPlayer(final Player player) {
        if (player == null) throw new IllegalArgumentException("Player must not be null.");
        return this.players.add(player);

    }

    /**
     * Löscht einen Spieler von der Spielerliste.
     *
     * @param player Der Spieler.
     * @return True, wenn die Spielerliste sich verändert hat, d.h. der Spieler war in der Liste.
     * @throws IllegalArgumentException if player is null.
     */
    public boolean removePlayer(final Player player) {
        if (player == null) throw new IllegalArgumentException("Player must not be null.");
        return this.players.remove(player);
    }

    /**
     * Gibt eine Kopie der Spielerliste zurück.
     *
     * @return Eine Kopie der Spielerliste.
     */
    public Collection<Player> getPlayers() {
        return new ArrayList<Player>(this.players);
    }

    /**
     * Adds the specified component, which gets notified when the score changes.
     * @param scoreListener the component to be added
     */
    public void subscribeScoreChange(ScoreListener scoreListener) {
        scoreListeners.add(scoreListener);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (color != team.color) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
