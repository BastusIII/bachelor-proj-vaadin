package edu.hm.webtech.domination.ui.component;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import edu.hm.webtech.domination.model.IGame;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Detailed display of a team.
 *
 * @author Sebastian Josef Stumpf, Felix Schramm
 */
public class TeamDetails extends CustomComponent {

    /**
     * The team that is displayed detailed.
     */
    private final ITeam team;

    /**
     * The game used to get meta information.
     */
    private final IGame game;

    public TeamDetails(final IGame game, final ITeam team) {
        this.team = team;
        this.game = game;
        initializeComponents();
    }

    /**
     * Sets the components used for the details display.
     */
    private void initializeComponents() {
        Layout content = new VerticalComponentGroup();

        Label name = new Label("<u>Team</u>: " + team.getTeamIdentifier(), Label.CONTENT_XHTML);
        Label score = new Label("<u>Score</u>: " + String.valueOf(team.getScore()), Label.CONTENT_XHTML);
        StringBuilder sb = new StringBuilder();
        for (IPlayer player : game.getPlayers()) {
            if (player.getTeam().equals(team)) {
                sb.append(player.getIdentifier() + " | ");
            }
        }
        sb.delete(sb.length() - 3, sb.length() - 1);
        Label players = new Label("<u>Players</u>: " + sb.toString(), Label.CONTENT_XHTML);

        content.addComponent(name);
        content.addComponent(score);
        content.addComponent(players);

        /* Setting absolute height, because automatic height detection for VerticalComponentGroup is broken. */
        setHeight("76px");


        setCompositionRoot(content);
    }
}
