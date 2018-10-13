package hr.fer.zemris.optjava.dz9;

/**
 * Interface that represent function whit his basic methods
 * @author Branko
 *
 */
public interface IFunction {

	/**
	 * Method calculates value of function at desire point
	 * @param point desire point 
	 * @return value of function at desire point
	 */
	double[] valueAt(double[] point);
	
	/**
	 * Public getter gets dimension of input
	 * @return dimension of input
	 */
	int dimOdInput();
	
	/**
	 * Public getter gets dimension of output
	 * @return dimension of output
	 */
	int dimOfOutput();
}
