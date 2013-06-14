package edu.hm.webtech.domination.manager.lobby;


import edu.hm.webtech.domination.manager.game.IGameManager;
import junit.framework.TestCase;

import java.util.Collection;

/**
 * Test for the LobbyManager.
 */
public class LobbyManagerTest extends TestCase{

     public void testGetGames() {
         Collection<IGameManager> gameManagers = new LobbyManager().getGames();
         assert(gameManagers != null);
     }

}
