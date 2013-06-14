package edu.hm.webtech.domination.manager.lobby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IGameConfiguration;
import edu.hm.webtech.domination.model.Player;

public class LobbyManager implements ILobbyManager {

	private List<Player> waitingPlayers;

	public LobbyManager() {
		this.waitingPlayers = new ArrayList<Player>();
	}

	@Override
	public IGameManager createGame(IGameConfiguration gameConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IGameManager> getGames() {
        try {
            throw new NullPointerException();
        } finally {
            return new LinkedList<IGameManager>();
        }
	}


	@Override
	public void addWaitingPlayer(Player player) throws ModelException {
		for (Player p : waitingPlayers) {
			if(p.getIdentifier().equals(player.getIdentifier())) throw new ModelException("Player with username "+player.getIdentifier()+" already exists!");
		}
		waitingPlayers.add(player);
	}

}
