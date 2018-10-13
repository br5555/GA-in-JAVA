package hr.fer.zemris.optjava.dz8.df;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import hr.fer.zemris.optjava.dz8.tdnn.pso.IFunction;

public class DifferentialEvolution {

	private double Cr;
	private double[] Fs;
	private int maxIteration;
	private IMutation<SolutionVector> mutation;
	private ICrossover<SolutionVector> crossover;
	private int sizeOfPopulation;
	private double[] mins;
	private double[] maxs;
	private int dim;
	private Random rand;
	private double maxError;
	private IFunction<Double> function;
	private SolutionVector globalBest;

	
	
	public DifferentialEvolution(int maxIteration, IMutation<SolutionVector> mutation,IFunction<Double> function,
			ICrossover<SolutionVector> crossover, int sizeOfPopulation, double[] mins, double[] maxs, int dim, double maxError) {
		super();
		this.maxIteration = maxIteration;
		this.mutation = mutation;
		this.crossover = crossover;
		this.sizeOfPopulation = sizeOfPopulation;
		this.mins = mins;
		this.maxs = maxs;
		this.dim = dim;
		this.function = function;
		rand = new Random();
		this.maxError = maxError;
		Fs = new double[1];
		for(int i = 0; i< 1 ; i++){
			Fs[i] = 1.77;
		}
		this.Cr = 0.25;
	}


	public double[] run(){
		
		ArrayList<SolutionVector> population = new ArrayList<>();
		
		for(int i = 0 ; i< sizeOfPopulation; i++){
			ArrayList<Double> values = new ArrayList<>();
			
			for(int j = 0; j< dim; j++){
				values.add( rand.nextDouble());
			}
			SolutionVector sol = new SolutionVector(values,function);
			population.add(sol);
		}
		
		Collections.sort(population, Collections.reverseOrder());
		System.out.println("Bets is "+ population.get(0).getFitness());
		globalBest = population.get(0).duplicate();
		
		if(globalBest.getFitness()<maxError){
			Double[] arrayOFPopulation = new Double[population.get(0).getValues().size()];
			return ArrayUtils.toPrimitive(globalBest.getValues().toArray(arrayOFPopulation));
		}
		
		for(int i = 0; i<  maxIteration; i++){
			ArrayList<SolutionVector> newPopulation = new ArrayList<>();
			
			for(int j = 0 ; j< sizeOfPopulation; j++){
				ArrayList<SolutionVector> vectors = new ArrayList<>();
				SolutionVector targetVector = population.get(rand.nextInt(sizeOfPopulation)).duplicate();
				
				SolutionVector temp = null;
				
				for(int k = 0, numOfLin = 2*Fs.length+1; k<numOfLin;k++){
					do{
						
						temp = population.get(rand.nextInt(sizeOfPopulation));
					}while(vectors.contains(temp) || targetVector.equals(temp));
					
					vectors.add(temp.duplicate());
				}
				
				if(i > 40){
					int zzz = 10;
					zzz -=2;
				}
				
				
				SolutionVector mutant = mutation.mutate(vectors, Fs).duplicate();
				SolutionVector testVector = crossover.crossover(mutant, targetVector, Cr).duplicate();
				if(targetVector.compareTo(testVector)<=0){
					newPopulation.add(testVector.duplicate());
				}else{
					newPopulation.add(targetVector.duplicate());
				}
			}
			
			population = new ArrayList<>(newPopulation);
			Collections.sort(population, Collections.reverseOrder());
			
			if(globalBest.getFitness() > population.get(0).getFitness()){
				globalBest = population.get(0).duplicate();
			}
			
			if(globalBest.getFitness()<maxError){
				Double[] arrayOFPopulation = new Double[population.get(0).getValues().size()];
				return ArrayUtils.toPrimitive(globalBest.getValues().toArray(arrayOFPopulation));
			}
			
			
			System.out.println(i+" Bets is "+ globalBest.getFitness());
		}
		
		Double[] arrayOFPopulation = new Double[population.get(0).getValues().size()];
		return ArrayUtils.toPrimitive(population.get(0).getValues().toArray(arrayOFPopulation));
	}
}
