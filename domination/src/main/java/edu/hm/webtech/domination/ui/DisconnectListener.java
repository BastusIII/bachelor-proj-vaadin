package edu.hm.webtech.domination.ui;

import com.vaadin.ui.Window;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.util.Logger;

/**
 * Disconnect listener handles disconnected players.
 *
 * @author Felix Schramm
 */
public class DisconnectListener implements Window.CloseListener {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = new Logger(DisconnectListener.class.getName());

    @Override
    public void windowClose(Window.CloseEvent closeEvent) {
                /* Signal the session manager that the player has left. */
        IPlayer player = (IPlayer) closeEvent.getWindow().getApplication().getUser();
        if (player != null) {
            LOGGER.infoLog("Player: " + player.getIdentifier() + " disconnected.");
            MyVaadinApplication.getSm().handleDisconnectedPlayer(player);
        }
    }
}
