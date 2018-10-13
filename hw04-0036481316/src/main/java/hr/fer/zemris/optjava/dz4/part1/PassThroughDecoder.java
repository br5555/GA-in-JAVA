package hr.fer.zemris.optjava.dz4.part1;

import java.util.Arrays;

/**
 * Class implements {@linkplain IDecoder} and it is used to 
 * extract needed information from {@linkplain DoubleArraySolution}
 * 
 * @author Branko
 *
 */
public class PassThroughDecoder implements IDecoder<DoubleArraySolution>{

	@Override
	public double[] decode(DoubleArraySolution t) {
		DoubleArraySolution temp = t.duplicate();
		return temp.values;
	}

	@Override
	public void decode(DoubleArraySolution t, double[] array) {
		array = Arrays.copyOf(t.values, t.values.length);
		
	}

	/**
	 * Public constructor
	 */
	public PassThroughDecoder() {
	}

	@Override
	public void setValues(DoubleArraySolution t, double[] array) {
		t.values = Arrays.copyOf(array, array.length);
		
	}
}
