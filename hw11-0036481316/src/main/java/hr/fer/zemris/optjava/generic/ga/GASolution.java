package hr.fer.zemris.optjava.generic.ga;

/**
 * Class implements {@linkplain Comparable} and represent abstract solution of GA algorithm
 * @author Branko
 *
 * @param <T> generic parameter
 */
public abstract class GASolution<T> implements Comparable<GASolution<T>> {
	/**
	 * data
	 */
	protected T data;
	/**
	 * fitness (quality of solution)
	 */
	public double fitness;

	/**
	 * Public constructor
	 */
	public GASolution() {
	}

	/**
	 * Public getter returns data
	 * @return data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Method creates duplicate from this instance
	 * @return duplicate from this instance
	 */
	public abstract GASolution<T> duplicate();

	@Override
	public int compareTo(GASolution<T> o) {
		return -Double.compare(this.fitness, o.fitness);
	}
	
}
