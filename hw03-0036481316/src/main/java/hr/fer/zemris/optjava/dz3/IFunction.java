package hr.fer.zemris.optjava.dz3;

/**
 * Interface that represent function and has generalisation method of every function.
 * 
 * @author Branko
 *
 */
public interface IFunction {

	/**
	 * Public method which calculates value of function at desire point.
	 * 
	 * @param array desire point
	 * @return value of function at desire point
	 */
	double valueAt(double[] array);

	int numberOfVariables();
}
