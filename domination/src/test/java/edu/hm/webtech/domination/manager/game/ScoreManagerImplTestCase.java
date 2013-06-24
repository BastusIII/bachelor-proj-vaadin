package edu.hm.webtech.domination.manager.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.hm.webtech.domination.model.DominationPoint;
import edu.hm.webtech.domination.model.Game;
import edu.hm.webtech.domination.model.GameConfiguration;
import edu.hm.webtech.domination.model.GameType;
import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.Player;
import edu.hm.webtech.domination.model.Team;
import edu.hm.webtech.domination.model.TeamIdentifier;

/**
 * {@link TestCase} for the {@link ScoreManagerImpl}.
 * 
 * @author Marco Wolff
 * 
 */
public class ScoreManagerImplTestCase extends TestCase {

	private ScoreManagerImpl scoreManager;
	private IGame game;
	private ITeam teamBlue;
	private ITeam teamRed;
	private IDominationPoint dominationPoint;

	@Before
	public void setUp() throws Exception {
		dominationPoint = new DominationPoint(0.0, 0.0, 0.0, 100);

		game = new Game(new GameConfiguration(-1, -1, -1, -1, new Player(0, 0,
				"DummyOwner"), GameType.HM_BACKYARD_DUMMY, "schmarre")) {
			@Override
			public Collection<IDominationPoint> getDominationPoints() {
				List<IDominationPoint> dominationPoints = new ArrayList<IDominationPoint>();
				dominationPoints.add(dominationPoint);
				return dominationPoints;
			}
		};

		teamBlue = new Team(TeamIdentifier.BLUE, 0);
		teamRed = new Team(TeamIdentifier.RED, 0);
		scoreManager = new ScoreManagerImpl();
	}

	@Test
	public void testScoring() throws Exception {
		int oldTeamBlueScore = teamBlue.getScore();
		int oldTeamRedScore = teamRed.getScore();
		dominationPoint.setOwnerTeam(teamBlue);
		scoreManager.tick(game);
		
		assertNotSame(oldTeamBlueScore, teamBlue.getScore());
		assertEquals(oldTeamRedScore, teamRed.getScore());
		
		oldTeamBlueScore = teamBlue.getScore();
		oldTeamRedScore = teamRed.getScore();
		dominationPoint.setOwnerTeam(teamRed);
		scoreManager.tick(game);
		
		assertEquals(oldTeamBlueScore, teamBlue.getScore());
		assertNotSame(oldTeamRedScore, teamRed.getScore());
		

		oldTeamBlueScore = teamBlue.getScore();
		oldTeamRedScore = teamRed.getScore();
		dominationPoint.setOwnerTeam(null);
		scoreManager.tick(game);
		
		assertEquals(oldTeamBlueScore, teamBlue.getScore());
		assertEquals(oldTeamRedScore, teamRed.getScore());
	}
}
