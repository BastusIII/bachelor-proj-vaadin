package edu.hm.webtech.domination.manager.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.hm.webtech.domination.model.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link TestCase} for the {@link DominationManagerImpl}.
 * 
 * @author Marco Wolff
 * 
 */
public class DominationManagerImplTestCase extends TestCase {

	private IGame game;
	private DominationManagerImpl dominationManager;
	private IDominationPoint dominationPoint;
	private List<IPlayer> players = new ArrayList<IPlayer>();
	private ITeam teamBlue;
	private ITeam teamRed;

	@Before
	public void setUp() throws Exception {
		game = new Game(new GameConfiguration(-1,-1,-1,-1,new Player(0,0,"DummyOwner"), GameType.HM_BACKYARD_DUMMY, "schmarre")) {
			@Override
			public Collection<IDominationPoint> getDominationPoints() {
				List<IDominationPoint> dominationPoints = new ArrayList<IDominationPoint>();
				dominationPoints.add(dominationPoint);
				return dominationPoints;
			}

			@Override
			public Collection<IPlayer> getPlayers() {
				return players;
			}
		};

		teamBlue = new Team(TeamIdentifier.BLUE, 0);
		teamRed = new Team(TeamIdentifier.RED, 0);

		players.add(new Player(11.555418, 48.154221, "Player One", teamBlue));
		players.add(new Player(11.555418, 48.154221, "Player Two", teamRed));
		players.add(new Player(11.555418, 48.154221, "Player Three", teamBlue));

		dominationManager = new DominationManagerImpl();
	}

	@Test
	public void testCapturingTeamBlue() throws Exception {
		dominationPoint = new DominationPoint(11.555418, 48.154221, 0.1, 0);
		dominationManager.updateCapturing(game);

		assertEquals(teamBlue, dominationPoint.getCaptureTeam());
		assertEquals(null, dominationPoint.getOwnerTeam());
		assertEquals(10, dominationPoint.getCaptureProgress());
	}

	@Test
	public void testOwningTeamBlue() throws Exception {
		dominationPoint = new DominationPoint(11.555418, 48.154221, 0.1, 90);
		dominationPoint.setCaptureTeam(teamBlue);
		dominationManager.updateCapturing(game);

		assertEquals(teamBlue, dominationPoint.getOwnerTeam());
		assertEquals(teamBlue, dominationPoint.getCaptureTeam());
		assertEquals(100, dominationPoint.getCaptureProgress());
	}

	@Test
	public void testDeOwningTeamRed() throws Exception {
		dominationPoint = new DominationPoint(11.555418, 48.154221, 0.1, 100);
		dominationPoint.setCaptureTeam(teamRed);
		dominationPoint.setOwnerTeam(teamRed);
		dominationManager.updateCapturing(game);

		assertEquals(null, dominationPoint.getOwnerTeam());
		assertEquals(teamRed, dominationPoint.getCaptureTeam());
		assertEquals(90, dominationPoint.getCaptureProgress());
	}

	@Test
	public void testDeCapturingTeamRed() throws Exception {
		dominationPoint = new DominationPoint(11.555418, 48.154221, 0.1, 10);
		dominationPoint.setCaptureTeam(teamRed);
		dominationManager.updateCapturing(game);

		assertEquals(null, dominationPoint.getOwnerTeam());
		assertEquals(null, dominationPoint.getCaptureTeam());
		assertEquals(0, dominationPoint.getCaptureProgress());
	}

	@Test
	public void testTeamBlueOverOwning() throws Exception {
		dominationPoint = new DominationPoint(11.555418, 48.154221, 0.1, 100);
		dominationPoint.setOwnerTeam(teamBlue);
		dominationPoint.setCaptureTeam(teamBlue);
		dominationManager.updateCapturing(game);

		assertEquals(teamBlue, dominationPoint.getOwnerTeam());
		assertEquals(teamBlue, dominationPoint.getCaptureTeam());
		assertEquals(110, dominationPoint.getCaptureProgress());
	}

	@Test
	public void testTeamBlueNotOverOverOwning() throws Exception {
		dominationPoint = new DominationPoint(11.555418, 48.154221, 0.1, 130);
		dominationPoint.setOwnerTeam(teamBlue);
		dominationPoint.setCaptureTeam(teamBlue);
		dominationManager.updateCapturing(game);

		int oldTeamBlueScore = teamBlue.getScore();
		assertEquals(teamBlue, dominationPoint.getOwnerTeam());
		assertEquals(teamBlue, dominationPoint.getCaptureTeam());
		assertEquals(130, dominationPoint.getCaptureProgress());
		assertEquals(oldTeamBlueScore, teamBlue.getScore());
	}
}
