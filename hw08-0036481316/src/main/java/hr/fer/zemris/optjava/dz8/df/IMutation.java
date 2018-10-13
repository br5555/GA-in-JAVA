package hr.fer.zemris.optjava.dz8.df;

import java.util.ArrayList;

/**
 * Interface represents mutation in genetic algorithms
 * 
 * @author Branko
 *
 * @param <T>
 *            generic parameter
 */
public interface IMutation<T> {

	/**
	 * Method mutate entity
	 * 
	 * @param orginal
	 *            original entity
	 * @param F
	 *            scalars constant (hyper parameter of mutation)
	 * @return mutated entity
	 */
	T mutate(ArrayList<T> orginal, double[] F);

}
