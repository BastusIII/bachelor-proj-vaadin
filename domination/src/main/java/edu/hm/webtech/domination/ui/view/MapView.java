package edu.hm.webtech.domination.ui.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import edu.hm.webtech.domination.manager.game.LocationManager;
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
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.ApplicationConfiguration;
import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.Player;
import edu.hm.webtech.domination.model.TeamIdentifier;
import edu.hm.webtech.domination.util.Logger;

/**
 * This view shows the map with the current positions of the players and the domination points
 * to the user.
 *
 * @author Maximlian Briglmeier
 */
@SuppressWarnings("serial")
public class MapView extends NavigationView implements RefreshListener {

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
    private IGameManager gameManager;

    /**
     * Needed for locking the screen.
     */
    private boolean locked = false;
    /**
     * The user of the application. Preinitialized with a dummy, so that there don't 
     * occur errors, when the connection is too slow to transfer the user data to
     * the map.
     */
    private IPlayer user = new Player(0, 0, "Dummy");
    /**
     * The OpenStreetMap
     */
    private OpenLayersMap openLayersMap;

    /**
     * Used to get the location of the user.
     */
    private final LocationManager locationManager;

    /**
     * Refreshes the map constantly and so updates it.
     */
    private final Refresher refresher;

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
    public MapView(IGameManager gameManager, Refresher refresher) {
        this.gameManager = gameManager;
        this.refresher = refresher;
        locationManager = new LocationManager();
        refresher.addListener(this);
        generateStyleMaps();
    }

    @Override
    public void attach() {
        buildView();
        super.attach();
    }

    /**
     * Generates the icons of the players and domination points.
     */
    private void generateStyleMaps() {
        synchronized (MapView.class) {
            if (MY_LOCATION_RING == null) {
                MY_LOCATION_RING = createStyleMap(PLAYER_ICON_SIZE + 10, PLAYER_ICON_SIZE + 10, ApplicationConfiguration.MY_LOCATION_RING_ICON);
            }
            if (PLAYER_RED == null) {
                PLAYER_RED = createStyleMap(PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, ApplicationConfiguration.PLAYER_RED_ICON_PATH);
            }
            if (PLAYER_BLUE == null) {
                PLAYER_BLUE = createStyleMap(PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, ApplicationConfiguration.PLAYER_BLUE_ICON_PATH);
            }
            if (DOMINATION_POINT_LOCATION_POINT_RED == null) {
                DOMINATION_POINT_LOCATION_POINT_RED = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_RED);
            }
            if (DOMINATION_POINT_LOCATION_POINT_BLUE == null) {
                DOMINATION_POINT_LOCATION_POINT_BLUE = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_BLUE);
            }
            if (DOMINATION_POINT_LOCATION_POINT_NEUTRAL == null) {
                DOMINATION_POINT_LOCATION_POINT_NEUTRAL = createStyleMap(DOMINATION_POINT_ICON_SIZE, DOMINATION_POINT_ICON_SIZE, ApplicationConfiguration.DOMINATION_POINT_NEUTRAL);
            }
        }
    }

    /**
     * Loads the given icon and creates a Style Map out of it.
     *
     * @param graphicHeight the height of the icon
     * @param graphicWidth  the width of the icon
     * @param graphicPath   the path of the icon
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
                    if (!locked) {
                        locked = true;
                        openLayersMap.setRestrictedExtent(openLayersMap.getExtend());
                    }
                }
            };

            initOpenLayersMap();
            openLayersMap.addComponent(refresher);
            setContent(openLayersMap);

            setCenter();

            initVectors();
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
        openLayersMap.setZoom(gameManager.getGame().getMap().getZoomFactor());
    }

    /**
     * Configuring the controls of the map.
     */
    private void configureMapControls() {
        openLayersMap.getControls().clear();
        openLayersMap.addControl(Control.Attribution);
        openLayersMap.addControl(Control.TouchNavigation);
        openLayersMap.addControl(Control.ZoomPanel);
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
        if (gameManager.getWinnerTeam() != null) {
            setContent(new ResultView(gameManager));
        } else {
            IGame game = this.gameManager.getGame();
            IPlayer player = (IPlayer) MyVaadinApplication.getApp().getUser();
            if (player == null) {
                Position position = locationManager.getPosition();
                if (position != null) {
                    player = new Player(position.getLongitude(), position.getLatitude(), "Dummy Player");
                } else {
                    player = new Player(0, 0, "Dummy Player");
                }
            }
            user = player;

            try {
                updateMyPosition();
                updatePlayerLocations(game);
                updateDominationPoints(game);

                addVectorsToMap();
            } catch (NullPointerException ex) {
                logger.errorLog("User could not be identified. Maybe the access of reading the location was refused.");
            }
        }
    }

    /**
     * Updates the position of the user and sets a ring around his cursor.
     */
    private void updateMyPosition() {
        myLocationRingVector.removeAllComponents();
        PointVector myRing = new PointVector(user.getLongitude(), user.getLatitude());
        myLocationRingVector.addVector(myRing);
    }

    /**
     * Updates the locations of all users.
     *
     * @param game contains the players.
     */
    private void updatePlayerLocations(IGame game) {
        playerBlueLocationVector.removeAllComponents();
        playerRedLocationVector.removeAllComponents();

        ITeam myTeam = user.getTeam();
        for (IPlayer player : game.getPlayers()) {
            if (myTeam.equals(player.getTeam())) {
                PointVector location = new PointVector(player.getLongitude(), player.getLatitude());
                switch (myTeam.getTeamIdentifier()) {
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
    }

    /**
     * Updates the status of all domination points.
     *
     * @param game contains the status of the domination points
     */
    private void updateDominationPoints(IGame game) {
        TouchKitWindow window = (TouchKitWindow) getWindow();
        float windowWidth = window.getWidth();
        float windowHeight = window.getHeight();

        Bounds bounds = openLayersMap.getExtend();
        double longitudeDifference = bounds.getMaxLon() - bounds.getMinLon();
        double latitudeDifference = bounds.getMaxLat() - bounds.getMinLat();

        dominationPointLocationVector_red.removeAllComponents();
        dominationPointLocationVector_blue.removeAllComponents();
        dominationPointLocationVector_neutral.removeAllComponents();
        for (VectorLayer vLayer : areas)
            vLayer.removeAllComponents();
        for (IDominationPoint dominationPoint : game.getDominationPoints()) {
            PointVector pointLocation = new PointVector(dominationPoint.getLongitude(), dominationPoint.getLatitude());
            PointVector areaLocation = new PointVector(dominationPoint.getLongitude(), dominationPoint.getLatitude());

            // size of the area graphic is been calculated by converting the location and the radius into pixels with the help of the window size
            double latitudeOffset = getLatitudeOffset(2 * dominationPoint.getRadius());
            double longitudeOffset = getLongitudeOffset(dominationPoint.getLatitude(), 2 * dominationPoint.getRadius());
            
            int graphicHeight = (int) ((windowHeight * latitudeOffset) / latitudeDifference);
            int graphicWidth = (int) ((windowWidth * longitudeOffset) / longitudeDifference);

            StyleMap areaStyle = createStyleMap(graphicHeight, graphicWidth, ApplicationConfiguration.DOMINATION_POINT_AREA);
            VectorLayer areaVector = new VectorLayer();
            areaVector.setStyleMap(areaStyle);
            areaVector.addVector(areaLocation);
            areas.add(areaVector);

            ITeam owner = dominationPoint.getOwnerTeam();
            if (owner == null)
                dominationPointLocationVector_neutral.addVector(pointLocation);
            else {
                TeamIdentifier identifier;
                identifier = owner.getTeamIdentifier();
                switch (identifier) {
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
    }

    private void addVectorsToMap() {
        for (VectorLayer vLayer : areas) {
            if (vLayer.getParent() == null)
                openLayersMap.addLayer(vLayer);
        }
        if (dominationPointLocationVector_red.getParent() == null)
            openLayersMap.addLayer(dominationPointLocationVector_red);
        if (dominationPointLocationVector_blue.getParent() == null)
            openLayersMap.addLayer(dominationPointLocationVector_blue);
        if (dominationPointLocationVector_neutral.getParent() == null)
            openLayersMap.addLayer(dominationPointLocationVector_neutral);
        if (playerRedLocationVector.getParent() == null)
            openLayersMap.addLayer(playerRedLocationVector);
        if (playerBlueLocationVector.getParent() == null)
            openLayersMap.addLayer(playerBlueLocationVector);
        if (myLocationRingVector.getParent() == null)
            openLayersMap.addLayer(myLocationRingVector);
    }

    /**
     * Calculates the longitude of a point moved by the given meters in longitude direction.
     * This is only an approximation.
     *
     * @param latitude  the latitude of the base location
     * @param meters    the distance
     * @return the new longitude
     */
    private double getLongitudeOffset(double latitude, double meters) {
    	BigDecimal convertedLatitude = new BigDecimal(latitude);
    	BigDecimal convertedMeters = new BigDecimal(meters);
    	
    	final BigDecimal tmp = (new BigDecimal(111111)).multiply(new BigDecimal(Math.cos(convertedLatitude.doubleValue())));
		final BigDecimal offsetLongitude = convertedMeters.divide(tmp,10,RoundingMode.HALF_UP);
		
		return offsetLongitude.abs().doubleValue();
    }

    /**
     * Calculates the latitude of a point moved by the given meters in latitude direction.
     *
     * @param meters    the distance
     * @return the new latitude
     */
    private double getLatitudeOffset(double meters) {
    	final BigDecimal convertedMeters = new BigDecimal(meters);
    	
    	final BigDecimal offsetLatitude = convertedMeters.divide(new BigDecimal(111111),10,RoundingMode.HALF_UP);
    	
        return offsetLatitude.abs().doubleValue();
    }

    /**
     * Forces the map to show the center of the game.
     */
    private void setCenter() {
        IGame game = this.gameManager.getGame();
        if (openLayersMap != null) {
            openLayersMap.setCenter(game.getMap().getLongitude(), game.getMap().getLatitude());

        }
    }

    @Override
    public void refresh(Refresher refresher) {
        updateLocations();
    }
}
