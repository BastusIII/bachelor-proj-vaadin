package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.GameConfiguration;
import edu.hm.webtech.domination.model.GameType;
import edu.hm.webtech.domination.model.IGameConfiguration;
import edu.hm.webtech.domination.model.IPlayer;

/**
 * Die Lobby View kommt nach dem erfolgreichen Login eines Spielers.<br />
 * Hier bekommt man Informationen zu laufenden Spielen, kann einem Spiel beitreten und ein neues Spiel erstellen.<br />
 * Von der Lobby View wird man weitergeleitet auf die GameView.
 *
 * @author Sebastian Stumpf
 */
public class LobbyView extends AbstractNavigationView {

    VerticalLayout base;
    VerticalComponentGroup gamesContainer;
    Label noGamesAvailable;

    /**
     * The lobby views default caption.
     */
    private static final String CAPTION = "Lobby";

    /**
     * LobbyView with default caption.
     */
    public LobbyView() {
        super(CAPTION);
        init();
    }

    /**
     * Konstruktor.
     *
     * @param caption Name der View.
     */
    public LobbyView(final String caption) {
        super(CAPTION);
        init();
    }

    @Override
    protected Component initializeComponent() {

        this.base = new VerticalLayout();
        this.gamesContainer = new VerticalComponentGroup();
        this.noGamesAvailable = new Label("Currently no games available.");
        noGamesAvailable.setVisible(MyVaadinApplication.getLm().getGames().size() <= 0);
        NavigationButton createGame = new NavigationButton("Create new Game");
        createGame.addListener(new NavigationButton.ClickListener() {
            @Override
            public void buttonClick(final NavigationButton.ClickEvent event) {
                // TODO: Sebastian Stumpf -> CreateCustomGame View mit personalisierten parametern (Center, dps, maxGameScore etc)
            	// TODO: Requirement implemented. Now it's your turn. ;)
                IGameConfiguration gameConfiguration = new GameConfiguration(-1,-1,-1,-1,-1,(IPlayer)MyVaadinApplication.getApp().getUser(), GameType.HM_BACKYARD_DUMMY, "Game " + MyVaadinApplication.getLm().getMaxGameCounter());
                IGameManager gameManager = MyVaadinApplication.getLm().createGame(gameConfiguration);
                gamesContainer.addComponent(buildGameContainer(gameManager));
                noGamesAvailable.setVisible(MyVaadinApplication.getLm().getGames().size() <= 0);
            }
        });
        VerticalComponentGroup createGameWrapper = new VerticalComponentGroup();
        createGameWrapper.addComponent(createGame);

        this.base.addComponent(this.gamesContainer);
        this.gamesContainer.addComponent(this.noGamesAvailable);
        this.base.addComponent(createGameWrapper);
		for (IGameManager gameManager : MyVaadinApplication.getLm().getGames()) {
            Component gameTab = buildGameContainer(gameManager);
            this.gamesContainer.addComponent(gameTab);
        }

        return base;
    }

    /**
     * Hilfsmethode, die Spielreihe baut.
     *
     * @param gameManager Der Game Manager.
     * @return Die gebaute Component.
     */
    private Component buildGameContainer(final IGameManager gameManager) {
        HorizontalComponentGroup gameContainer = new HorizontalComponentGroup();
        Button nameButton = new Button(gameManager.getGame().getName());
        Button detailsButton = new Button("Show Details");
        detailsButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                GameDetailsPopover gameDetailsPopover = new GameDetailsPopover(getWindow(), gameManager);
                gameDetailsPopover.showRelativeTo(getNavigationBar());
            }
        });
        Button removeButton = new Button("Remove Game");
        removeButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                MyVaadinApplication.getLm().removeGame(gameManager);
                gamesContainer.removeComponent(event.getComponent().getParent());
                noGamesAvailable.setVisible(MyVaadinApplication.getLm().getGames().size() <= 0);
            }
        });

        gameContainer.addComponent(nameButton);
        gameContainer.addComponent(detailsButton);
        gameContainer.addComponent(removeButton);

        return gameContainer;
    }
}
