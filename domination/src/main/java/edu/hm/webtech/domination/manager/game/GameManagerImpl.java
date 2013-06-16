package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IGameConfiguration;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Default implementaton of {@link IGameManager}.
 * 
 * @author Marco Wolff
 * 
 */
public class GameManagerImpl implements IGameManager {

    public GameManagerImpl(final IGame game) {
       // TODO implement me!
    }

	@Override
	public void joinGame(IPlayer player) {
	}

	@Override
	public void leaveGame(IPlayer player) {
	}

	@Override
	public void changeTeam(IPlayer player, ITeam team) {
	}

	@Override
	public IGame getGame() {
        // TODO: Implement!!!
		return null;
	}

	@Override
	public void startGame() {
	}

	@Override
	public void updateLocation(IPlayer player, double longitude, double latitude) {
	}

}
