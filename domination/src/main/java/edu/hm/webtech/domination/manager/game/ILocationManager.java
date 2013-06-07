package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.listener.IGameTickListener;
import edu.hm.webtech.domination.model.ILocationObject;
import edu.hm.webtech.domination.model.IPlayer;

/**
 * Defines methods for handling and updating {@link ILocationObject}s based on
 * game ticks by {@link IGameManager}.
 * 
 * @author Marco Wolff
 * 
 */
public interface ILocationManager extends IGameTickListener {

	/**
	 * Updates the location on given {@link IPlayer} to given value of longitude
	 * and latitude.
	 * 
	 * @param locationObject
	 *            {@link ILocationObject} whichs location will be updated.
	 * @param longitude
	 *            longitude of the new location.
	 * @param latitude
	 *            latitude of the new location.
	 */
	public void updateLocation(ILocationObject locationObject,
			double longitude, double latitude);
}
