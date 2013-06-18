package edu.hm.webtech.domination.manager.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.util.Logger;

/**
 * Default implementation of {@link IDominationManager}.
 * 
 * @author Marco Wolff
 * 
 */
public class DominationManagerImpl implements IDominationManager {

	/**
	 * The {@link Logger} of this {@link IDominationManager}.
	 */
	private Logger logger = new Logger(this.getClass().getSimpleName());

	/**
	 * The increment of the capture progress, if a team has a majority of
	 * capturing a {@link IDominationPoint}.
	 */
	public static final int CAPTURE_PROGRESS_INCREMENT = 10;

	/**
	 * The threshold, when there is no capturing {@link ITeam} at the
	 * {@link IDominationPoint}.
	 */
	public static final int NON_CAPTURING_THRESHOLD = 0;

	/**
	 * The threshold from capturing to owning a {@link IDominationPoint} for a
	 * team.
	 */
	public static final int OWNING_THRESHOLD = 100;

	/**
	 * {@link IDominationPoint}s can be over captured, so that when another team
	 * captures the {@link IDominationPoint} its not automatically 'disowned'.
	 */
	public static final int OVER_OWNING_THRESHOLD = 30;

	@Override
	public void updateCapturing(IGame game) {
		if (game == null) {
			throw new IllegalArgumentException("Game may not be null");
		}

		List<IDominationPoint> dominationPoints = (List<IDominationPoint>) game
				.getDominationPoints();
		List<IPlayer> players = (List<IPlayer>) game.getPlayers();

		for (IDominationPoint dominationPoint : dominationPoints) {

			/*
			 * First of all, get all players who are in reach of this domination
			 * point.
			 */
			List<IPlayer> playersInReach = retrievePlayersInReach(
					dominationPoint, players);

			/*
			 * Determine which team has the most players in reach of the
			 * domination point.
			 */
			ITeam currentCapturingTeam = retrieveCurrentCapturingTeam(playersInReach);

			/*
			 * If capturingTeamIdentifier is not null, some team has the most
			 * players in the reach of the domination point.
			 */
			if (currentCapturingTeam != null) {
				/*
				 * This flag insures that players and team don't get any points
				 * for over over owning the domination point!
				 */
				boolean overOverOwning = false;
				/*
				 * First, check if there is a owning team in order to calculate
				 * the capture progress.
				 */
				ITeam owningTeam = dominationPoint.getOwnerTeam();
				if (owningTeam != null) {
					/*
					 * Owner exists but is different from the currently
					 * capturing team.
					 */
					if (owningTeam != currentCapturingTeam) {
						/*
						 * Decrement the capture progress!
						 */
						dominationPoint
								.addCaptureProgress(CAPTURE_PROGRESS_INCREMENT
										* -1);
						int captureProgress = dominationPoint
								.getCaptureProgress();
						/*
						 * Is their ownership lost?
						 */
						if (captureProgress < OWNING_THRESHOLD) {
							dominationPoint.setOwnerTeam(null);
							logger.infoLog("Team '"
									+ currentCapturingTeam.getTeamIdentifier()
									+ "' stopped ownage on domination point '"
									+ dominationPoint + "'!");
						}
					} else {
						/*
						 * Oh, we are already dominating this place!
						 */
						int captureProgress = dominationPoint
								.getCaptureProgress();
						/*
						 * As long as we do not reach the over owning threshold,
						 * lets dominate the shit out of this bitch!
						 */
						if (captureProgress < OWNING_THRESHOLD
								+ OVER_OWNING_THRESHOLD) {
							dominationPoint
									.addCaptureProgress(CAPTURE_PROGRESS_INCREMENT);
						} else {
							overOverOwning = true;
						}
					}
				}
				/*
				 * No owner yet?
				 */
				else {
					/*
					 * Then check if there is a capturing team!
					 */
					ITeam capturingTeam = dominationPoint.getCaptureTeam();
					if (capturingTeam != null) {
						/*
						 * There is one, but its not us, uargh!
						 */
						if (capturingTeam != currentCapturingTeam) {
							/*
							 * Let us decrease their capturing advance!
							 */
							dominationPoint
									.addCaptureProgress(CAPTURE_PROGRESS_INCREMENT
											* -1);
							int captureProgress = dominationPoint
									.getCaptureProgress();
							/*
							 * Is their capturing lost?
							 */
							if (captureProgress <= NON_CAPTURING_THRESHOLD) {
								dominationPoint.setCaptureTeam(null);
								logger.infoLog("Team '"
										+ currentCapturingTeam
												.getTeamIdentifier()
										+ "' stopped the capturing status on domination point '"
										+ dominationPoint + "'!");
							}
						} else {
							/*
							 * Oh, we are already capturing the shit out of this
							 * bitch! LETS OWN IT!
							 */
							dominationPoint
									.addCaptureProgress(CAPTURE_PROGRESS_INCREMENT);
							int captureProgress = dominationPoint
									.getCaptureProgress();
							/*
							 * Check if we own it now!
							 */
							if (captureProgress >= OWNING_THRESHOLD) {
								dominationPoint
										.setOwnerTeam(currentCapturingTeam);
								logger.infoLog("Team '"
										+ currentCapturingTeam
												.getTeamIdentifier()
										+ "' owns the domination point '"
										+ dominationPoint + "'!");
							}
						}
					}
					/*
					 * No capturing team either, what the....
					 */
					else {
						dominationPoint.setCaptureTeam(currentCapturingTeam);
						dominationPoint
								.addCaptureProgress(CAPTURE_PROGRESS_INCREMENT);
						logger.infoLog("Team '"
								+ currentCapturingTeam.getTeamIdentifier()
								+ "' is about to capture the domination point '"
								+ dominationPoint + "'!");
						/*
						 * Since there was no capturing team before, we don't
						 * really have to check if the owning threshold is
						 * already reached...
						 */
					}
				}

				/*
				 * While team is not over over owning, give them the score they
				 * earned!
				 */
				if (!overOverOwning) {
					/*
					 * Now add the scores for capturing team and its directly
					 * involved players.
					 */
					for (IPlayer player : playersInReach) {
						if (player.getTeam().equals(currentCapturingTeam)) {
							player.addScore(CAPTURE_PROGRESS_INCREMENT);
							logger.infoLog("Player '" + player.getIdentifier()
									+ "' from team '"
									+ currentCapturingTeam.getTeamIdentifier()
									+ "' increased score by '"
									+ CAPTURE_PROGRESS_INCREMENT + "'!");
						}
					}
					currentCapturingTeam.addScore(CAPTURE_PROGRESS_INCREMENT);
					logger.infoLog("Team '"
							+ currentCapturingTeam.getTeamIdentifier()
							+ "' increased score by '"
							+ CAPTURE_PROGRESS_INCREMENT + "'!");
				}
			}
		}
	}

	/**
	 * Retrieves all {@link IPlayer}s which are in reach of the
	 * {@link IDominationPoint} which means that they are potential capturers.
	 * 
	 * @param dominationPoint
	 *            {@link IDominationPoint} which will be considered.
	 * @param considerablePlayers
	 *            {@link List} containing all {@link IPlayer}s that should be
	 *            considered.
	 * @return {@link List} containing all {@link IPlayer}s which are within
	 *         reach of {@link IDominationPoint}.
	 */
	protected List<IPlayer> retrievePlayersInReach(
			IDominationPoint dominationPoint, List<IPlayer> considerablePlayers) {
		if (dominationPoint == null || considerablePlayers == null) {
			throw new IllegalArgumentException(
					"Domination point nor considerable players may be null!");
		}

		List<IPlayer> playersInReach = new ArrayList<IPlayer>();
		for (IPlayer player : considerablePlayers) {
			double distance = Math.abs(dominationPoint.getDistance(
					player.getLongitude(), player.getLatitude()));
			if (distance <= dominationPoint.getRadius()) {
				playersInReach.add(player);
			}
		}
		return playersInReach;
	}

	/**
	 * Returns the capturing {@link ITeam} in consideration of all {@link ITeam}
	 * s that are in reach of the {@link IDominationPoint} at this moment. The
	 * {@link ITeam} with the most capturing {@link IPlayer}s will be returned,
	 * or 'null', if no {@link ITeam} has the most of capturing {@link IPlayer}
	 * s.
	 * 
	 * @param playersInReach
	 *            {@link List} containing the {@link IPlayer}s in reach of the
	 *            {@link IDominationPoint}.
	 * @return {@link ITeam} with the most {@link IPlayer}s capturing the
	 *         {@link IDominationPoint}, else 'null' if none has the most of
	 *         them.
	 */
	protected ITeam retrieveCurrentCapturingTeam(List<IPlayer> playersInReach) {
		if (playersInReach == null) {
			throw new IllegalArgumentException(
					"Players in reach may not be null!");
		}
		/*
		 * Take all teams, which have players in reach, in consideration in
		 * order to determine the capturing team!
		 */
		Map<ITeam, Integer> teamsInReach = new HashMap<ITeam, Integer>();
		for (IPlayer player : playersInReach) {
			ITeam playersTeam = player.getTeam();
			Integer numberOfPlayers = teamsInReach.get(playersTeam);
			if (numberOfPlayers == null) {
				numberOfPlayers = new Integer(1);
			} else {
				numberOfPlayers++;
			}
			teamsInReach.put(playersTeam, numberOfPlayers);
		}

		/*
		 * Now check if there is a team with most players within reach of the
		 * domination point!
		 */
		Set<Entry<ITeam, Integer>> entrySet = teamsInReach.entrySet();
		ITeam currentCapturingTeam = null;
		int playerCounter = Integer.MIN_VALUE;
		for (Entry<ITeam, Integer> entry : entrySet) {
			Integer numberOfPlayers = entry.getValue();
			int result = numberOfPlayers.compareTo(playerCounter);
			if (result > 0) {
				currentCapturingTeam = entry.getKey();
				playerCounter = numberOfPlayers;
			} else if (result == 0) {
				currentCapturingTeam = null;
			}
		}

		return currentCapturingTeam;
	}
}
