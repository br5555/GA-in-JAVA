package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Collections;

/**
 * Class implements greedy search algorithm. A greedy algorithm is 
 * an algorithmic paradigm that follows the problem solving heuristic 
 * of making the locally optimal choice at each stage with the hope of
 *  finding a global optimum. In many problems, a greedy strategy does 
 *  not in general produce an optimal solution, but nonetheless a greedy 
 *  heuristic may yield locally optimal solutions that approximate a global
 *   optimal solution in a reasonable time.
 *   
 * @author Branko
 *
 */
public class Algorithm2 {
	/**
	 * Private reference to clauses expression {@link SATFormula}
	 */
	private SATFormula formula;
	/**
	 *  Private reference to {@link SATFormulaStats}
	 */
	private SATFormulaStats formulaStats;

	/**
	 * Public constructor which stores desire clauses expression.
	 * 
	 * @param formula desire clauses expression
	 */
	public Algorithm2(SATFormula formula) {
		this.formula = formula;
		formulaStats = new SATFormulaStats(formula);

	}
	
	
	/**
	 * Private method which implements greedy search algorithm.
	 */
	protected void executeAlgh() {
		int numOfvar = formula.getNumberOfVariables();
		int numOfClauses = formula.getNumberOfClauses();
		MutableBitVector vecStart = new MutableBitVector(formula.getBin((int)(Math.random() * ((Math.pow(2, formula.getNumberOfVariables())-1) + 1))
				, numOfvar));
	    for(int i = 0; i < 100000; i++){
	    	formulaStats.setAssignment(vecStart, false);
	    	int fitStart = formulaStats.getNumberOfSatisfied();
	    	if(fitStart == numOfClauses){
	    		System.out.println(vecStart.toString());
	    	}
	    	BitVectorNGenerator generator = new BitVectorNGenerator(vecStart);
	    	MutableBitVector[] vectorNeigh = generator.createNeighborhood();
	    	MutableBitVector[] best = new MutableBitVector[numOfvar];
	    	int fitMax=fitStart;
	    	
	    	int numOfBest = 0;
	    	for(int j = 0; j < numOfvar; j++){
	    		
		    	formulaStats.setAssignment(vectorNeigh[j], false);
		    	int fitTemp = formulaStats.getNumberOfSatisfied();
		    	if(fitMax>fitTemp){
		    		fitMax = fitTemp;
		    		Arrays.fill(best, null);
		    		numOfBest=0;
		    		best[numOfBest++] = vectorNeigh[j];
		    	}else if(fitMax==fitTemp){
		    		best[numOfBest++] = vectorNeigh[j];
		    	}

	    	}
	    	if(numOfBest == 0){
	    		System.out.println("Dear user, algorithm stuck in local optimum");
	    		System.exit(0);
	    	}
	    	vecStart = best[(int)(Math.random() * (numOfBest-1))];
	    	
	    }

		
	}
	
	
	
}
