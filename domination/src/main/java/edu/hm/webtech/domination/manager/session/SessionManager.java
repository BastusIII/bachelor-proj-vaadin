package edu.hm.webtech.domination.manager.session;

import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Notification;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.manager.lobby.ILobbyManager;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.Player;
import edu.hm.webtech.domination.ui.view.LobbyView;

import java.util.LinkedList;
import java.util.List;

/**
 * This class adds users, if the don't already exist, to a list of waiting players in the lobby.
 * 
 * @author Daniel Brielbeck
 * 
 */
public class SessionManager implements ISessionManager, LoginListener {


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

	/**
	 * Called by click on login. Delegates chosen username to method createAndRegisterPlayer
	 * @param event
	 * 		holds the login data from the LoginEvent Object 
	 */
	@Override
	public void onLogin(LoginEvent event) {
		if(createAndRegisterPlayer(event.getLoginParameter("username"))){
			System.out.println("Create User "+event.getLoginParameter("username")+" Successful");
			MyVaadinApplication.getApp().getMainWindow().setContent(new LobbyView("Game Lobby"));
		}
		else{
			//Show that username is already in use
		}
	}


    @Override
    public void addPlayer(final IPlayer player) throws ModelException {
        for (IPlayer p : knownPlayers) {
            if(p.getIdentifier().equals(player.getIdentifier())) throw new ModelException("Player with username "+player.getIdentifier()+" already exists!");
        }
        knownPlayers.add(player);
    }

}
