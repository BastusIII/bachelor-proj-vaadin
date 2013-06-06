package edu.hm.webtech.domination.gameInternals.uiTest;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.touchkit.ui.TouchKitApplication;

import edu.hm.webtech.domination.gameInternals.ScoreManager;

public class Game implements IGame{

	private List<IPlayer> players = new ArrayList<IPlayer>();
	private List<IDominationPoint> dominationPoints = new ArrayList<IDominationPoint>();
	private TouchKitApplication tka;
	
	public Game(){
	}

	public Game(TouchKitApplication tka) {
		this.tka=tka;
	}

	@Override
	public List<IPlayer> getPlayers() {
		return this.players;
	}
	
	@Override
	public List<IDominationPoint> getDominationPoints() {
		return this.dominationPoints;
	}
	
	public void setUpStub(IPlayer me, double longitude, double latitude) {
		players.add(me);
		for(int i = 0; i < 2; i++) {
			IPlayer player = new Player(ScoreManager.Teams.BLUE);
			//Location of 'Pliening'
			player.setLocation(11.79894 + (0.0001 * i), 48.19636 + (0.0001 * i));
			players.add(player);
		}
		for(int i = 0; i < 3; i++) {
			IPlayer player = new Player(ScoreManager.Teams.RED);
			//Location of 'Pliening'
			player.setLocation(11.79894 + (0.001 * i), 48.19636 + (0.001 * i));
			players.add(player);
		}
		//Location of 'Pliening'
		IDominationPoint redDomPoint = new DominationPoint(48.19636, 11.79894);
		redDomPoint.setOwner(ScoreManager.Teams.RED);
		IDominationPoint blueDomPoint = new DominationPoint(48.1966, 11.79894);
		blueDomPoint.setOwner(ScoreManager.Teams.BLUE);
		IDominationPoint neutralDomPoint = new DominationPoint(48.1962, 11.7986);
		neutralDomPoint.setOwner(null);
		this.dominationPoints.add(redDomPoint);
		this.dominationPoints.add(blueDomPoint);
		this.dominationPoints.add(neutralDomPoint);
	}

	@Override
	public IPlayer getSelf() {
		return (IPlayer) tka.getMainWindow().getApplication().getUser();
	}

	@Override
	public void addPlayer(IPlayer player) {
		players.add(player);
	}

	@Override
	public void setSelf(IPlayer player) {
		tka.getMainWindow().getApplication().setUser(player);
	}
}
