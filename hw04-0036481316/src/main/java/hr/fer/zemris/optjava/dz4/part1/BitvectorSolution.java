package hr.fer.zemris.optjava.dz4.part1;

import java.util.Arrays;
import java.util.Random;
/**
 *  * Class inherent {@linkplain SingleObjectiveSolution}. Class represent solution
 * expressed in bits
 * 
 * @author Branko
 *
 */
public class BitvectorSolution extends SingleObjectiveSolution{

	/**
	 * values of bits
	 */
	public byte[] bits;
	
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
	public BitvectorSolution(int size,IDecoder decoder, INeighborhood neighborhood, IFunction function,
			IOptAlgorithm algorithm) {
		super(decoder, neighborhood, function, algorithm);
		this.bits =new byte[size];

	}
	
	/**
	 * Public method which returns new variable like this
	 * 
	 * @return new variable like this
	 */
	public BitvectorSolution newLikeThis(){
		BitvectorSolution temp = new BitvectorSolution(bits.length, decoder, neighborhood, function, algorithm);
		temp.bits = Arrays.copyOf(bits, bits.length);
		temp.fitness = this.fitness;
		temp.value = this.value;
		return temp;
				
	}
	
	/**
	 * Public method which returns duplicate of this
	 * 
	 * @return duplicate
	 */
	public BitvectorSolution duplicate(){
		BitvectorSolution temp = new BitvectorSolution(bits.length, decoder, neighborhood, function, algorithm);
		temp.bits = Arrays.copyOf(bits, bits.length);
		temp.fitness = this.fitness;
		temp.value = this.value;
		return temp;
	}
	
	/**
	 * Public function which sets random {@linkplain BitvectorSolution#bits}
	 * 
	 * @param rand
	 *            random seed and instance of {@linkplain Random}

	 */
	public void randomize(Random rand){
			 rand.nextBytes(bits);
		
	}
	
 }
