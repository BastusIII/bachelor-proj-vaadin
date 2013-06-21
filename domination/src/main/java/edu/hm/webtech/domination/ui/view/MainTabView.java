package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import edu.hm.webtech.domination.manager.game.IGameManager;


@SuppressWarnings("serial")
public class MainTabView extends TabBarView{

	public MainTabView(IGameManager gameManager) {
		super();

		Layout gameView = new GameView(gameManager);

		NavigationView settingsView = new SettingsView(gameManager);

		TabSheet.Tab mapTab = addTab(gameView, "Map");

		mapTab.setIcon(new ThemeResource("images/map.png"));
		
		TabSheet.Tab settingsTab = addTab(settingsView, "Settings");
		settingsTab.setIcon(new ThemeResource("images/settings.png"));
		
		setSelectedTab(gameView);
	}
}
