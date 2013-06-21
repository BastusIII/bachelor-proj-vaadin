package edu.hm.webtech.domination.manager.lobby;

import java.util.*;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.manager.game.GameManagerImpl;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.*;
import edu.hm.webtech.test.GameFactory;

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
    private Set<String> createdGames = new HashSet<>();
    private int maxGameCounter = 0;

	@Override
	synchronized public IGameManager createGame(final IGameConfiguration gameConfiguration) {
        if(!createdGames.add(gameConfiguration.getName())) {
            return null;
        }
        maxGameCounter++;
        IGameManager gameManager = new GameManagerImpl(GameFactory.getGame(gameConfiguration));
        gameManagers.add(gameManager);
        return gameManager;

	}

    @Override
    synchronized public boolean removeGame(IGameManager gameManager) {
        if(gameManager.getGame().getPlayers().isEmpty()) {
            gameManagers.remove(gameManager);
            createdGames.remove(gameManager.getGame().getName());
            return true;
        }
        return false;
    }

    @Override
	public Collection<IGameManager> getGames() {
        return gameManagers;
	}

    @Override
    public int getMaxGameCounter() {
        return this.maxGameCounter;
    }

}
