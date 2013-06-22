package edu.hm.webtech.test;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.model.*;

import java.util.ArrayList;

/**
 * A Factory creating dummy games for testing reasons.
 * @author Maximilian Briglmeier
 *
 */
public class GameFactory {

	/**
	 * Creates games and out of the given {@link GameConfiguration}.
	 * @param gameConfiguration gives the configurations and settings for the game to be created
	 * @return the created game
	 */
	public static IGame getGame(IGameConfiguration gameConfiguration) {

        IGame game = null;

        ArrayList<ITeam> teams = new ArrayList<>();
        teams.add(new Team(TeamIdentifier.BLUE, 0));
        teams.add(new Team(TeamIdentifier.RED, 0));

        // Initialize HMBACKYARD fields
        IDominationPoint[] dominationPointsHMBackYard = new IDominationPoint[3];
        dominationPointsHMBackYard[0] = new DominationPoint(11.555418, 48.154221, 10, 50);
        dominationPointsHMBackYard[1] = new DominationPoint(11.556129, 48.154171, 10, 100);
        dominationPointsHMBackYard[2] = new DominationPoint(11.556526, 48.153536, 10, 100);
        Map mapHMBackYard = new Map(11.556062, 48.153991,18);
		
        switch(gameConfiguration.getGameType()) {
            case HM_BACKYARD :
            	game = new Game(gameConfiguration);
            	game.setMap(mapHMBackYard);
                try {
                    for (IDominationPoint domPoint : dominationPointsHMBackYard)
                        game.addDominationPoint(domPoint);
                    for(ITeam team : teams) {
                        game.addTeam(team);
                    }
                } catch (ModelException ex) {
                    ex.printStackTrace();
                }
                break;
            /**
             * Serves as dummy and exists for testing reasons. Represents a domination points 
             * and simulated movement of players in the HM garden.
             */
            case HM_BACKYARD_DUMMY:

                ITeam teamBlue = teams.get(0);
                ITeam teamRed = teams.get(1);

                final IPlayer[] players = new IPlayer[6];
                players[0] = new Player(11.556062, 48.153991, "Player One", teamRed);
                players[1] = new Player(11.556209, 48.153543, "Player Two", teamRed);
                players[2] = new Player(11.555437, 48.154180, "Player Three", teamBlue);
                players[3] = new Player(11.555898, 48.154230, "Player Four", teamRed);
                players[4] = new Player(11.556300, 48.153790, "Player Five", teamBlue);
                players[5] = new Player(11.556711, 48.153617, "Player Six", teamBlue);

                game = new Game(new GameConfiguration(250,2,players.length / 2,dominationPointsHMBackYard.length,gameConfiguration.getOwner(),null,gameConfiguration.getName()));
                game.setMap(new Map(11.556062, 48.153991, 18));

                dominationPointsHMBackYard[0].setOwnerTeam(teamRed);
                dominationPointsHMBackYard[2].setOwnerTeam(teamBlue);

                try {
                    for (IPlayer player : players)
                        game.addPlayer(player);
                    for (IDominationPoint domPoint : dominationPointsHMBackYard)
                        game.addDominationPoint(domPoint);
                    for(ITeam team : teams) {
                        game.addTeam(team);
                    }
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
                break;
            default:
                break;
        }

		return game;
	}
}
