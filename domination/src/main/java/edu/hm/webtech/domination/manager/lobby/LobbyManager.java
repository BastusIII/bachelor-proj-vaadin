package edu.hm.webtech.domination.manager.lobby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.manager.game.GameManagerImpl;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.*;

/**
 * This class manages the players waiting in the lobby.
 * 
 * @author Daniel Brielbeck
 * 
 */
public class LobbyManager implements ILobbyManager {

    /**
     * Collection of all active game managers.
     */
    private Collection<IGameManager> gameManagers = new LinkedList<IGameManager>();

	@Override
	public IGameManager createGame(final IGameConfiguration gameConfiguration) {
        IGameManager gameManager;
        IGame game = new Game(gameConfiguration);
        gameManager = new GameManagerImpl(game);
        gameManagers.add(gameManager);
        return gameManager;

	}

    @Override
    public void removeGame(IGameManager gameManager) {
        gameManagers.remove(gameManager);
    }

    @Override
	public Collection<IGameManager> getGames() {
        return gameManagers;
	}

}
