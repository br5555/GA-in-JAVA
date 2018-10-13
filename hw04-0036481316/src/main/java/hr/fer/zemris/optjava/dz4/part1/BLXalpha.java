package hr.fer.zemris.optjava.dz4.part1;

import java.util.Arrays;
import java.util.Random;
/**
 * Class implements BLX alpha crossover. Class implements {@linkplain Crossover}.
 * @author Branko
 *
 */
public class BLXalpha implements Crossover{

	/**
	 * Private double hyperparameter for crossover regularization 
	 */
	private double alpha;
	/**
	 * Private reference of {@linkplain Random}
	 */
	private Random rand;
	
	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param alpha desire hyperparameter
	 */
	public BLXalpha( double alpha) {
		
		this.alpha = alpha;
		rand = new Random();
	}
	
	@Override
	public DoubleArraySolution cross(DoubleArraySolution parent1, DoubleArraySolution parent2) {
		double[] parent1Dob = parent1.values;
		double[] parent2Dob = parent2.values;
		double[] child = new double[parent1Dob.length];
		
		if(parent1Dob.length != parent2Dob.length){
			throw new IllegalArgumentException("Paretns aren not equal");
		}
		for(int i = 0, size = parent1Dob.length ; i < size; i++){
			double max = Math.max(parent1Dob[i], parent2Dob[i]);
			double min = Math.min(parent1Dob[i], parent2Dob[i]);

			child[i] = min - (max-min)*alpha + rand.nextDouble()*((max-min)*(2*alpha+1));
		}
		DoubleArraySolution childSol = parent1.newLikeThis();
		childSol.values = Arrays.copyOf(child, child.length);
		return childSol;
	}

}
