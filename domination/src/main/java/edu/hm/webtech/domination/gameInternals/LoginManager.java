package edu.hm.webtech.domination.gameInternals;

import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;

import edu.hm.webtech.domination.gameInternals.uiTest.Game;
import edu.hm.webtech.domination.gameInternals.uiTest.IGame;
import edu.hm.webtech.domination.gameInternals.uiTest.IPlayer;
import edu.hm.webtech.domination.gameInternals.uiTest.Player;
import edu.hm.webtech.domination.ui.view.LoginNavigationView;
import edu.hm.webtech.domination.ui.view.MainTabView;

/**
 * Der LoginManager verarbeitet die Daten aus dem LoginNAvigationView.
 * Hier werden Usernamen in Game.player hinzugefuegt und der eigene Username in Game.self hinterlegt.
 * Die Klasse benoetigt also eine Referenz zum Game-Objekt.
 * 
 * Eine Authentifizierung findet nicht statt. Benutzername und Passwort ist beliebig.
 * 
 * @author Daniel
 *
 */
public class LoginManager implements LoginListener{

	private LoginNavigationView loginNavigationView;
	private MainTabView mainTabView;
	
	public LoginManager(LoginNavigationView loginNavigationView, MainTabView mainTabView) {
		this.loginNavigationView = loginNavigationView;
		this.mainTabView = mainTabView;
	}

	@Override
	public void onLogin(LoginEvent event) {
		//TODO Hier nur Testgame, ich brauche Referenz zum echten Game, um die Spieler anzulegen...!
		IGame game = new Game();
		IPlayer iplay = new Player(ScoreManager.Teams.BLUE,event.getLoginParameter("username"));
		game.addPlayer(iplay);
		game.setSelf(iplay);
		
//		String username = event.getLoginParameter("username");
//        String password = event.getLoginParameter("password");
        loginNavigationView.getWindow().showNotification("Added Player:  " + game.getCurrentPlayer().getName());
        loginNavigationView.getWindow().setContent(mainTabView);
		
	}

}
