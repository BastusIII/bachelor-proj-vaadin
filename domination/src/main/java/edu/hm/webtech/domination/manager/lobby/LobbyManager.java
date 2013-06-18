package edu.hm.webtech.domination.manager.lobby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

	@Override
	public IGameManager createGame(final IGameConfiguration gameConfiguration) {

        // TODO: Daniel Brielbeck -> Initialize Game before giving it to GameManager, maybe by a GameFactoryMethod taking the ConfigFile as a parameter
        /* Workaround: use Game from GameFactory! Plz test functionality before removing.
        IGameManager gameManager;
        IGame game = new Game(gameConfiguration);
        gameManager = new GameManagerImpl(game);*/
        IGameManager gameManager = new GameManagerImpl(GameFactory.GetHMGarden());
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
