package edu.hm.webtech.domination.ui.view;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.ui.AbsoluteLayout;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.manager.game.RefreshManager;
import edu.hm.webtech.domination.ui.component.ScoreBoard;

/**
 * A GameView containing a ScoreBoard and a Map View.
 *
 * @author Felix Schramm
 */
public class GameView  extends AbsoluteLayout{

	public GameView(IGameManager gameManager) {
        Refresher refresher = new RefreshManager().getRefresher();
        MapView mapView = new MapView(gameManager, refresher);
        mapView.setSizeFull();
		addComponent(mapView, "left: 0%; right: 0%; top: 0%; bottom: 0%;");
		addComponent(new ScoreBoard(gameManager, refresher), "top: 1%;");
	}

}
