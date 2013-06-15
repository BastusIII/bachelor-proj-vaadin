package edu.hm.webtech.domination;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;

import edu.hm.webtech.domination.manager.lobby.ILobbyManager;
import edu.hm.webtech.domination.manager.lobby.LobbyManager;
import edu.hm.webtech.domination.manager.session.ISessionManager;
import edu.hm.webtech.domination.manager.session.SessionManager;
import edu.hm.webtech.domination.ui.view.AbstractNavigationView;
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
	
	private static ISessionManager sm;
	private static ILobbyManager lm;

	private double currentLatitude;
	private double currentLongitude;
	
	/**
	 * @author Daniel Briebeck
	 * @return the SessionManager
	 */
	public static ISessionManager getSm() {
		return sm;
	}

	/**
	 * @author Daniel Briebeck
	 * @return the LobbyManager
	 */
	public static ILobbyManager getLm() {
		return lm;
	}

	@Override
	public void init() {
		super.init();
		this.lm=new LobbyManager();
		this.sm = new SessionManager(lm);
		this.loginNaviView = new LoginNavigationView("Login");
		setTheme("domination");
		getMainWindow().setContent(loginNaviView);
	}

	@Override
	public void onBrowserDetailsReady() {
		getMainWindow().setContent(loginNaviView);
	}
	
	public double getCurrentLongitude() {
		return this.currentLongitude;
	}

	public double getCurrentLatitude() {
		return this.currentLatitude;
	}
	
	public void setCurrentLongitude(double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}
	
	public void setCurrentLatitude(double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}
	
	public static MyVaadinApplication getApp(){
		return (MyVaadinApplication) get();
	}
}
