package hr.fer.zemris.optjava.dz13;
/**
 * Interface represents mutation of genetic programming
 * @author Branko
 *
 */
public interface IMutation {

	/**
	 * Method mutates desire tree 
	 * @param orginal desire tree
	 * @return mutated desire tree
	 */
	Node mutate(Node orginal);
}
