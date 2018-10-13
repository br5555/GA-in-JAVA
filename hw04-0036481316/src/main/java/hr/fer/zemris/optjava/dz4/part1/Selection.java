package hr.fer.zemris.optjava.dz4.part1;

/**
 * Interface represent generalisation of selection in geneteic algorithm
 * 
 * @author Branko
 *
 */
public interface Selection {

	/**
	 * Method returns unit from the group
	 * @param group group of units
	 * @return unit as a result of selection
	 */
	DoubleArraySolution select(DoubleArraySolution[] group);
}
