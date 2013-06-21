package edu.hm.webtech.domination.model;

/**
 * <h1>Teams werden anhand von Farben identifiziert.</h1>
 *
 * @author Sebastian Stumpf, Maximilian Briglmeier
 */
public enum TeamIdentifier {
    RED(ApplicationConfiguration.PLAYER_RED_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_RED, "Red"),
    GREEN(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_GREEN, "Green"),
    BLUE(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_BLUE, "Blue"),
    PINK(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_PINK, "Pink"),
    PURPLE(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_PURPLE, "Purple"),
    BLACK(ApplicationConfiguration.PLAYER_BLUE_ICON_PATH, ApplicationConfiguration.DOMINATION_POINT_BLACK, "Black");

    private final String playerIconPath;
    private final String dominationPointIconPath;
    private final String name;

    /**
     * Konstruktor.
     *
     * @param playerIconPath          Dateipfad zum Spieler Icon des Teams.
     * @param dominationPointIconPath Dateipfad zum Domination Point Icon des Teams.
     * @param name                    Der Name des Teams.
     */
    private TeamIdentifier(String playerIconPath, String dominationPointIconPath, String name) {
        this.name = name;
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
     *
     * @return the path of the domination point icon
     */
    public String getDominationPointIconPath() {
        return this.dominationPointIconPath;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Get css class for a team colored background..
     * @return The css class name.
     */
    public String getBgStyleClass() {
        return "team-" + this.toString() + "-bg";
    }

}
