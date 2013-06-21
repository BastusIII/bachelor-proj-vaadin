package edu.hm.webtech.domination.model;

/**
 * <h1>DominationPoint ist das Datenobjekt eines zu erobernden Punktes auf dem
 * Map.</h1>
 * 
 * @author Sebastian Stumpf
 */
public class DominationPoint extends LocationObject implements IDominationPoint {

	/**
	 * Radius, in dem man sich befinden muss, um zu erobern in Metern.
	 */
	private double radius;
	/**
	 * Der Stand der Eroberung in Prozent. 0<=progress<=100;
	 */
	private int progress;
	/**
	 * Das gerade erobernde Team. Kann null sein.
	 */
	private ITeam capturingTeam;
	/**
	 * Das gerade dominierende Team. Kann null sein.
	 */
	private ITeam dominatingTeam;

	/**
	 * Konstruktor.
	 * 
	 * @param longitude
	 *            Längengrad.
	 * @param latitude
	 *            Breitengrad.
	 * @param radius
	 *            Der Radius.
	 * @param progress
	 *            Der Stand der Eroberung in Prozent. 0<=progress<=100.
	 */
	public DominationPoint(final double longitude, final double latitude,
			final double radius, final int progress) {
		super(longitude, latitude);
		this.radius = radius;
		this.progress = progress;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param longitude
	 *            Längengrad.
	 * @param latitude
	 *            Breitengrad.
	 * @param radius
	 *            Der Radius.
	 */
	public DominationPoint(final double longitude, final double latitude,
			final double radius) {
		this(longitude, latitude, radius, 0);
	}

	/**
	 * Copy constructor, deep copying given {@link IDominationPoint}.
	 * 
	 * @author Marco Wolff
	 * @param player
	 *            {@link IDominationPoint} object that will be copied.
	 */
	public DominationPoint(IDominationPoint dominationPoint) {
		// Location will be set to real values after object was checked
		// for 'null'.
		super(0.0d, 0.0d);
		if (dominationPoint == null) {
			throw new IllegalArgumentException(
					"Domination point may not be null!");
		}
		setGeoCoordinates(dominationPoint.getLongitude(),
				dominationPoint.getLatitude());
		this.radius = dominationPoint.getRadius();
		this.progress = dominationPoint.getCaptureProgress();
		// It is possible that a domination point is a 'virgin', meaning it has
		// no capturing or owning team either. So check for null!
		ITeam dominationPointsCapturingTeam = dominationPoint.getCaptureTeam();
		if (dominationPointsCapturingTeam == null) {
			this.capturingTeam = null;
		} else {
			this.capturingTeam = new Team(dominationPointsCapturingTeam);
		}
		ITeam dominationPointsDominatingTeam = dominationPoint.getOwnerTeam();
		if (dominationPointsDominatingTeam == null) {
			this.dominatingTeam = null;
		} else {
			this.dominatingTeam = new Team(dominationPointsDominatingTeam);
		}
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public void setRadius(final double radius) {
		this.radius = radius;
	}

	@Override
	public ITeam getCaptureTeam() {
		return capturingTeam;
	}

	@Override
	public void setCaptureTeam(final ITeam captureTeam) {
		this.capturingTeam = captureTeam;
	}

	@Override
	public ITeam getOwnerTeam() {
		return this.dominatingTeam;
	}

	@Override
	public void setOwnerTeam(final ITeam ownerTeam) {
		this.dominatingTeam = ownerTeam;
	}

	@Override
	public int getCaptureProgress() {
		return progress;
	}

	@Override
	public void addCaptureProgress(final int captureProgress) {
		this.progress += captureProgress;
	}

	@Override
	public void resetCaptureProgress() {
		this.progress = 0;
	}
}
