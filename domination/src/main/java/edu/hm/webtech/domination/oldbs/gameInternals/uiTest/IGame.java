package edu.hm.webtech.domination.oldbs.gameInternals.uiTest;

import java.util.List;

public interface IGame {

	List<IPlayer> getPlayers();
	List<IDominationPoint> getDominationPoints();
	
	/**
	 * Liefert den eigenen Spielernamen zurueck. Damit soll der Spieler spaeter unterschieden werden.
	 * @return den eigenen Spielernamen
	 */
	IPlayer getSelf();
	
	/**
	 * Fuegt einen Spieler in die Liste der Spieler hinzu.
	 * @param player der neue Spieler
	 */
	void addPlayer(IPlayer player);
	
	/**
	 * Setzt den eigenen Spielernamen.
	 * @param player der eigene Spielername
	 */
	void setSelf(IPlayer player);
}
