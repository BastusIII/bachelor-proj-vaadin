package edu.hm.webtech.domination.manager.session;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.Player;
import java.util.LinkedList;
import java.util.List;

/**
 * This class adds users, if the don't already exist, to a list of waiting players in the lobby.
 * 
 * @author Daniel Brielbeck
 * 
 */
public class SessionManager implements ISessionManager {


    /**
     * List of all players known to the lobby.
     */
    private List<IPlayer> knownPlayers = new LinkedList<IPlayer>();


	@Override
	public boolean createAndRegisterPlayer(String identifier) {
		try {
			Player player = new Player(0, 0, identifier);
						
			addPlayer(player);
			MyVaadinApplication.getApp().setUser(player);
			System.out.println("DEBUG-----------------------------------------");
			//MyVaadinApplication.getApp().getMainWindow().getApplication().setUser(player);
			return true;
		} catch (ModelException me) {
			System.out.println(me.getMessage());
		}		
		return false;
	}

    @Override
    public void addPlayer(final IPlayer player) throws ModelException {
        for (IPlayer p : knownPlayers) {
            if(p.getIdentifier().equals(player.getIdentifier())) throw new ModelException("Player with username "+player.getIdentifier()+" already exists!");
        }
        knownPlayers.add(player);
    }

	

}
