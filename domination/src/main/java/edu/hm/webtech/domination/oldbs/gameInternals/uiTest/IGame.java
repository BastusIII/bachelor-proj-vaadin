package edu.hm.webtech.domination.oldbs.gameInternals.uiTest;

import java.util.List;

public interface IGame {

	List<IPlayer> getPlayers();
	List<IDominationPoint> getDominationPoints();
}
