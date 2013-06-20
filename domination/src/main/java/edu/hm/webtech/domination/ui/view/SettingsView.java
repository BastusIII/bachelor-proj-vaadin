package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.manager.lobby.LobbyManager;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;
import edu.hm.webtech.domination.ui.component.ChangeTeamSelector;
import edu.hm.webtech.domination.ui.component.LeaveGameButton;

/**
 * This view shows the current score to the user.
 * @author Felix Schramm
 *
 */
@SuppressWarnings("serial")
public class SettingsView extends AbstractNavigationView {

    /**
     * The default caption for the settings view.
     */
    private static final String CAPTION = "Settings";

    private final IGameManager gameManager;

	/**
	 * A settings view for domination games.
	 * @param gameManager The game manager on which the settings are applied.
	 */
	public SettingsView(final IGameManager gameManager) {
		super(CAPTION);
        this.gameManager = gameManager;
        init();
	}

	/**
	 * Initializes the components of the {@link SettingsView} by adding
	 * a Label representing the score for each team.
	 */
	@Override
	protected Component initializeComponent() {
		// Initialize container components
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();

        componentGroup.addComponent(new ChangeTeamSelector(gameManager));
        componentGroup.addComponent(new LeaveGameButton(gameManager));

		return componentGroup;
	}



}
