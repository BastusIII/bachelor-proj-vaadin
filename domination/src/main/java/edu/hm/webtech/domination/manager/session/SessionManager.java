package edu.hm.webtech.domination.manager.session;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.manager.lobby.ILobbyManager;
import edu.hm.webtech.domination.manager.lobby.LobbyManager;
import edu.hm.webtech.domination.model.Player;

/**
 * This class adds users, if the don't already exist, to a list of waiting players in the lobby.
 * 
 * @author Daniel Brielbeck
 * 
 */
public class SessionManager implements ISessionManager, LoginListener {

	private LobbyManager lobbyManager;

	 /** Ctor, intializing this with the given lobbymanager.
	  *  Lobby manager is needed to add Players to the lobby.
	  * 
	  * @param lm
	  *            LobbyManager of the App
	  */
	public SessionManager(ILobbyManager lm){
		this.lobbyManager = (LobbyManager) lm;
	}
	
	@Override
	public void createAndRegisterPlayer(String identifier) {
		try {
			Player player = new Player(0, 0, identifier);
			lobbyManager.addWaitingPlayer(player);
			MyVaadinApplication.getApp().getMainWindow().getApplication().setUser(player);
			// TODO Create? and open Lobby View 
		} catch (ModelException me) {
			System.out.println(me.getMessage());
		}		
	}

	/**
	 * Called by click on login. Delegates chosen username to method createAndRegisterPlayer
	 * @param event
	 * 		holds the login data from the LoginEvent Object 
	 */
	@Override
	public void onLogin(LoginEvent event) {
		createAndRegisterPlayer(event.getLoginParameter("username"));
	}

}
