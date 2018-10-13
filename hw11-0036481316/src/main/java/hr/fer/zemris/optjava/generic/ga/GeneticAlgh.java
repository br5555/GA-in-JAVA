package hr.fer.zemris.optjava.generic.ga;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.provimpl.ThreadLocalEvaluator;
import hr.fer.zemris.optjava.rng.IRNG;

/**
 * Class represents simple GA Algorithm with parallelised evaluation of GA solution
 * 
 * @author Branko
 *
 */
public class GeneticAlgh {

	/**
	 * size of population
	 */
	private int sizeOfPopulation;
	/**
	 * solution dimension
	 */
	private int dimOfSolution;
	/**
	 * hyper parameter
	 */
	private double alpha;
	/**
	 * hyper parameter
	 */
	private double beta;
	/**
	 * private instance of {@linkplain IRNG}
	 */
	private IRNG irng;
	/**
	 * color
	 */
	private int color;
	/**
	 * height of image
	 */
	private int height;
	/**
	 * width of image
	 */
	private int width;
	/**
	 * maximal number of iteration
	 */
	private int maxIteration;
	/**
	 * hyperparameter of selection
	 */
	private int kSel;
	/**
	 * private instance of {@linkplain Selection}
	 */
	private Selection selection;
	/**
	 * private insatnce of {@linkplain Crossover}
	 */
	private Crossover crossover;
	/**
	 * private instance of {@linkplain Mutation}
	 */
	private Mutation mutation;
	/**
	 * private template {@linkplain GrayScaleImage}
	 */
	private GrayScaleImage template;

	
	
	/**
	 * Public constructor accepts desire settings
	 * @param sizeOfPopulation size of population
	 * @param numOfSqu number of rectangles
	 * @param irng desire instance of {@linkplain IRNG}
	 * @param maxIteration maximal number of iteration
	 * @param kSel hyper parameter of selection
	 * @param selection desire instance of {@linkplain Selection} 
	 * @param crossover desire instance of {@linkplain Crossover} 
	 * @param mutation desire instance of {@linkplain Mutation} 
	 * @param template desire template
	 */
	public GeneticAlgh(int sizeOfPopulation, int numOfSqu, IRNG irng, int maxIteration, int kSel,
			Selection selection, Crossover crossover, Mutation mutation, GrayScaleImage template) {
		super();
		this.sizeOfPopulation = sizeOfPopulation;
		this.dimOfSolution = 1+5*numOfSqu;
		this.irng = irng;
		this.maxIteration = maxIteration;
		this.kSel = kSel;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.template = template;
		this.color = 255;
		this.height = template.getHeight();
		this.width = template.getWidth();
	}

	/**
	 * Method which executes GA algorithm
	 * @return best solution {@linkplain GASolutionVector}
	 */
	public  GASolutionVector run() {
		GASolutionVector[] population = new GASolutionVector[sizeOfPopulation];
		
		ConcurrentLinkedQueue<GASolutionVector> queue = new ConcurrentLinkedQueue<GASolutionVector>();
		
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		Thread[] workers = new Thread[numberOfThreads];
		ThreadLocalEvaluator eval = new ThreadLocalEvaluator(template);
		
		

		for (int i = 0; i < sizeOfPopulation; i++) {
			population[i] = new GASolutionVector();
			population[i].data = new int[dimOfSolution];
			
			population[i].data[0] = irng.nextInt(0,color);

			for (int j = 1; j < dimOfSolution; j++) {

				if ((j - 1) % 5 == 0)
					population[i].data[j] = irng.nextInt(0,color);
				else if ((j - 1) % 4 == 0)
					population[i].data[j] = irng.nextInt(0,height);
				else if ((j - 1) % 3 == 0)
					population[i].data[j] = irng.nextInt(0,width);
				else if ((j - 1) % 2 == 0)
					population[i].data[j] = irng.nextInt(0,height);
				else
					population[i].data[j] = irng.nextInt(0,width);

			}
			queue.add(population[i]);
		}

		for (int j = 0; j < numberOfThreads; j++) {
			workers[j] = new Thread(new Consumer(queue, template, eval));

		}

		
		for (int j = 0; j < numberOfThreads; j++) {
			workers[j].start();

		}
		
		for (int j = 0; j < numberOfThreads; j++) {
			while (true) {
				try {
					workers[j].join();
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		
		
		GASolutionVector[] newPopulation = new GASolutionVector[sizeOfPopulation];

		for (int i = 0; i < maxIteration; i++) {

			for (int k = 0; k < sizeOfPopulation; k++) {

				GASolutionVector[] selection1 = new GASolutionVector[kSel];
				int index = irng.nextInt(0,sizeOfPopulation);

				for (int j = 0; j < kSel; j++) {
					selection1[j] = (GASolutionVector) population[(j + index) % sizeOfPopulation].duplicate();
				}
				GASolutionVector parent1 = selection.select(selection1);

				index = irng.nextInt(0,sizeOfPopulation);

				for (int j = 0; j < kSel; j++) {
					selection1[j] = (GASolutionVector) population[(j + index) % sizeOfPopulation].duplicate();
				}
				GASolutionVector parent2 = selection.select(selection1);

				GASolutionVector child = crossover.crossover(parent1, parent2);
				child.data = mutation.mutate(child.data);
				newPopulation[k] = child;
				queue.add(child);

			}
			
			
			

			for (int j = 0; j < numberOfThreads; j++) {
				workers[j] = new Thread(new Consumer(queue, template, eval));

			}

			
			for (int j = 0; j < numberOfThreads; j++) {
				workers[j].start();

			}
			
			for (int j = 0; j < numberOfThreads; j++) {
				while (true) {
					try {
						workers[j].join();
						break;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}


		}
		Collections.sort(Arrays.asList(population));
		return population[0];

	}

	/**
	 * Method which evaluates population of {@linkplain GASolutionVector}
	 * @param population unevaluated population of {@linkplain GASolutionVector}
	 * @return evaluated population of {@linkplain GASolutionVector}
	 */
	private GASolutionVector[] evaluatePopulation(GASolutionVector[] population) {
		int numOfThreads = Runtime.getRuntime().availableProcessors();
		Thread[] workers = new Thread[numOfThreads];

		for (int i = 0; i < sizeOfPopulation; i += numOfThreads) {

			for (int j = 0; j < numOfThreads; j++) {
				final int index = i + j;

				Runnable job = new Runnable() {
					@Override
					public void run() {
						ThreadLocalEvaluator eval = new ThreadLocalEvaluator(template);
						eval.evaluate(population[index]);
					}
				};

				workers[j] = new Thread(job);
			}

			for (int j = 0; j < numOfThreads; j++) {
				workers[j].start();

			}
			for (int j = 0; j < numOfThreads; j++) {
				while (true) {
					try {
						workers[j].join();
						break;
					} catch (InterruptedException ex) {

					}
				}

			}

		}

		return population;
	}

}
