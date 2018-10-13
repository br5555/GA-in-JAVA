package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;
import java.util.Random;

/**
 * Simulated annealing (SA) is a probabilistic technique for approximating the
 * global optimum of a given function. Specifically, it is a metaheuristic to
 * approximate global optimization in a large search space. It is often used
 * when the search space is discrete. Class implemnts {@linkplain IOptAlgorithm}
 * 
 * @author Branko
 *
 * @param <T>
 *            the class with which the algorithm works
 */
public class SimulatedAnnealing<T extends Comparable > implements IOptAlgorithm<T> {

	/**
	 * Private reference to {@linkplain IDecoder}
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
	 * Private reference to {@linkplain Random}
	 */
	private Random rand;
	/**
	 * Private reference to {@linkplain ITempSchedule}
	 */
	private ITempSchedule schedules;

	/**
	 * Public constructor which stores desire settings.
	 * 
	 * @param decoder reference to {@linkplain IDecoder}
	 * @param neighborhood reference to {@linkplain INeighborhood}
	 * @param startWith starting solution
	 * @param function reference to {@linkplain IFunction}
	 * @param minimize {@linkplain SimulatedAnnealing#minimize}
	 * @param schedules reference to {@linkplain ITempSchedule}
	 */
	public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith, IFunction function,
			boolean minimize, ITempSchedule schedules) {

		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
		this.schedules = schedules;
		rand = new Random();
	}

	@Override
	public T run() {
		T temp = startWith;
		
		if (schedules == null) {
			schedules = new GeometricTempSchedule(1000, 0.99, 100000, 1000);
		}
		for (int i = 0, size = schedules.getOuterLoopCounter(); i < size; i++) {
			
			double temperature = schedules.getNextTemperature();
			
			for (int j = 0, size1 = schedules.getInnerLoopCounter(); j < size1; j++) {
				
				double startValue = function.valueAt(decoder.decode(temp));
				if(startValue == 0){
					return temp;
				}
				System.out.println("Temp is "+temperature +" Error is :" +startValue+ " result is "+Arrays.toString(decoder.decode(temp)));
				T neigh = neighborhood.randomNeighbor(temp);
				double valueNeigh = function.valueAt(decoder.decode(neigh));

//				if (minimize) {
//					if (valueNeigh <= startValue) {
//						temp = neigh;
//					}
//					if (rand.nextDouble() < Math.exp(-(valueNeigh - startValue) / temperature)) {
//						temp = neigh;
//
//					}
//				} else {
//					if (valueNeigh >= startValue) {
//						temp = neigh;
//					}
//					if (rand.nextDouble() < Math.exp((valueNeigh - startValue) / temperature)) {
//						temp = neigh;
//
//					}
//				}
				
				
				if (minimize) {
					if ((neigh).compareTo(temp) <= 0) {
						temp = neigh;
					}
					else if (rand.nextDouble() < Math.exp(-(valueNeigh - startValue) / temperature)) {
						temp = neigh;

					}
				} else {
					if ((neigh).compareTo(temp) >= 0) {
						temp = neigh;
					}
					else if (rand.nextDouble() < Math.exp((valueNeigh - startValue) / temperature)) {
						temp = neigh;

					}
				}
			}
		}
		return temp;
	}

}
