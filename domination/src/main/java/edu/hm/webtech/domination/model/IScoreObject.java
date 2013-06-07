package edu.hm.webtech.domination.model;

/**
 * Defines methods for a object having a score.
 * 
 * @author Marco Wolff
 * 
 */
public interface IScoreObject {

	/**
	 * @return score of the object.
	 */
	public int getScore();

	/**
	 * Adds given value to the score of the object.
	 * 
	 * @param score
	 *            will be added to objects score.
	 */
	public void addScore(int score);

	/**
	 * Resets the score of the object, setting it to '0'.
	 */
	public void resetScore();
}
