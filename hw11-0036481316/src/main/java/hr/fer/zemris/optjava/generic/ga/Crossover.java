package hr.fer.zemris.optjava.generic.ga;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
/**
 * Class implements GA crossover
 * @author Branko
 *
 */
public class Crossover {

	/**
	 * Private instance of {@linkplain IRNGProvider}
	 */
	private IRNG irng;
	/**
	 * hyper parameter of crossover
	 */
	private double alpha;

	/**
	 * Public constructor accepts desire settings
	 * @param alpha desire hyper parameter of crossover
	 * @param irng desire instance of {@linkplain IRNG}
	 */
	public Crossover(int alpha, IRNG irng) {
		super();
		this.alpha = alpha;
		this.irng = irng;
	}
	
	/**
	 * Method implements GA crossover
	 * @param parent1 first parent
	 * @param parent2 second parent
	 * @return child
	 */
	public GASolutionVector crossover(GASolutionVector parent1, GASolutionVector parent2) {
		GASolutionVector child = (GASolutionVector) parent1.duplicate();

		for (int i = 0, size = parent1.data.length; i < size; i++) {
			
			if (irng.nextDouble() < alpha) 	child.data[i] = parent1.data[i];
			else child.data[i] = parent2.data[i];
		}
		
		return child;
	}
}
