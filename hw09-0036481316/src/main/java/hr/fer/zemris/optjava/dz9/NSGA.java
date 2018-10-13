package hr.fer.zemris.optjava.dz9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * Class represent impleemntaion of NSGA algorithm
 * 
 * @author Branko
 *
 */
public class NSGA {
	
	/**
	 * hyper parameter of pareto fronts
	 */
	private double epsilon;
	/**
	 * hyper parameter of niches
	 */
	private double sigmaShare;
	/**
	 * minimal fitness
	 */
	private double Fmin;
	/**
	 * starting fitness
	 */
	private double Fstart;
	/**
	 * Maximal number of iterations
	 */
	private int maxIteration;
	/**
	 * upper bounds
	 */
	private double[] xMaxs;
	/**
	 * lower bounds
	 */
	private double[] xMins;
	/**
	 * hyper parameter of niches
	 */
	private double alpha;
	/**
	 * size of population
	 */
	private int sizeOfPopulation;
	/**
	 * private instance of {@linkplain ProportionalSelection}
	 */
	private ProportionalSelection selection;
	/**
	 * private instance of {@linkplain Crossover}
	 */
	private Crossover crossover;
	/**
	 * private instance of {@linkplain Mutation}
	 */
	private Mutation mutation;
	/**
	 * dimension of input space
	 */
	private int dimension;
	/**
	 * private instance of {@linkplain Random}
	 */
	private Random rand;
	/**
	 * private instance of {@linkplain MOOPProblem}
	 */
	private MOOPProblem moop;
	/**
	 * String which decide where to calculate distance
	 */
	private String space;

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param maxIteration desire maximal number of iterations
	 * @param xMaxs desire upper bounds
	 * @param xMins desire lower bounds
	 * @param sizeOfPopulation desire size of population
	 * @param space where to calculate distance
	 * @param moop instance of {@linkplain MOOPProblem}
	 */
	public NSGA(int maxIteration, double[] xMaxs, double[] xMins, int sizeOfPopulation, String space, MOOPProblem moop) {
		super();
		selection = new ProportionalSelection();
		crossover = new Crossover();
		mutation = new Mutation(xMaxs, xMins);
		this.maxIteration = maxIteration;
		this.xMaxs = xMaxs;
		this.xMins = xMins;
		this.dimension = xMaxs.length;
		rand = new Random();
		this.sizeOfPopulation = sizeOfPopulation;
		this.space = space;
		alpha = 2;
		epsilon = 0.75;
		this.moop = moop;
		this.sigmaShare = 5;
		Fstart = 5;
		
	}

	/**
	 * Method which executes NSGA algorithm
	 * @return pareto fronts of final solutions
	 */
	public ArrayList<ArrayList<SolutionVector>> run() {
		ArrayList<SolutionVector> population = new ArrayList<>();

		for (int i = 0; i < sizeOfPopulation; i++) {
			double[] values = new double[dimension];
			for (int k = 0; k < dimension; k++) {
				values[k] = xMins[k]+(xMaxs[k]-xMins[k])* rand.nextDouble();

			}
			double[] solution = new double[dimension];
			SolutionVector child = new SolutionVector(values);
			moop.evaluateSolution(child.getValues(), solution);
			child.solutions = Arrays.copyOf(solution, solution.length);
			population.add(child);
		}

		for (int r = 0; r < maxIteration; r++) {

			ArrayList<ArrayList<SolutionVector>> paretoFronts = this.paretoFronts(population);

			Fmin = Fstart + epsilon;
			for (ArrayList<SolutionVector> front : paretoFronts) {
				Fmin *= epsilon;

				if (Fmin < 0) {
					throw new IllegalArgumentException("Fitness cannot be negative");
				}

				double[] niches = new double[front.size()];
				niches[front.size() - 1] = 1;

				for (int i = 0, size = front.size(); i < size ; i++) {

					front.get(i).setFitness(Fmin);
					niches[i] += 1;

					for (int j = i + 1; j < size; j++) {

						double distance = 0;

						if (space.matches("decision-space")) {
							distance = distanceVal(front.get(i), front.get(j));
						} else if (space.matches("objective-space")) {
							distance = distanceSol(front.get(i), front.get(j));
						}

						if (distance < sigmaShare) {
							double niche = 1 - Math.pow(distance / sigmaShare, alpha);
							niches[i] += niche;
							niches[j] += niche;
						}

					}

				}

				for (int i = 0, size = front.size(); i < size; i++) {
					double dividedFitness = front.get(i).getFitness() / niches[i];
					front.get(i).setDividedFitness(dividedFitness);
					if (dividedFitness < Fmin) {
						Fmin = dividedFitness;
					}
				}
			}

			ArrayList<SolutionVector> newPopulation = new ArrayList<>();

			for (int i = 0; i < sizeOfPopulation; i++) {
				SolutionVector parent1 = selection.Roulletewheel(paretoFronts);
				SolutionVector parent2 = selection.Roulletewheel(paretoFronts);
				SolutionVector child = crossover.crossover(parent1, parent2);
				child = mutation.mutate(child);
				double[] solution = new double[moop.getNumberOfObjectives()];

				moop.evaluateSolution(child.getValues(), solution);
				child.solutions = Arrays.copyOf(solution, solution.length);
				newPopulation.add(child.duplicate());

			}
			population = new ArrayList<>(newPopulation);
		}
		return this.paretoFronts(population);
	}

	/**
	 * Method which calculates distance in input space
	 * @param vec1 first {@linkplain SolutionVector}
 	 * @param vec2 second {@linkplain SolutionVector}
	 * @return distance in input space
	 */
	private double distanceVal(SolutionVector vec1, SolutionVector vec2) {
		double result = 0;
		double[] vec1Values = vec1.getValues();
		double[] vec2Values = vec2.getValues();

		for (int i = 0, size = vec1Values.length; i < size; i++) {
			result += Math.pow((vec1Values[i] - vec2Values[i]) / (xMaxs[i] - xMins[i]), 2);
		}
		return Math.sqrt(result);
	}

	/**
	 * Method which calculates distance in solution space
	 * @param vec1 first {@linkplain SolutionVector}
 	 * @param vec2 second {@linkplain SolutionVector}
	 * @return distance in solution space
	 */
	private double distanceSol(SolutionVector vec1, SolutionVector vec2) {
		double result = 0;
		double[] vec1Values = vec1.solutions;
		double[] vec2Values = vec2.solutions;

		for (int i = 0, size = vec1Values.length; i < size; i++) {
			result += Math.pow((vec1Values[i] - vec2Values[i]) / (xMaxs[i] - xMins[i]), 2);
		}
		return Math.sqrt(result);
	}

	/**
	 * Method which construct pareto fronts from inputed solutions
	 * @param solutions list of {@linkplain SolutionVector}
	 * @return pareto fronts of {@linkplain SolutionVector}
	 */
	public ArrayList<ArrayList<SolutionVector>> paretoFronts(ArrayList<SolutionVector> solutions) {

		ArrayList<ArrayList<SolutionVector>> fronts = new ArrayList<>();

		for (int i = 0, size = solutions.size(); i < size - 1; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j)
					continue;

				int compare = compareArrays(solutions.get(i).getSolutions(), solutions.get(j).getSolutions());

				if (compare == 1) {
					solutions.get(j).newSuperior();
					solutions.get(i).newSubordinate(solutions.get(j).duplicate());
					continue;
				} else if (compare == -1) {
					// solutions.get(i).newSuperior();
					// solutions.get(j).newSubordered(solutions.get(i));
					continue;
				}
			}
		}
		while (solutions.size() != 0) {
			ArrayList<SolutionVector> front = new ArrayList<>();

			for (int i = 0, size = solutions.size(); i < size; i++) {
				if (solutions.get(i).getNumberOfSuperior() == 0) {
					front.add(solutions.get(i).duplicate());
				}
			}

			fronts.add(front);
			solutions = new ArrayList<>();

			for (int i = 0, size = front.size(); i < size; i++) {
				ArrayList<SolutionVector> suborders = front.get(i).getSubordinate();
				for (int j = 0, sizeSubordered = suborders.size(); j < sizeSubordered; j++) {

					suborders.get(j).removeOneOfSuperior();

					if (suborders.get(j).getNumberOfSuperior() == 0) {
						solutions.add(suborders.get(j).duplicate());
					}

					// if(!solutions.contains(suborders.get(j))){
					// solutions.add(suborders.get(j));
					// }

				}
			}
		}
		return fronts;

	}

	/**
	 * Method which compares two arrays with concept of domination 
	 * @param array1 first array
	 * @param array2 second array
	 * @return 1 if first dominates second , -1 if second dominates first, 0 otherwise
	 */
	public int compareArrays(double[] array1, double[] array2) {
		boolean firstDominate = false;
		boolean secondDominate = false;

		for (int i = 0, size = array1.length; i < size; i++) {
			if (firstDominate && secondDominate) {
				return 0;
			}
			if (array1[i] < array2[i]) {
				firstDominate = true;
				continue;
			} else if (array1[i] > array2[i]) {
				secondDominate = true;
				continue;
			}
		}
		if ((firstDominate && secondDominate) || (!firstDominate && !secondDominate)) {
			return 0;
		} else if (firstDominate) {
			return 1;
		} else {
			return -1;
		}

	}
}
