package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.*;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IDominationPoint;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;
import edu.hm.webtech.domination.model.Player;

import java.util.Collection;

/**
 * Ansicht der Spieldetails in einer Popover View (Popup).
 *
 * @author Sebastian Stumpf
 */
public class GameDetailsPopover extends Popover implements Button.ClickListener {

    IGameManager gameManager;
    IPlayer currentPlayer;

    /**
     * Konstruktor.
     *
     * @param gameManager Der Game Manager aus dem Die verwendeten Spielinfos kommen.
     */
    public GameDetailsPopover(final IGameManager gameManager) {

        this.currentPlayer = (IPlayer)MyVaadinApplication.getApp().getUser();
        this.gameManager = gameManager;
        setStyleName("domination");
        setWidth("100%");
        setHeight("65%");
        // build content
        Layout content = new FormLayout();
        Label owner = new Label("<u>Owner</u>: " + gameManager.getGame().getOwner().getIdentifier(), Label.CONTENT_XHTML);
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
        Collection<ITeam> teams = gameManager.getGame().getTeams();
        for (ITeam team : teams) {
            content.addComponent(buildTeamComponent(team));
        }
        content.addComponent(joinButtonWrapper);

        // Decorate with navigation view
        NavigationView navigationViewWrapper = new NavigationView(content);
        navigationViewWrapper.setCaption("Details of " + gameManager.getGame().getName());
        setContent(navigationViewWrapper);

        // Have a close button
        Button close = new Button("close", this);
        navigationViewWrapper.setRightComponent(close);

    }

    /**
     * Der Clicklistener, der das Fenster schließt.
     *
     * @param event Click auf Close.
     */
    public void buttonClick(Button.ClickEvent event) {
        close();
    }

    /**
     * Hilfsmethode, die eine Component mit Team Infos baut.
     *
     * @param team Das Team.
     * @return Die gebaute Component.
     */
    private Component buildTeamComponent(final ITeam team) {
        Layout content = new VerticalComponentGroup();
        Label name = new Label("<u>Team</u>: " + team.getTeamIdentifier(), Label.CONTENT_XHTML);
        Label score = new Label("<u>Score</u>: " + String.valueOf(team.getScore()), Label.CONTENT_XHTML);
        StringBuilder sb = new StringBuilder();
        for (IPlayer player : gameManager.getGame().getPlayers()) {
            if (player.getTeam().equals(team)) {
                sb.append(player.getIdentifier() + " | ");
            }
        }
        if(sb.length() >=3) sb.delete(sb.length() - 3, sb.length() - 1);
        Label players = new Label("<u>Players</u>: " + sb.toString(), Label.CONTENT_XHTML);

        content.addComponent(name);
        content.addComponent(score);
        content.addComponent(players);

        return content;
    }
}
