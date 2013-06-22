package edu.hm.webtech.domination.manager.lobby;

import edu.hm.webtech.domination.manager.game.GameManagerImpl;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IGameConfiguration;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.test.GameFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class manages the players waiting in the lobby.
 *
 * @author Sebastian Stumpf
 */
public class LobbyManager implements ILobbyManager {

    /**
     * Collection of all active game managers.
     */
    private Collection<IGameManager> gameManagers = new LinkedList<IGameManager>();
    /**
     * Set of names of all createdGames. Used to Check that no game name exists a second time.
     */
    private Set<String> createdGames = new HashSet<>();
    /**
     * Set of game owners.
     */
    private Set<IPlayer> owners = new HashSet<>();
    /**
     * Counter for created Games.
     */
    private int maxGameCounter = 0;

    @Override
    synchronized public IGameManager createGame(final IGameConfiguration gameConfiguration) {
        if (!createdGames.add(gameConfiguration.getName())) {
            return null;
        }
        if (owners.contains(gameConfiguration.getOwner())) {
            return null;
        }
        maxGameCounter++;
        IGameManager gameManager = new GameManagerImpl(GameFactory.getGame(gameConfiguration));
        gameManagers.add(gameManager);
        owners.add(gameConfiguration.getOwner());
        return gameManager;

    }

    @Override
    synchronized public boolean removeGame(IGameManager gameManager) {
        if (gameManager.getGame().getPlayers().isEmpty()) {
            gameManagers.remove(gameManager);
            createdGames.remove(gameManager.getGame().getName());
            owners.remove(gameManager.getGame().getGameConfiguration().getOwner());
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
