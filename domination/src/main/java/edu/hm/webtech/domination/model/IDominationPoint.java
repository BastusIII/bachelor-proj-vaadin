package edu.hm.webtech.domination.model;

/**
 * Defines methods for a object representing a domination point in domination.
 *
 * @author Marco Wolff
 */
public interface IDominationPoint extends ILocationObject {

    /**
     * @return radius of the domination point.
     */
    public double getRadius();

    /**
     * Sets the radius of the domination point.
     *
     * @param radius of the domination point.
     */
    public void setRadius(double radius);

    /**
     * @return {@link ITeam} currently capturing the domination point.
     */
    public ITeam getCaptureTeam();

    /**
     * Sets the {@link ITeam} which is currently capturing the domination point.
     *
     * @param captureTeam {@link ITeam} currently capturing the domination point.
     */
    public void setCaptureTeam(ITeam captureTeam);

    /**
     * @return {@link ITeam} currently owning the domination point.
     */
    public ITeam getOwnerTeam();

    /**
     * Sets the {@link ITeam} which is currently owning the domination point.
     *
     * @param ownerTeam {@link ITeam} currently owning the domination point.
     */
    public void setOwnerTeam(ITeam ownerTeam);

    /**
     * @return current capture progress of the domination point.
     */
    public int getCaptureProgress();

    /**
     * Adds given value to the capture progress of the domination point based on
     * currently capturing team and owning team.
     *
     * @param captureProgress value which will be added to the capture progress.
     */
    public void addCaptureProgress(int captureProgress);

    /**
     * Resets the capture progress of the domination point.
     */
    public void resetCaptureProgress();
}
