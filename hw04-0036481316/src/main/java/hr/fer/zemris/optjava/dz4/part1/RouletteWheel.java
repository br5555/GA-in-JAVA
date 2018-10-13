package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Class implements {@linkplain Selection}. Fitness proportionate selection,
 *  also known as roulette wheel selection, is a genetic operator used in 
 *  genetic algorithms for selecting potentially useful solutions for recombination.
 *  In fitness proportionate selection, as in all selection methods, the fitness function 
 *  assigns a fitness to possible solutions or chromosomes. This fitness level is used to 
 *  associate a probability of selection with each individual chromosome.
 *  
 * @author Branko
 *
 */
public class RouletteWheel implements Selection{

	@Override
	public DoubleArraySolution select(DoubleArraySolution[] group) {
		Random rand = new Random();
		Collections.sort(Arrays.asList(group), new Comparator<DoubleArraySolution>() {
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
		
		double sumFitness = 0;
		for(int i = 0, size = group.length; i<size; i++){
			group[i].fitness -=group[0].fitness;
			sumFitness += group[i].fitness;
		}
		
		DoubleArraySolution roulette = null;
		double sum = 0;
		for(int i = 0, size = group.length-1; i<size; i++){
			double wheel = rand.nextDouble();
			sum += group[i].fitness/sumFitness;
			if(wheel<sum){
				roulette = group[i];
				break;
			}
		}
		if(roulette == null){
			roulette = group[group.length-1];
		}
		return roulette;
	}

}
