package edu.hm.webtech.domination.model;

/**
 * Defines methods for a object having a location based on longitude and
 * latitude.
 *
 * @author Marco Wolff
 */
public interface ILocationObject {
	
	/**
	 * Radius of the equator.
	 */
	public static final double EQUATOR_RADIUS = 6378137.0d;

    /**
     * @return longitude of the object.
     */
    public double getLongitude();

    /**
     * @return latitude of the object.
     */
    public double getLatitude();

    /**
     * Sets the geo coordinates of the object.
     *
     * @param longitude The longitude.
     * @param latitude  The latitude.
     */
    public void setGeoCoordinates(final double longitude, final double latitude);

    /**
     * Calculates the distance between this objects location and the given one.
     *
     * @param longitude The longitude.
     * @param latitude  The latitude.
     * @return distance of this location to given location in meters.
     */
    public double getDistance(final double longitude, final double latitude);
}
