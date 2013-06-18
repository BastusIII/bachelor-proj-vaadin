package edu.hm.webtech.domination.model;

import java.util.ArrayList;
import java.util.List;

import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScorePublisher;

/**
 * <h1>Team ist das Datenobjekt eines Teams, welches die Spieler enthält, eine
 * Farbe, Namen und Punktestand des Teams speichert.</h1>
 * 
 * @author Sebastian Stumpf
 */
public class Team implements ScorePublisher, ITeam {

	/**
	 * Der Identifier des Teams ist eine Farbe.
	 */
	private TeamIdentifier identifier;
	/**
	 * Der Punktestand des Teams.
	 */
	private int score;

	/**
	 * Contains components, which are notified when the score changes.
	 */
	private List<ScoreListener> scoreListeners = new ArrayList<ScoreListener>();

	/**
	 * Konstrutor.
	 * 
	 * @param identifier
	 *            Der Team Identifier.
	 * @param score
	 *            Der Punktestand.
	 * @throws IllegalArgumentException
	 *             if identifier is null.
	 */
	public Team(final TeamIdentifier identifier, final int score) {
		this.identifier = identifier;
		this.score = score;
	}

	/**
	 * Copy constructor, deep copying given {@link ITeam}.
	 * 
	 * @author Marco Wolff
	 * @param team
	 *            {@link ITeam} object that will be copied.
	 */
	public Team(ITeam team) {
		if (team == null) {
			throw new IllegalArgumentException("Team may not be null!");
		}
		this.identifier = team.getTeamIdentifier();
		this.score = team.getScore();
	}

	@Override
	public void setTeamIdentifier(final TeamIdentifier teamIdentifier) {
		this.identifier = teamIdentifier;
	}

	@Override
	public String getPlayerIcon() {
		return this.identifier.getPlayerIconPath();
	}

	@Override
	public String getDominationPointIcon() {
		return this.identifier.getDominationPointIconPath();
	}

	@Override
	synchronized public void resetScore() {
		this.score = 0;
		for (ScoreListener listener : scoreListeners) {
			listener.ScoreChanged();
		}
	}

	@Override
	public TeamIdentifier getTeamIdentifier() {
		return identifier;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	synchronized public void addScore(final int score) {
		this.score += score;
		for (ScoreListener listener : scoreListeners) {
			listener.ScoreChanged();
		}
	}

	@Override
	public void subscribeScoreChange(ScoreListener scoreListener) {
		scoreListeners.add(scoreListener);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Team team = (Team) o;

		if (identifier != team.identifier)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return identifier != null ? identifier.hashCode() : 0;
	}
}
