package edu.hm.webtech.domination.manager.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.model.Game;
import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.util.Logger;

/**
 * Default implementaton of {@link IGameManager}.
 * 
 * @author Marco Wolff
 * 
 */
public class GameManagerImpl implements IGameManager {

	/**
	 * The {@link IGame} containing the actual game data.
	 */
	private IGame game;
	/**
	 * {@link ILocationManager} responsible for proper location management.
	 */
	private ILocationManager locationManger;
	/**
	 * {@link IDominationManager} responsible for proper domination point
	 * management.
	 */
	private IDominationManager dominationManager;
	/**
	 * {@link IScoreManager} responsible for proper score and winner check
	 * management.
	 */
	private IScoreManager scoreManager;
	/**
	 * This class' logger.
	 */
	private Logger logger = new Logger(this.getClass().getSimpleName());
	/**
	 * The winner {@link ITeam} of this game session.
	 */
	private ITeam winnerTeam;

	/**
	 * Creates a new {@link GameManagerImpl} with given {@link IGame}.
	 * 
	 * @param game
	 *            {@link IGame} on which this {@link IGameManager} will work.
	 */
	public GameManagerImpl(IGame game) {
		if (game == null) {
			throw new IllegalArgumentException("Game may not be null!");
		}
		this.game = game;
		this.winnerTeam = null;
		this.scoreManager = new ScoreManagerImpl();
		this.dominationManager = new DominationManagerImpl();
		this.locationManger = new LocationManager();
	}

	@Override
	public void joinGame(IPlayer player) {
		if (player == null) {
			throw new IllegalArgumentException("Player may not be null!");
		}

		if (getWinnerTeam() != null) {
			logger.errorLog("Game is already over!");
		}

		synchronized (game) {
			List<IPlayer> currentPlayers = (List<IPlayer>) game.getPlayers();
			/*
			 * Check for team sizes for fair team assignment.
			 */
			Map<ITeam, Integer> teamsSizes = new HashMap<ITeam, Integer>();
			/*
			 * First of all, put all teams registered at game in the map.
			 */
			List<ITeam> teams = (List<ITeam>) game.getTeams();
			for (ITeam team : teams) {
				if (!teamsSizes.containsKey(team)) {
					teamsSizes.put(team, new Integer(0));
				}
			}
			/*
			 * Now update their player numbers based on the teams assigned to
			 * already registered players.
			 */
			for (IPlayer currentPlayer : currentPlayers) {
				ITeam playersTeam = currentPlayer.getTeam();
				Integer currentTeamSize = teamsSizes.get(playersTeam);
				currentTeamSize++;
				teamsSizes.put(playersTeam, currentTeamSize);
			}
			/*
			 * Assign team with fewest number of players to joining player.
			 */
			ITeam newTeam = null;
			Set<Entry<ITeam, Integer>> entries = teamsSizes.entrySet();
			int lowestTeamSize = Integer.MAX_VALUE;
			for (Entry<ITeam, Integer> entry : entries) {
				if (entry.getValue() < lowestTeamSize) {
					lowestTeamSize = entry.getValue();
					newTeam = entry.getKey();
				}
			}
			/*
			 * Set team at player.
			 */
			player.setTeam(newTeam);
			try {
				/*
				 * Add that player.
				 */
				game.addPlayer(player);
			} catch (ModelException e) {
				logger.errorLog("Player '" + player.getIdentifier()
						+ "' wasn't able to join the game from player '"
						+ game.getOwner() + "'!");
			}
			logger.infoLog("Player '" + player.getIdentifier()
					+ "' successfully joined the game!");
		}
	}

	@Override
	public void leaveGame(IPlayer player) {
		if (player == null) {
			throw new IllegalArgumentException("Player may not be null!");
		}
		synchronized (game) {
			try {
				/*
				 * Remove the player from game.
				 */
				game.removePlayer(player);
			} catch (ModelException e) {
				e.printStackTrace();
				logger.errorLog("Player '" + player.getIdentifier()
						+ "' wasn't able to leave the game from player '"
						+ game.getOwner() + "'!");
			}
			logger.infoLog("Player '" + player.getIdentifier()
					+ "' successfully left the game!");
		}
	}

	@Override
	public void changeTeam(IPlayer player, ITeam team) {
		if (player == null || team == null) {
			throw new IllegalArgumentException("Player nor team may be null!");
		}

		if (getWinnerTeam() != null) {
			logger.errorLog("Game is already over!");
		}

		synchronized (game) {
			/*
			 * Change the team of given player at the player object in the game.
			 */
			List<IPlayer> currentPlayers = (List<IPlayer>) game.getPlayers();
			for (IPlayer currentPlayer : currentPlayers) {
				if (currentPlayer.getIdentifier()
						.equals(player.getIdentifier())) {
					currentPlayer.setTeam(team);
					logger.infoLog("Player '" + player.getIdentifier()
							+ "' successfully changed to team '"
							+ team.getTeamIdentifier() + "'!");
					break;
				}
			}
		}
	}

	@Override
	public IGame getGame() {

		if (getWinnerTeam() != null) {
			logger.errorLog("Game is already over!");
		}

		synchronized (game) {
			// TODO Could be problematic with synchronization. (e.g. new player joined)
			// TODO: UPDATE: Did not return the copy, because the ui doesn't get all informations then.
			//return game;
			return new Game(game);
		}
	}

	@Override
	public void startGame() {
		if (!checkStartConditions()) {
			logger.errorLog("Could not start game, not enough teams / players in game!");
		} else if (getWinnerTeam() != null) {
			logger.errorLog("Could not start game, since it is already over!");
		} else {
			Thread gameThread = new Thread(new GameRunnable());
			gameThread.start();
		}
	}

	@Override
	public void updateLocation(IPlayer player, double longitude, double latitude) {
		synchronized (game) {
			locationManger.updateLocation(player, longitude, latitude);
			logger.infoLog("Player '" + player
					+ "' successfully updated his location to '" + longitude
					+ "' '" + latitude + "'!");
		}
	}

	/**
	 * Checks if the start criteria is fulfilled.
	 * 
	 * @return true, if the start criteria is fulfilled, else false.
	 */
	private boolean checkStartConditions() {
		synchronized (game) {
			/*
			 * At the moment the criteria is, that two players with different
			 * teams are assigned. We could over think this thing again, if
			 * somebody demands, or we even make it configurable?
			 */
			List<IPlayer> players = (List<IPlayer>) game.getPlayers();

			if (players.size() < 2) {
				return false;
			}

			List<ITeam> teams = (List<ITeam>) game.getTeams();

			if (teams.size() < 2) {
				return false;
			}

			return true;
		}

	}

	/**
	 * The game runnable which calls the {@link IDominationManager} in homogene
	 * time intervals in order to calculate capturing progress on
	 * {@link IDominationPoint}s.
	 * 
	 * @author Marco Wolff
	 * 
	 */
	private class GameRunnable implements Runnable {

		/**
		 * The delay between two ticks.
		 */
		private static final long TICK_DELAY = 1000;

		@Override
		public void run() {
			while (winnerTeam == null) {
				synchronized (game) {
					dominationManager.updateCapturing(game);
					winnerTeam = scoreManager.checkForWinner(game);
				}
				try {
					Thread.sleep(TICK_DELAY);
				} catch (InterruptedException e) {
					throw new RuntimeException("I SHOULD NOT HAPPEN!", e);
				}
			}
		}
	}

	@Override
	public ITeam getWinnerTeam() {
		synchronized (game) {
			return winnerTeam;
		}
	}

}
