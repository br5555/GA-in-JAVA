package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Algorithm6 {
	/**
	 * Private list for storing already seen results
	 */
	private List<String> setVec;

	/**
	 * Private reference to clauses expression {@link SATFormula}
	 */
	private SATFormula formula;
	/**
	 *  Private reference to {@link SATFormulaStats}
	 */
	private SATFormulaStats formulaStats;
	/**
	 * Private double array which stores goodness of neighborhood
	 */
	private double[] goodness;
	/**
	 * Private number which tells from how many best neighbours will be new solution chosen 
	 */
	private int numberOfBest=2;
	/**
	 * Private {@linkplain MutableBitVector} for storing best neighbours
	 */
	private MutableBitVector[] best; 
	
	/**
	 * Public constructor which stores desire clauses expression.
	 * 
	 * @param formula desire clauses expression
	 */
	public Algorithm6(SATFormula formula) {
		this.formula = formula;
		formulaStats = new SATFormulaStats(formula);
		goodness = new double[formula.getNumberOfVariables()];
    	best = new MutableBitVector[numberOfBest];
		setVec = new ArrayList<>();


	}
	
	/**
	 * Protected method which executes algorithm
	 * 
	 * @param maxIter number of iteration
	 */
	protected void executeAlgh(int maxIter) {
		int numOfvar = formula.getNumberOfVariables();
		int numOfClauses = formula.getNumberOfClauses();
		
		MutableBitVector vecStart = new MutableBitVector(formula.getBin((int)(Math.random() * ((Math.pow(2, formula.getNumberOfVariables())-1)))
				, numOfvar));
		MutableBitVector[] neigh;
		BitVectorNGenerator generator;
		
	    for(int i = 0; i < maxIter; i++){
			generator = new BitVectorNGenerator(vecStart);

	    	formulaStats.setAssignment(vecStart, true);
	    	
	    	if(formulaStats.isSatisfied()){
	    		if(!setVec.contains(vecStart.toString())){
					setVec.add(vecStart.toString());
					System.out.println(vecStart.toString());
				}
	    	}
	    	neigh = generator.createNeighborhood();
	    	
	    	for(int j = 0, size = neigh.length; j< size; j++){
	    		formulaStats.setAssignment(neigh[j], false);
	    	
	    		neigh[j].setGoodness(formulaStats.getPercentageBonus());
	    		
	    	}
	    	
	    	Collections.sort(Arrays.asList(neigh), new Comparator<MutableBitVector>() {
	    	    @Override
	    	    public int compare(MutableBitVector o1, MutableBitVector o2) {
	    	        if(o1.getGoodness() > o2.getGoodness()){
	    	        	return -1;
	    	        	}
	    	        else if(o1.getGoodness() == o2.getGoodness()){
	    	        	return 0;
	    	        	}
	    	        return 1;
	    	        	
	    	        
	    	    }
	    	});
	    	

	    	vecStart = neigh[(int)(Math.random()*(numberOfBest-1))];
	    	
	    }	
	}
}
