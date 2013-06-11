package edu.hm.webtech.domination.ui.view;

import java.util.Collection;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;
import com.vaadin.ui.VerticalLayout;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.Player;
import edu.hm.webtech.domination.model.TeamIdentifier;
import edu.hm.webtech.test.GameFactory;

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
public class MapView extends NavigationView implements PositionCallback {
	
	
	//The icons displayed on the map
	public static StyleMap MY_LOCATION_POINT; 
	public static StyleMap TEAM_MEMBERS_LOCATION_POINT;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_RED;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_BLUE;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_NEUTRAL;
	
	/**
	 * Containing all the important informations about the game.
	 */
	private IGame game = GameFactory.GetHMGarden();
	private IPlayer me;
	private boolean locked = false;
	/**
	 * Refreshes the screen constantly.
	 */
	private Refresher refresher;
	/**
	 * The OpenStreetMap
	 */
	private OpenLayersMap openLayersMap;
	private double latestLongitude;
	private double latestLatitude;
	
	// Mapping between objects and icons
	private VectorLayer myLocationVector = new VectorLayer();
	private VectorLayer teamMemberLocationVector = new VectorLayer();
	private VectorLayer dominationPointLocationVector_red = new VectorLayer();
	private VectorLayer dominationPointLocationVector_blue = new VectorLayer();
	private VectorLayer dominationPointLocationVector_neutral = new VectorLayer();

	/**
	 * Constructor initializing the important member variables and sets up a refresher for 
	 * automatic screen updating.  
	 */
	public MapView() {
		// TODO test
		ITeam team = null;
		Collection<ITeam> teams = game.getTeams();
		for(ITeam aTeam: teams){
			try {
				if(aTeam.getTeamIdentifier().equals(TeamIdentifier.BLUE))
					team = aTeam;
			} catch (ModelException e) {
				// Should not occur
				e.printStackTrace();
			}
		}
		refresher = new Refresher();
		refresher.setRefreshInterval(500);
		me = new Player(11.556062, 48.153991, "Player One", team);
		try {
			game.addPlayer(me);
		} catch (ModelException e) {
			// TODO Should not occur
			e.printStackTrace();
		}
		// test end
		generateStyleMaps();
		MyVaadinApplication.get().getMainWindow().detectCurrentPosition(this);
	}

	/**
	 * Generates the icons of the player and domination points.
	 */
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
	
	/**
	 * Builds the essential elements for the map and configures the map.
	 */
	private void buildView() {
		if (openLayersMap == null) {

			openLayersMap = new OpenLayersMap() {
				@Override
				protected void updateExtent(java.util.Map<String, Object> variables) {
					super.updateExtent(variables);
					if(!locked){
						locked = true;
						openLayersMap.setRestrictedExtent(openLayersMap.getExtend());
					}
				}
			};

			configureMapControls();
			openLayersMap.setImmediate(true);
			openLayersMap.addLayer(new OpenStreetMapLayer());

			openLayersMap.setSizeFull();
			try {
				openLayersMap.setZoom(game.getMap().getZoomFactor());
			} catch (ModelException e) {
				openLayersMap.setZoom(18);
			}
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
			
			teamMemberLocationVector.setStyleMap(TEAM_MEMBERS_LOCATION_POINT);
			teamMemberLocationVector.setSelectionMode(SelectionMode.SIMPLE);
			
			dominationPointLocationVector_red.setStyleMap(DOMINATION_POINT_LOCATION_POINT_RED);
			dominationPointLocationVector_red.setSelectionMode(SelectionMode.SIMPLE);
			
			dominationPointLocationVector_blue.setStyleMap(DOMINATION_POINT_LOCATION_POINT_BLUE);
			dominationPointLocationVector_blue.setSelectionMode(SelectionMode.SIMPLE);
			
			dominationPointLocationVector_neutral.setStyleMap(DOMINATION_POINT_LOCATION_POINT_NEUTRAL);
			dominationPointLocationVector_neutral.setSelectionMode(SelectionMode.SIMPLE);
			
			refresher.addListener(new RefreshListener() {
				
				@Override
				public void refresh(Refresher source) {
					updateLocations();	
				}
			});
			openLayersMap.addComponent(refresher);
		}

	}

	/**
	 * Configuring the controls of the map.
	 */
	private void configureMapControls() {
		openLayersMap.getControls().clear();
		openLayersMap.addControl(Control.Attribution);
		openLayersMap.addControl(Control.TouchNavigation);
	}

	/**
	 * Updates the map by drawing the icons to the new position of the elements.
	 */
	private void updateLocations() {
		myLocationVector.removeAllComponents();
		PointVector myLocation = new PointVector(me.getLongitude(), me.getLatitude());
		myLocationVector.addVector(myLocation);		
		
		teamMemberLocationVector.removeAllComponents();
		for(IPlayer player: game.getPlayers()) {
			if(me.getTeam().equals(player.getTeam()) && (!me.equals(player))) {
				//TODO  The '(0.001 * counter)' part makes the teammembers run. Only for testing. Has to be removed.
				//PointVector location = new PointVector(player.getLongitude() + (0.001*counter), player.getLatitude());
				PointVector location = new PointVector(player.getLongitude(), player.getLatitude());
				teamMemberLocationVector.addVector(location);
			}
		}
		
		dominationPointLocationVector_red.removeAllComponents();
		dominationPointLocationVector_blue.removeAllComponents();
		dominationPointLocationVector_neutral.removeAllComponents();
		for(IDominationPoint dominationPoint: game.getDominationPoints()) {
			PointVector location = new PointVector(dominationPoint.getLongitude(), dominationPoint.getLatitude());
			ITeam owner = dominationPoint.getOwnerTeam();
			if(owner == null)
				dominationPointLocationVector_neutral.addVector(location);
			else {
				TeamIdentifier identifier;
				try {
					identifier = owner.getTeamIdentifier();
					switch(identifier) {
					case RED:
						dominationPointLocationVector_red.addVector(location);
						break;
					case BLUE:
						dominationPointLocationVector_blue.addVector(location);
						break;
					default:
						dominationPointLocationVector_neutral.addVector(location);
					}
				} catch (ModelException e) {
					// Should not occur
					e.printStackTrace();
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
	}
	
	/**
	 * Gets the current location from the user.
	 */
	public void onSuccess(Position position) {
		latestLatitude = position.getLatitude();
		latestLongitude = position.getLongitude();

		MyVaadinApplication touchKitApplication = (MyVaadinApplication) MyVaadinApplication
				.get();
		touchKitApplication.setCurrentLatitude(position.getLatitude());
		touchKitApplication.setCurrentLongitude(position.getLongitude());
	}

	/**
	 * Forces the map to show the center of the game.
	 */
	private void setCenter() {
		if (openLayersMap != null) {
			try {
				openLayersMap.setCenter(game.getMap().getLongitude(), game.getMap().getLatitude());
			} catch (ModelException e) {
				openLayersMap.setCenter(latestLongitude, latestLatitude);
			}
		}
	}

	public void onFailure(int errorCode) {
		// TODO Auto-generated method stub

	}
}
