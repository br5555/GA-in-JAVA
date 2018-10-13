package hr.fer.zemris.optjava.dz4.part1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Class implements {@linkplain IOptAlgorithm}. Class represents generalisation of 
 * genetic algorithm.
 * 
 * @author Branko
 *
 */
public class GeneticAlgorithm implements IOptAlgorithm<DoubleArraySolution> {

	/**
	 * private int which represents size of population
	 */
	private  int sizeOfPopulation;
	/**
	 * Private double which stores minimal error which result must satisfied
	 */
	private  double minError;
	/**
	 * Private int which stores number of iteration
	 */
	private  int maxIter;
	/**
	 * Private reference of {@linkplain Selection}
	 */
	private  Selection selection;
	/**
	 * Private double which is hyperparameter of mutattion
	 */
	private  double sigma;
	/**
	 * Private reference of {@linkplain IDecoder}
	 */
	private IDecoder<DoubleArraySolution> decoder;
	/**
	 * Private reference of {@linkplain IFunction}
	 */
	private  IFunction function;
	/**
	 * Private reference of {@linkplain INeighborhood}
	 */
	private INeighborhood<DoubleArraySolution> neighborhood;
	/**
	 * Private preference of {@linkplain Crossover}
	 */
	private Crossover crossover;
	/**
	 * Private reference of {@linkplain Mutation}
	 */
	private Mutation mutation;
	
	
	
	/**
	 * Main method from which program starts to execute. Program use arguments form command line
	 * 
	 * @param args first argument must be size of population,
	 *  second is satisfied error, third is maximal number of 
	 *  generation, fourth selection function, fifth sigma
	 */
	public static void main(String[] args) {
		int sizeOfPopulation=0;
		double minError=0;
		int maxIter=0;
		Selection selection=null;
		double sigma=0;
		
		if (args.length != 5) {
			errorMsg();
			System.out.println("Pri broju unosa");
		}
		try{
			sizeOfPopulation = Integer.parseInt(args[0].trim());
			minError = Double.parseDouble(args[1].trim());
			maxIter = Integer.parseInt(args[2].trim());
			sigma = Double.parseDouble(args[4].trim());
			args[3] = args[3].trim();
			
			if(args[3].startsWith("roulette")){
				selection = new RouletteWheel();
			}else if (args[3].startsWith("tournament")){
				String[] parts = args[3].split("\\:");
				selection = new Tournament(Integer.parseInt(parts[1].trim()));
			}
		}catch(NumberFormatException ex){
			System.out.println("Pri unosu");
			errorMsg();
		}
		Path path= Paths.get("./src/main/resources/prijenosna.txt");
		if(!Files.exists(path)){
			
			System.out.println("Na folderu");
			errorMsg();
		}

		Prijenosna sus = new Prijenosna();
		
		try(Stream<String> lines = Files.lines(path)){
			Iterator<String> iter = lines.iterator();
			
			
			while(iter.hasNext()){
				String line = iter.next();
				if(line.startsWith("[")){
					line = line.replaceAll("\\[", "").replaceAll("\\]", "");
					line = line.replaceAll("\\,", " ");
					line = 	line.trim();
					String[] values = line.split("\\s+");
					
					if(values.length != 6){
						System.out.println("Dear user, file is wrongly structured");
						System.exit(0);
					}
					double[] grad = new double[6];
					for(int i = 0; i < 6 ; i++){
						grad[i]= Double.parseDouble(values[i]);
					}
					sus.setValues(grad);
					
				}
				
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		double[] deltas = new double[sus.numberOfVariables()];


		for(int i =0, numVar = sus.numberOfVariables(); i<numVar; i++){
			deltas[i] = 2;

		}
		
		INeighborhood<DoubleArraySolution> neighborhood = new DoubleArrayNormNeighborhood(deltas);
		GeneticAlgorithm genAlgh = new GeneticAlgorithm(sizeOfPopulation, minError, maxIter, selection, sigma, new PassThroughDecoder(), sus, neighborhood, new BLXalpha(sigma), new Mutation());
		genAlgh.run();

	}
	

	
	/**
	 * Public constructor accepts desire settings.
	 * @param sizeOfPopulation size of population
	 * @param minError desire minimal acceptable error
	 * @param maxIter maximal number of iteration
	 * @param selection desire {@linkplain Selection}
	 * @param sigma hyperparameter for mutation
	 * @param decoder desire {@linkplain IDecoder}
	 * @param function desire {@linkplain IFunction }
	 * @param neighborhood desire {@linkplain INeighborhood}
	 * @param crossover desire {@linkplain Crossover}
	 * @param mutation desire {@linkplain Mutation}
	 */
	public GeneticAlgorithm(int sizeOfPopulation, double minError, int maxIter, Selection selection, double sigma,
			IDecoder<DoubleArraySolution> decoder, IFunction function, INeighborhood<DoubleArraySolution> neighborhood,
			Crossover crossover, Mutation mutation) {
		super();
		this.sizeOfPopulation = sizeOfPopulation;
		this.minError = minError;
		this.maxIter = maxIter;
		this.selection = selection;
		this.sigma = sigma;
		this.decoder = decoder;
		this.function = function;
		this.neighborhood = neighborhood;
		this.crossover = crossover;
		this.mutation = mutation;
	}



	/**
	 * Public getter which returns {@linkplain GeneticAlgorithm#selection}
	 * @return {@linkplain GeneticAlgorithm#selection}
	 */
	public Selection getSelection() {
		return selection;
	}
	/**
	 * Public getter which returns {@linkplain GeneticAlgorithm#maxIter}
	 * @return {@linkplain GeneticAlgorithm#maxIter}
	 */
	public int getMaxIter() {
		return maxIter;
	}
	/**
	 * Public getter which returns {@linkplain GeneticAlgorithm#minError}
	 * @return {@linkplain GeneticAlgorithm#minError}
	 */
	public double getMinError() {
		return minError;
	}
	/**
	 * Public getter which returns {@linkplain GeneticAlgorithm#sigma}
	 * @return {@linkplain GeneticAlgorithm#sigma}
	 */
	public double getSigma() {
		return sigma;
	}

	/**
	 * Public getter which returns {@linkplain GeneticAlgorithm#sizeOfPopulation}
	 * @return {@linkplain GeneticAlgorithm#sizeOfPopulation}
	 */
	public int getSizeOfPopulation() {
		return sizeOfPopulation;
	}

	
	/**
	 * Method display user a message of how to call a program from command line
	 */
	private static void errorMsg() {
		System.out.println("Dear user, first argument must be size of population,"
				+ " second is satisfied error, third is maximal number of "
				+ "generation, fourth selection function, fifth sigma");

		System.exit(0);
	}

	@Override
	public DoubleArraySolution run() {
		ArrayList<DoubleArraySolution> startPopulation = new ArrayList<>();
		double[] lower = new double[function.numberOfVariables()];
		double[] upper = new double[function.numberOfVariables()];

		for(int i = 0, size =function.numberOfVariables();i<size;i++ ){
			lower[i] = -30;
			upper[i] = 30;
		}
		for(int i = 0;i< sizeOfPopulation; i++){
			DoubleArraySolution temp = new DoubleArraySolution(function.numberOfVariables(), decoder, neighborhood, function, this);
			temp.randomize(new Random(), lower, upper);
			startPopulation.add(temp);
			
		}
		ArrayList<DoubleArraySolution> population = new ArrayList<>(startPopulation);
		for(int i =0; i< maxIter; i++){
			Collections.sort(population, new Comparator<DoubleArraySolution>() {
	    	    @Override
	    	    public int compare(DoubleArraySolution o1, DoubleArraySolution o2) {
	    	        if(o1.fitness > o2.fitness){
	    	        	return 1;
	    	        	}
	    	        else if(o1.fitness == o2.fitness){
	    	        	return 0;
	    	        	}
	    	        return -1;
	    	        	
	    	        
	    	    }
	    	});
			DoubleArraySolution parent1, parent2, child; 
			ArrayList<DoubleArraySolution> populationAll = new ArrayList<>(population);
			DoubleArraySolution[] populationArray  = Arrays.copyOf(population.toArray(), population.size(), DoubleArraySolution[].class);//new DoubleArraySolution[population.size()];
			

			for(int j = 0; j< sizeOfPopulation; j++){
				parent1 = selection.select(populationArray);
				parent2 = selection.select(populationArray);
				child = crossover.cross(parent1, parent2);
				mutation.mutate(decoder.decode(child), sigma);
				child.updateFitness();
				if(!populationAll.contains(child));
					populationAll.add(child);
			}
			Collections.sort(populationAll, new Comparator<DoubleArraySolution>() {
	    	    @Override
	    	    public int compare(DoubleArraySolution o1, DoubleArraySolution o2) {
	    	        if(o1.fitness > o2.fitness){
	    	        	return 1;
	    	        	}
	    	        else if(o1.fitness == o2.fitness){
	    	        	return 0;
	    	        	}
	    	        return -1;
	    	        	
	    	        
	    	    }
	    	});
			
			
			System.out.println("Best in new population is " + Arrays.toString(populationAll.get(0).values) );
			System.out.println("Error is: "+populationAll.get(0).fitness);
			if(populationAll.get(0).fitness <= minError){
				return populationAll.get(0);
			}
			for(int k = 0; k< sizeOfPopulation; k++){
				population.set(k,populationAll.get(k));
			}
			
		}
		return population.get(0);
		
	}

}
