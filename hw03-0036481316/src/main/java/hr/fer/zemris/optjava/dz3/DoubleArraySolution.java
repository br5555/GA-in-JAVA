package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;
import java.util.Random;

/**
 * Class inherent {@linkplain SingleObjectiveSolution}. Class represent solution
 * expressed in decimal numbers
 * 
 * @author Branko
 *
 */
public class DoubleArraySolution extends SingleObjectiveSolution {

	/**
	 * Private array of values
	 */
	public double[] values;

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param size
	 *            size of solution
	 * @param decoder
	 *            reference to {@linkplain IDecoder}
	 * @param neighborhood
	 *            reference to {@linkplain INeighborhood}
	 * @param function
	 *            reference to {@linkplain IFunction}
	 * @param algorithm
	 *            reference to {@linkplain IOptAlgorithm}
	 */
	public DoubleArraySolution(int size, IDecoder decoder, INeighborhood neighborhood, IFunction function,
			IOptAlgorithm algorithm) {
		super(decoder, neighborhood, function, algorithm);
		values = new double[size];
		this.value = function.valueAt(values);
		this.fitness = this.value;
	}

	/**
	 * Public method which returns new variable like this
	 * 
	 * @return new variable like this
	 */
	public DoubleArraySolution newLikeThis() {
		DoubleArraySolution temp = new DoubleArraySolution(values.length, decoder, neighborhood, function, algorithm);
		return temp;
	}

	/**
	 * Public method which returns duplicate of this
	 * 
	 * @return duplicate
	 */
	public DoubleArraySolution duplicate() {
		DoubleArraySolution temp = new DoubleArraySolution(values.length, decoder, neighborhood, function, algorithm);
		temp.fitness = this.fitness;
		temp.value = this.value;
		temp.values = Arrays.copyOf(values, values.length);
		return temp;
	}

	/**
	 * Public function which sets random {@linkplain DoubleArraySolution#values}
	 * 
	 * @param rand
	 *            random seed and instance of {@linkplain Random}
	 * @param lower
	 *            lower limits of variables
	 * @param upper
	 *            upper limits of variables
	 */
	public void randomize(Random rand, double[] lower, double[] upper) {
		if (lower.length != upper.length || lower.length != values.length) {
			throw new IllegalArgumentException("Size of upper and lower bounderies must be the same length");
		}
		for (int i = 0, size = values.length; i < size; i++) {
			values[i] = lower[i] + (upper[i] - lower[i]) * rand.nextDouble();
		}
		updateFitness();
	}
	
	/**
	 * Public method for calculating fitness
	 */
	public void updateFitness(){
		fitness = function.valueAt(values);
		this.value = fitness;
	}


}
