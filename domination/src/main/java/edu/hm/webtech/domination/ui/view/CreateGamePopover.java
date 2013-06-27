package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.model.GameConfiguration;
import edu.hm.webtech.domination.model.GameType;
import edu.hm.webtech.domination.model.IGameConfiguration;
import edu.hm.webtech.domination.model.IPlayer;

/**
 * Ansicht der Spieldetails in einer Popover View (Popup).
 *
 * @author Sebastian Stumpf
 */
public class CreateGamePopover extends Popover implements Button.ClickListener {

    final IPlayer currentPlayer;

    /**
     * Konstruktor.
     *
     */
    public CreateGamePopover() {

        this.currentPlayer = (IPlayer) MyVaadinApplication.getApp().getUser();
        setStyleName("domination");
        setWidth("100%");
        setHeight("90%");
        // build content

        final IPlayer owner = currentPlayer;

        Layout content = new FormLayout();
        Label ownerLabel = new Label("Qwner: " + owner.getIdentifier());
        Label scoreLimitLabel = new Label("Score Limit: ");
        Label maxPlayersPerTeamLabel = new Label("Maximum Players per Team: ");
        Label gameTypeLabel = new Label("Type of the Game: ");
        Label identifierLabel = new Label("Name of the Game: ");
        final TextField scoreLimitField = new TextField();
        scoreLimitField.setValue(Integer.toString(GameConfiguration.SCORE_LIMIT_DEFAULT));
        final TextField maxPlayersPerTeamField = new TextField();
        maxPlayersPerTeamField.setValue(Integer.toString(GameConfiguration.MAX_PLAYERS_PER_TEAM_DEFAULT));
        final TextField identifierField = new TextField();
        identifierField.setInputPrompt("Name");
        final NativeSelect gameTypeSelect = new NativeSelect();
        for (GameType type : GameType.values()) {
            gameTypeSelect.addItem(type.getName());
        }
        gameTypeSelect.setValue(GameConfiguration.GAME_TYPE_DEFAULT.getName());

        NavigationButton createGame = new NavigationButton("Create game");
        createGame.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                createGame(scoreLimitField,maxPlayersPerTeamField,identifierField,gameTypeSelect,owner);
            }
        });
        identifierField.addShortcutListener(new AbstractField.FocusShortcut(identifierField, ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if(target.equals(identifierField))
                    createGame(scoreLimitField,maxPlayersPerTeamField,identifierField,gameTypeSelect,owner);
            }
        });
        Layout createButtonWrapper = new VerticalComponentGroup();
        createButtonWrapper.addComponent(createGame);

        content.addComponent(ownerLabel);
        content.addComponent(scoreLimitLabel);
        content.addComponent(scoreLimitField);
        content.addComponent(maxPlayersPerTeamLabel);
        content.addComponent(maxPlayersPerTeamField);
        content.addComponent(gameTypeLabel);
        content.addComponent(gameTypeSelect);
        content.addComponent(identifierLabel);
        content.addComponent(identifierField);
        content.addComponent(createButtonWrapper);

        // Decorate with navigation view
        NavigationView navigationViewWrapper = new NavigationView(content);
        navigationViewWrapper.setCaption("Create Game");
        setContent(navigationViewWrapper);

        // Have a close button
        Button close = new Button("close", this);
        navigationViewWrapper.setRightComponent(close);

    }

    private void createGame(TextField scoreLimitField, TextField maxPlayersPerTeamField, TextField identifierField, NativeSelect gameTypeSelect, IPlayer owner){
        int scoreLimit = 0;
        int maxPlayersPerTeam = 0;
        String identifier = null;
        try {
            scoreLimit = Integer.parseInt((String) scoreLimitField.getValue());
            maxPlayersPerTeam = Integer.parseInt((String) maxPlayersPerTeamField.getValue());
            identifier = (String) identifierField.getValue();
            if (identifier == "") {
                getWindow().showNotification("Error", "Enter a name for your game!!", 2);
            }
            else {
                GameType gameType = GameType.getType((String) gameTypeSelect.getValue());

                IGameConfiguration config = new GameConfiguration(scoreLimit, -1, maxPlayersPerTeam, -1, owner, gameType, identifier);
                if (MyVaadinApplication.getLm().createGame(config) == null) {
                    getWindow().showNotification("Error", "You either have already created a game or the name you chose is in use!", 2);
                } else {
                    getParent().setContent(new LobbyView());
                    close();
                }
            }
        } catch (NumberFormatException e) {
            getWindow().showNotification("Error", "Please enter numbers in score and player field!", 2);
        }
    }

    /**
     * Der Clicklistener, der das Fenster schließt.
     *
     * @param event Click auf Close.
     */
    public void buttonClick(Button.ClickEvent event) {
        close();
    }
}
