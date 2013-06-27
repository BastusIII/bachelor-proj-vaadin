package edu.hm.webtech.domination.manager.game;

import com.github.wolfie.refresher.Refresher;

/**
 * Created with IntelliJ IDEA.
 * User: Christoph
 * Date: 27.06.13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class RefreshManager {

    private final Refresher refresher;

    public RefreshManager(){
        refresher = new Refresher();
        refresher.setRefreshInterval(2000);
        refresher.addListener(new LocationManager());
    }

    public Refresher getRefresher(){
        return refresher;
    }

}
