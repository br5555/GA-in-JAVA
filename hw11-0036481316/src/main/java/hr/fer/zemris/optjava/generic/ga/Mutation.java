package hr.fer.zemris.optjava.generic.ga;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

/**
 * Class represnts muatation of GA algorithm
 * 
 * @author Branko
 *
 */
public class Mutation {

	/**
	 * hyper parameter of mutation
	 */
	private int alpha;
	/**
	 * private instance of {@linkplain IRNGProvider}
	 */
	private IRNG irng;

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param alpha
	 *            desire hyper parameter
	 * @param irng
	 *            instance of {@linkplain IRNG}
	 */
	public Mutation(int alpha, IRNG irng) {
		super();
		this.alpha = alpha;
		this.irng = irng;
	}

	/**
	 * Public method which mutate desire entity
	 * 
	 * @param orginal
	 *            unmutated desire entity
	 * @return mutate desire entity
	 */
	public int[] mutate(int[] orginal) {
		int index = irng.nextInt(0, orginal.length);
		int[] mutate = new int[orginal.length];

		for (int i = 0, size = orginal.length; i < size; i++) {
			mutate[i] = orginal[i];
		}

		mutate[index] += irng.nextInt(0, (alpha) - (alpha / 2));

		return mutate;
	}

}
