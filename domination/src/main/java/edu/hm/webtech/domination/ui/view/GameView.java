package edu.hm.webtech.domination.ui.view;

import com.vaadin.ui.AbsoluteLayout;
import edu.hm.webtech.domination.ui.component.ScoreBoard;

/**
 * A GameView containing a ScoreBoard and a Map View.
 *
 * @author Felix Schramm
 */
public class GameView  extends AbsoluteLayout{

	public GameView() {
        MapView mapView = new MapView();
        mapView.setSizeFull();
		addComponent(mapView, "left: 0%; right: 0%; top: 0%; bottom: 0%;");
		addComponent(new ScoreBoard(), "top: 1%;");
	}

}
