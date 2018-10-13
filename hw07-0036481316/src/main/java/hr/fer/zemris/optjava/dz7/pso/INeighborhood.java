package hr.fer.zemris.optjava.dz7.pso;

import java.util.ArrayList;

/**
 * Class which represent neighbourhood 
 * @author Branko
 *
 * @param <T> generic parameter
 */
public interface INeighborhood<T> {
 
	/**
	 * Method scans neighbourhood 
	 * @param particle population of particles
	 */
	void scan(ArrayList<T> particle);
	
	/**
	 * Method returns best neighbour values for desire particle
	 * @param index index of desire particle
	 * @return  best neighbour values
	 */
	double[] bestValues(int index);
}
