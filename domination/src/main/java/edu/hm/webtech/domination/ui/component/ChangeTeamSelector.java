package edu.hm.webtech.domination.ui.component;

import com.vaadin.ui.*;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Selector component for changing the team.
 *
 * @author Felix Schramm
 */
public class ChangeTeamSelector extends CustomComponent {

    /**
     * The game manager which is used to change the teams.
     */
    private final IGameManager gameManager;

    /**
     * Selector component to change the team for the player.
     * @param gameManager The game manager used to change the team.
     */
    public ChangeTeamSelector(final IGameManager gameManager) {
        this.gameManager = gameManager;
        initLayout();
    }

    /**
     * Initializes the ScoreBoards layout.
     */
    private void initLayout() {
        VerticalLayout changeTeamLayout = new VerticalLayout();
        Label caption = new Label("Change to team:");
        HorizontalLayout buttonLayout = new HorizontalLayout();

        for (ITeam team: gameManager.getGame().getTeams()) {
            Button joinTeamButton = new JoinTeamButton((IPlayer) MyVaadinApplication.getApp().getUser(), team);
            buttonLayout.addComponent(joinTeamButton);
        }

        changeTeamLayout.addComponent(caption);
        changeTeamLayout.addComponent(buttonLayout);

        setCompositionRoot(changeTeamLayout);
    }

    /**
     * A button to join a team.
     */
    private class JoinTeamButton extends Button {

        private JoinTeamButton(final IPlayer player, final ITeam team) {
            super(team.getTeamIdentifier().toString());
            addListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    gameManager.changeTeam(player, team);
                }
            });
        }
    }
}
