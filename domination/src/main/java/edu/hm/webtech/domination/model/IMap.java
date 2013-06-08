package edu.hm.webtech.domination.model;

/**
 * Defines methods for a object representing a map in domination.
 *
 * @author Marco Wolff
 */
public interface IMap extends ILocationObject {

    /**
     * @return zoom factor of the map.
     */
    public int getZoomFactor();

    /**
     * Sets the zoom factor of the map.
     *
     * @param zoomFactor The zoom factor.
     */
    public void setZoomFactor(int zoomFactor);
}
