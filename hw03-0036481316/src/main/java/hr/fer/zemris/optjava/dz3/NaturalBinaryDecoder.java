package hr.fer.zemris.optjava.dz3;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Class inherent {@linkplain BitvectorDecoder} and decodes 
 * bits coded in natural binary code to decimal values.
 * 
 * @author Branko
 *
 */
public class NaturalBinaryDecoder extends BitvectorDecoder{

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param min the lower limit
	 * @param max the upper limit
	 * @param bit desire bit
	 * @param dimension dimension 
	 */
	public NaturalBinaryDecoder(double min, double max, int bit, int dimension) {
		super(min, max, bit, dimension);
	}
	
	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param mins desire lower limit for every variable
	 * @param maxs desire upper limit for every variable
	 * @param bits bits which will be decoded
	 * @param n dimension
	 */
	public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int dimension) {
		super(mins, maxs, bits, dimension);
	}

	@Override
	public double[] decode(BitvectorSolution t) {
		double[] temp = new double[this.getDimensions()];
		int index =0;
		for(int i = 0, size= this.getDimensions(); i<size; i++){
			byte[] variable = new byte[this.bits[i]];
			System.arraycopy(t.bits, index, variable, 0, this.bits[i]);
			index += this.bits[i];
			
			temp[i] = mins[i]+ByteBuffer.wrap(variable).getInt()*((maxs[i]-mins[i])/(Math.pow(2, this.bits[i])-1));
		}
		return temp;
	}

	@Override
	public void decode(BitvectorSolution t, double[] solution) {
		solution = new double[this.getDimensions()];
		int index =0;
		for(int i = 0, size= this.getDimensions(); i<size; i++){
			byte[] variable = new byte[this.bits[i]];
			System.arraycopy(t.bits, index, variable, 0, this.bits[i]);
			index += this.bits[i];
			solution[i] = mins[i]+ByteBuffer.wrap(variable).getInt()*((maxs[i]-mins[i])/(Math.pow(2, this.bits[i])-1));
		}
		
	}

}
