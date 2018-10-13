package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Class implements local neighbourhood.Class implements
 * {@linkplain INeighborhood}. User must first call method scan and than
 * getBestValues.
 * 
 * @author Branko
 *
 */
public class LocalNeighborhood implements INeighborhood<Particle<Double>> {

	/**
	 * best particle
	 */
	private ArrayList<Particle<Double>> bestParticles;
	/**
	 * number of particels
	 */
	private int particlesCount;

	/**
	 * dimension
	 */
	private int dims;
	/**
	 * best neighbours
	 */
	private double[][] best;
	/**
	 * minimize or maximize
	 */
	private boolean minimize;
	/**
	 * number of neigbours
	 */
	private int numOfNeigh;

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param particlesCount
	 *            number of particles
	 * @param dims
	 *            dimension
	 * @param minimize
	 *            minimize or maximize
	 * @param numOfNeigh
	 *            number of neighbours
	 */
	public LocalNeighborhood(int particlesCount, int dims, boolean minimize, int numOfNeigh) {
		super();
		this.particlesCount = particlesCount;
		this.dims = dims;
		this.minimize = minimize;
		this.numOfNeigh = numOfNeigh;
		this.best = new double[particlesCount][dims];
		bestParticles = new ArrayList<>();
		for (int i = 0; i < particlesCount; i++) {
			bestParticles.add(null);
		}
	}

	@Override
	public void scan(ArrayList<Particle<Double>> particles) {
		for (int i = 0, size = particles.size(); i < size; i++) {

			int startIndex = i - numOfNeigh / 2;
			int endIndex = i + numOfNeigh / 2;
			if (startIndex < 0)
				startIndex = 0;
			if (endIndex >= particles.size())
				endIndex = particles.size() - 1;
			if (bestParticles.get(i) == null) {
				bestParticles.set(i, particles.get(startIndex).duplicate());
			}

			// already set startIndex as best
			for (int j = startIndex + 1; j <= endIndex; j++) {
				if ((minimize && particles.get(j).getBestFitness() < bestParticles.get(i).getBestFitness())
						|| (!minimize && particles.get(j).getBestFitness() > bestParticles.get(i).getBestFitness())) {
					bestParticles.set(i, particles.get(j).duplicate());

				}
			}
			double[] temp = ArrayUtils.toPrimitive(bestParticles.get(i).getBestValues());
			best[i] = Arrays.copyOf(temp, temp.length);
		}

	}

	@Override
	public double[] bestValues(int index) {
		return best[index];
	}
}
