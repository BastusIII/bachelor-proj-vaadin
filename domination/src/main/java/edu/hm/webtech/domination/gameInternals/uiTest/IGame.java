package edu.hm.webtech.domination.gameInternals.uiTest;

import java.util.List;

public interface IGame {

	List<IPlayer> getPlayers();
	List<IDominationPoint> getDominationPoints();
}
