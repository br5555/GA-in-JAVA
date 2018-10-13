package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
/**
 * Class implements {@linkplain Selection}.Tournament selection is 
 * a method of selecting an individual from a population of individuals in a genetic algorithm.
 * The winner of each tournament (the one with the best fitness) is selected for crossover.
 * @author Branko
 *
 */
public class Tournament implements Selection{
	/**
	 * Number of units that participate in tournament
	 */
	private int n;
	
	/**
	 * Public constructor that accepts desire settings.
	 * 
	 * @param n desire number of units that participate in tournament
	 */
	public Tournament(int n) {
		this.n = n;
	}

	@Override
	public DoubleArraySolution select(DoubleArraySolution[] group) {
		Random rand = new Random();
		ArrayList<DoubleArraySolution> parents = new ArrayList<>();
		for(int i = 0; i<n; i++){
			int index = rand.nextInt(group.length);
			parents.add(group[index]);
		}
		Collections.sort(parents, new Comparator<DoubleArraySolution>() {
    	    @Override
    	    public int compare(DoubleArraySolution o1, DoubleArraySolution o2) {
    	        if(o1.fitness > o2.fitness){
    	        	return -1;
    	        	}
    	        else if(o1.fitness == o2.fitness){
    	        	return 0;
    	        	}
    	        return 1;
    	        	
    	        
    	    }
    	});
		return parents.get(0);
	}
}
