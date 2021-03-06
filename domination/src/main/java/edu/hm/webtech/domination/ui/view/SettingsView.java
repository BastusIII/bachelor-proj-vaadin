package edu.hm.webtech.domination.ui.view;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.ui.component.ChangeTeamSelector;
import edu.hm.webtech.domination.ui.component.GameDetails;
import edu.hm.webtech.domination.ui.component.LeaveGameButton;

/**
 * This view shows the current score to the user.
 * @author Felix Schramm
 *
 */
@SuppressWarnings("serial")
public class SettingsView extends AbstractNavigationView implements Refresher.RefreshListener {

    /**
     * The default caption for the settings view.
     */
    private static final String CAPTION = "Settings";

    /**
     * The game manager used to apply the settings.
     */
    private final IGameManager gameManager;

    private GameDetails gameDetails;

	/**
	 * A settings view for domination games.
	 * @param gameManager The game manager on which the settings are applied.
	 */
	public SettingsView(final IGameManager gameManager) {
		super(CAPTION);
        this.gameManager = gameManager;
        refresher.addListener(this);
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

        /* Add a start game button for the game owner. */
        if (MyVaadinApplication.getApp().getUser().equals(gameManager.getGame().getOwner())) {
            componentGroup.addComponent(new StartGameButton());
        }

        gameDetails = new GameDetails(gameManager.getGame());

        componentGroup.addComponent(gameDetails);

        ChangeTeamSelector changeTeamSelector = new ChangeTeamSelector(gameManager);

        componentGroup.addComponent(changeTeamSelector);
        LeaveGameButton leaveGameButton = new LeaveGameButton(gameManager);

        componentGroup.addComponent(leaveGameButton);


		return componentGroup;
	}

    @Override
    public void refresh(Refresher refresher) {
        init();
        this.requestRepaintAll();
    }

    private class StartGameButton extends Button {
        private StartGameButton() {
            super("Start game");
            if(gameManager.isGameRunning() || !gameManager.isGameReady())  {
                setEnabled(false);
            } else {
                setDisableOnClick(true);
            }

            addListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    gameManager.startGame();
                }
            });
        }
    }


}
