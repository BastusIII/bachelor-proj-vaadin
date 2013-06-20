package edu.hm.webtech.domination;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;

import edu.hm.webtech.domination.manager.lobby.ILobbyManager;
import edu.hm.webtech.domination.manager.lobby.LobbyManager;
import edu.hm.webtech.domination.manager.session.ISessionManager;
import edu.hm.webtech.domination.manager.session.SessionManager;
import edu.hm.webtech.domination.ui.view.AbstractNavigationView;
import edu.hm.webtech.domination.ui.view.LobbyView;
import edu.hm.webtech.domination.ui.view.LoginNavigationView;

/**
 * The Application's "main" class, starting the DOMINATION!
 * 
 * @author Marco Wolff
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends TouchKitApplication {

	/**
	 * The start {@link NavigationView}, managing the login.
	 */
	private AbstractNavigationView loginNaviView;
	
	private static final ILobbyManager lm = new LobbyManager();
	private static final ISessionManager sm = new SessionManager(lm);

	
	/**
	 * @author Daniel Briebeck
	 * @return the SessionManager
	 */
	public static ISessionManager getSm() {
		return sm;
	}

	/**
	 * @author Daniel Briebeck!
	 * @return the LobbyManager
	 */
	public static ILobbyManager getLm() {
		System.out.println("Open Games: "+lm.getGames().size());
		return lm;
	}

	@Override
	public void init() {
		super.init();
		this.loginNaviView = new LoginNavigationView("Login");
        //this.loginNaviView = new LobbyView("Game Lobby");
		setTheme("domination");
		getMainWindow().setContent(loginNaviView);
	}

	@Override
	public void onBrowserDetailsReady() {
		getMainWindow().setContent(loginNaviView);
	}
	
	public static MyVaadinApplication getApp(){
		return (MyVaadinApplication) get();
	}
}
