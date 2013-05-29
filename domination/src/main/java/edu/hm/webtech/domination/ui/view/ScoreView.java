package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import edu.hm.webtech.domination.gameInternals.ScoreListener;
import edu.hm.webtech.domination.gameInternals.ScoreManager;

/**
 * This view shows the current score to the user.
 * @author Maximilian Briglmeier
 *
 */
@SuppressWarnings("serial")
public class ScoreView extends AbstractNavigationView implements ScoreListener{

	/**
	 * Responsible for updateing the score.
	 */
	private ScoreManager scoreManager;
	/**
	 * Represents the score of team 'Blue'
	 */
	private Label blueScore;
	/**
	 * Represents the score of team 'Red'
	 */
	private Label redScore;
	
	/**
	 * Initializes the view.
	 * @param caption the caption of the view
	 * @param scoreManager responsible for updateing the score
	 */
	public ScoreView(String caption) {
		super(caption);
	}

	/**
	 * Initializes the components of the {@link ScoreView} by adding 
	 * a Label representing the score for each team.
	 */
	@Override
	protected Component initializeComponent() {
		this.scoreManager = new ScoreManager();
		// Initialize container components
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
		VerticalComponentGroup blueComponentGroup = new VerticalComponentGroup("Team Blue");
		VerticalComponentGroup redComponentGroup = new VerticalComponentGroup("Team Red");
		
		// Initialize Labels representing the score
		blueScore = new Label(String.valueOf(scoreManager.getScore(ScoreManager.Teams.BLUE)));
		redScore = new Label(String.valueOf(scoreManager.getScore(ScoreManager.Teams.RED)));
		
		//Build the view
		blueComponentGroup.addComponent(blueScore);
		redComponentGroup.addComponent(redScore);		
		componentGroup.addComponent(blueComponentGroup);
		componentGroup.addComponent(redComponentGroup);
		
		return componentGroup;
	}

	/**
	 * Updates the score when it changes.
	 */
	@Override
	public void ScoreChanged() {
		blueScore.setCaption(String.valueOf(scoreManager.getScore(ScoreManager.Teams.BLUE)));
		redScore.setCaption(String.valueOf(scoreManager.getScore(ScoreManager.Teams.RED)));
	}
}