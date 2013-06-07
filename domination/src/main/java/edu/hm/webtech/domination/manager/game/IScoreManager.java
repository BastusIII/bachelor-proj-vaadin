package edu.hm.webtech.domination.manager.game;

import edu.hm.webtech.domination.listener.IGameTickListener;
import edu.hm.webtech.domination.model.IScoreObject;

/**
 * Marker interface for managers that are managing {@link IScoreObject}s based
 * on game ticks by {@link IGameManager}.
 * 
 * @author Marco Wolff
 * 
 */
public interface IScoreManager extends IGameTickListener {
}
