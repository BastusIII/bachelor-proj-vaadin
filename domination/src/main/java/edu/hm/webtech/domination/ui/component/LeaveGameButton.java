package edu.hm.webtech.domination.ui.component;

import com.vaadin.ui.Button;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.ui.view.LobbyView;

/**
 * A button for leaving the game.
 *
 * @author  Felix Schramm
 */
public class LeaveGameButton extends Button{

    /**
     * The game manager which is used to leave the game.
     */
    private final IGameManager gameManager;

    /**
     * A button to leave the game.
     * @param gameManager
     */
    public LeaveGameButton(final IGameManager gameManager) {
        super("Leave Game");
        this.gameManager = gameManager;
        addListener(new LeaveGameListener());
    }

    /**
     * ClickListener for the leave game button.
     */
    private class LeaveGameListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            gameManager.leaveGame((IPlayer) MyVaadinApplication.getApp().getUser());
            getWindow().setContent(new LobbyView());
        }
    }

}
