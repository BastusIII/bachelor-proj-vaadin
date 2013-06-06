package edu.hm.webtech.domination;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;

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

	private double currentLatitude;
	
	private double currentLongitude;
	
	@Override
	public void init() {
		super.init();
		this.loginNaviView = new LoginNavigationView("Login", this);
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
