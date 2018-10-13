package hr.fer.zemris.optjava.dz13;

/**
 * Interface represtens crossover of genetic progaramming
 * @author Branko
 *
 */
public interface ICrossover {

	/**
	 * Method crossover two desire parents and produces a child
	 * @param parent1 first parents
	 * @param parent2 second parent
	 * @return child
	 */
	 Node crossover(Node parent1, Node parent2);
}
