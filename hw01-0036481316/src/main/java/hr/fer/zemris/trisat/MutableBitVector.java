package hr.fer.zemris.trisat;

import java.util.Arrays;
/**
 * Class inherent {@linkplain BitVector} and makes binary vector mutable.
 * 
 * @author Branko
 *
 */
public class MutableBitVector extends BitVector {
	/**
	 * private double property which stores quality of solution for specific expression.
	 * How well vector satisfied expression.
	 */
	private double goodness;
	
	/**
	 * Public constructor which stores desire binary vector.
	 * 
	 * @param bits desire binary vector
	 */
	public MutableBitVector(boolean... bits) {
		if(bits == null){
			throw new IllegalArgumentException("Dear user, null is not valid input");
		}
		this.bits = Arrays.copyOf(bits, bits.length);
	}

	/**
	 * Public constructor which makes binary vector from whole number
	 * 
	 * @param n desire whole number
	 */
	public MutableBitVector(int n) {
		this.bits = SATFormula.getBin(n, (int)Math.ceil(Math.log(n)/Math.log(2)));
	}

	/**
	 * Public method changes desire i-th component of vector
	 * 
	 * @param index desire i-th component
	 * @param value desire value of i-th component
	 * @throws IllegalArgumentException if index is negative or index is out of size
	 */
	public void set(int index, boolean value) {
		if(index < 0 || index >= bits.length){
			throw new IllegalArgumentException("Dear user, requested index is out of range");

		}
		bits[index]= value;
	}
	
	/**
	 * Getter method which return {@link #goodness}
	 * 
	 * @return {@link #goodness}
	 */
	public double getGoodness() {
		return goodness;
	}
	
	/**
	 * Setter method which sets {@link #goodness}
	 * 
	 * @param goodness goodness of binary vector for specific expression
	 */
	public void setGoodness(double goodness) {
		this.goodness = goodness;
	}
}