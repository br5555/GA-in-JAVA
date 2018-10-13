package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

/**
 * Class implements binary vector with additional methods which simplify
 * handling with binary vector.
 *  
 * @author Branko
 *
 */
public class BitVector {
	
	/**
	 * Protected boolean array which stores components of binary vector
	 */
	protected boolean[] bits;

	/**
	 * Public constructor which makes binary vector from input arguments.
	 *  
	 * @param rand instance of class {@linkplain Random}
	 * @param numberOfBits desire size of binary vector
	 */
	public BitVector(Random rand, int numberOfBits) {
		bits = SATFormula.getBin(rand.nextInt(), numberOfBits);
		}

	/**
	 * Public constructor which stores desire binary vector.
	 * 
	 * @param bits desire binary vector.
	 */
	public BitVector(boolean ... bits) {
		if(bits == null){
			throw new IllegalArgumentException("Dear user, null is not valid input");
		}
		this.bits = Arrays.copyOf(bits, bits.length);
	}

	/**
	 * Public constructor which makes binary vector from desire whole number.
	 * 
	 * @param n desire whole number
	 */
	public BitVector(int n) {
		this.bits = SATFormula.getBin(n, (int)Math.ceil(Math.log(n)/Math.log(2)));
	}

	/**
	 * Public method which return i-th component of binary vector
	 * 
	 * @param index desire i-th component
	 * @return i-th component of vector
	 * @throws IllegalArgumentException if number is negative or of of size.
	 */
	public boolean get(int index) {
		if(index<0 || index >= bits.length){
			throw new IllegalArgumentException("Dear user, requested index does not exists");
		}
		return bits[index];
	}

	/**
	 * Public method returns size of binary vector
	 * 
	 * @return return size of binary vector
	 */
	public int getSize() {
		return bits.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(bits.length);
		for(int i =0, size = bits.length; i< size; i++){
			if(bits[i]){
				sb.append("1");
			}else{
				sb.append("0");
			}
		}
		return sb.toString();
	}

	/**
	 * Public method makes mutable {@linkplain MutableBitVector} copy of binary vector
	 * 
	 * @return instance of {@linkplain MutableBitVector}
	 */
	public MutableBitVector copy() {
		return new MutableBitVector(bits);
	}
}
