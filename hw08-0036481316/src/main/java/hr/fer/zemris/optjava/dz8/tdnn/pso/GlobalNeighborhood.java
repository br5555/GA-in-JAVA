package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Class implements global neighbourhood.Class implements
 * {@linkplain INeighborhood}. User must first call method scan and than
 * getBestValues.
 * 
 * @author Branko
 *
 */
public class GlobalNeighborhood implements INeighborhood<Particle<Double>> {

	/**
	 * best particle
	 */
	private Particle<Double> bestParticle;
	/**
	 * number of particles
	 */
	private int particlesCount;

	/**
	 * dimension
	 */
	private int dims;
	/**
	 * index of best particle
	 */
	private int indexOfBest;
	/**
	 * best values
	 */
	private double[] best;
	/**
	 * minimize or maximize
	 */
	private boolean minimize;
	/**
	 * have best
	 */
	private boolean haveBest;

	/**
	 * public constructor accepts desire settings
	 * 
	 * @param particlesCount
	 *            number of particles
	 * @param dims
	 *            dimension
	 * @param minimize
	 *            minimize or maximize
	 */
	public GlobalNeighborhood(int particlesCount, int dims, boolean minimize) {
		super();
		this.particlesCount = particlesCount;
		this.dims = dims;
		this.minimize = minimize;
		haveBest = false;
	}

	@Override
	public void scan(ArrayList<Particle<Double>> particles) {
		indexOfBest = -1;
		int index = 0;
		if (!haveBest) {
			bestParticle = particles.get(0).duplicate();
			haveBest = true;
		}
		// bestParticle = particles.get(0).duplicate();

		for (Particle<Double> particle : particles) {
			if ((minimize && (particle.getBestFitness() < bestParticle.getBestFitness()))
					|| (!minimize && (particle.getBestFitness() > bestParticle.getBestFitness()))) {
				bestParticle = particle.duplicate();
				indexOfBest = index;

			}
			index++;
		}
		double[] temp = ArrayUtils.toPrimitive(bestParticle.getBestValues());
		best = Arrays.copyOf(temp, temp.length);
	}

	@Override
	public double[] bestValues(int index) {
		return best;
	}

}
