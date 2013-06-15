package edu.hm.webtech.domination.model;

/**
 * Default implementation of {@link ILocationObject}.
 *
 * @author Marco Wolff
 * @author Sebastian Stumpf
 */
public class LocationObject implements ILocationObject {

    /**
     * The longitude of the object.
     */
    private double longitude;
    /**
     * The latitude of the object.
     */
    private double latitude;

    /**
     * Creates a new {@link LocationObject} with given longitude and latitude.
     *
     * @param longitude The longitude.
     * @param latitude  The latitude.
     */
    public LocationObject(final double longitude, final double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    synchronized public double getLongitude() {
        return longitude;
    }

    @Override
    synchronized public void setGeoCoordinates(final double longitude, final double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    synchronized public double getLatitude() {
        return latitude;
    }

    @Override
    public double getDistance(final double longitude, final double latitude) {
        // Umrechung der Gradzahl in RAD
        double myLongitudeRad = this.longitude / 180 * Math.PI;
        double myLatitudeRad = this.latitude / 180 * Math.PI;
        double longitudeRad = longitude / 180 * Math.PI;
        double latitudeRad = latitude / 180 * Math.PI;

        // Berechnung der Entfernung am Einheitskreis
        double distance = Math.acos(Math.sin(myLatitudeRad) * Math.sin(latitudeRad) +
                Math.cos(myLatitudeRad) * Math.cos(latitudeRad) * Math.cos(longitudeRad - myLongitudeRad));

        // Multiplikation des Ergebnisses mit Äquatorradius
        return distance * 6378137;
    }
}
