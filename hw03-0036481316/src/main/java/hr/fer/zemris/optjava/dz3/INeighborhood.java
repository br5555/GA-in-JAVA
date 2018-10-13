package hr.fer.zemris.optjava.dz3;

/**
 * Interface that represent generalisation function for finding neighbourhood of 
 * desire point
 * 
 * @author Branko
 *
 * @param <T> class whose neighbourhood function needs to find.
 */
public interface INeighborhood<T> {

	/**
	 * Public method for finding neighbourhood of desire instance of T
	 * 
	 * @param t desire instance of T
	 * @return neighbourhood of desire instance of T
	 */
	T randomNeighbor(T t);
}
