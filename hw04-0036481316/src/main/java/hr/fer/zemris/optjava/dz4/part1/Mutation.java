package hr.fer.zemris.optjava.dz4.part1;

import java.util.Random;
/**
 * Class represents generalisation of mutation in geentic algorithm.
 * 
 * @author Branko
 *
 */
public class Mutation {
	
	/**
	 * Public method which mutates desire unit from normal distribution.
	 * 
	 * @param unit desire unit which will be mutate
	 * @param standardDeviation standard deviation of normal distribution.
	 */
	public void mutate(double[] unit, double standardDeviation ){
		Random rand = new Random();
		
		for(int i = 0, size = unit.length; i< size; i++){
			unit[i] += rand.nextGaussian()*standardDeviation;
		}
	}
}
