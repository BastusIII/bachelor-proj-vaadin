package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;


@SuppressWarnings("serial")
public class MainTabView extends TabBarView{

	public MainTabView() {
		super();

		Layout gameView = new GameView();

		NavigationView settingsView = new ScoreView("Score");

		TabSheet.Tab mapTab = addTab(gameView, "Map");

		mapTab.setIcon(new ThemeResource("images/map.png"));

		TabSheet.Tab settingsTab = addTab(settingsView, "Settings");
		settingsTab.setIcon(new ThemeResource("images/settings.png"));
		
		setSelectedTab(gameView);
	}
}
