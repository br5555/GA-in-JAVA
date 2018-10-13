package hr.fer.zemris.optjava.generic.ga;

import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

/**
 * Class which implements {@linkplain Runnable} and implements job for
 * generating desire number of children in GA
 * 
 * @author Branko
 *
 */
public class Consumer2 implements Runnable {

	/**
	 * private instance of {@linkplain ConcurrentLinkedQueue} which is source
	 * queue
	 */
	private ConcurrentLinkedQueue<JobDescription> queue;
	/**
	 * private instance of {@linkplain ConcurrentLinkedQueue} which is output
	 * queue
	 */
	private ConcurrentLinkedQueue<GASolutionVector> queue2;
	/**
	 * hyper parameter for selction
	 */
	private int kSel;
	/**
	 * Private instance of {@linkplain Selection}
	 */
	private Selection selection;
	/**
	 * Private instance of {@linkplain Crossover}
	 */
	private Crossover crossover;
	/**
	 * Private instance of {@linkplain Mutation}
	 */
	private Mutation mutation;
	/**
	 * Private instance of {@linkplain IRNGProvider}
	 */
	private IRNG irng;
	/**
	 * Private instance of {@linkplain IGAEvaluator}
	 */
	IGAEvaluator<int[]> evalu;

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param queue
	 *            desire input queue
	 * @param queue2
	 *            desire output queue
	 * @param template
	 *            desire template image
	 * @param irngProv
	 *            desire instance of {@linkplain IRNGProvider}
	 */
	Consumer2(ConcurrentLinkedQueue<JobDescription> queue, ConcurrentLinkedQueue<GASolutionVector> queue2,
			GrayScaleImage template, IRNG irng, IGAEvaluator<int[]> evalu,Selection selection,Mutation mutation,Crossover crossover, int kSel) {
		this.queue = queue;
		this.queue2 = queue2;
		this.irng = irng;
		this.evalu = evalu;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.kSel = kSel;
	}

	@Override
	public void run() {
		JobDescription vec;

		while ((vec = queue.poll()) != null) {
			for (int i = 0; i < vec.desireNum; i++) {
				GASolutionVector[] selection1 = new GASolutionVector[kSel];
				int index = irng.nextInt(0, vec.newPopulation.length);

				for (int j = 0; j < kSel; j++) {
					selection1[j] = (GASolutionVector) vec.newPopulation[(j + index) % vec.newPopulation.length]
							.duplicate();
				}
				GASolutionVector parent1 = selection.select(selection1);

				index = irng.nextInt(0, vec.newPopulation.length);

				for (int j = 0; j < kSel; j++) {
					selection1[j] = (GASolutionVector) vec.newPopulation[(j + index) % vec.newPopulation.length]
							.duplicate();
				}
				GASolutionVector parent2 = selection.select(selection1);

				GASolutionVector child = crossover.crossover(parent1, parent2);
				child.data = mutation.mutate(child.data);
				evalu.evaluate(child);
				queue2.add(child);
			}

		}

	}

}
