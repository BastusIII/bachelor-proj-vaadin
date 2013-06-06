package edu.hm.webtech.domination.model;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 13:30
 * <h1>DominationPoint ist das Datenobjekt eines zu erobernden Punktes auf dem Map.</h1>
 */
public class DominationPoint extends ObjectCoordinates {

    /**
     * Radius, in dem man sich befinden muss, um zu erobern in Zentimetern.
     */
    private int radius;
    /**
     * Der Stand der Eroberung in Prozent. 0<=score<=100;
     */
    private int score;
    /**
     * Der Zustand in dem sich der Domination Point gerade befindet.
     */
    private Status status;
    /**
     * Das gerade erobernde Team. Kann null sein.
     */
    private Team capturingTeam;
    /**
     * Das gerade dominierende Team. Kann null sein.
     */
    private Team dominatingTeam;


    /**
     * Konstruktor.
     *
     * @param x      Die x-Koordinate.
     * @param y      Die y-Koordinate.
     * @param radius Der Radius.
     * @param score  Der Stand der Eroberung in Prozent. 0<=score<=100.
     */
    public DominationPoint(final double x, final double y, final int radius, final int score) {
        super(x, y);
        this.radius = radius;
        this.score = score;
    }

    /**
     * Konstruktor.
     *
     * @param x      Die x-Koordinate.
     * @param y      Die y-Koordinate.
     * @param radius Der Radius.
     */
    public DominationPoint(final double x, final double y, final int radius) {
        super(x, y);
        this.radius = radius;
        this.score = 0;
    }

    /**
     * Getter für Radius.
     *
     * @return Der Radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Setter für Radius.
     *
     * @param radius Der Radius.
     */
    public void setRadius(final int radius) {
        this.radius = radius;
    }

    /**
     * Getter für Score.
     *
     * @return Der Score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter für Score.
     *
     * @param score
     */
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * Getter für Status.
     *
     * @return Der Status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter für Status.
     *
     * @param status Der Status.
     */
    public void setStatus(final Status status) {
        this.status = status;
    }

    /**
     * Getter für das dominierende Team.
     *
     * @return Das gerade dominierende Team oder null, wenn kein Team dominiert ({@link Status} DRAW: score == 0).
     */
    public Team getDominatingTeam() {
        return dominatingTeam;
    }

    /**
     * Setter für das dominierende Team.
     *
     * @param dominatingTeam Das gerade dominierende Team oder null, wenn kein Team dominiert ({@link Status} DRAW: score == 0).
     */
    public void setDominatingTeam(final Team dominatingTeam) {
        this.dominatingTeam = dominatingTeam;
    }

    /**
     * Getter für das erobernde Team.
     *
     * @return Das gerade erobernde Team ({@link Status} IS_BEING_CAPTURED) oder null, wenn kein Team erobert ({@link Status} FREE).
     */
    public Team getCapturingTeam() {
        return capturingTeam;
    }

    /**
     * Setter für das erobernde Team.
     *
     * @param capturingTeam Das gerade erobernde Team ({@link Status} IS_BEING_CAPTURED) oder null, wenn kein Team erobert ({@link Status} FREE).
     */
    public void setCapturingTeam(final Team capturingTeam) {
        this.capturingTeam = capturingTeam;
    }

}
