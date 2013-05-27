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

	@Override
	public void init() {
		super.init();
		this.loginNaviView = new LoginNavigationView("Login");
		getMainWindow().setContent(loginNaviView);
	}

	@Override
	public void onBrowserDetailsReady() {
		getMainWindow().setContent(loginNaviView);
	}

}
