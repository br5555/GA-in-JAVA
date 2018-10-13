package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;
import java.util.Random;

/**
 * Class implements {@linkplain INeighborhood} and calculates next neighbour
 * from uniform distribution centered root which neighbourhood is calculated
 * 
 * @author Branko
 *
 */
public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {

	/**
	 * ranges of uniform distribution for every variables
	 */
	private double[] deltas;
	/**
	 * Private reference to {@linkplain Random}
	 */
	protected Random rand;

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param deltas
	 */
	public DoubleArrayUnifNeighborhood(double[] deltas) {
		this.deltas = Arrays.copyOf(deltas, deltas.length);
		rand = new Random();
	}

	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution t) {
		DoubleArraySolution temp = t.duplicate();
		for (int i = 0, size = t.values.length; i < size; i++) {
			temp.values[i] += -deltas[i] / 2 + (deltas[i]) * rand.nextDouble();
		}
		temp.updateFitness();
		return temp;
	}

}
