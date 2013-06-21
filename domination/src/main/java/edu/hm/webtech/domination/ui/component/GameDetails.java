package edu.hm.webtech.domination.ui.component;

import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.ui.CustomComponent;
import edu.hm.webtech.domination.model.IGame;

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
        initLayout();
    }

    /**
     * Initializes the ScoreBoards layout.
     */
    private void initLayout() {
        HorizontalComponentGroup gameDetailsGroup = new HorizontalComponentGroup();
        setCompositionRoot(gameDetailsGroup);
    }
}
