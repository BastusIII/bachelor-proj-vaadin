package edu.hm.webtech.domination.model;

/**
 * <h1>Map ist das Datenobjekt der Karte und wird durch ein Zentrum repräsentiert, sowie Radius (Begrenzung des Spielfeldes) und Zoomfaktor.</h1>
 *
 * @author Sebastian Stumpf
 */
public class Map extends LocationObject implements IMap {

    /**
     * Der Zoomfaktor der Map.
     */
    private int zoomFactor;

    /**
     * Konstruktor.
     *
     * @param longitude  Längengrad.
     * @param latitude   Breitengrad.
     * @param zoomFactor Der Radius.
     */
    public Map(final double longitude, final double latitude, final int zoomFactor) {
        super(longitude, latitude);
        this.zoomFactor = zoomFactor;
    }

    /**
     * Getter für Zoomfaktor.
     *
     * @return Der Zoomfaktor.
     */
    public int getZoomFactor() {
        return zoomFactor;
    }

    /**
     * Setter für Zoomfaktor.
     *
     * @param zoomFactor Der Zoomfaktor.
     */
    public void setZoomFactor(final int zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
}
