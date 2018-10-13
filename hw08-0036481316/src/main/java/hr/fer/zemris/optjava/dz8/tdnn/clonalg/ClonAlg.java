package hr.fer.zemris.optjava.dz8.tdnn.clonalg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import hr.fer.zemris.optjava.dz8.tdnn.pso.IFunction;

/**
 * Class implements CLONALG
 * 
 * @author Branko
 *
 */
public class ClonAlg {
	/**
	 * Hyper parameter of cloning
	 */
	private double beta;
	/**
	 * population size
	 */
	private int n;
	/**
	 * size of new entities
	 */
	private int d;
	/**
	 * dimension of solution
	 */
	private int dim;
	/**
	 * private instance of {@linkplain IFunction}
	 */
	private IFunction<Double> function;
	/**
	 * maximum number of iterations
	 */
	private int maxIter;
	/**
	 * Private instance {@linkplain Random}
	 */
	private Random rand;
	/**
	 * Private instance of {@linkplain Clone}
	 */
	private Clone clone;
	/**
	 * Private instance of {@linkplain IHypermutation}
	 */
	private IHypermutation hypermutation;
	/**
	 * the best solution
	 */
	private Antigen<Double> antigenGlobal;
	/**
	 * maximum acceptable error
	 */
	private double maxError;

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param beta
	 *            hyper parameter of cloning
	 * @param n
	 *            size of population
	 * @param d
	 *            size of new entities
	 * @param dim
	 *            dimension of solution
	 * @param function
	 *            instance of {@linkplain IFunction}
	 * @param maxIter
	 *            maximum number of iteration
	 * @param maxError
	 *            maximum acceptable error
	 */
	public ClonAlg(double beta, int n, int d, int dim, IFunction<Double> function, int maxIter, double maxError) {
		super();
		this.beta = beta;
		this.n = n;
		this.d = d;
		this.dim = dim;
		this.function = function;
		this.maxIter = maxIter;
		rand = new Random();
		clone = new Clone();
		hypermutation = new Hypermutation1(0.7, 0.7);
		this.maxError = maxError;

	}

	/**
	 * Method which executes algorithm
	 */
	public double[] run() {
		ArrayList<Antigen<Double>> antigens = initAntigens(n);
		antigenGlobal = antigens.get(0).duplicate();
		for (int i = 0; i < maxIter; i++) {

			antigens = new ArrayList<>(antigens.stream().limit(n).collect(Collectors.toList()));
			antigens = clone.clonning(beta, antigens);
			antigens = hypermutation.hypermutation(antigens, beta);
			Collections.sort(antigens);
			if (antigenGlobal.getFitness() > antigens.get(0).getFitness()) {
				antigenGlobal = antigens.get(0).duplicate();
			}

			if (antigenGlobal.getFitness() < maxError) {
				System.out.println(" Best is " + antigenGlobal.getFitness());
				System.out.println(Arrays.toString(antigenGlobal.getValues()));
				return ArrayUtils.toPrimitive(antigenGlobal.getValues());
			}

			if (i % 100 == 0)
				System.out.println(i + " Best is " + antigenGlobal.getFitness());

			antigens = new ArrayList<>(antigens.stream().limit(n).collect(Collectors.toList()));
			ArrayList<Antigen<Double>> newAntigens = initAntigens(d);

			for (int l = 0; l < d; l++) {
				antigens.set(n - 1 - l, newAntigens.get(l));
			}
		}

		System.out.println(" Best is " + antigenGlobal.getFitness());
		System.out.println(Arrays.toString(antigenGlobal.getValues()));
		return ArrayUtils.toPrimitive(antigenGlobal.getValues());
	}

	/**
	 * Method which makes new instances of Antigens(K cell)
	 * 
	 * @param numOfAntigens
	 *            number of new Antigens
	 * @return new antigens
	 */
	public ArrayList<Antigen<Double>> initAntigens(int numOfAntigens) {

		ArrayList<Antigen<Double>> antigens = new ArrayList<>();

		for (int i = 0; i < numOfAntigens; i++) {

			Double[] values = new Double[dim];

			for (int k = 0; k < dim; k++) {

				values[k] = rand.nextDouble();
			}

			Antigen<Double> antigen = new Antigen<>(values, function);
			antigens.add(antigen);
		}
		return antigens;
	}
}
