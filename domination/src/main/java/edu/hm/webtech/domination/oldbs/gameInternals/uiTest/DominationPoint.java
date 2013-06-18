package edu.hm.webtech.domination.oldbs.gameInternals.uiTest;

import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager.Teams;

public class DominationPoint implements IDominationPoint {

	private double latitude;
	private double longitude;
	private ScoreManager.Teams owner = null;

	public DominationPoint(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public Teams getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(ScoreManager.Teams team) {
		this.owner = team;
	}
}
