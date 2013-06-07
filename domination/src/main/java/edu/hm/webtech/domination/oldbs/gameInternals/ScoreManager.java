package edu.hm.webtech.domination.oldbs.gameInternals;

import java.util.ArrayList;
import java.util.List;

/**
 * The ScoreManager 
 * @author Maximilian Briglmeier
 *
 */
@Deprecated
public class ScoreManager implements ScorePublisher{
	/**
	 * Represent the teams available.
	 * @author Maximilian Briglmeier
	 *
	 */
	// Might be moved into another, more appropriate class later.
	public enum Teams{BLUE,RED}
	
	/**
	 * Contains components, which are notified when the score changes.
	 */
	private List<ScoreListener> scoreListeners = new ArrayList<ScoreListener>();
	
	/**
	 * The score of team 'Blue'
	 */
	private int blueScore = 0;
	/**
	 * The score of team 'Red'
	 */
	private int redScore = 0;
	
	/**
	 * Default constructor.
	 */
	public ScoreManager() {
		
	}
	
	/**
	 * Gets the score of the specified team.
	 * @param team determines, which score to get
	 * @return the score
	 */
	public int getScore(Teams team) {
		switch(team){
			case BLUE: return blueScore;
			case RED: return redScore;
			default: return -1;
		}
	}
	
	/**
	 * Increases the score of the specified team by the given amount.
	 * @param team the team of which the score should be increased
	 * @param amount the amount of which the score should be increased
	 */
	public void increaseScore(Teams team, int amount) {
		switch(team) {
			case BLUE: 
				blueScore += amount;
				break;
			case RED:
				redScore += amount;
				break;
		}
		for(ScoreListener scoreListener: scoreListeners) {
			scoreListener.ScoreChanged();
		}
	}
	
	/**
	 * Resets the score of all teams.
	 */
	public void resetScore() {
		blueScore = 0;
		redScore = 0;
	}
	
	/**
	 * Adds the specified component, which gets notified when the score changes.
	 * @param scoreListener the component to be added
	 */
	public void subscribeScoreChange(ScoreListener scoreListener) {
		scoreListeners.add(scoreListener);
	}
}
