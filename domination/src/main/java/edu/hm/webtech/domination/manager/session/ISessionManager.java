package edu.hm.webtech.domination.manager.session;

import com.vaadin.service.ApplicationContext;
import edu.hm.webtech.domination.exception.ModelException;
import edu.hm.webtech.domination.model.IPlayer;

/**
 * Defines methods for handling and creating sessions for {@link IPlayer}s.
 * 
 * @author Marco Wolff
 * 
 */
public interface ISessionManager {

	/**
	 * Creates and registers a new {@link IPlayer} to the
	 * {@link ApplicationContext} using given identifier.
	 * 
	 * @param identifier
	 *            uniquely identifying the new {@link IPlayer}.
	 */
	public boolean createAndRegisterPlayer(String identifier);

    /**
     * Adds a new Player to the list of known players
     * @param player
     * 			username to check, if already exists
     * @author Daniel Brielbeck
     */
    public void addPlayer(IPlayer player) throws ModelException;

}
