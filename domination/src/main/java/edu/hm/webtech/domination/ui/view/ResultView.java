package edu.hm.webtech.domination.ui.view;

import com.vaadin.ui.*;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.game.IGameManager;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.model.ITeam;

/**
 * Created with IntelliJ IDEA.
 * User: Christoph Waldleitner
 */
public class ResultView extends AbsoluteLayout {

    private final ITeam winningTeam;
    private final IPlayer player;

    public ResultView(IGameManager gameManager){
        this.winningTeam = gameManager.getWinnerTeam();
        this.player = (IPlayer) MyVaadinApplication.getApp().getUser();
        addComponent(initializeComponent());
    }

    private Component initializeComponent() {
        VerticalLayout vcg = new VerticalLayout();
        //vcg.addStyleName("resultView");
        Label resultLabel;
        if(player.getTeam().getTeamIdentifier()==winningTeam.getTeamIdentifier()){
            resultLabel = new Label("You Win");
        }
        else{
            resultLabel = new Label("You Lost");
        }
        resultLabel.addStyleName("resultLabel");
        vcg.setSizeFull();
        vcg.addComponent(resultLabel);
        vcg.setComponentAlignment(resultLabel, Alignment.MIDDLE_CENTER);

        return vcg;
    }
}
