package hr.fer.zemris.optjava.dz10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Class represent implementation of elitist NSGA algorithm
 * 
 * @author Branko
 *
 */
public class NSGA2 {

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
	 * Public constructor accepts desire settings
	 * 
	 * @param maxIteration
	 *            desire maximal number of iterations
	 * @param xMaxs
	 *            desire upper bounds
	 * @param xMins
	 *            desire lower bounds
	 * @param sizeOfPopulation
	 *            desire size of population
	 * @param space
	 *            where to calculate distance
	 * @param moop
	 *            instance of {@linkplain MOOPProblem}
	 */
	public NSGA2(int maxIteration, double[] xMaxs, double[] xMins, int sizeOfPopulation, 
			MOOPProblem moop) {
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
		alpha = 2;
		epsilon = 0.75;
		this.moop = moop;
		this.sigmaShare = 5;
		Fstart = 5;

	}

	/**
	 * Method which executes NSGA algorithm
	 * 
	 * @return pareto fronts of final solutions
	 */
	public ArrayList<ArrayList<SolutionVector>> run() {
		ArrayList<SolutionVector> population = new ArrayList<>();

		for (int i = 0; i < sizeOfPopulation; i++) {
			double[] values = new double[dimension];
			for (int k = 0; k < dimension; k++) {
				values[k] = xMins[k] + (xMaxs[k] - xMins[k]) * rand.nextDouble();

			}
			double[] solution = new double[dimension];
			SolutionVector child = new SolutionVector(values);
			moop.evaluateSolution(child.getValues(), solution);
			child.solutions = Arrays.copyOf(solution, solution.length);
			population.add(child);
		}

		ArrayList<ArrayList<SolutionVector>> paretoFronts = this.paretoFronts(population);
		int index = 1;

		for (ArrayList<SolutionVector> front : paretoFronts) {
			groupingDistance(front);

			for (SolutionVector vec : front) {
				vec.setNumOfFront(index);
			}
			index++;
		}

		for (int r = 0; r < maxIteration; r++) {

			for (int i = 0; i < sizeOfPopulation; i++) {
				SolutionVector parent1 = selection.selection(population);
				SolutionVector parent2 = selection.selection(population);
				SolutionVector child = crossover.crossover(parent1, parent2);
				child = mutation.mutate(child);
				double[] solution = new double[moop.getNumberOfObjectives()];

				moop.evaluateSolution(child.getValues(), solution);
				child.solutions = Arrays.copyOf(solution, solution.length);
				population.add(child.duplicate());

			}

			paretoFronts = this.paretoFronts(population);

			index = 1;

			for (ArrayList<SolutionVector> front : paretoFronts) {
				groupingDistance(front);
				for (SolutionVector vec : front) {
					vec.setNumOfFront(index);
				}
				index++;
			}

			ArrayList<SolutionVector> newPopulation = new ArrayList<>();

			for (ArrayList<SolutionVector> front : paretoFronts) {
				if (front.size() + newPopulation.size() <= sizeOfPopulation) {
					for (int i = 0, size = front.size(); i < size; i++) {
						newPopulation.add(front.get(i).duplicate());
					}
				} else {
					
					Collections.sort(front, new Comparator<SolutionVector>() {
						@Override
						public int compare(SolutionVector o1, SolutionVector o2) {
							if (o1.dividedDistance > o2.dividedDistance) {
								return -1;
							} else if (o1.dividedDistance < o2.dividedDistance) {
								return 1;
							} else {
								return 0;
							}
						}
					});

					for (int k = 0, size = sizeOfPopulation - newPopulation.size(); k < size; k++) {
						newPopulation.add(front.get(k));
					}

					break;
				}

			}

			population = new ArrayList<>(newPopulation);
		}
		return this.paretoFronts(population);
	}

	private static void groupingDistance(ArrayList<SolutionVector> front) {

	int dimension = front.get(0).getSolutions().length;
	
	for(int index = 0; index< dimension; index++){
		final int i = index;
		
		Collections.sort(front, new Comparator<SolutionVector>() {
			@Override
			public int compare(SolutionVector o1, SolutionVector o2) {
				if (o1.solutions[i] > o2.solutions[i]) {
					return -1;
				} else if (o1.solutions[i] < o2.solutions[i]) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		double fmax = front.get(0).solutions[index];
		double fmin = front.get(front.size()-1).solutions[index];

		if(index == 0){
			front.get(0).setDividedDistance(9999);
			front.get(front.size()-1).setDividedDistance(9999);
			for(int k = 1; k<front.size()-1; k++){
				double value = (front.get(k-1).solutions[index]-front.get(k+1).solutions[index])/(fmax-fmin);
				front.get(k).setDividedDistance(value);
			}
		}else{
			front.get(0).addDividedDistance(9999);
			front.get(front.size()-1).addDividedDistance(9999);
			for(int k = 1; k<front.size()-1; k++){
				double value = (front.get(k-1).solutions[index]-front.get(k+1).solutions[index])/(fmax-fmin);
				front.get(k).addDividedDistance(value);
			}
		}
		
	}
		
		
	}




	/**
	 * Method which construct pareto fronts from inputed solutions
	 * 
	 * @param solutions
	 *            list of {@linkplain SolutionVector}
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
	 * 
	 * @param array1
	 *            first array
	 * @param array2
	 *            second array
	 * @return 1 if first dominates second , -1 if second dominates first, 0
	 *         otherwise
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
