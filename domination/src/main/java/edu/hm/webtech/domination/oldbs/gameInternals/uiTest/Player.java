package edu.hm.webtech.domination.oldbs.gameInternals.uiTest;

import com.vaadin.addon.touchkit.ui.TouchKitApplication;

import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager.Teams;

public class Player implements IPlayer{
	
	private ScoreManager.Teams team;
	
	private double longitude;
	private double latitude;
	private String name;
	
	public Player(ScoreManager.Teams team) {
		this.team = team;
		this.name = "Nobody";
	}
	
	public Player(ScoreManager.Teams team, String name) {
		this.team = team;
		this.name=name;
	}

	@Override
	public void setLocation(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public Teams getTeam() {
		return this.team;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}
}
