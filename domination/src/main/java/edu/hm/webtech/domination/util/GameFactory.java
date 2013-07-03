package edu.hm.webtech.domination.util;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory creating games.
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
        List<ITeam> teams = new ArrayList<ITeam>();
		List<IDominationPoint> dominationpoints = new ArrayList<IDominationPoint>();
		Map map;
        
        switch(gameConfiguration.getGameType()) {
        	case MUNICH_CENTRAL:
        		teams.add(new Team(TeamIdentifier.BLUE, 0));
                teams.add(new Team(TeamIdentifier.RED, 0));
                
                // Praterinsel
                dominationpoints.add(new DominationPoint(11.590748, 48.136652, 75));
                // Theresienwiese
                dominationpoints.add(new DominationPoint(11.549292, 48.131439, 300));
                // Alter Botanischer Garten
                dominationpoints.add(new DominationPoint(11.564054, 48.141750, 100));
                // Marienplatz - Rathaus
                dominationpoints.add(new DominationPoint(11.575513, 48.137375, 25));
                // Sendlinger Tor
                dominationpoints.add(new DominationPoint(11.567587, 48.133996, 25));
                // Hofgarten - Dianatempel
                dominationpoints.add(new DominationPoint(11.579998, 48.142951, 50));
                
                
                map = new Map(11.578732, 48.136480,14);
            	game = new Game(gameConfiguration);
            	game.setMap(map);
                try {
                    for (IDominationPoint domPoint : dominationpoints)
                        game.addDominationPoint(domPoint);
                    for(ITeam team : teams) {
                        game.addTeam(team);
                    }
                } catch (ModelException ex) {
                    ex.printStackTrace();
                }
        		break;
        	case SCHWABING_WEST:
        		teams.add(new Team(TeamIdentifier.BLUE, 0));
                teams.add(new Team(TeamIdentifier.RED, 0));
                
                // Hochschule München
                dominationpoints.add(new DominationPoint(11.559554, 48.157290, 50));
                // Nordbad
                dominationpoints.add(new DominationPoint(11.563690, 48.160317, 50));
                // Alter Nordfriedhof
                dominationpoints.add(new DominationPoint(11.571747, 48.153486, 25));
                // Leopoldpark
                dominationpoints.add(new DominationPoint(11.581170, 48.156489, 100));
                // Universität (LMU)
                dominationpoints.add(new DominationPoint(11.579853, 48.149835, 75));
                
                map = new Map(11.566895, 48.155236,15);
            	game = new Game(gameConfiguration);
            	game.setMap(map);
                try {
                    for (IDominationPoint domPoint : dominationpoints)
                        game.addDominationPoint(domPoint);
                    for(ITeam team : teams) {
                        game.addTeam(team);
                    }
                } catch (ModelException ex) {
                    ex.printStackTrace();
                }
        		break;
        		
            case HM_BACKYARD :
                teams.add(new Team(TeamIdentifier.BLUE, 0));
                teams.add(new Team(TeamIdentifier.RED, 0));

                dominationpoints.add(new DominationPoint(11.555418, 48.154221, 10, 50));
                dominationpoints.add(new DominationPoint(11.556129, 48.154171, 10, 100));
                dominationpoints.add(new DominationPoint(11.556526, 48.153536, 10, 100));
                map = new Map(11.556062, 48.153991,18);
            	
            	game = new Game(gameConfiguration);
            	game.setMap(map);
                try {
                    for (IDominationPoint domPoint : dominationpoints)
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
            	teams.add(new Team(TeamIdentifier.BLUE, 0));
                teams.add(new Team(TeamIdentifier.RED, 0));

                dominationpoints.add(new DominationPoint(11.555418, 48.154221, 10, 50));
                dominationpoints.add(new DominationPoint(11.556129, 48.154171, 10, 100));
                dominationpoints.add(new DominationPoint(11.556526, 48.153536, 10, 100));
                map = new Map(11.556062, 48.153991,18);
            	
                ITeam teamBlue = teams.get(0);
                ITeam teamRed = teams.get(1);

                final IPlayer[] players = new IPlayer[6];
                players[0] = new Player(11.556062, 48.153991, "Player One", teamRed);
                players[1] = new Player(11.556209, 48.153543, "Player Two", teamRed);
                players[2] = new Player(11.555437, 48.154180, "Player Three", teamBlue);
                players[3] = new Player(11.555898, 48.154230, "Player Four", teamRed);
                players[4] = new Player(11.556300, 48.153790, "Player Five", teamBlue);
                players[5] = new Player(11.556711, 48.153617, "Player Six", teamBlue);

                game = new Game(new GameConfiguration(250,2,players.length / 2,dominationpoints.size(),gameConfiguration.getOwner(),null,gameConfiguration.getName()));
                game.setMap(new Map(11.556062, 48.153991, 18));

                dominationpoints.get(0).setOwnerTeam(teamRed);
                dominationpoints.get(2).setOwnerTeam(teamBlue);

                try {
                    for (IPlayer player : players)
                        game.addPlayer(player);
                    for (IDominationPoint domPoint : dominationpoints)
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
