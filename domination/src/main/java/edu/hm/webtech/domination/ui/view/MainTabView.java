package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.TabBarView;

@SuppressWarnings("serial")
public class MainTabView extends TabBarView{

	private MapView mapView;
	private ScoreView scoreView;
	
	public MainTabView() {
		super();
		
		this.mapView = new MapView();
		this.scoreView = new ScoreView("Score");
		
		addTab(this.mapView, "Map");
		addTab(this.scoreView, "Score");
		
		setSelectedTab(this.mapView);
	}
}
