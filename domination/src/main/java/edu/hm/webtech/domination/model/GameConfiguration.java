package edu.hm.webtech.domination.model;

/**
 * Default implementation of {@link IGameConfiguration}.
 *
 * @author Marco Wolff, Sebastian Stumpf
 * @author Sebastian Stumpf
 */

public class GameConfiguration implements IGameConfiguration {

    public static final int SCORE_LIMIT_DEFAULT = 200;
    public static final int ZOOM_FACTOR_DEFAULT = 18;
    public static final int NUMBER_OF_TEAMS_DEFAULT = 2;
    public static final int MAX_PLAYERS_PER_TEAM_DEFAULT = 5;
    public static final int NUMBER_OF_DOMINATION_POINTS = 2;
    public static final GameType GAME_TYPE_DEFAULT = GameType.HM_BACKYARD;

    /**
     * Score limit which a team needs to hit in order to win!
     */
    private final int scoreLimit;
    /**
     * Zoom Factor of map.
     */
    private final int zoomFactor;
    /**
     * Number of teams
     */
    private final int numberOfTeams;
    /**
     * Maximum of players per team
     */
    private final int maxPlayersPerTeam;
    /**
     * the number of domination points
     */
    private final int numberOfDominationPoints;
    /**
     * the creator and owner of the game.
     */
    private final IPlayer owner;
    /**
     * The game type.
     */
    private final GameType gameType;
    /**
     * The game name.
     */
    private final String identifier;

    /**
     * Constructor, setting all game config options at once.<br />
     * If numbers are set to -1 or gameType is null the default value will be taken.
     *
     *
     * @param scoreLimit               Score limit which a team needs to hit in order to win!
     * @param zoomFactor               Zoom Factor of map.
     * @param numberOfTeams            Number of teams
     * @param maxPlayersPerTeam        Maximum of players per team
     * @param numberOfDominationPoints the number of domination points
     * @param owner                    the creator and owner of the game. must not be null!
     * @param gameType                 The game type.
     */
    public GameConfiguration(final int scoreLimit, final int zoomFactor, final int numberOfTeams, final int maxPlayersPerTeam, final int numberOfDominationPoints, final IPlayer owner, final GameType gameType, final String identifier) {
        if(owner == null || identifier == null) {
            throw new IllegalArgumentException("Owner and identifier must not be null.");
        }

        this.scoreLimit = scoreLimit != -1 ? scoreLimit : SCORE_LIMIT_DEFAULT;
        this.zoomFactor = zoomFactor != -1 ? zoomFactor : ZOOM_FACTOR_DEFAULT;
        this.numberOfTeams = numberOfTeams != -1 ? numberOfTeams : NUMBER_OF_TEAMS_DEFAULT;
        this.maxPlayersPerTeam = maxPlayersPerTeam != -1 ? maxPlayersPerTeam : MAX_PLAYERS_PER_TEAM_DEFAULT;
        this.numberOfDominationPoints = numberOfDominationPoints != -1 ? numberOfDominationPoints : NUMBER_OF_DOMINATION_POINTS;
        this.owner = owner;
        this.gameType = gameType != null ? gameType : GAME_TYPE_DEFAULT;
        this.identifier = identifier;
    }

    /**
     * Copy constructor, deep copying given {@link IGameConfiguration}.
     * TODO: NOT NEEDED???
     * @param gameConfiguration {@link IGameConfiguration} object that will be copied.
     * @author Marco Wolff, Sebastian Stumpf
     */
    public GameConfiguration(IGameConfiguration gameConfiguration) {
        if (gameConfiguration == null) {
            throw new IllegalArgumentException("Game configuration may not be null!");
        }
        this.scoreLimit = gameConfiguration.getScoreLimit();
        this.zoomFactor = gameConfiguration.getZoomFactor();
        this.numberOfTeams = gameConfiguration.getNumberOfTeams();
        this.maxPlayersPerTeam = gameConfiguration.getMaxPlayersPerTeam();
        this.numberOfDominationPoints = gameConfiguration.getNumberOfDominationPoints();
        this.owner = gameConfiguration.getOwner();
        this.gameType = gameConfiguration.getGameType();
        this.identifier = gameConfiguration.getName();
    }

    @Override
    public int getScoreLimit() {
        return scoreLimit;
    }

    @Override
    public int getZoomFactor() {
        return zoomFactor;
    }

    @Override
    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    @Override
    public int getMaxPlayersPerTeam() {
        return maxPlayersPerTeam;
    }

    @Override
    public int getNumberOfDominationPoints() {
        return numberOfDominationPoints;
    }

    @Override
    public IPlayer getOwner() {
        return owner;
    }

    @Override
    public GameType getGameType() {
        return gameType;
    }

    @Override
    public String getName() {
        return this.identifier;
    }

}
