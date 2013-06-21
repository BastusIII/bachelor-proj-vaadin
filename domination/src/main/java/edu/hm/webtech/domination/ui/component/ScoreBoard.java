package edu.hm.webtech.domination.ui.component;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.ui.*;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.TeamIdentifier;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;

import java.util.HashMap;
import java.util.Map;


/**
 * A Vaadin Component for display of the games Score.
 *
 * @author Felix Schramm (incl hardcore copy pasta from Maximilian Briglmeier)
 */
public class ScoreBoard extends CustomComponent implements ScoreListener{

    /**
     * Map with labels for all teams.
     */
    private final Map<TeamIdentifier, Label> labelMap = new HashMap<>();

    /**
     * The game manager from which the score is read.
     */
    private final IGameManager gameManager;

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
     * ScoreBoard for domination games.
     *
     * @param gameManager The game manager from which the score is read.
     */
    public ScoreBoard(final IGameManager gameManager) {
        this.gameManager = gameManager;
        initLayout();
    }

    /**
     * Initializes the ScoreBoards layout.
     */
    private void initLayout() {

    	Refresher refresher = new Refresher();
		refresher.setRefreshInterval(500);
        final ScoreBoard thisBoard = this;
		refresher.addListener(new RefreshListener() {
			@Override
			public void refresh(Refresher source) {
                thisBoard.redrawScores();
			}
			
		});
        HorizontalLayout scoreLayout = new HorizontalLayout();
        scoreLayout.setStyleName("score-bg");

        /* Create a label for each team. */
        for(ITeam team: gameManager.getGame().getTeams()) {
            Label teamLabel = new Label(Integer.toString(team.getScore()));
            teamLabel.setStyleName(team.getTeamIdentifier().getBgStyleClass());
            labelMap.put(team.getTeamIdentifier(), teamLabel);
            scoreLayout.addComponent(teamLabel);
        }

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.addComponent(refresher);
        mainLayout.addComponent(scoreLayout);
        mainLayout.setComponentAlignment(scoreLayout, Alignment.MIDDLE_CENTER);

        setCompositionRoot(mainLayout);

        this.scoreManager.subscribeScoreChange(this);
    }

    @Override
    public void ScoreChanged() {
        redrawScores();
    }

    /**
     * Redraws the team scores.
     */
    public void redrawScores() {
        for(ITeam team: gameManager.getGame().getTeams()) {
            Label scoreLabel = labelMap.get(team.getTeamIdentifier());
            scoreLabel.setValue(team.getScore());
            scoreLabel.requestRepaint();
        }
    }

}
