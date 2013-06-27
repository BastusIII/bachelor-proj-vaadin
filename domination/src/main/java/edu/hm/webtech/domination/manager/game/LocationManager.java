package edu.hm.webtech.domination.manager.game;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;
import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.model.IPlayer;
import edu.hm.webtech.domination.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Christoph Waldleitner
 */
public class LocationManager implements Refresher.RefreshListener, PositionCallback {

    private static Logger logger = new Logger(LocationManager.class.toString());

    private TouchKitWindow window;

    private Position lastPosition;

    private final LocationManager that;

    public LocationManager() {
        this.that = this;
    }

    @Override
    public void refresh(Refresher refresher) {
        window = MyVaadinApplication.getApp().getMainWindow();
        window.detectCurrentPosition(that);
    }

    @Override
    public void onSuccess(Position position) {
        this.lastPosition = position;
        IPlayer player = (IPlayer) MyVaadinApplication.getApp().getUser();
        if (player != null) {
            player.setGeoCoordinates(position.getLongitude(), position.getLatitude());
            System.out.println("Geo|" + player.getIdentifier() + ": " + position.getLongitude() + " :: " + position.getLatitude());
        }
    }

    @Override
    public void onFailure(int errorCode) {
        logger.infoLog("Location detection failed with code: " + errorCode);
    }

    /**
     * Last detected Position
     *
     * @return Last detected Position
     */
    public Position getPosition() {
        return lastPosition;
    }
}
