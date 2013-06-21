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
     * Css class for the change team button.
     */
    private static final String CHANGE_TEAM_BUTTON_CLASS = "change-team-button";

    /**
     * Css class for the change team caption.
     */
    private static final String CHANGE_TEAM_CAPTION = "change-team-caption";

    /**
     * The layout containing the buttons.
     */
    private Layout buttonLayout;

    /**
     * Initializes the ScoreBoards layout.
     */
    private void initLayout() {
        Layout changeTeamLayout = new VerticalLayout();
        Label caption = new Label("Change to team:");
        caption.addStyleName(CHANGE_TEAM_CAPTION);
        buttonLayout = new HorizontalLayout();

        refreshButtons();

        changeTeamLayout.addComponent(caption);
        changeTeamLayout.addComponent(buttonLayout);

        setCompositionRoot(changeTeamLayout);
    }

    private void refreshButtons() {
        buttonLayout.removeAllComponents();
        for (ITeam team: gameManager.getGame().getTeams()) {
            Button joinTeamButton = new JoinTeamButton((IPlayer) MyVaadinApplication.getApp().getUser(), team);
            buttonLayout.addComponent(joinTeamButton);
        }
    }

    /**
     * A button to join a team.
     */
    private class JoinTeamButton extends Button {

        private JoinTeamButton(final IPlayer player, final ITeam team) {
            super(team.getTeamIdentifier().toString());
            addStyleName(CHANGE_TEAM_BUTTON_CLASS);
            addStyleName(team.getTeamIdentifier().getBgStyleClass());
            addListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    gameManager.changeTeam(player, team);
                    refreshButtons();
                }
            });
            /* Disable button for current team of the player. */
            if(team.getTeamIdentifier().equals(player.getTeam().getTeamIdentifier())) {
                setEnabled(false);
            }
        }
    }
}
