package edu.hm.webtech.domination.oldbs.model;

/**
 * User: Basti
 * Date: 04.06.13
 * Time: 14:10
 * <h1>Status definiert den Zustand eines {@link DominationPoint}.</h1>
 * <p>LOCKED: Temporär geblockt da gerade erst erobert.<br />
 * IS_BEING_CAPTURED: Wird gerade von einer Gruppe erobert.<br />
 * FREE: Kein Spieler innerhalb des Radius des Domination Points.<br />
 * DRAW: Erobernde Spieler gleichen sich aus.</p>
 */
@Deprecated
public enum Status {
    LOCKED, IS_BEING_CAPTURED, FREE, DRAW
}
