package edu.hm.webtech.domination.model;

/**
 * Enum for the types of Games.
 *
 * @author Sebastian Stumpf
 */
public enum GameType {
    HM_BACKYARD_DUMMY("HM - Yard Dummy"),
    HM_BACKYARD("HM - Yard");

    private final String name;

    private GameType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static GameType getType(final String name) {
        for(GameType type : GameType.values()){
            if(type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
