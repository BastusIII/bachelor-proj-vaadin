package edu.hm.webtech.domination.manager.game;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.hm.webtech.domination.model.Game;
import edu.hm.webtech.domination.model.GameConfiguration;
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

	@Before
	public void setUp() throws Exception {
		game = new Game(new GameConfiguration(10));
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
}
