package hr.fer.zemris.optjava.dz9;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class implements genetic algorithm selection
 * 
 * @author Branko
 *
 */
public class ProportionalSelection {

	/**
	 * Method select entity from inputed population
	 * 
	 * @param solutions
	 *            inputed population
	 * @return selected entity
	 */
	public SolutionVector Roulletewheel(ArrayList<ArrayList<SolutionVector>> solutions) {
		Random rand = new Random();
		double sum = 0;

		for (ArrayList<SolutionVector> front : solutions) {
			for (SolutionVector vec : front) {
				sum += vec.getDividedFitness();
			}
		}

		double randNumber = sum * rand.nextDouble();

		double distance = 0;

		for (ArrayList<SolutionVector> front : solutions) {
			for (SolutionVector vec : front) {
				distance += vec.getDividedFitness();
				if (distance >= randNumber) {
					return vec.duplicate();
				}
			}
		}

		return null;

	}
}
