package edu.hm.webtech.domination.ui.view;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.ui.component.TeamDetails;

import java.util.Collection;

/**
 * Ansicht der Spieldetails in einer Popover View (Popup).
 *
 * @author Sebastian Stumpf
 */
public class GameDetailsPopover extends Popover implements Button.ClickListener, Refresher.RefreshListener {

    IGameManager gameManager;
    IPlayer currentPlayer;

    /**
     * Konstruktor.
     *
     * @param gameManager Der Game Manager aus dem Die verwendeten Spielinfos kommen.
     */
    public GameDetailsPopover(final IGameManager gameManager, Refresher refresher) {

        this.currentPlayer = (IPlayer)MyVaadinApplication.getApp().getUser();
        this.gameManager = gameManager;
        refresher.addListener(this);
        setStyleName("domination");
        setWidth("100%");
        setHeight("90%");
        setContent(drawContent());

    }

    private NavigationView drawContent(){
        Layout content = new FormLayout();
        Label owner = new Label("<u>Owner</u>: " + gameManager.getGame().getOwner().getIdentifier(), Label.CONTENT_XHTML);
        Label map = new Label("<u>Map</u>: " + gameManager.getGame().getGameConfiguration().getGameType().getName(), Label.CONTENT_XHTML);
        NavigationButton join = new NavigationButton("Join this game");
        join.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                gameManager.joinGame(currentPlayer);
                getParent().setContent(new MainTabView(gameManager));
                close();
            }
        });
        Layout joinButtonWrapper = new VerticalComponentGroup();
        joinButtonWrapper.addComponent(join);

        content.addComponent(owner);
        content.addComponent(map);
        Collection<ITeam> teams = gameManager.getGame().getTeams();
        for (ITeam team : teams) {
            content.addComponent(new TeamDetails(gameManager.getGame(),team));
        }
        content.addComponent(joinButtonWrapper);
        // Decorate with navigation view
        NavigationView navigationViewWrapper = new NavigationView(content);
        navigationViewWrapper.setCaption("Details of " + gameManager.getGame().getName());
        // Have a close button
        Button close = new Button("close", this);
        navigationViewWrapper.setRightComponent(close);
        return navigationViewWrapper;
    }

    /**
     * Der Clicklistener, der das Fenster schließt.
     *
     * @param event Click auf Close.
     */
    public void buttonClick(Button.ClickEvent event) {
        close();
    }

    @Override
    public void refresh(Refresher refresher) {
        setContent(drawContent());
        requestRepaintAll();
    }
}
