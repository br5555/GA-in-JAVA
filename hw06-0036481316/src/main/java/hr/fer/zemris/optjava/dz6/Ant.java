package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;

/**
 * Class implements {@linkplain Comparable} and represent a simple ant.
 * 
 * @author Branko
 *
 */
public class Ant implements Comparable<Ant> {

	/**
	 * index of starting city
	 */
	private int startIndex;
	/**
	 * List of cities that ant saw
	 */
	protected ArrayList<Integer> cities;
	/**
	 * Distance that ant walked
	 */
	protected double distance;

	/**
	 * Public constructor accepts desire settings.
	 * 
	 * @param startIndex
	 *            index of starting city
	 */
	public Ant(int startIndex) {
		this.startIndex = startIndex;
		cities = new ArrayList<>();
		cities.add(startIndex);
		distance = 0;
	}

	@Override
	public int compareTo(Ant o) {
		if (!(o instanceof Ant)) {
			throw new IllegalArgumentException("Object must be instanceof Ant");
		}
		Ant ant2 = (Ant) o;
		if (this.distance < ant2.distance) {
			return 1;
		} else if (this.distance == ant2.distance) {
			return 0;
		}
		return -1;
	}

	/**
	 * Method creates duplicate of {@linkplain Ant}
	 * 
	 * @return duplicate of {@linkplain Ant}
	 */
	public Ant duplicate() {
		Ant temp = new Ant(this.startIndex);
		temp.cities = new ArrayList<>(this.cities);
		temp.distance = this.distance;
		return temp;
	}

	/**
	 * Method reset memory of ant
	 */
	public void resetCities() {
		cities = new ArrayList<>();
		distance = 0;

	}

	/**
	 * Method adds city that ant saw
	 * 
	 * @param i
	 *            index of city that ant just saw
	 */
	public void addCity(int i) {
		cities.add(i);
	}

	/**
	 * Public getter which returns cities that ant saw
	 * 
	 * @return
	 */
	public ArrayList<Integer> getCities() {
		return cities;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0, size = cities.size(); i < size; i++) {
			sb.append(cities.get(i));
			if (i != (size - 1)) {
				sb.append(", ");
			}
		}
		sb.append(" ]");
		return sb.toString();

	}

	/**
	 * Public setter which sets ants starting city
	 * 
	 * @param startIndex
	 *            index of ants starting city
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
		cities.add(startIndex);
	}

	/**
	 * Public method which adds distance that ant just passed
	 * 
	 * @param dist
	 *            distance that ant just passed
	 */
	public void addDistance(double dist) {
		distance += dist;
	}

	/**
	 * Public getter which returns distance that ant walked
	 * 
	 * @return distance that ant walked
	 */
	public double getDistance() {
		return distance;
	}

}
