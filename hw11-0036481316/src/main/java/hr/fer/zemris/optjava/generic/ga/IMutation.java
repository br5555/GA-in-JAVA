package hr.fer.zemris.optjava.generic.ga;

/**
 * Interface which represents GA mutation
 * @author Branko
 *
 * @param <T>
 */
public interface IMutation<T> {
 
	/**
	 * Method which mutates desire GA solution
	 * @param orginal unmutated desire GA solution
	 * @return mutated desire GA solution
	 */
	T[] mutate(T[] orginal);
	
}
