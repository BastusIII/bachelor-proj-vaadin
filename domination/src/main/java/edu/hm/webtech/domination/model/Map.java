package edu.hm.webtech.domination.model;

/**
 * <h1>Map ist das Datenobjekt der Karte und wird durch ein Zentrum
 * repräsentiert, sowie Radius (Begrenzung des Spielfeldes) und Zoomfaktor.</h1>
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
     * Copy constructor, deep copying given {@link IMap}.
     *
     * @param map {@link IMap} object that will be copied.
     * @author Marco Wolff
     */
    public Map(final IMap map) {
        this(map.getLongitude(), map.getLatitude(), map.getZoomFactor());
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
