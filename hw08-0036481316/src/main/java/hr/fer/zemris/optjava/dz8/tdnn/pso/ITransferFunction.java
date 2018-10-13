package hr.fer.zemris.optjava.dz8.tdnn.pso;
/**
 * Interface which represent Transfer function of hidden layer
 * 
 * @author Branko
 *
 */
public interface ITransferFunction {

	/**
	 * Method which evaluates value of function at desire point
	 * 
	 * @param point
	 *            desire point
	 * @return value of function at desire point
	 */
	double valueAt(double point);
}