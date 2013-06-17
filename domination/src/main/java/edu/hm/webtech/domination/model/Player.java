package edu.hm.webtech.domination.model;

import java.util.ArrayList;
import java.util.List;

import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScorePublisher;

/**
 * <h1>Player ist das Datenobjekt eines Spielers.</h1>
 * 
 * @author Sebastian Stumpf
 */
public class Player extends LocationObject implements IPlayer, ScorePublisher {
	/**
	 * Der eindeutige Name des Spielers.
	 */
	private String name;
	/**
	 * Das Team dem der Spieler angehört. null falls der Spieler keinem Spiel
	 * angehört.
	 */
	private ITeam team;
	/**
	 * Die Punkte, die der Spieler durch Eroberungen gesammelt hat.
	 */
	private int score;

	/**
	 * Contains components, which are notified when the score changes.
	 */
	private List<ScoreListener> scoreListeners = new ArrayList<ScoreListener>();

	/**
	 * Konstruktor.
	 * 
	 * @param longitude
	 *            Längengrad.
	 * @param latitude
	 *            Breitengrad.
	 * @param name
	 *            Der Name des Spielers.
	 * @param team
	 *            Das Team des Spielers.
	 * @param score
	 *            Die Punkte des Spielers.
	 */
	public Player(final double longitude, final double latitude,
			final String name, final ITeam team, final int score) {
		super(longitude, latitude);
		this.name = name;
		this.team = team;
		this.score = score;
	}

	/**
	 * Copy constructor, deep copying given {@link IPlayer}.
	 * 
	 * @author Marco Wolff
	 * @param player
	 *            {@link IPlayer} object that will be copied.
	 */
	public Player(IPlayer player) {
		this(player.getLongitude(), player.getLatitude(), player
				.getIdentifier(), new Team(player.getTeam()), player.getScore());
	}

	/**
	 * Konstruktor für Spieler mit 0 Punkten.
	 * 
	 * @param longitude
	 *            Längengrad.
	 * @param latitude
	 *            Breitengrad.
	 * @param name
	 *            Der Name des Spielers.
	 * @param team
	 *            Das Team des Spielers.
	 */
	public Player(final double longitude, final double latitude,
			final String name, final ITeam team) {
		this(longitude, latitude, name, team, 0);
	}

	/**
	 * Konstruktor für Spieler der noch keinem Team angehört.
	 * 
	 * @param longitude
	 *            Längengrad.
	 * @param latitude
	 *            Breitengrad.
	 * @param name
	 *            Der Name des Spielers.
	 */
	public Player(final double longitude, final double latitude,
			final String name) {
		this(longitude, latitude, name, null);
	}

	@Override
	public String getIdentifier() {
		return this.name;
	}

	@Override
	public void setIdentifier(final String identifier) {
		if (identifier == null)
			throw new IllegalArgumentException("Identifier must not be null.");
		this.name = identifier;
	}

	@Override
	public void setTeam(final ITeam team) {
		this.team = team;
	}

	@Override
	public ITeam getTeam() {
		return team;
	}

	@Override
	synchronized public void resetScore() {
		this.score = 0;
		for (ScoreListener listener : scoreListeners) {
			listener.ScoreChanged();
		}
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	synchronized public void addScore(int toAdd) {
		this.score += toAdd;
		for (ScoreListener listener : scoreListeners) {
			listener.ScoreChanged();
		}
	}

	@Override
	public void subscribeScoreChange(ScoreListener scoreListener) {
		scoreListeners.add(scoreListener);
	}
}
