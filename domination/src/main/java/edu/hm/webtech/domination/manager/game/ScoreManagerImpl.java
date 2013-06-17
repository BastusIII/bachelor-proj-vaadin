package edu.hm.webtech.domination.manager.game;

import java.util.List;

import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IGameConfiguration;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Default implementation of {@link IScoreManager}.
 * 
 * @author Marco Wolff
 * 
 */
public class ScoreManagerImpl implements IScoreManager {

	@Override
	public ITeam checkForWinner(IGame game) {
		IGameConfiguration gameConfiguration = game.getGameConfiguration();
		List<ITeam> teams = (List<ITeam>) game.getTeams();

		for (ITeam team : teams) {
			if (team.getScore() >= gameConfiguration.getScoreLimit()) {
				return team;
			}
		}

		return null;
	}

}
