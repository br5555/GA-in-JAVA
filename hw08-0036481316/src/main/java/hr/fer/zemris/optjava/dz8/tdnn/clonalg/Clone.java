package hr.fer.zemris.optjava.dz8.tdnn.clonalg;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class implements process of cloning a antigen
 * 
 * @author Branko
 *
 */
public class Clone {

	/**
	 * Method which clones antigens
	 * 
	 * @param beta
	 *            hyper parameter
	 * @param antigens
	 *            population of antigens
	 * @return cloned population of antigens
	 */
	public ArrayList<Antigen<Double>> clonning(double beta, ArrayList<Antigen<Double>> antigens) {
		Collections.sort(antigens);
		ArrayList<Antigen<Double>> clones = new ArrayList<>(antigens);

		for (int i = 0, size = antigens.size(); i < size; i++) {
			int numClones = (int) (beta * size / (i + 1));
			for (int j = 0; j < numClones; j++) {
				clones.add(antigens.get(i));
			}
		}
		return clones;

	}
}