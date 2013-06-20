package edu.hm.webtech.domination.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.vol.Bounds;
import org.vaadin.vol.Control;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.PointVector;
import org.vaadin.vol.Style;
import org.vaadin.vol.StyleMap;
import org.vaadin.vol.VectorLayer;
import org.vaadin.vol.VectorLayer.SelectionMode;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.ApplicationConfiguration;
import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.TeamIdentifier;
import edu.hm.webtech.domination.util.Logger;

/**
 * This view shows the map with the current positions of the players and the domination points
 * to the user.
 * 
 * @author Maximlian Briglmeier
 * 
 */
@SuppressWarnings("serial")
public class MapView extends NavigationView implements PositionCallback {
	
	private static Logger logger = new Logger(MapView.class.toString());
	
	// The size of the icons
	private static final int DOMINATION_POINT_ICON_SIZE = 20;
	private static final int PLAYER_ICON_SIZE = 10;
	
	//The icons displayed on the map
	private StyleMap MY_LOCATION_RING;
	private StyleMap PLAYER_BLUE;
	private StyleMap PLAYER_RED;
	private StyleMap DOMINATION_POINT_LOCATION_POINT_RED;
	private StyleMap DOMINATION_POINT_LOCATION_POINT_BLUE;
	private StyleMap DOMINATION_POINT_LOCATION_POINT_NEUTRAL;
	
	/**
	 * Containing all the important informations about the game.
	 */
	private IGame game;
	
	/**
	 * Needed for locking the screen.
	 */
	private boolean locked = false;
	/**
	 * Refreshes the screen constantly.
	 */
	private Refresher refresher;
	/**
	 * The OpenStreetMap
	 */
	private OpenLayersMap openLayersMap;
	
	// Mapping between objects and icons
	private VectorLayer playerRedLocationVector = new VectorLayer();
	private VectorLayer playerBlueLocationVector = new VectorLayer();
	private VectorLayer myLocationRingVector = new VectorLayer();
	private List<VectorLayer> areas = new ArrayList<VectorLayer>();
	private VectorLayer dominationPointLocationVector_red = new VectorLayer();
	private VectorLayer dominationPointLocationVector_blue = new VectorLayer();
	private VectorLayer dominationPointLocationVector_neutral = new VectorLayer();

	/**
	 * Constructor initializing the important member variables and sets up a refresher for 
	 * automatic screen updating.  
	 */
	public MapView(IGameManager gameManager) {
		game = gameManager.getGame();
		refresher = new Refresher();
		refresher.setRefreshInterval(500);
		generateStyleMaps();
		MyVaadinApplication.get().getMainWindow().detectCurrentPosition(this);
	}

	/**
	 * Generates the icons of the players and domination points.
	 */
	private void generateStyleMaps() {
		synchronized (MapView.class) {
			if(MY_LOCATION_RING == null) {
				MY_LOCATION_RING = createStyleMap(PLAYER_ICON_SIZE + 10, PLAYER_ICON_SIZE + 10, ApplicationConfiguration.MY_LOCATION_RING_ICON);
			}
			if(PLAYER_RED == null) {
				PLAYER_RED = createStyleMap(PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, ApplicationConfiguration.PLAYER_RED_ICON_PATH);
			}
			if(PLAYER_BLUE == null) {
				PLAYER_BLUE = createStyleMap(PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, ApplicationConfiguration.PLAYER_BLUE_ICON_PATH);
			}
			if(DOMINATION_POINT_LOCATION_POINT_RED == null) {
				DOMINATION_POINT_LOCATION_POINT_RED = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_RED);
			}
			if(DOMINATION_POINT_LOCATION_POINT_BLUE == null) {
				DOMINATION_POINT_LOCATION_POINT_BLUE = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_BLUE);
			}
			if(DOMINATION_POINT_LOCATION_POINT_NEUTRAL == null) {
				DOMINATION_POINT_LOCATION_POINT_NEUTRAL = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_NEUTRAL);
			}
		}
	}
	
	/**
	 * Loads the given icon and creates a Style Map out of it.
	 * @param graphicHeight the height of the icon 
	 * @param graphicWidth the width of the icon
	 * @param graphicPath the path of the icon
	 * @return the created Style Map
	 */
	private StyleMap createStyleMap(int graphicHeight, int graphicWidth, String graphicPath) {
		Style style = new Style();
		style.setExternalGraphic(graphicPath);
		style.setGraphicHeight(graphicHeight);
		style.setGraphicWidth(graphicWidth);
		style.setFillOpacity(1);
		StyleMap styleMap = new StyleMap(style);
		styleMap.setExtendDefault(true);
		return styleMap;
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
					// Sets the border of the game, thus forbids the user to scroll away from the map
					if(!locked){
						locked = true;
						openLayersMap.setRestrictedExtent(openLayersMap.getExtend());
					}
				}
			};

			initOpenLayersMap();
			setContent(openLayersMap);
			
			TouchKitWindow window = (TouchKitWindow) getWindow();
			setCenter();
			window.detectCurrentPosition(this);
			
			initVectors();
			
			// Add refresher, which updates the UI to the current situation.
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
	 * Initializes the member variable 'openLayersMap'.
	 */
	private void initOpenLayersMap() {
		configureMapControls();
		openLayersMap.setImmediate(true);
		openLayersMap.addLayer(new OpenStreetMapLayer());
		openLayersMap.setSizeFull();
		openLayersMap.setZoom(game.getMap().getZoomFactor());
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
	 * Initializes the 'Vector's
	 */
	private void initVectors() {
		myLocationRingVector.setStyleMap(MY_LOCATION_RING);
		myLocationRingVector.setSelectionMode(SelectionMode.SIMPLE);
		
		playerRedLocationVector.setStyleMap(PLAYER_RED);
		playerRedLocationVector.setSelectionMode(SelectionMode.SIMPLE);
		
		playerBlueLocationVector.setStyleMap(PLAYER_BLUE);
		playerBlueLocationVector.setSelectionMode(SelectionMode.SIMPLE);
		
		dominationPointLocationVector_red.setStyleMap(DOMINATION_POINT_LOCATION_POINT_RED);
		dominationPointLocationVector_red.setSelectionMode(SelectionMode.SIMPLE);
		
		dominationPointLocationVector_blue.setStyleMap(DOMINATION_POINT_LOCATION_POINT_BLUE);
		dominationPointLocationVector_blue.setSelectionMode(SelectionMode.SIMPLE);
		
		dominationPointLocationVector_neutral.setStyleMap(DOMINATION_POINT_LOCATION_POINT_NEUTRAL);
		dominationPointLocationVector_neutral.setSelectionMode(SelectionMode.SIMPLE);
	}
	
	/**
	 * Updates the map by drawing the icons to the new position of the elements.
	 */
	private void updateLocations() {
		//Update the vectors
		myLocationRingVector.removeAllComponents();
		//TODO Takes always the owner of the game for testing reasons. Has to be removed at the time the user can be identified.
		IPlayer dummy = game.getOwner();
		PointVector myRing = new PointVector(dummy.getLongitude(), dummy.getLatitude());
		myLocationRingVector.addVector(myRing);
		// TODO end
		
		playerBlueLocationVector.removeAllComponents();
		playerRedLocationVector.removeAllComponents();
		// TODO Takes always the team of the owner for testing reasons. Has to be removed at the time the user can be identified. 
		//ITeam myTeam = me.getTeam();
		ITeam myTeam = dummy.getTeam();
		for(IPlayer player: game.getPlayers()) {
			//logger.infoLog("Player location: " + player.getLongitude() + ", " + player.getLatitude());
			if(myTeam.equals(player.getTeam())) {
				PointVector location = new PointVector(player.getLongitude(), player.getLatitude());
				switch(myTeam.getTeamIdentifier()) {
					case RED:
						playerRedLocationVector.addVector(location);
						break;
					case BLUE:
						playerBlueLocationVector.addVector(location);
						break;
					default:
						logger.errorLog("Given Color can not be used yet(not implemented yet).");
						break;
				}
			}
		}
		
		TouchKitWindow window = (TouchKitWindow) getWindow();
		float windowWidth = window.getWidth();
		float windowHeight = window.getHeight();
		
		Bounds bounds= openLayersMap.getExtend();
		double longitudeDifference = bounds.getMaxLon() - bounds.getMinLon();
		double latitudeDifference = bounds.getMaxLat() - bounds.getMinLat();
		
		dominationPointLocationVector_red.removeAllComponents();
		dominationPointLocationVector_blue.removeAllComponents();
		dominationPointLocationVector_neutral.removeAllComponents();
		for(VectorLayer vLayer: areas)
			vLayer.removeAllComponents();
		for(IDominationPoint dominationPoint: game.getDominationPoints()) {
			PointVector pointLocation = new PointVector(dominationPoint.getLongitude(), dominationPoint.getLatitude());
			PointVector areaLocation = new PointVector(dominationPoint.getLongitude(), dominationPoint.getLatitude());

			// size of the area graphic is been calculated by converting the location and the radius into pixels with the help of the window size
			double latitudeOffset = getLatitudeDistance(dominationPoint.getLongitude(), dominationPoint.getLatitude(), 2 * dominationPoint.getRadius());
			double longitudeOffset = getLongitudeDistance(dominationPoint.getLongitude(), dominationPoint.getLatitude(), 2 * dominationPoint.getRadius());
			//System.out.println("Distance: " + dominationPoint.getDistance(dominationPoint.getLongitude() + longitudeOffset, dominationPoint.getLatitude()+ latitudeOffset));
			//System.out.println("window height: " + windowHeight + ", lat Dif: " + latitudeDifference + ", lat offset: " + latitudeOffset);
			int graphicHeight = (int )(windowHeight / (latitudeDifference * latitudeOffset));
			int graphicWidth = (int) (windowWidth / (longitudeDifference * longitudeOffset));
			//System.out.println("Graphic Height: " + graphicHeight + ", Graphic Width: " + graphicWidth);
			
			// TODO 50 has to be replaced
			StyleMap areaStyle = createStyleMap(50, 50, ApplicationConfiguration.DOMINATION_POINT_AREA);
			VectorLayer areaVector = new VectorLayer();
			areaVector.setStyleMap(areaStyle);
			areaVector.addVector(areaLocation);
			areas.add(areaVector);
			
			ITeam owner = dominationPoint.getOwnerTeam();
			if(owner == null)
				dominationPointLocationVector_neutral.addVector(pointLocation);
			else {
				TeamIdentifier identifier;
				identifier = owner.getTeamIdentifier();
				switch(identifier) {
				case RED:
					dominationPointLocationVector_red.addVector(pointLocation);
					break;
				case BLUE:
					dominationPointLocationVector_blue.addVector(pointLocation);
					break;
				default:
					dominationPointLocationVector_neutral.addVector(pointLocation);
				}
			}
		}
		// Add the vectors to the 'openLayersMap'
		for(VectorLayer vLayer: areas){
			if (vLayer.getParent() == null)
				openLayersMap.addLayer(vLayer);
		}
		if(dominationPointLocationVector_red.getParent() == null) 
			openLayersMap.addLayer(dominationPointLocationVector_red);
		if(dominationPointLocationVector_blue.getParent() == null) 
			openLayersMap.addLayer(dominationPointLocationVector_blue);
		if(dominationPointLocationVector_neutral.getParent() == null) 
			openLayersMap.addLayer(dominationPointLocationVector_neutral);
		if(playerRedLocationVector.getParent() == null)
			openLayersMap.addLayer(playerRedLocationVector);
		if(playerBlueLocationVector.getParent() == null)
			openLayersMap.addLayer(playerBlueLocationVector);
		if(myLocationRingVector.getParent() == null)
			openLayersMap.addLayer(myLocationRingVector);
		
		((TouchKitWindow) getWindow()).detectCurrentPosition(this);
	}

	
	/**
	 * Calculates the longitude of a point moved by the given meters in longitude direction.
	 * This is only an approximation.
	 * @param longitude the longitude of the base location
	 * @param latitude the latitude of the base location
	 * @param meters the distance
	 * @return the new longitude
	 */
	private double getLongitudeDistance(double longitude, double latitude, double meters) {
		/*final int earthRadius = 6378137;
		double offset = meters / (earthRadius * Math.cos(Math.PI * latitude / 180));
		double result = longitude + (offset * 180/Math.PI);
		*/
		final double factor = 1 / (111.111 * Math.cos(latitude));
		double result = factor * meters;
		result = result * Math.PI/ 180;
		
		return result;
	}
	
	/**
	 * Calculates the latitude of a point moved by the given meters in latitude direction.
	 * @param longitude the longitude of the base location
	 * @param latitude the latitude of the base location
	 * @param meters the distance
	 * @return the new latitude
	 */
	private double getLatitudeDistance(double longitude, double latitude, double meters) {
		/*final int earthRadius = 6378137;
		double offset = meters / earthRadius;
		double result = latitude + offset * 180 / Math.PI;
		*/
		final double factor = 1 / 111.111;
		double result = factor* meters;
		result = result * Math.PI/ 180;
		
		return result;
	}
	
	/**
	 * Gets the current location from the user.
	 * @param position the location, which got determined
	 */
	public void onSuccess(Position position) {
		IPlayer player = (IPlayer) MyVaadinApplication.getApp().getUser();
		player.setGeoCoordinates(position.getLongitude(), position.getLatitude());
	}

	/**
	 * Forces the map to show the center of the game.
	 */
	private void setCenter() {
		if (openLayersMap != null) {
			openLayersMap.setCenter(game.getMap().getLongitude(), game.getMap().getLatitude());

		}
	}

	/**
	 * Gets called, if the position of the user can not be determined.
	 * @ errorCode the error Code determining the kind of error, which occurred
	 */
	public void onFailure(int errorCode) {
		logger.infoLog("Location detection failed with code: " + errorCode);
	}
}
