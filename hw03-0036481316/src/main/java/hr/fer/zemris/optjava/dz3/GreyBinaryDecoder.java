package hr.fer.zemris.optjava.dz3;

import java.nio.ByteBuffer;

/**
 * Class inherent {@linkplain BitvectorDecoder} and implements decoder from grey binary
 * code to decimal number
 * 
 * @author Branko
 *
 */
public class GreyBinaryDecoder extends BitvectorDecoder{

	/**
	 * Public constructor which accepts desire settings and stores it into
	 * protected variables
	 * 
	 * @param min lower limit
	 * @param max upper limit
	 * @param totalBits number of total bits
	 * @param n dimension
	 */
	public GreyBinaryDecoder(double min, double max, int totalBits, int n) {
		super(min, max, totalBits,n);
	}
	
	/**
	 * Public constructor which accepts desire settings and stores it into
	 * protected variables
	 * 
	 * @param mins desire lower limit for every variable
	 * @param maxs desire upper limit for every variable
	 * @param bits bits which will be decoded
	 * @param n dimension
	 */
	public  GreyBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins,  maxs,  bits, n);
	}
	
	@Override
	public double[] decode(BitvectorSolution t) {
		double[] temp = new double[this.getDimensions()];
		int index =0;
		for(int i = 0, size= this.getDimensions(); i<size; i++){
			byte[] variable = new byte[this.bits[i]];
			System.arraycopy(t.bits, index, variable, 0, this.bits[i]);
			variable = greyToBinary(variable);
			index += this.bits[i];
			temp[i] = mins[i]+ByteBuffer.wrap(variable).getInt()*((maxs[i]-mins[i])/(Math.pow(2, this.bits[i])-1));
		}
		return temp;
	}

	/**
	 * Private method which converts from grey bits to natural binary bits
	 * 
	 * @param grey grey code
	 * @return natural binary code
	 */
	private byte[] greyToBinary(byte[] grey) {
		byte[] bin = new byte[grey.length];
		for(int i = 0; i< grey.length; i++){
			bin[i] = (byte)decodeGray(grey[i]);
		}
		return bin;
	}
	
	/**
	 * Private method from converting one byte from grey to natural binary code
	 * 
	 * @param gray grey bit
	 * @return natural binary bit
	 */
	public static int decodeGray(int gray) {

		int natural = 0;
		while (gray != 0) {
			natural ^= gray;
			gray >>>= 1;
		}
		return natural;

	}

	@Override
	public void decode(BitvectorSolution t, double[] solution) {
		solution = new double[this.getDimensions()];
		int index =0;
		for(int i = 0, size= this.getDimensions(); i<size; i++){
			byte[] variable = new byte[this.bits[i]];
			System.arraycopy(t.bits, index, variable, 0, this.bits[i]);
			variable = greyToBinary(variable);
			index += this.bits[i];
			solution[i] = mins[i]+ByteBuffer.wrap(variable).getInt()*((maxs[i]-mins[i])/(Math.pow(2, this.bits[i])-1));
		}
		
		
	}

	
}
