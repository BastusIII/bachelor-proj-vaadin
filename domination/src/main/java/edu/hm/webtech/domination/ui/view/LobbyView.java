package edu.hm.webtech.domination.ui.view;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.*;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;

/**
 * Die Lobby View kommt nach dem erfolgreichen Login eines Spielers.<br />
 * Hier bekommt man Informationen zu laufenden Spielen, kann einem Spiel beitreten und ein neues Spiel erstellen.<br />
 * Von der Lobby View wird man weitergeleitet auf die GameView.
 *
 * @author Sebastian Stumpf
 */
public class LobbyView extends AbstractNavigationView implements Refresher.RefreshListener{

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
        refresher.addListener(this);
        init();
    }

    /**
     * Konstruktor.
     *
     * @param caption Name der View.
     */
    public LobbyView(final String caption) {
        super(CAPTION);
        refresher.addListener(this);
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
                CreateGamePopover createGamePopover = new CreateGamePopover();
                createGamePopover.showRelativeTo(getNavigationBar());
                noGamesAvailable.setVisible(MyVaadinApplication.getLm().getGames().size() <= 0);
            }
        });
        VerticalComponentGroup createGameWrapper = new VerticalComponentGroup();
        createGameWrapper.addComponent(createGame);

        this.base.addComponent(this.gamesContainer);
        this.gamesContainer.addComponent(this.noGamesAvailable);
        this.base.addComponent(createGameWrapper);
		for (IGameManager gameManager : MyVaadinApplication.getLm().getGames()) {
            addGameToContainer(gameManager);
        }

        return base;
    }

    /**
     * Hilfsmethode, die Spielreihe baut.
     *
     * @param gameManager Der Game Manager.
     * @return Die gebaute Component.
     */
    public void addGameToContainer(final IGameManager gameManager) {
        HorizontalComponentGroup gameContainer = new HorizontalComponentGroup();
        Button nameButton = new Button(gameManager.getGame().getName());
        Button detailsButton = new Button("Show Details");
        detailsButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                GameDetailsPopover gameDetailsPopover = new GameDetailsPopover(gameManager,refresher);
                gameDetailsPopover.showRelativeTo(getNavigationBar());
            }
        });
        Button removeButton = new Button("Remove Game");
        removeButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                if(MyVaadinApplication.getLm().removeGame(gameManager)) {
                    gamesContainer.removeComponent(event.getComponent().getParent());
                    noGamesAvailable.setVisible(MyVaadinApplication.getLm().getGames().size() <= 0);
                }
                else {
                    getWindow().showNotification("Error", "Only empty games can be removed!", 2);
                }
            }
        });

        gameContainer.addComponent(nameButton);
        gameContainer.addComponent(detailsButton);
        gameContainer.addComponent(removeButton);
        this.gamesContainer.addComponent(gameContainer);
    }

    @Override
    public void refresh(Refresher refresher) {
        this.gamesContainer.removeAllComponents();
        this.gamesContainer.addComponent(this.noGamesAvailable);
        for (IGameManager gameManager : MyVaadinApplication.getLm().getGames()) {
            addGameToContainer(gameManager);
        }
        gamesContainer.requestRepaintAll();
    }
}
