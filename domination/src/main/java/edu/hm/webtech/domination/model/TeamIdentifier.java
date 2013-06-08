package edu.hm.webtech.domination.model;

/**
 * <h1>Teams werden anhand von Farben identifiziert.</h1>
 * @author Sebastian Stumpf
 */
public enum TeamIdentifier {
    RED(ApplicationConfiguration.PATH_EXTENSION_RED),
    GREEN(ApplicationConfiguration.PATH_EXTENSION_GREEN),
    BLUE(ApplicationConfiguration.PATH_EXTENSION_BLUE),
    PINK(ApplicationConfiguration.PATH_EXTENSION_PINK),
    PURPLE(ApplicationConfiguration.PATH_EXTENSION_PURPLE),
    BLACK(ApplicationConfiguration.PATH_EXTENSION_BLACK);

    private final String pathExtension;

    /**
     * Konstruktor.
     * @param pathExtension Die Erweiterung des Dateinamens von Icons, die diesem Identifier zugeordnet sind.
     */
    private TeamIdentifier(String pathExtension) {
        this.pathExtension = pathExtension;
    }

    /**
     * Getter für Erweiterung des Dateinamens.
     * @return Die Erweiterung.
     */
    public String getPathExtension() {
        return this.pathExtension;
    }
}
