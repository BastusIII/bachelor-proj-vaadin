package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.oldbs.gameInternals.ScoreManager;
import edu.hm.webtech.domination.oldbs.gameInternals.uiTest.Game;
import edu.hm.webtech.domination.oldbs.gameInternals.uiTest.IDominationPoint;
import edu.hm.webtech.domination.oldbs.gameInternals.uiTest.IPlayer;
import edu.hm.webtech.domination.oldbs.gameInternals.uiTest.Player;
import org.vaadin.vol.*;
import org.vaadin.vol.VectorLayer.SelectionMode;
import org.vaadin.vol.VectorLayer.VectorSelectedEvent;
import org.vaadin.vol.VectorLayer.VectorSelectedListener;

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

	//TODO Exists only for testing reasons. Has to be removed.
	static int counter = 0;
	
	public static StyleMap MY_LOCATION_POINT; 
	public static StyleMap TEAM_MEMBERS_LOCATION_POINT;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_RED;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_BLUE;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_NEUTRAL;
	
	private Game game = new Game();
	private IPlayer me;
	private OpenLayersMap openLayersMap;
	private double latestLongitude;
	private double latestLatitude;
	private VectorLayer myLocationVector = new VectorLayer();
	private VectorLayer teamMemberLocationVector = new VectorLayer();
	private VectorLayer dominationPointLocationVector_red = new VectorLayer();
	private VectorLayer dominationPointLocationVector_blue = new VectorLayer();
	private VectorLayer dominationPointLocationVector_neutral = new VectorLayer();

	public MapView() {
		// test
		me = new Player(ScoreManager.Teams.BLUE);
		// test end
		generateStyleMaps();
		MyVaadinApplication.get().getMainWindow().detectCurrentPosition(this);
	}

	private static void generateStyleMaps() {
		if(MY_LOCATION_POINT == null) {
			synchronized (MapView.class) {
				Style style = new Style();
				style.setExternalGraphic(MyVaadinApplication.getApp().getURL() + "VAADIN/themes/domination/images/my_location_point.png");
				style.setGraphicHeight(10);
				style.setGraphicWidth(10);
				style.setFillOpacity(1);
				MY_LOCATION_POINT = new StyleMap(style);
				MY_LOCATION_POINT.setExtendDefault(true);
			}
		}
		if(TEAM_MEMBERS_LOCATION_POINT == null) {
			synchronized (MapView.class) {
				Style style = new Style();
				style.setExternalGraphic(MyVaadinApplication.getApp().getURL() + "VAADIN/themes/domination/images/team_member_location_point.png");
				style.setGraphicHeight(10);
				style.setGraphicWidth(10);
				style.setFillOpacity(1);
				TEAM_MEMBERS_LOCATION_POINT = new StyleMap(style);
				TEAM_MEMBERS_LOCATION_POINT.setExtendDefault(true);
			}
		}
		if(DOMINATION_POINT_LOCATION_POINT_RED == null) {
			synchronized (MapView.class) {
				Style style = new Style();
				style.setExternalGraphic(MyVaadinApplication.getApp().getURL() + "VAADIN/themes/domination/images/domination_point_location_point(red).png");
				style.setGraphicHeight(20);
				style.setGraphicWidth(20);
				style.setFillOpacity(1);
				DOMINATION_POINT_LOCATION_POINT_RED = new StyleMap(style);
				DOMINATION_POINT_LOCATION_POINT_RED.setExtendDefault(true);
			}
		}
		if(DOMINATION_POINT_LOCATION_POINT_BLUE == null) {
			synchronized (MapView.class) {
				Style style = new Style();
				style.setExternalGraphic(MyVaadinApplication.getApp().getURL() + "VAADIN/themes/domination/images/domination_point_location_point(blue).png");
				style.setGraphicHeight(20);
				style.setGraphicWidth(20);
				style.setFillOpacity(1);
				DOMINATION_POINT_LOCATION_POINT_BLUE = new StyleMap(style);
				DOMINATION_POINT_LOCATION_POINT_BLUE.setExtendDefault(true);
			}
		}
		if(DOMINATION_POINT_LOCATION_POINT_NEUTRAL == null) {
			synchronized (MapView.class) {
				Style style = new Style();
				style.setExternalGraphic(MyVaadinApplication.getApp().getURL() + "VAADIN/themes/domination/images/domination_point_location_point(neutral).png");
				style.setGraphicHeight(20);
				style.setGraphicWidth(20);
				style.setFillOpacity(1);
				DOMINATION_POINT_LOCATION_POINT_NEUTRAL = new StyleMap(style);
				DOMINATION_POINT_LOCATION_POINT_NEUTRAL.setExtendDefault(true);
			}
		}
	}
	
	@Override
	public void attach() {
		buildView();
		super.attach();
	}

	private void buildView() {
		if (openLayersMap == null) {

			openLayersMap = new OpenLayersMap() {
				@Override
				protected void updateExtent(java.util.Map<String, Object> variables) {
					super.updateExtent(variables);
					updateLocations();
				}
			};

			configureMapControls();
			openLayersMap.setImmediate(true);
			openLayersMap.addLayer(new OpenStreetMapLayer());

			openLayersMap.setSizeFull();
			openLayersMap.setZoom(18);
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
			
			myLocationVector.setStyleMap(MY_LOCATION_POINT);
			myLocationVector.setSelectionMode(SelectionMode.SIMPLE);
			myLocationVector.addListener(this);
			
			teamMemberLocationVector.setStyleMap(TEAM_MEMBERS_LOCATION_POINT);
			teamMemberLocationVector.setSelectionMode(SelectionMode.SIMPLE);
			teamMemberLocationVector.addListener(this);
			
			dominationPointLocationVector_red.setStyleMap(DOMINATION_POINT_LOCATION_POINT_RED);
			dominationPointLocationVector_red.setSelectionMode(SelectionMode.SIMPLE);
			dominationPointLocationVector_red.addListener(this);
			
			dominationPointLocationVector_blue.setStyleMap(DOMINATION_POINT_LOCATION_POINT_BLUE);
			dominationPointLocationVector_blue.setSelectionMode(SelectionMode.SIMPLE);
			dominationPointLocationVector_blue.addListener(this);
			
			dominationPointLocationVector_neutral.setStyleMap(DOMINATION_POINT_LOCATION_POINT_NEUTRAL);
			dominationPointLocationVector_neutral.setSelectionMode(SelectionMode.SIMPLE);
			dominationPointLocationVector_neutral.addListener(this);
		}

	}

	private void configureMapControls() {
		openLayersMap.getControls().clear();
		openLayersMap.addControl(Control.Attribution);
		openLayersMap.addControl(Control.ZoomPanel);
		openLayersMap.addControl(Control.TouchNavigation);
	}

	private void updateLocations() {
		myLocationVector.removeAllComponents();
		PointVector myLocation = new PointVector(me.getLongitude(), me.getLatitude());
		myLocationVector.addVector(myLocation);		
		
		teamMemberLocationVector.removeAllComponents();
		for(IPlayer player: game.getPlayers()) {
			if(me.getTeam().equals(player.getTeam()) && (!me.equals(player))) {
				//TODO  The '(0.001 * counter)' part makes the teammembers run. Only for testing. Has to be removed.
				PointVector location = new PointVector(player.getLongitude() + (0.001*counter), player.getLatitude());
				teamMemberLocationVector.addVector(location);
			}
		}
		
		dominationPointLocationVector_red.removeAllComponents();
		dominationPointLocationVector_blue.removeAllComponents();
		dominationPointLocationVector_neutral.removeAllComponents();
		for(IDominationPoint dominationPoint: game.getDominationPoints()) {
			PointVector location = new PointVector(dominationPoint.getLongitude(), dominationPoint.getLatitude());
			ScoreManager.Teams owner = dominationPoint.getOwner();
			if(owner == null)
				dominationPointLocationVector_neutral.addVector(location);
			else {
				switch(owner) {
					case RED:
						dominationPointLocationVector_red.addVector(location);
						break;
					case BLUE:
						dominationPointLocationVector_blue.addVector(location);
						break;
					default:
						dominationPointLocationVector_neutral.addVector(location);
				}
			}
		}
		if(dominationPointLocationVector_red.getParent() == null) 
			openLayersMap.addLayer(dominationPointLocationVector_red);
		if(dominationPointLocationVector_blue.getParent() == null) 
			openLayersMap.addLayer(dominationPointLocationVector_blue);
		if(dominationPointLocationVector_neutral.getParent() == null) 
			openLayersMap.addLayer(dominationPointLocationVector_neutral);
		if(teamMemberLocationVector.getParent() == null) 
			openLayersMap.addLayer(teamMemberLocationVector);
		if(myLocationVector.getParent() == null) 
			openLayersMap.addLayer(myLocationVector);
		//TODO has to be removed
		counter++;
	}
	
	public void onSuccess(Position position) {
		latestLatitude = position.getLatitude();
		latestLongitude = position.getLongitude();

		MyVaadinApplication touchKitApplication = (MyVaadinApplication) MyVaadinApplication
				.get();
		// Test
		me.setLocation(position.getLongitude(), position.getLatitude());
		game.setUpStub(me, position.getLongitude(), position.getLatitude());
		// Test end
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
			openLayersMap.setCenter(me.getLongitude(), me.getLatitude());
			openLayersMap.setZoom(12);
			myLocationVector.setSelectedVector(null);
			teamMemberLocationVector.setSelectedVector(null);
			// Observation data = (Observation) vector.getData();
			// showPopup(data);
			// markerLayer.setSelectedVector(null);
		}
	}
}
