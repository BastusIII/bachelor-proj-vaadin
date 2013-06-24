package edu.hm.webtech.domination.model;

import edu.hm.webtech.domination.MyVaadinApplication;

import java.io.File;

/**
 * This class holds some static values needed for Application Configuration. For example filenames.
 *
 * @author Sebastian Stumpf, Maximilian Briglmeier
 */
public interface ApplicationConfiguration {

    public static final String ROOT_PATH = MyVaadinApplication.getApp() == null ? "I_AM_HERE_FOR_TESTING_REASONS" : MyVaadinApplication.getApp().getURL().toString();
    public static final String IMAGES_PATH = ROOT_PATH + "VAADIN" + File.separator + "themes"
                                                + File.separator + "domination" + File.separator
                                                + "images" + File.separator;
    public static final String PLAYER_ICON_BASE_NAME = IMAGES_PATH + "player_icons" + File.separator;
    public static final String DOMINATION_POINT_ICON_BASE_NAME = IMAGES_PATH + "domination_point_icons" + File.separator;
    public static final String PLAYER_RED_ICON_PATH = PLAYER_ICON_BASE_NAME + "player_location_icon_red.png";
    public static final String PLAYER_BLUE_ICON_PATH = PLAYER_ICON_BASE_NAME + "player_location_icon_blue.png";
    public static final String MY_LOCATION_RING_ICON = PLAYER_ICON_BASE_NAME + "my_location_ring_glowing_icon.png";
    public static final String DOMINATION_POINT_PATH = DOMINATION_POINT_ICON_BASE_NAME + "domination_point";
    public static final String DOMINATION_POINT_AREA = DOMINATION_POINT_ICON_BASE_NAME + "domination_point_area.png";
    public static final String DOMINATION_POINT_RED = DOMINATION_POINT_PATH + "_red.png";
    public static final String DOMINATION_POINT_GREEN = DOMINATION_POINT_PATH + "_green.png";
    public static final String DOMINATION_POINT_BLUE = DOMINATION_POINT_PATH + "_blue.png";
    public static final String DOMINATION_POINT_PINK = DOMINATION_POINT_PATH + "_pink.png";
    public static final String DOMINATION_POINT_PURPLE = DOMINATION_POINT_PATH + "_purple.png";
    public static final String DOMINATION_POINT_BLACK = DOMINATION_POINT_PATH + "_black.png";
    public static final String DOMINATION_POINT_NEUTRAL = DOMINATION_POINT_PATH + "_neutral.png";
}
