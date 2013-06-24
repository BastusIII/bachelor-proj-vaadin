package edu.hm.webtech.domination.manager.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.listener.IGameTickListener;
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
	 * {@link List} containing {@link IGameTickListener}s that will be called
	 * per game tick.
	 */
	private List<IGameTickListener> gameTickListeners;

	/**
	 * Shows if the game is running.
	 */
	private boolean isGameRunning = false;

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
		this.gameTickListeners = new ArrayList<IGameTickListener>();
		this.gameTickListeners.add((IGameTickListener) dominationManager);
		this.gameTickListeners.add((IGameTickListener) scoreManager);
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
				logger.infoLog("Player '" + player.getIdentifier()
						+ "' successfully left the game!");
			} catch (ModelException e) {
				e.printStackTrace();
				logger.errorLog("Player '" + player.getIdentifier()
						+ "' wasn't able to leave the game from player '"
						+ game.getOwner() + "'!");
			}

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
			return new Game(game);
		}
	}

	@Override
	public void startGame() {
		if (!isGameReady()) {
			logger.errorLog("Could not start game, not enough teams / players in game!");
		} else if (getWinnerTeam() != null) {
			logger.errorLog("Could not start game, since it is already over!");
		} else if (isGameRunning()) {
			logger.errorLog("Could not start game, since it is already running!");
		} else {
			Thread gameThread = new Thread(new GameRunnable());
			gameThread.start();
			isGameRunning = true;
			logger.infoLog("The game has been started!");
		}
	}

	@Override
	public boolean isGameRunning() {
		return isGameRunning;
	}

	@Override
	public void updateLocation(IPlayer player, double longitude, double latitude) {
		if (player == null) {
			throw new IllegalArgumentException("Player may not be null!");
		}
		synchronized (game) {
			boolean locationUpdated = false;
			List<IPlayer> players = (List<IPlayer>) game.getPlayers();
			for (IPlayer playerToUpdate : players) {
				if (playerToUpdate.equals(player)) {
					playerToUpdate.setGeoCoordinates(longitude, latitude);
					locationUpdated = true;
					break;
				}
			}
			if (locationUpdated) {
				logger.infoLog("Player '" + player.getIdentifier()
						+ "' successfully updated his location to '"
						+ longitude + "' '" + latitude + "'!");
			} else {
				logger.errorLog("Player '"
						+ player.getIdentifier()
						+ "' could not updated his location to '"
						+ longitude
						+ "' '"
						+ latitude
						+ "', because there is no such player registered at the game!");
			}
		}
	}

	@Override
	public boolean isGameReady() {
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
					for (IGameTickListener gameTickListener : gameTickListeners) {
						gameTickListener.tick(game);
					}
					/*
					 * After tick, check if there is a winner yet!
					 */
					winnerTeam = determineWinnerTeam();
					if (winnerTeam != null) {
						logger.infoLog("Team '"
								+ winnerTeam.getTeamIdentifier()
								+ "' has won the game with a score of '"
								+ winnerTeam.getScore() + "'!");
					}
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

	/**
	 * Determine, if there is a winner {@link ITeam}, meaning it has the highest
	 * score in the game.
	 * 
	 * @return {@link ITeam} being the winner of this {@link IGame}, or null if
	 *         there is no winner yet.
	 */
	private ITeam determineWinnerTeam() {
		List<ITeam> teams = (List<ITeam>) game.getTeams();
		List<ITeam> winningTeams = new ArrayList<ITeam>();
		int scoreLimit = game.getGameConfiguration().getScoreLimit();

		/*
		 * Look up all teams, that are above the score limit.
		 */
		for (ITeam team : teams) {
			if (team.getScore() >= scoreLimit) {
				winningTeams.add(team);
			}
		}

		/*
		 * Now check, if there is a team, that has the absolute highest score.
		 * If there is a draw, there will be no winner, leading to extended game
		 * time in order to determine a absolute winner. There is no draw in
		 * domination since it is named "DOMINATION" not "EVERYBODYISAWINNER".
		 */
		ITeam winningTeam = null;
		int highestScore = Integer.MIN_VALUE;
		for (ITeam team : winningTeams) {
			if (team.getScore() > highestScore) {
				winningTeam = team;
			} else if (team.getScore() == highestScore) {
				winningTeam = null;
			}
		}

		return winningTeam;
	}

}
