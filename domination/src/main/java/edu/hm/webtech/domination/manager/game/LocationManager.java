package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.ILocationObject;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.oldbs.model.Game;
import edu.hm.webtech.domination.oldbs.model.ObjectCoordinates;
import edu.hm.webtech.domination.oldbs.model.Player;

/**
 * User: Basti Date: 04.06.13 Time: 13:32 <h1>Der LocationManager kümmert
 * sich um die Aktualisierung der Spielerpositionen.</h1>
 */
public class LocationManager implements ILocationManager {
	/**
	 * Das Spiel.
	 */
	private IGame game;

	/**
	 * Konstruktor.
	 * 
	 * @param game
	 *            Das Spiel.
	 */
	public LocationManager(final IGame game) {
        if(game == null) throw new IllegalArgumentException();
		this.game = game;
	}

    @Override
    public void updateLocation(final ILocationObject locationObject, final double longitude, final double latitude) {

    }

    @Override
    public void tick() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
