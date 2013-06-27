package edu.hm.webtech.domination.ui.component;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.ui.*;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.TeamIdentifier;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A Vaadin Component for display of the games Score.
 *
 * @author Felix Schramm (incl hardcore copy pasta from Maximilian Briglmeier)
 */
public class ScoreBoard extends CustomComponent implements ScoreListener, RefreshListener{

    /**
     * Map with labels for all teams.
     */
    private final Map<TeamIdentifier, Label> labelMap = new HashMap<>();

    /**
     * The game manager from which the score is read.
     */
    private final IGameManager gameManager;

    /**
     * The class name for the score board background.
     */
    private static final String SCOREBOARD_BG_CLASS = "score-bg";

    /**
     * The css class for team score element.
     */
    private static final String TEAM_SCORE_CLASS = "team-score";

    /**
     * ScoreBoard for domination games.
     *
     * @param gameManager The game manager from which the score is read.
     */
    public ScoreBoard(final IGameManager gameManager, Refresher refresher) {
        this.gameManager = gameManager;
        refresher.addListener(this);
        initLayout();
    }

    /**
     * Initializes the ScoreBoards layout.
     */
    private void initLayout() {
        HorizontalLayout scoreLayout = new HorizontalLayout();
        scoreLayout.setStyleName(SCOREBOARD_BG_CLASS);

        /* Create a label for each team. */
        for(ITeam team: gameManager.getGame().getTeams()) {
            Label teamLabel = new Label(Integer.toString(team.getScore()));
            teamLabel.addStyleName(TEAM_SCORE_CLASS);
            teamLabel.addStyleName(team.getTeamIdentifier().getBgStyleClass());
            labelMap.put(team.getTeamIdentifier(), teamLabel);
            scoreLayout.addComponent(teamLabel);
        }

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.addComponent(scoreLayout);
        mainLayout.setComponentAlignment(scoreLayout, Alignment.MIDDLE_CENTER);

        setCompositionRoot(mainLayout);

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

    @Override
    public void refresh(Refresher refresher) {
        redrawScores();
    }
}
