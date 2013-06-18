package edu.hm.webtech.test;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.model.*;

/**
 * A Factory creating dummy games for testing reasons.
 * @author Maximilian Briglmeier
 *
 */
public class GameFactory {

	/**
	 * Serves as dummy and exists for testing reasons. Represents a domination points 
	 * and simulated movement of players in the HM garden.
	 * @return the created dummy game
	 */
	public static IGame GetHMGarden() {
		IGame game = new Game();
		game.setMap(new Map(11.556062, 48.153991, 18));
        game.setGameConfiguration(new GameConfiguration(500));
		IDominationPoint[] dominationPoints = new IDominationPoint[3];
		dominationPoints[0] = new DominationPoint(11.555418, 48.154221, 10, 50);
		dominationPoints[1] = new DominationPoint(11.556129, 48.154171, 10, 100);
		dominationPoints[2] = new DominationPoint(11.556526, 48.153536, 10, 100);

		ITeam teamBlue = new Team(TeamIdentifier.BLUE, 0);
		ITeam teamRed = new Team(TeamIdentifier.RED, 0);

		final IPlayer[] players = new IPlayer[6];
		players[0] = new Player(11.556062, 48.153991, "Player One", teamRed);
		players[1] = new Player(11.556209, 48.153543, "Player Two", teamRed);
		players[2] = new Player(11.555437, 48.154180, "Player Three", teamBlue);
		players[3] = new Player(11.555898, 48.154230, "Player Four", teamRed);
		players[4] = new Player(11.556300, 48.153790, "Player Five", teamBlue);
		players[5] = new Player(11.556711, 48.153617, "Player Six", teamBlue);

		dominationPoints[0].setOwnerTeam(teamRed);
		dominationPoints[2].setOwnerTeam(teamBlue);

        // Sebastian Stumpf -> need Owner ;)
        game.setOwner(players[0]);

		try {
			for (IPlayer player : players)
				game.addPlayer(player);
			for (IDominationPoint domPoint : dominationPoints)
				game.addDominationPoint(domPoint);
			game.addTeam(teamRed);
			game.addTeam(teamBlue);
		} catch (ModelException ex) {
			ex.printStackTrace();
		}

		// Lets the players move
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean switcher = true;
				while (true) {
					for (IPlayer player : players) {
						if (switcher)
							player.setGeoCoordinates(player.getLongitude() + 0.0002, player.getLatitude());
						else
                            player.setGeoCoordinates(player.getLongitude() - 0.0002, player.getLatitude());
					}
					try {
						Thread.sleep(2000);
						switcher = !switcher;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();

		return game;
	}

    // TODO: Maximilian Briglmeier? -> Define Methods in IGameConfiguration that are needed to create Custom Game here.
}
