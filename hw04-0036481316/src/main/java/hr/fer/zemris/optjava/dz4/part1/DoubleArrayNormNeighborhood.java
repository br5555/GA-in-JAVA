package hr.fer.zemris.optjava.dz4.part1;

import java.util.Arrays;
import java.util.Random;

/**
 * Class implements {@linkplain INeighborhood} and calculates next neighbour
 * from normal distribution. Mean value is  root which neighbourhood is calculated
 * 
 * @author Branko
 *
 */
public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution>{

	/**
	 * sigmas for every variable
	 */
	private double[] deltas;
	/**
	 * Private reference {@linkplain Random} 
	 */
	protected Random rand;
	
	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param deltas desire sigmas for every variable
	 */
	public DoubleArrayNormNeighborhood(double[] deltas) {
		this.deltas = Arrays.copyOf(deltas, deltas.length);
		rand = new Random();
	}
	
	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution t) {
		DoubleArraySolution temp = t.duplicate();
		for(int i=0, size= t.values.length; i<size; i++){
			temp.values[i] += rand.nextGaussian()* deltas[i]/2;
		}
		return temp;
	}

}
