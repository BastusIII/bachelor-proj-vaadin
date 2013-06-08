package edu.hm.webtech.domination.model;

import edu.hm.webtech.domination.exception.ModelException;

/**
 * Defines methods for a object representing a player in domination.
 *
 * @author Marco Wolff
 * @author Sebastian Stumpf
 */
public interface IPlayer extends ILocationObject, IScoreObject {

    /**
     * @return {@link String} representing the identifier of the player.
     * @throws ModelException if the player identifier is not initialized.
     */
    public String getIdentifier() throws ModelException;

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
