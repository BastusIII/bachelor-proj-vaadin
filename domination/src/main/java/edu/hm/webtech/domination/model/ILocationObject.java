package edu.hm.webtech.domination.model;

/**
 * Defines methods for a object having a location based on longitude and
 * latitude.
 *
 * @author Marco Wolff
 */
public interface ILocationObject {

    /**
     * @return longitude of the object.
     */
    public double getLongitude();

    /**
     * Sets the longitude of the object.
     *
     * @param longitude The longitude.
     */
    public void setLongitude(double longitude);

    /**
     * @return latitude of the object.
     */
    public double getLatitude();

    /**
     * Sets the latitude of the object.
     *
     * @param latitude The latitude.
     */
    public void setLatitude(double latitude);

    /**
     * Calculates the distance between this objects location and the given one.
     *
     * @param longitude The longitude.
     * @param latitude The latitude.
     * @return distance of this location to given location in meters.
     */
    public double getDistance(double longitude, double latitude);
}
