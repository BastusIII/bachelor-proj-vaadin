package edu.hm.webtech.domination.model;

import java.io.Serializable;


/**
 * Defines methods for a object representing a player in domination.
 *
 * @author Marco Wolff
 * @author Sebastian Stumpf
 */
public interface IPlayer extends ILocationObject, IScoreObject, Serializable{

    /**
     * @return {@link String} representing the identifier of the player.
     */
    public String getIdentifier();

    /**
     * Sets the identifer of the player.
     *
     * @param identifier {@link String} representing the identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * @return {@link ITeam} of the player.
     */
    public ITeam getTeam();

    /**
     * Sets the team of which the player is a member.
     *
     * @param team {@link ITeam} of which the player is a member.
     */
    public void setTeam(ITeam team);

}
