package hr.fer.zemris.optjava.generic.ga;

import java.util.Arrays;
import java.util.Collections;

/**
 * Class implements selection of GA algorithm
 * @author Branko
 *
 */
public class Selection {

	/**
	 * Method which selects best entity
	 * @param parents array of {@linkplain GASolutionVector } potential best solution 
	 * @return best entity
	 */
	public GASolutionVector select(GASolutionVector[] parents) {
		
		Collections.sort(Arrays.asList(parents));
		
		return parents[0];
		
	}

}
