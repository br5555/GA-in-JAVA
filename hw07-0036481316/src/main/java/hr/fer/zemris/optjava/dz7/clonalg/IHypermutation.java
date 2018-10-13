package hr.fer.zemris.optjava.dz7.clonalg;

import java.util.ArrayList;

/**
 * Interface which implements hypermutation
 * @author Branko
 *
 */
public interface IHypermutation {

	/**
	 * Method hypermutate antigens
	 * @param antigens population of antigens
	 * @param beta hyper parameter of muation
	 * @return hypermutated antigens
	 */
	ArrayList<Antigen<Double>> hypermutation(ArrayList<Antigen<Double>> antigens, double beta);
}
