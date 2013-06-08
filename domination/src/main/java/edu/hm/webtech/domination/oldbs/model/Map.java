package edu.hm.webtech.domination.oldbs.model;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 13:32
 * <h1>Map ist das Datenobjekt der Karte und wird durch ein Zentrum repräsentiert, sowie Radius (Begrenzung des Spielfeldes) und Zoomfaktor.</h1>
 */
@Deprecated
public class Map extends ObjectCoordinates {

    /**
     * Die Begrenzung des Spielfeldes.
     */
    private int radius;
    /**
     * Der Zoomfaktor der Map.
     */
    private int zoomFactor;

    /**
     * Konstruktor.
     *
     * @param x          Die x-Koordinate.
     * @param y          Die y-Koordinate.
     * @param radius     Der Radius. Wird 1 gesetzt, falls <= 0.
     * @param zoomFactor Der Zoomfaktor. Wird 0 gesetzt, falls < 0.
     */
    public Map(final double x, final double y, final int radius, final int zoomFactor) {
        super(x, y);
        this.radius = radius <= 0 ? 1 : radius;
        this.zoomFactor = zoomFactor < 0 ? 0 : zoomFactor;
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
     * @param radius Der Radius. Wird 1 gesetzt, falls <= 0.
     */
    public void setRadius(final int radius) {
        this.radius = radius <= 0 ? 1 : radius;
        this.radius = radius;
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
     * @param zoomFactor Der Zoomfaktor. Wird 0 gesetzt, falls < 0.
     */
    public void setZoomFactor(final int zoomFactor) {
        this.zoomFactor = zoomFactor < 0 ? 0 : zoomFactor;
        this.zoomFactor = zoomFactor;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Map map = (Map) o;

        if (radius != map.radius) return false;
        if (zoomFactor != map.zoomFactor) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + radius;
        result = 31 * result + zoomFactor;
        return result;
    }
}
