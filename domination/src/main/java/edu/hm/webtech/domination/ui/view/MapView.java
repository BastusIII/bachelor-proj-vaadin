package edu.hm.webtech.domination.ui.view;

import java.util.List;
import java.util.ResourceBundle;

import org.vaadin.vol.Control;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Vector;
import org.vaadin.vol.VectorLayer.VectorSelectedEvent;
import org.vaadin.vol.VectorLayer.VectorSelectedListener;

import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;

import edu.hm.webtech.domination.MyVaadinApplication;

/**
 * This view shows the map with the current positions of the players to the
 * user.
 * 
 * @author Maximlian Briglmeier
 * 
 */
@SuppressWarnings("serial")
public class MapView extends NavigationView implements PositionCallback,
		VectorSelectedListener {

	private OpenLayersMap openLayersMap;
	private double latestLongitude;
	private double latestLatitude;

	public MapView() {
		MyVaadinApplication.get().getMainWindow().detectCurrentPosition(this);
	}

	@Override
	public void attach() {
		buildView();
		super.attach();
	};

	private void buildView() {
		if (openLayersMap == null) {

			openLayersMap = new OpenLayersMap() {
				@Override
				protected void updateExtent(
						java.util.Map<String, Object> variables) {
					super.updateExtent(variables);
					// Maybe update positions here
					// updateMarkers();
				};
			};

			configureMapControls();
			openLayersMap.setImmediate(true);
			openLayersMap.addLayer(new OpenStreetMapLayer());

			openLayersMap.setSizeFull();
			openLayersMap.setZoom(12);
			setContent(openLayersMap);
			TouchKitWindow window = (TouchKitWindow) getWindow();
			if (latestLatitude != 0) {
				setCenter();
			} else {
				latestLatitude = 60;
				latestLongitude = 22.3;
				setCenter();
				window.detectCurrentPosition(this);
			}
		}
	}

	private void configureMapControls() {
		openLayersMap.getControls().clear();
		openLayersMap.addControl(Control.Attribution);
		openLayersMap.addControl(Control.ZoomPanel);
		openLayersMap.addControl(Control.TouchNavigation);
	}

	public void onSuccess(Position position) {
		latestLatitude = position.getLatitude();
		latestLongitude = position.getLongitude();

		MyVaadinApplication touchKitApplication = (MyVaadinApplication) MyVaadinApplication
				.get();

		touchKitApplication.setCurrentLatitude(position.getLatitude());
		touchKitApplication.setCurrentLongitude(position.getLongitude());

		setCenter();

	}

	private void setCenter() {
		if (openLayersMap != null) {
			openLayersMap.setCenter(latestLongitude, latestLatitude);
		}
	}

	public void onFailure(int errorCode) {
		// TODO Auto-generated method stub

	}

	public void vectorSelected(VectorSelectedEvent event) {
		Vector vector = event.getVector();
		if (vector != null) {
			// Observation data = (Observation) vector.getData();
			// showPopup(data);
			// markerLayer.setSelectedVector(null);
		}
	}
}
