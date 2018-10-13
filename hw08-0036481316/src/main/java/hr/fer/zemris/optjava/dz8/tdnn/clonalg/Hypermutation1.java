package hr.fer.zemris.optjava.dz8.tdnn.clonalg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class which implements {@link IHypermutation}
 * 
 * @author Branko
 *
 */
public class Hypermutation1 implements IHypermutation {

	/**
	 * Hyper parameter of mutation, probability of changing a value
	 */
	private double rho;
	/**
	 * Hyper parameter of mutation, change of variance
	 */
	private double sigma;

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param rho
	 *            probability of changing a value
	 * @param sigma
	 *            change of variance
	 */
	public Hypermutation1(double rho, double sigma) {
		this.rho = rho;
		this.sigma = sigma;

	}

	@Override
	public ArrayList<Antigen<Double>> hypermutation(ArrayList<Antigen<Double>> antigens, double beta) {

		ArrayList<Antigen<Double>> mutants = new ArrayList<>();
		Random rand = new Random();

		for (int i = 0, size = antigens.size(); i < size; i++) {

			Double[] values = antigens.get(i).getValues();
			double fitness = antigens.get(i).getFitness();
			double probability = (1 / rho) * Math.exp(-1 * (fitness));

			for (int j = 0, num = values.length; j < num; j++) {

				if (rand.nextDouble() < probability) {
					values[j] += rand.nextGaussian() * sigma;

				}

			}
			antigens.get(i).setValues(values);
			mutants.add(antigens.get(i).duplicate());

		}
		return mutants;
	}
}
