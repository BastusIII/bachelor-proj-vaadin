package edu.hm.webtech.domination.ui.view;

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
import edu.hm.webtech.test.GameFactory;

import org.vaadin.vol.*;
import org.vaadin.vol.VectorLayer.SelectionMode;

/**
 * This view shows the map with the current positions of the players to the
 * user.
 * 
 * @author Maximlian Briglmeier
 * 
 */
@SuppressWarnings("serial")
public class MapView extends NavigationView implements PositionCallback {
	
	private static Logger logger = new Logger(MapView.class.toString());
	
	private static final int DOMINATION_POINT_ICON_SIZE = 20;
	private static final int PLAYER_ICON_SIZE = 10;
	
	//The icons displayed on the map
	public static StyleMap MY_LOCATION_POINT; 
	public static StyleMap MY_LOCATION_RING;
	public static StyleMap PLAYER_BLUE;
	public static StyleMap PLAYER_RED;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_RED;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_BLUE;
	public static StyleMap DOMINATION_POINT_LOCATION_POINT_NEUTRAL;
	
	/**
	 * Containing all the important informations about the game.
	 */
	private IGame game;
	//private IPlayer me;
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
	private VectorLayer playerRedLocationVector = new VectorLayer();
	private VectorLayer playerBlueLocationVector = new VectorLayer();
	private VectorLayer myLocationRingVector = new VectorLayer();
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
	private static void generateStyleMaps() {
		if(MY_LOCATION_RING == null) {
			synchronized (MapView.class) {
				MY_LOCATION_RING = createStyleMap(PLAYER_ICON_SIZE + 10, PLAYER_ICON_SIZE + 10, ApplicationConfiguration.MY_LOCATION_RING_ICON);
			}
		}
		if(PLAYER_RED == null) {
			synchronized (MapView.class) {
				PLAYER_RED = createStyleMap(PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, ApplicationConfiguration.PLAYER_RED_ICON_PATH);
			}
		}
		if(PLAYER_BLUE == null) {
			synchronized (MapView.class) {
				PLAYER_BLUE = createStyleMap(PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, ApplicationConfiguration.PLAYER_BLUE_ICON_PATH);
			}
		}
		if(DOMINATION_POINT_LOCATION_POINT_RED == null) {
			synchronized (MapView.class) {
				DOMINATION_POINT_LOCATION_POINT_RED = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_RED);
			}
		}
		if(DOMINATION_POINT_LOCATION_POINT_BLUE == null) {
			synchronized (MapView.class) {
				DOMINATION_POINT_LOCATION_POINT_BLUE = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_BLUE);
			}
		}
		if(DOMINATION_POINT_LOCATION_POINT_NEUTRAL == null) {
			synchronized (MapView.class) {
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
	private static StyleMap createStyleMap(int graphicHeight, int graphicWidth, String graphicPath) {
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
			if (latestLatitude != 0) {
				setCenter();
			} else {
				latestLatitude = 60;
				latestLongitude = 22.3;
				setCenter();
				window.detectCurrentPosition(this);
			}
			
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
		for(IPlayer player: game.getPlayers()) {
			// TODO Takes always the team of the owner for testing reasons. Has to be removed at the time the user can be identified. 
			//ITeam myTeam = me.getTeam();
			ITeam myTeam = dummy.getTeam();
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
			}
		}
		// Add the vectors to the 'openLayersMap'
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
			openLayersMap.setCenter(game.getMap().getLongitude(), game.getMap().getLatitude());

		}
	}

	/**
	 * Gets called, if the position of the user can not be determined.
	 */
	public void onFailure(int errorCode) {
		logger.infoLog("Location detection failed with code: " + errorCode);
	}
}
