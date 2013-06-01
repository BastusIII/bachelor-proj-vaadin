package edu.hm.webtech.domination.gameInternals.uiTest;

import edu.hm.webtech.domination.gameInternals.ScoreManager;

public interface IDominationPoint {

	double getLatitude();
	double getLongitude();
	ScoreManager.Teams getOwner();
	void setOwner(ScoreManager.Teams team);
}
