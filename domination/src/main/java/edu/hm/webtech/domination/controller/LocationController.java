package edu.hm.webtech.domination.controller;

import edu.hm.webtech.domination.model.Game;
import edu.hm.webtech.domination.model.ObjectCoordinates;
import edu.hm.webtech.domination.model.Player;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 13:32
 * <h1>Der LocationController kümmert sich um die Aktualisierung der Spielerpositionen.</h1>
 */
public class LocationController {
    /**
     * Das Spiel.
     */
    private Game game;

    /**
     * Konstruktor.
     *
     * @param game Das Spiel.
     */
    public LocationController(final Game game) {
        this.game = game;
    }

    /**
     * Aktualisiert die Koordinaten eines Spielers.<br/>
     * Ist der Spieler nicht in der Spielerliste des Spiels, wird die Anfrage ignoriert.
     *
     * @param playerName
     * @param coordinates
     */
    public void updatePlayer(final String playerName, final ObjectCoordinates coordinates) {
        Player player = game.getPlayer(playerName);
        if (player == null) return;
        player.setXY(coordinates.getX(), coordinates.getY());
    }
}
