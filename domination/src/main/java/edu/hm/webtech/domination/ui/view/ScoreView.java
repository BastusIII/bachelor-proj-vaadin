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
		this.scoreManager = new ScoreManager();
		this.scoreManager.addScoreListener(this);
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					scoreManager.increaseScore(ScoreManager.Teams.BLUE, 10);
					scoreManager.increaseScore(ScoreManager.Teams.RED, 5);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	/**
	 * Initializes the components of the {@link ScoreView} by adding 
	 * a Label representing the score for each team.
	 */
	@Override
	protected Component initializeComponent() {
		// Initialize container components
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
		VerticalComponentGroup blueComponentGroup = new VerticalComponentGroup("Team Blue");
		VerticalComponentGroup redComponentGroup = new VerticalComponentGroup("Team Red");
		
		// Initialize Labels representing the score
		blueScore = new Label("0");
		redScore = new Label("0");
		blueScore.setHeight(30, UNITS_PIXELS);
		redScore.setHeight(30, UNITS_PIXELS);
		
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
		blueScore.setValue(String.valueOf(scoreManager.getScore(ScoreManager.Teams.BLUE)));
		redScore.setValue(String.valueOf(scoreManager.getScore(ScoreManager.Teams.RED)));
		blueScore.requestRepaint();
		redScore.requestRepaint();
	}
}