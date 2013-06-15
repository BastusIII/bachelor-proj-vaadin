package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Component;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.manager.lobby.LobbyManager;
import com.vaadin.ui.Button;
import edu.hm.webtech.domination.manager.session.SessionManager;

/**
 * Die Lobby View kommt nach dem erfolgreichen Login eines Spielers.<br />
 * Hier bekommt man Informationen zu laufenden Spielen, kann einem Spiel beitreten und ein neues Spiel erstellen.<br />
 * Von der Lobby View wird man weitergeleitet auf die GameView.
 *
 * @author Sebastian Stumpf
 */
public class LobbyView extends AbstractNavigationView {

    LobbyManager lobbyManager;
    SessionManager sessionManager;
    VerticalComponentGroup base;
    VerticalComponentGroup gamesContainer;
    Button noGamesAvailable;

    public LobbyView(final String caption, SessionManager sessionManager) {
        super(caption);
        this.sessionManager = sessionManager;
        this.lobbyManager = new LobbyManager();
        init();
    }

    @Override
    protected Component initializeComponent() {

        this.base = new VerticalComponentGroup();
        this.gamesContainer = new VerticalComponentGroup();
        this.base.addComponent(gamesContainer);
        this.noGamesAvailable = new Button("Currently no games available.");

        noGamesAvailable.setVisible(this.lobbyManager.getGames().size() <= 0);
        this.gamesContainer.addComponent(noGamesAvailable);

        Button createGame = new Button("Create new Game");
        createGame.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                IGameManager gameManager = lobbyManager.createGame(null);
                gamesContainer.addComponent(createGameTab(gameManager, "Game" + lobbyManager.getGames().size()));
                noGamesAvailable.setVisible(lobbyManager.getGames().size() <= 0);
            }
        });
        base.addComponent(createGame);

        int gameCounter = 0;
        for(IGameManager gameManager: this.lobbyManager.getGames()) {
            Component gameTab = createGameTab(gameManager, "Game " + ++gameCounter);
            this.gamesContainer.addComponent(gameTab);
        }

        return base;
    }

    private Component createGameTab(final IGameManager gameManager, final String name) {
        VerticalComponentGroup gameContainer = new VerticalComponentGroup();

        HorizontalComponentGroup gameHead = new HorizontalComponentGroup();
        Button nameButton = new Button(name);
        Button removeButton = new Button("Remove Game");
        removeButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                lobbyManager.removeGame(gameManager);
                gamesContainer.removeComponent(event.getComponent().getParent().getParent());
                noGamesAvailable.setVisible(lobbyManager.getGames().size() <= 0);
            }
        });
        gameHead.addComponent(nameButton);
        gameHead.addComponent(removeButton);
        gameContainer.addComponent(gameHead);

        return gameContainer;
    }
}
