package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.ILocationObject;

/**
 * <h1>Der LocationManager kümmert
 * sich um die Aktualisierung der Spielerpositionen.</h1>
 *
 * @author Sebastian Stumpf
 */
public class LocationManager implements ILocationManager {
    /**
     * Das Spiel.
     */
    private IGame game;

    /**
     * Konstruktor.
     *
     * @param game Das Spiel.
     */
    public LocationManager(final IGame game) {
        if (game == null) throw new IllegalArgumentException();
        this.game = game;
    }

    @Override
    public void updateLocation(final ILocationObject locationObject, final double longitude, final double latitude) {
        locationObject.setGeoCoordinates(longitude, latitude);
    }

    @Override
    public void tick() {
        // TODO: Not needed?
    }
}
