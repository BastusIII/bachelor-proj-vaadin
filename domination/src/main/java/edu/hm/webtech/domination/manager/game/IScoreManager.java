package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.listener.IGameTickListener;
import edu.hm.webtech.domination.model.IScoreObject;

/**
 * Marker interface for managers that are managing {@link IScoreObject}s in
 * order to determine if there is a winner.
 * 
 * @author Marco Wolff
 * 
 */
public interface IScoreManager extends IGameTickListener {
}
