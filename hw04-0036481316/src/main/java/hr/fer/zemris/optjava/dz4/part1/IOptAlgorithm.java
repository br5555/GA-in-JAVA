package hr.fer.zemris.optjava.dz4.part1;

/**
 * Interface represent generalisation function for finding optimum
 * 
 * @author Branko
 *
 * @param <T> instance of class which represent optimum
 */
public interface IOptAlgorithm<T> {

	T run();
}
