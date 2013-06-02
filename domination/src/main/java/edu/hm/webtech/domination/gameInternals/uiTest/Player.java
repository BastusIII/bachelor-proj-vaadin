package edu.hm.webtech.domination.gameInternals.uiTest;

import edu.hm.webtech.domination.gameInternals.ScoreManager;
import edu.hm.webtech.domination.gameInternals.ScoreManager.Teams;

public class Player implements IPlayer{
	
	private ScoreManager.Teams team;
	
	private double longitude;
	private double latitude;
	
	public Player(ScoreManager.Teams team) {
		this.team = team;
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
}
