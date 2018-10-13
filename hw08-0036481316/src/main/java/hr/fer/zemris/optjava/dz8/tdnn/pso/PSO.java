package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
/**
 * Class which implements Particle Swarm Optimization
 * 
 * @author Branko
 *
 */
public class PSO {

	/**
	 * Maximal number of iterations
	 */
	private int maxIteration;
	/**
	 * number of particles
	 */
	private int numberOfParticles;
	/**
	 * dimension of solution
	 */
	private int dim;
	/**
	 * maximal velocities
	 */
	private double[] vMax;
	/**
	 * Minimal velocities
	 */
	private double[] vMin;
	/**
	 * maximal positions
	 */
	private double[] xMax;
	/**
	 * minimal position
	 */
	private double[] xMin;
	/**
	 * private instance of {@linkplain Random}
	 */
	private Random rand;
	/**
	 * private instance of {@linkplain IFunction}
	 */
	private IFunction<Double> function;
	/**
	 * private instance of {@linkplain INeighborhood}
	 */
	private INeighborhood<Particle<Double>> neighborhood;
	/**
	 * hyper parameter for velocity
	 */
	private double c1;
	/**
	 * hyper parameter for velocity
	 */
	private double c2;
	/**
	 * hyper parameter for velocity
	 */
	private int t;
	/**
	 * maximal w
	 */
	private double wMax;
	/**
	 * minimal w
	 */
	private double wMin;
	/**
	 * maximal acceptable error
	 */
	private double maxError;

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param maxIteration
	 *            maximal number of iterations
	 * @param numberOfParticles
	 *            number of particles
	 * @param dim
	 *            dimension of solution
	 * @param vMax
	 *            maximal velocities
	 * @param vMin
	 *            minimal velocities
	 * @param xMax
	 *            maximal position
	 * @param xMin
	 *            minimal position
	 * @param neighborhood
	 *            desire neighbourhood
	 * @param function
	 *            desire function
	 * @param maxError
	 *            desire maximal acceptable error
	 */
	public PSO(int maxIteration, int numberOfParticles, int dim, double[] vMax, double[] vMin, double[] xMax,
			double[] xMin, INeighborhood<Particle<Double>> neighborhood, IFunction<Double> function, double maxError) {
		super();
		this.maxIteration = maxIteration;
		this.numberOfParticles = numberOfParticles;
		this.dim = dim;
		this.vMax = vMax;
		this.vMin = vMin;
		this.xMax = xMax;
		this.xMin = xMin;
		rand = new Random();
		c1 = 2.05;
		c2 = 2.05;
		t = (int) (0.55 * maxIteration);
		this.neighborhood = neighborhood;
		this.function = function;
		wMax = 0.9;
		wMin = 0.1;
		this.maxError = maxError;

	}

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param maxIteration
	 *            maximal number of iterations
	 * @param numberOfParticles
	 *            number of particles
	 * @param dim
	 *            dimension of solution
	 * @param vMax
	 *            maximal velocities
	 * @param vMin
	 *            minimal velocities
	 * @param xMax
	 *            maximal position
	 * @param xMin
	 *            minimal position
	 * @param neighborhood
	 *            desire neighbourhood
	 * @param function
	 *            desire function
	 * @param maxError
	 *            desire maximal acceptable error
	 */
	public PSO(int maxIteration, int numberOfParticles, int dim, double vMax, double vMin, double xMax, double xMin,
			IFunction<Double> function, INeighborhood<Particle<Double>> neighborhood,double maxError) {
		super();
		this.maxIteration = maxIteration;
		this.numberOfParticles = numberOfParticles;
		this.dim = dim;
		this.vMax = new double[dim];
		this.vMin = new double[dim];
		this.xMax = new double[dim];
		this.xMin = new double[dim];
		for (int i = 0; i < dim; i++) {
			this.vMax[i] = vMax;
			this.vMin[i] = vMin;
			this.xMax[i] = xMax;
			this.xMin[i] = xMin;
		}
		this.function = function;
		this.neighborhood = neighborhood;
		rand = new Random();
		c1 = 2.05;
		c2 = 2.05;
		t = (int) (0.55 * maxIteration);
		wMax = 0.9;
		wMin = 0.1;
		this.maxError = maxError;
	}

	/**
	 * Method which executes algorithm
	 */
	public double[] run() {

		ArrayList<Particle<Double>> particles = initParticles();
		GlobalNeighborhood neigh = new GlobalNeighborhood(numberOfParticles, dim, true);

		
		for (int i = 0; i < maxIteration; i++) {
			neighborhood.scan(particles);

			neigh.scan(particles);

			if (maxError >= function.valueAt(neigh.bestValues(0))) {
				System.out.println("Best is " + Arrays.toString(neigh.bestValues(0)));
				System.out.println("Best value is:" + function.valueAt(neigh.bestValues(0)));
				return neigh.bestValues(0);
			}
			
			
			for (int j = 0, size = particles.size(); j < size; j++) {

				double[] best = neighborhood.bestValues(j);
				Double[] positions = particles.get(j).getValues();
				Double[] velocities = particles.get(j).getVelocities();
				Double[] bestPosition = particles.get(j).getBestValues();
				double w = 0;
				if (i <= t) {
					w = (i / t) * (-wMax + wMin) + wMax;
				} else {
					w = wMin;
				}
				for (int k = 0; k < dim; k++) {

					velocities[k] = w * velocities[k] + c1 * rand.nextDouble() * (bestPosition[k] - positions[k])
							+ c2 * rand.nextDouble() * (best[k] - positions[k]);
					if (velocities[k] > vMax[k]) {
						velocities[k] = vMax[k];
					}
					if (velocities[k] < vMin[k]) {
						velocities[k] = vMin[k];
					}
					positions[k] += velocities[k];
				}
				particles.get(j).setFeatures(positions);
				particles.get(j).setVelocities(velocities);
			}

		}
		neigh.scan(particles);
		System.out.println("Best is " + Arrays.toString(neigh.bestValues(0)));
		System.out.println("Best value is:" + function.valueAt(neigh.bestValues(0)));

		return neigh.bestValues(0);
	}

	/**
	 * Method which initialises particles
	 * 
	 * @return initialised particles
	 */
	public ArrayList<Particle<Double>> initParticles() {
		ArrayList<Particle<Double>> particles = new ArrayList<>();
		for (int i = 0; i < numberOfParticles; i++) {
			Double[] velocities = new Double[dim];
			Double[] positions = new Double[dim];
			for (int j = 0; j < dim; j++) {
				velocities[j] = vMin[j] + (vMax[j] - vMin[j]) * rand.nextDouble();
				positions[j] = xMin[j] + (xMax[j] - xMin[j]) * rand.nextDouble();
			}
			Particle<Double> particle = new Particle<>(dim, positions, velocities, function, true);
			particles.add(particle);

		}
		return particles;
	}
}
