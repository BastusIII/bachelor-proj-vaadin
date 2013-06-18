package edu.hm.webtech.domination.oldbs.gameInternals.uiTest;

import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;

public interface IDominationPoint {

	double getLatitude();
	double getLongitude();
	ScoreManager.Teams getOwner();
	void setOwner(ScoreManager.Teams team);
}
