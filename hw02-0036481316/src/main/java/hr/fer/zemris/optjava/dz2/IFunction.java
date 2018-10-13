package hr.fer.zemris.optjava.dz2;
/**
 * Interface that represent abstract function. Interface contains 
 * important methods for analysing behaviours of function on some domain
 * like dimension of space, function value at specific point in that space,
 * derivation at specific point.
 * 
 * @author Branko
 *
 */
public interface IFunction {
	/**
	 * Public method which returns number of function variables
	 * 
	 * @return number of function variables
	 */
	int numberOfVariables();
	
	/**
	 * Public method which returns value of function at desire point
	 * 
	 * @param point desire point
	 * @return value of function at desire point
	 */
	double functionValue(double[] point);
	
	/**
	 * Public method which computes gradient of function at specific point
	 * 
	 * @param point desire point
	 * @return double vector which represent gradient at specific point
	 */
	double[] gradientOfFunction(double[] point);
}
