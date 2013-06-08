package edu.hm.webtech.domination.model;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScorePublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Team ist das Datenobjekt eines Teams, welches die Spieler enthält, eine Farbe, Namen und Punktestand des Teams speichert.</h1>
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
     * @param identifier Der Team Identifier.
     * @param score      Der Punktestand.
     * @throws {@link IllegalArgumentException} if identifier is null.
     */
    public Team(final TeamIdentifier identifier, final int score) {
        this.identifier = identifier;
        this.score = score;
    }

    @Override
    public void setTeamIdentifier(final TeamIdentifier teamIdentifier) {
        this.identifier = teamIdentifier;
    }

    @Override
    public String getPlayerIcon() {
        return ApplicationConfiguration.PLAYER_ICONS_PATH + ApplicationConfiguration.PLAYER_ICON_BASE_NAME + this.identifier.getPathExtension();
    }

    @Override
    public String getDominationPointIcon() {
        return ApplicationConfiguration.DOMINATION_POINT_ICONS_PATH + ApplicationConfiguration.DOMINATION_POINT_ICON_BASE_NAME + this.identifier.getPathExtension();
    }

    @Override
    synchronized public void resetScore() {
        this.score = 0;
        for(ScoreListener listener : scoreListeners) {
            listener.ScoreChanged();
        }
    }

    @Override
    public TeamIdentifier getTeamIdentifier() throws ModelException {
        if (this.identifier == null)
            throw new ModelException("Team identifier is not yet initialized. Initialize before use.");
        return identifier;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    synchronized public void addScore(final int score) {
        this.score += score;
        for(ScoreListener listener : scoreListeners) {
            listener.ScoreChanged();
        }
    }

    @Override
    public void subscribeScoreChange(ScoreListener scoreListener) {
        scoreListeners.add(scoreListener);
    }
}
