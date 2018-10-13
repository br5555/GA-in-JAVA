package hr.fer.zemris.optjava.dz3;

/**
 * Class implements {@linkplain IOptAlgorithm}. A greedy algorithm is an
 * algorithmic paradigm that follows the problem solving heuristic of making the
 * locally optimal choice at each stage with the hope of finding a global
 * optimum.
 * 
 * @author Branko
 *
 * @param <T> the class with which algorithm works 
 */
public class GreedyAlgorithm<T extends Comparable> implements IOptAlgorithm<T> {

	/**
	 * Private refernce to {@linkplain IDecoder}
	 */
	private IDecoder<T> decoder;
	/**
	 * Private reference to {@linkplain INeighborhood}
	 */
	private INeighborhood<T> neighborhood;
	/**
	 * Starting solution
	 */
	private T startWith;
	/**
	 * Private reference to {@linkplain IFunction} which is optimise.
	 */
	private IFunction function;
	/**
	 * Private flag which tells is problem finding minimum or maximum
	 */
	private boolean minimize;

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param decoder reference to {@linkplain IDecoder}
	 * @param neighborhood reference to {@linkplain INeighborhood} 
	 * @param startWith starting solution
	 * @param function reference to {@linkplain IFunction}
	 * @param minimize {@linkplain GreedyAlgorithm #minimize}
	 */
	public GreedyAlgorithm(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith, IFunction function,
			boolean minimize) {
		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
	}

	@Override
	public T run() {
		T temp = startWith;
		
		do {
			System.out.println(" Error is :" +function.valueAt(decoder.decode(temp))+ "result is "+decoder.decode(temp));

			T neighbor = neighborhood.randomNeighbor(startWith);
			
			if (minimize) {
				if ((neighbor).compareTo(temp) <= 0) {
					temp = neighbor;
				}
				break;
			} else {
				if ((neighbor).compareTo(temp) >= 0) {
					temp = neighbor;
				}
				break;
			}
		} while (true);
		
		return temp;
	}

}
