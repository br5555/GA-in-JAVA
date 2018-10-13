package hr.fer.zemris.optjava.dz10;

import java.util.Arrays;
import java.util.Random;

/**
 * Class represents genetic algorithm mutation 
 * @author Branko
 *
 */
public class Mutation {
	
	/**
	 * Hyper parameter of mutation
	 */
	private double sigma = 0.2;
	/**
	 * upper bounds
	 */
	private double[] maxs;
	/**
	 * lower bounds
	 */
	private double[] mins;

	/**
	 * Public constructor accepts desire settings
	 * @param maxs desire upper bounds
	 * @param mins desire lower bounds
	 */
	public Mutation(double[] maxs, double[] mins) {
		this.maxs = Arrays.copyOf(maxs, maxs.length);
		this.mins = Arrays.copyOf(mins, mins.length);
	}

	/**
	 * Method which implements mutation 
	 * @param orginal original entity
	 * @return mutate entity
	 */
	public SolutionVector mutate(SolutionVector orginal) {
		double[] values = orginal.getValues();
		Random rand = new Random();

		for (int i = 0; i < values.length; i++) {
			values[i] += sigma * rand.nextGaussian() - sigma / 2;
			
			if (maxs[i] < values[i]) {
				values[i] = maxs[i];
			} else if (mins[i] > values[i]) {
				values[i] = mins[i];
			}

		}

		SolutionVector mutate = new SolutionVector(values);
		return mutate;
	}
}
