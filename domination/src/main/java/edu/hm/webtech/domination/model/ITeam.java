package edu.hm.webtech.domination.model;


/**
 * Defines methods for a object representing a team in domination.
 *
 * @author Marco Wolff
 */
public interface ITeam extends IScoreObject {

    /**
     * @return {@link TeamIdentifier} of the team.
     */
    public TeamIdentifier getTeamIdentifier();

    /**
     * Sets the {@link TeamIdentifier} of the team.
     *
     * @param teamIdentifier {@link TeamIdentifier} identifying the team.
     */
    public void setTeamIdentifier(TeamIdentifier teamIdentifier);

    /**
     * @return {@link String} representing the location to the player icon.
     */
    public String getPlayerIcon();

    /**
     * @return {@link String} representing the location to the domination point
     *         icon.
     */
    public String getDominationPointIcon();
}
