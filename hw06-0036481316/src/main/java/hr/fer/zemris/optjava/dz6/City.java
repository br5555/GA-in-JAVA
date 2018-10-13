package hr.fer.zemris.optjava.dz6;

/**
 * Class that implements {@linkplain CartesianPoint} and represents simple city.
 * 
 * @author Branko
 *
 */
public class City implements CartesianPoint {

	/**
	 * ID of city
	 */
	private int ID;
	/**
	 * x and y coordinate of city
	 */
	private double x, y;

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param ID
	 *            ID of city
	 * @param x
	 *            x coordinate of city
	 * @param y
	 *            y coordinate of city
	 */
	public City(int ID, double x, double y) {
		super();
		this.ID = ID;
		this.x = x;
		this.y = y;
	}

	/**
	 * Public getter which returns ID of city
	 * 
	 * @return ID of city
	 */
	public int getName() {
		return ID;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("X: ").append(this.getX()).append(", Y: ").append(this.getY());
		return sb.toString();
	}

}
