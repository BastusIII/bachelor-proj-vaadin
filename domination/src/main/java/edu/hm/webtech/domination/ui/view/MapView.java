package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationView;
import org.vaadin.vol.Control;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;

/**
 * This view shows the map with the current positions of the players to the user.
 * @author Brigi
 *
 */
@SuppressWarnings("serial")
public class MapView extends NavigationView implements PositionCallback {

	private OpenLayersMap openLayersMap;

	public MapView(String caption) {
		super(caption);
	}


	@Override
	public void attach() {
		buildView();
		super.attach();
	};

	private void buildView() {
		setCaption("Map");

		if (openLayersMap == null) {

			openLayersMap = new OpenLayersMap();

			configureMapControls();
			openLayersMap.setImmediate(true);
			openLayersMap.addLayer(new OpenStreetMapLayer());

			openLayersMap.setSizeFull();
			openLayersMap.setZoom(12);
			setContent(openLayersMap);

		}
	}

	private void configureMapControls() {
		openLayersMap.getControls().clear();
		openLayersMap.addControl(Control.Attribution);
		openLayersMap.addControl(Control.ZoomPanel);
		openLayersMap.addControl(Control.TouchNavigation);
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
