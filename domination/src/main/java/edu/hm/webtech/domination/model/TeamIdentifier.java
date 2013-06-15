package edu.hm.webtech.domination.model;

/**
 * <h1>Teams werden anhand von Farben identifiziert.</h1>
 *
 * @author Sebastian Stumpf, Maximilian Briglmeier
 */
public enum TeamIdentifier {
    RED(ApplicationConfiguration.PLAYER_RED_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_RED),
    GREEN(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_GREEN),
    BLUE(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_BLUE),
    PINK(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_PINK),
    PURPLE(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_PURPLE),
    BLACK(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_BLACK);

    private final String playerIconPath;
    private final String dominationPointIconPath;

    /**
     * Konstruktor.
     *
     * @param pathExtension Die Erweiterung des Dateinamens von Icons, die diesem Identifier zugeordnet sind.
     */
    private TeamIdentifier(String playerIconPath, String dominationPointIconPath) {
        this.playerIconPath = playerIconPath;
        this.dominationPointIconPath = dominationPointIconPath;
    }

    /**
     * Getter für Erweiterung des Dateinamens.
     *
     * @return Die Erweiterung.
     */
    public String getPlayerIconPath() {
        return this.playerIconPath;
    }
    
    /**
     * Gets the path for the icon of the team's domination point.
     * @return the path of the domination point icon
     */
    public String getDominationPointIconPath() {
    	return this.dominationPointIconPath;
    }
}
