package hr.fer.zemris.optjava.dz10;

/**
 * Class represent constraint on functions and implements
 * {@linkplain IObjective}
 * 
 * @author Branko
 *
 */
public class Objective1 implements IObjective {

	/**
	 * dimension of inputs
	 */
	private int numberOfInputs = 2;
	/**
	 * array of boundaries
	 */
	private double[] boundaries;
	/**
	 * array which contains dimension of input for all functions
	 */
	private int[] indexOfDimensions;
	/**
	 * Number of constraints for dimension
	 */
	private int[] numOfConstriantsForDimension;
	/**
	 * comparison operators for every constraint
	 */
	private String[] operators;

	@Override
	public int numberOfInputs() {
		return numberOfInputs;
	}

	/**
	 * Method which returns number of constraints for desire dimension
	 * 
	 * @param index
	 *            desire dimension
	 * @return number of constraints for desire dimension
	 */
	public int getNumOfConstr(int index) {
		return numOfConstriantsForDimension[index];
	}

	@Override
	public boolean isValid(double[] input) {
		int count = 0;
		for (int index : indexOfDimensions) {

			int numOfCon = getNumOfConstr(index);

			for (int i = 0; i < numOfCon; i++) {

				if (operators[count + i].equals(">=")) {
					if (input[index] < boundaries[count + i]) {
						return false;
					}
				} else if (operators[count + i].equals(">")) {
					if (input[index] <= boundaries[count + i]) {
						return false;
					}
				} else if (operators[count + i].equals("<=")) {
					if (input[index] > boundaries[count + i]) {
						return false;
					}
				} else if (operators[count + i].equals("<")) {
					if (input[index] >= boundaries[count + i]) {
						return false;
					}

				} else if (operators[count + i].equals("==")) {
					if (input[index] == boundaries[count + i]) {
						return false;
					}
				} else {
					throw new IllegalArgumentException("Operator does not exists");
				}

			}

			count += numOfCon;

		}
		return true;
	}

}
