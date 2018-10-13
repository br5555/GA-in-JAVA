package hr.fer.zemris.optjava.dz4.part1;

/**
 * Interface represents generalisation of crossover in genetic algorithm.
 * 
 * @author Branko
 *
 */
public interface Crossover {

	/**
	 * Method cross over two parents and return a child.
	 *  
	 * @param parent1 first parent instance of {@linkplain DoubleArraySolution}
	 * @param parent2 second parent instance of {@linkplain DoubleArraySolution}
	 * @return child as a crossover product  
	 */
	DoubleArraySolution cross(DoubleArraySolution parent1, DoubleArraySolution parent2);
}
