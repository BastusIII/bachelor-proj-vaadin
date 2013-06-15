package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreListener;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;

/**
 * This view shows the current score to the user.
 * @author Maximilian Briglmeier
 *
 */
@SuppressWarnings("serial")
public class SettingsView extends AbstractNavigationView {

	/**
	 * Initializes the view.
	 * @param caption the caption of the view
	 */
	public SettingsView(String caption) {
		super(caption);
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

        Button leaveGameButton = new Button("Leave Game");

		return componentGroup;
	}

}
