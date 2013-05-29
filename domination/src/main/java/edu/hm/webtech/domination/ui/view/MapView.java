package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationView;

/**
 * This view shows the map with the current positions of the players to the user.
 * @author Brigi
 *
 */
@SuppressWarnings("serial")
public class MapView extends NavigationView implements PositionCallback{

	public MapView(String caption) {
		super(caption);
	}
	
	@Override
	public void onSuccess(Position position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(int errorCode) {
		// TODO Auto-generated method stub
		
	}

}
