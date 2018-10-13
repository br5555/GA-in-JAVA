package hr.fer.zemris.optjava.dz3;

/**
 * Interface thath represent cooling plan need for {@linkplain SimulatedAnnealing}
 * 
 * @author Branko
 *
 */
public interface ITempSchedule {

	/**
	 * Public method for calculating next temperature for {@linkplain SimulatedAnnealing}
	 * 
	 * @return next temperature as double
	 */
	double getNextTemperature();
	
	/**
	 * Public getter which return number of iteration of outer loop
	 * 
	 * @return number of iteration of outer loop
	 */
	int getInnerLoopCounter();
	
	/**
	 * Public getter which return number of iteration of inner loop
	 * 
	 * @return number of iteration of inner loop
	 */
	int getOuterLoopCounter();
}
