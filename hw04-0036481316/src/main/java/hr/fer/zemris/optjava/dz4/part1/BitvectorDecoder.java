package hr.fer.zemris.optjava.dz4.part1;


import java.util.Arrays;
/**
 * Class represents bits decoder which decodes bits values into double value.
 * Class implements {@linkplain IDecoder}.
 *  
 * @author Branko
 *
 */
abstract class BitvectorDecoder implements IDecoder<BitvectorSolution>{
	
	/**
	 * Protected double array contains lower limit for every variable
	 */
	protected double[] mins;
	/**
	 * Protected double array contains upper limit for every variable
	 */
	protected double[] maxs;
	/**
	 * Protected integer arrays contains number of  bits per variable
	 */
	protected int[] bits;
	/**
	 * dimension
	 */
	protected int n;
	/**
	 * total number of bits
	 */
	protected int totalBits;
	
	/**
	 * Public abstract method which accepts instance of {@linkplain BitvectorSolution} and
	 * converts it into double array
	 * 
	 * @return decoded {@linkplain BitvectorSolution} as double array
	 */
	public abstract double[] decode(BitvectorSolution t);

	/**
	 * Public abstract method which accepts instance of {@linkplain BitvectorSolution} and
	 * converts it into double array
	 * 
	 */
	public abstract void decode(BitvectorSolution t, double[] array);

	/**
	 * Public constructor which accepts desire settings and stores it into
	 * protected variables
	 * 
	 * @param mins desire lower limit for every variable
	 * @param maxs desire upper limit for every variable
	 * @param bits bits which will be decoded
	 * @param n dimension
	 */
	public BitvectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		this.mins = Arrays.copyOf(mins, mins.length);
		this.maxs = Arrays.copyOf(maxs, maxs.length);
		this.bits = Arrays.copyOf(bits, bits.length);
		this.totalBits = bits.length;
		this.n = n;
	}
	
	/**
	 * Public constructor which accepts desire settings and stores it into
	 * protected variables
	 * 
	 * @param min lower limit
	 * @param max upper limit
	 * @param totalBits number of total bits
	 * @param n dimension
	 */
	public BitvectorDecoder(double min, double max, int totalBits, int n) {
		this.mins = new double[]{min};
		this.maxs = new double[]{max};
		this.bits = new int[]{totalBits};
		this.totalBits = bits.length;
		this.n = n;
	}
	
	/**
	 * Public getter return number of total bits
	 * 
	 * @return number of total bits
	 */
	public int getTotalBits(){
		return totalBits;
	}
	
	/**
	 * Public getter return dimension 
	 * 
	 * @return dimension
	 */
	public int getDimensions(){
		return n;
	}
	
}
