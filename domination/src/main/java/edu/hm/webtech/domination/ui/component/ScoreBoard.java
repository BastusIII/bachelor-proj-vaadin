package edu.hm.webtech.domination.ui.component;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.ui.*;

import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;


/**
 * A Vaadin Component for display of the games Score.
 *
 * @author Felix Schramm (incl hardcore copy pasta from Maximilian Briglmeier)
 */
public class ScoreBoard extends CustomComponent implements ScoreListener{

    /**
     * Label for blue teams score.
     */
    private Label blueScore;

    /**
     * Label for red teams score.
     */
    private Label redScore;

    /**
     * The score manager attached to this Score board.
     */
    private static ScoreManager scoreManager = new ScoreManager();
    static{
        // start dummy game
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
     * Dummy ScoreBoard for Game with two teams, red vs. blue.
     */
	public ScoreBoard() {
        initLayout();
    }

    /**
     * Initializes the ScoreBoards layout.
     */
    private void initLayout() {
    	Refresher refresher = new Refresher();
		refresher.setRefreshInterval(500);
		refresher.addListener(new RefreshListener(){
			@Override
			public void refresh(Refresher source) {
				blueScore.setValue(getTeamScore(ScoreManager.Teams.BLUE));
				redScore.setValue(getTeamScore(ScoreManager.Teams.RED));
				blueScore.requestRepaint();
				redScore.requestRepaint();
			}
			
		});
        HorizontalLayout scoreLayout = new HorizontalLayout();
        scoreLayout.addComponent(refresher);
        scoreLayout.setStyleName("score-bg");

        blueScore = new Label(getTeamScore(ScoreManager.Teams.BLUE));
        blueScore.setStyleName("blue-score");

        redScore = new Label(getTeamScore(ScoreManager.Teams.RED));
        redScore.setStyleName("red-score");

        scoreLayout.addComponent(blueScore);
        scoreLayout.addComponent(redScore);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.addComponent(scoreLayout);
        mainLayout.setComponentAlignment(scoreLayout, Alignment.MIDDLE_CENTER);

        setCompositionRoot(mainLayout);

        this.scoreManager.subscribeScoreChange(this);
    }

    /**
     * Return a String with the teams score.
     * @param team The team.
     * @return The score as a String.
     */
    private String getTeamScore(ScoreManager.Teams team) {
       return String.valueOf(scoreManager.getScore(team));
    }

    @Override
    public void ScoreChanged() {
        blueScore.setValue(getTeamScore(ScoreManager.Teams.BLUE));
        redScore.setValue(getTeamScore(ScoreManager.Teams.RED));
        blueScore.requestRepaint();
        redScore.requestRepaint();
    }
}
