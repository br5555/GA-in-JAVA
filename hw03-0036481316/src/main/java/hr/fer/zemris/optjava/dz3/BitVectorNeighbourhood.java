package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;
import java.util.Random;
/**
 * Class implements {@linkplain INeighborhood} and calculates next neighbour.
 * 
 * @author Branko
 *
 */
public class BitVectorNeighbourhood implements INeighborhood<BitvectorSolution>{

	/**
	 * ranges of uniform distribution for every variables
	 */
	private int[] deltas;
	/**
	 * Private reference {@linkplain Random} 
	 */
	protected Random rand;
	
	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param deltas desire neighbourhood range for every variable
	 */
	public BitVectorNeighbourhood(int[] deltas) {
		this.deltas = Arrays.copyOf(deltas, deltas.length);
		rand = new Random();
	}
	
	@Override
	public BitvectorSolution randomNeighbor(BitvectorSolution t) {
		Random rand = new Random();
		boolean change = false;
		BitvectorSolution temp = t.duplicate();
		for(int i = 0,limit =temp.bits.length ; i<limit; i++){
			if(rand.nextDouble()<0.25){
				temp.bits[i]  +=-deltas[i]/2+ rand.nextInt(deltas[i]);
				change = true;
			}
		}
		if(!change){
			int index = rand.nextInt(temp.bits.length);
			temp.bits[index]+= -deltas[index] / 2 + rand.nextInt(deltas[index]);
		}
		temp.updateFitness();
		return temp;
	}

}
