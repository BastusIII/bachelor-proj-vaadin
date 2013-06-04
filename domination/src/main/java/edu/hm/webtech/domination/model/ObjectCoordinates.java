package edu.hm.webtech.domination.model;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 13:38
 * <h1>ObjectCoordinates definiert eine Position auf der Karte mit X und y Koordinaten.</h1>
 */
public class ObjectCoordinates {
    /**
     * Die x Koordinate.
     */
    private double x;
    /**
     * Die y Koordinate.
     */
    private double y;

    /**
     * Konstruktor.
     *
     * @param x x Koordinate.
     * @param y y Koordinate.
     */
    public ObjectCoordinates(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter für x Koordinate.
     *
     * @return Die x Koordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Setter für x Koordinate.
     *
     * @param x Die x Koordinate.
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * Getter für y Koordinate.
     *
     * @return Die y Koordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Setter für y Koordinate.
     *
     * @param y Die y Koordinate.
     */
    public void setY(final double y) {
        this.y = y;
    }

    /**
     * Setter für x und y Koordinate.
     *
     * @param x Die x Koordinate.
     * @param y Die y Koordinate.
     */
    public void setXY(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectCoordinates that = (ObjectCoordinates) o;

        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
