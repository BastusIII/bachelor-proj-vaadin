package edu.hm.webtech.domination.model;

/**
 * Default implementation of {@link ILocationObject}.
 * 
 * @author Marco Wolff
 * 
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
	 * @param longitude
	 * @param latitude
	 */
	public LocationObject(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public double getDistance(double longitude, double latitude) {
		// TODO IMPLEMENT THE DISTANCE CALCULATION.
		throw new UnsupportedOperationException("NOT IMPLEMENT YET!!!");
	}

}
