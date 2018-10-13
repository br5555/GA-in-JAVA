package hr.fer.zemris.optjava.dz3;

/**
 * Interface represent decoder and has generalisation function that every decoder has.
 * 
 * @author Branko
 *
 * @param <T>  class that needs to be decoded to double value.
 */

public interface IDecoder<T> {

	/**
	 * Public method which accepts coded class and decoded it into double array
	 * 
	 * @param t instance of coded class
	 * @return decoded value as double array
	 */
	double[] decode(T t);
	
	/**
	 * Public method which accepts coded class and decoded it into double array
	 * 
	 * @param t  instance of coded class
	 * @param array decoded value as double array
	 */
	void decode(T t, double[] array);
}
