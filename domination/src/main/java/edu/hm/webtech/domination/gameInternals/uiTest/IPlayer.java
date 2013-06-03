package edu.hm.webtech.domination.gameInternals.uiTest;

import edu.hm.webtech.domination.gameInternals.ScoreManager;

public interface IPlayer {
	
	ScoreManager.Teams getTeam();
	double getLongitude();
	double getLatitude();
	void setLocation(double longitude, double latitude);
	String getName();
	void setName(String name);
}
