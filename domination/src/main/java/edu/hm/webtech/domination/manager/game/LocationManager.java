package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.model.ILocationObject;

/**
 * <h1>Der LocationManager kümmert sich um die Aktualisierung der
 * Spielerpositionen.</h1>
 * 
 * @author Sebastian Stumpf
 */
public class LocationManager implements ILocationManager {

	@Override
	public void updateLocation(final ILocationObject locationObject,
			final double longitude, final double latitude) {
		locationObject.setGeoCoordinates(longitude, latitude);
	}
}
