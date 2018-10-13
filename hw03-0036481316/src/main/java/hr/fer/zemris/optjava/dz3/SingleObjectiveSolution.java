package hr.fer.zemris.optjava.dz3;

/**
 * Public class represents solution of searching for optimum
 * 
 * @author Branko
 *
 */
public class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution>{

	/**
	 * value of fitness function for {@linkplain SingleObjectiveSolution} as input
	 */
	public double fitness;
	/**
	 * value of function for {@linkplain SingleObjectiveSolution} as input
	 */
	public double value;
	/**
	 * Private reference to {@linkplain IDecoder}
	 */
	protected IDecoder decoder;
	/**
	 * Private reference to {@linkplain INeighborhood}
	 */
	protected INeighborhood neighborhood;
	/**
	 * Private reference to {@linkplain IFunction}
	 */
	protected IFunction function;
	/**
	 * Private reference to {@linkplain IOptAlgorithm}
	 */
	protected IOptAlgorithm algorithm;

	/**
	 * Public constructor which accept desire settings.
	 * 
	 * @param decoder reference to {@linkplain IDecoder }
	 * @param neighborhood reference to {@linkplain INeighborhood} 
	 * @param function reference to {@linkplain IFunction}
	 * @param algorithm reference to {@linkplain IOptAlgorithm}
	 */
	public SingleObjectiveSolution(IDecoder decoder, INeighborhood neighborhood, IFunction function,
			IOptAlgorithm algorithm) {
		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.function = function;
		this.algorithm = algorithm;
	}

	/**
	 * Public method which compares to instance of {@linkplain SingleObjectiveSolution}
	 * 
	 * @param singleObj second instance of {@linkplain SingleObjectiveSolution}
	 * 
	 * @return 1 if first is better, 0 if there are equals and otherwise 0
	 */
	public int compareTo(SingleObjectiveSolution singleObj){
		int value = Double.compare(this.fitness, singleObj.fitness);
		int value1= Double.compare(this.value, singleObj.value);
		if(value >0) return 1;
		if(value1 > 0) return 1;
		if(value == 0 && value1 == 0) return 0;
		return -1;
	}
	

}
