package edu.hm.webtech.domination.ui.component;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Display component for domination game.
 *
 * @author Felix Schramm
 */
public class GameDetails extends CustomComponent {

    /**
     * The game that the details are displayed for.
     */
    private IGame game;

    public GameDetails(final IGame game) {
        this.game = game;
        setSizeUndefined();
        initLayout();
    }

    /**
     * Initializes the ScoreBoards layout.
     */
    private void initLayout() {
        Layout gameDetailsGroup = new VerticalComponentGroup();

        Label gameOwnerLabel = new Label("Game owner: " + game.getOwner().getIdentifier());
        gameDetailsGroup.addComponent(gameOwnerLabel);
        for (ITeam team: game.getTeams()) {
            TeamDetails teamDetails = new TeamDetails(game, team);
            gameDetailsGroup.addComponent(teamDetails);
        }

        setCompositionRoot(gameDetailsGroup);

    }




}
