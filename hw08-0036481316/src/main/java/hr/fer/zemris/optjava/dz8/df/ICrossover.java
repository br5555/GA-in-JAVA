package hr.fer.zemris.optjava.dz8.df;

/**
 * Interface represents crossover in genetic algorithm
 * @author Branko
 *
 * @param <T> 
 */
public interface ICrossover<T> {

	/**
	 * Method crossover two desire entities and returns one child
	 * @param mutante first entity 
	 * @param target second entity
	 * @param Cr hyper parameter of mutation
	 * @return child as a product of crossover
	 */
	T crossover(T mutante, T target, double Cr);
}
