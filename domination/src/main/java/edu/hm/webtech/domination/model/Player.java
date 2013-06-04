package edu.hm.webtech.domination.model;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 13:32
 * <h1>Player ist das Datenobjekt eines Spielers.</h1>
 */
public class Player extends ObjectCoordinates {
    /**
     * Der eindeutige Name des Spielers.
     */
    private final String name;
    /**
     * Das Team dem der Spieler angehört.
     */
    private Team team;
    /**
     * Die Punkte, die der Spieler durch Eroberungen gesammelt hat. Ein Prozent Eroberung = 1 Punkt.
     */
    private int score;

    /**
     * Konstruktor.
     *
     * @param x     Die x-Koordinate.
     * @param y     Die y-Koordinate.
     * @param name  Der Name des Spielers.
     * @param team  Das Team des Spielers.
     * @param score Die Punkte des Spielers.
     * @throws IllegalArgumentException wenn der Name null ist.
     */
    public Player(final double x, final double y, final String name, final Team team, final int score) {
        super(x, y);
        if (name == null) throw new IllegalArgumentException("Name must not be null.");
        this.name = name;
        this.team = team;
        this.score = score;
    }

    /**
     * Konstruktor für Spieler mit 0 Punkten.
     *
     * @param x    Die x-Koordinate.
     * @param y    Die y-Koordinate.
     * @param name Der Name des Spielers.
     * @param team Das Team des Spielers.
     * @throws IllegalArgumentException wenn der Name null ist.
     */
    public Player(final double x, final double y, final String name, final Team team) {
        this(x, y, name, team, 0);
    }

    /**
     * Konstruktor für Spieler der noch keinem Team angehört.
     *
     * @param x    Die x-Koordinate.
     * @param y    Die y-Koordinate.
     * @param name Der Name des Spielers.
     */
    public Player(final double x, final double y, final String name) {
        this(x, y, name, null);
    }

    /**
     * Getter für die Punkte des Spielers.
     *
     * @return Die Punkte des Spielers.
     */
    public int getScore() {
        return score;
    }

    /**
     * Addiert Punkte zu den Punkten des Spielers hinzu.
     *
     * @param toAdd Zu addierende Punkte.
     */
    public void addScore(int toAdd) {
        this.score += toAdd;
    }

    /**
     * Getter für das Team.
     *
     * @return Das Team oder null, wenn der Spieler keinem Team zugeordnet ist.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Setter für das Team.
     *
     * @param team Das Team oder null, wenn der Spieler keinem Team zugeordnet ist.
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Getter für den Spielernamen.
     *
     * @return Der Spielername.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Player player = (Player) o;

        if (name != null ? !name.equals(player.name) : player.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
