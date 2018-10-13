package hr.fer.zemris.optjava.dz8.tdnn.pso;
/**
 * Interface that represents function
 * @author Branko
 *
 * @param <T> genetic parameter
 */
public interface IFunction<T> {

	/**
	 * Method calculates function value 
	 * @param values desire value
	 * @return function value at desire point
	 */
	double valueAt(T[] values);
	/**
	 * Method calculates function value 
	 * @param values desire value
	 * @return function value at desire point
	 */
	double valueAt(double[] values);

}
