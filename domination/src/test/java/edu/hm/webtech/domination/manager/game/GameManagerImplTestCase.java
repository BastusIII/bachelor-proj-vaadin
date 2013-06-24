package edu.hm.webtech.domination.manager.game;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.hm.webtech.domination.model.DominationPoint;
import edu.hm.webtech.domination.model.Game;
import edu.hm.webtech.domination.model.GameConfiguration;
import edu.hm.webtech.domination.model.GameType;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.Player;
import edu.hm.webtech.domination.model.Team;
import edu.hm.webtech.domination.model.TeamIdentifier;

/**
 * {@link TestCase} for the {@link GameManagerImpl}.
 * 
 * @author Marco Wolff
 * 
 */
public class GameManagerImplTestCase extends TestCase {

	private GameManagerImpl gameManager;
	private IGame game;
	private ITeam teamRed;
	private ITeam teamBlue;
	private int scoreLimit;

	@Before
	public void setUp() throws Exception {
		scoreLimit = 50;
		game = new Game(new GameConfiguration(scoreLimit, -1, -1, -1,
				new Player(0, 0, "DummyOwner"), GameType.HM_BACKYARD_DUMMY,
				"schmarre"));
		gameManager = new GameManagerImpl(game);
		teamRed = new Team(TeamIdentifier.RED, 0);
		teamBlue = new Team(TeamIdentifier.BLUE, 0);
		game.addTeam(teamRed);
		game.addTeam(teamBlue);
	}

	@Test
	public void testJoinGame() throws Exception {
		IPlayer playerOne = new Player(0.01, 0.01, "depp");
		IPlayer playerTwo = new Player(0.01, 0.01, "volldepp");

		gameManager.joinGame(playerOne);
		gameManager.joinGame(playerTwo);

		assertEquals(2, game.getPlayers().size());
		assertEquals(2, game.getTeams().size());

		assertNotSame(playerOne.getTeam(), playerTwo.getTeam());
	}

	@Test
	public void testLeaveGame() throws Exception {
		IPlayer playerOne = new Player(0.01, 0.01, "depp");

		gameManager.joinGame(playerOne);

		assertEquals(1, game.getPlayers().size());

		gameManager.leaveGame(playerOne);

		assertEquals(0, game.getPlayers().size());
	}

	@Test
	public void testChangeTeam() throws Exception {
		IPlayer playerOne = new Player(0.01, 0.01, "depp");

		gameManager.joinGame(playerOne);

		assertEquals(1, game.getPlayers().size());

		ITeam oldTeam = playerOne.getTeam();

		gameManager.changeTeam(playerOne, oldTeam == teamBlue ? teamRed
				: teamBlue);

		assertNotSame(oldTeam, playerOne.getTeam());
	}

	@Test
	public void testUpdateLocation() throws Exception {
		IPlayer playerOne = new Player(0.0, 0.0, "volldepp");

		gameManager.joinGame(playerOne);

		gameManager.updateLocation(new Player(666.0, 666.0, "volldepp"), 10.0,
				20.0);

		assertEquals(10.0, playerOne.getLongitude(), 0.0d);
		assertEquals(20.0, playerOne.getLatitude(), 0.0d);
	}

	@Test
	public void testStartGame() throws Exception {
		game.addDominationPoint(new DominationPoint(11.555418, 48.154221, 0.1,
				0));
		gameManager = new GameManagerImpl(game);
		gameManager.joinGame(new Player(11.555418, 48.154221, "capturer"));
		gameManager.joinGame(new Player(0.0, 0.0, "dummy"));

		gameManager.startGame();

		assertEquals(true, gameManager.isGameRunning());

		while (gameManager.getWinnerTeam() == null) {
			Thread.sleep(500);
		}
		ITeam winnerTeam = gameManager.getWinnerTeam();

		assertEquals(scoreLimit, winnerTeam.getScore());
	}
}
