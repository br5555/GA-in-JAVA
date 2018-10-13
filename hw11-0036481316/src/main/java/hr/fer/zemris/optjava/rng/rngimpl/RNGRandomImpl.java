package hr.fer.zemris.optjava.rng.rngimpl;

import java.util.Random;

import hr.fer.zemris.optjava.rng.IRNG;

/**
 * Class which implements {@linkplain IRNG} with instance of {@linkplain Random} 
 * @author Branko
 *
 */
public class RNGRandomImpl implements IRNG{

	/**
	 * Private reference of {@linkplain Rando,}
	 */
	private Random rand;
	
	/**
	 * Public constructor sets desire settings
	 */
	public RNGRandomImpl() {
		rand = new Random();
	}
	
	@Override
	public double nextDouble() {
		return rand.nextDouble();
	}

	@Override
	public double nextDouble(double min, double max) {
		return min + (max-min)*rand.nextDouble();
	}

	@Override
	public float nextFloat() {
		return rand.nextFloat();
	}

	@Override
	public float nextFloat(float min, float max) {
		return min + (max-min)*rand.nextFloat();
	}

	@Override
	public int nextInt() {
		return rand.nextInt();
	}

	@Override
	public int nextInt(int min, int max) {
		return min + rand.nextInt(max-min);
	}

	@Override
	public boolean nextBoolean() {
		return rand.nextBoolean();
	}

	@Override
	public double nextGaussian() {
		return rand.nextGaussian();
	}

}
