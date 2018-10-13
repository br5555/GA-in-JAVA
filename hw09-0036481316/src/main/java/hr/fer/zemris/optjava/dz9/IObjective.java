package hr.fer.zemris.optjava.dz9;

/**
 * Interface represent constraints for some function
 * @author Branko
 *
 */
public interface IObjective {

	/**
	 * Public getter gets dimension of inputs
	 * @return dimension of inputs
	 */
	int numberOfInputs();
	
	/**
	 * Method checks if input is valid
	 * @param input desire input
	 * @return true if is valid, false otherwise
	 */
	boolean isValid(double[] input);
}
