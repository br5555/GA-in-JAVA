package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class implements GSAT algorithm. GSAT algorithm is local search algorithm.
 * GSAT makes the change which minimizes the number of unsatisfied clauses in 
 * the new assignment, or with some probability picks a variable at random.
 * 
 * @author Branko
 *
 */
public class Algorithm3 {
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
	 * Public constructor which stores desire clauses expression.
	 * 
	 * @param formula desire clauses expression
	 */
	public Algorithm3(SATFormula formula) {
		this.formula = formula;
		formulaStats = new SATFormulaStats(formula);
		setVec = new ArrayList<>();
	}
	
	/**
	 * Private method which implements GSAT algorithm.
	 * 
	 * @param maxFlips number of iteration of inner loop
	 * @param maxTries number of iteration of outer loop
	 */
	protected void executeAlgh(int maxFlips,int maxTries) {
		boolean find = false;
		if(maxFlips <= 0 || maxTries <= 0){
			throw new IllegalArgumentException("Dear user, max flips and max tries"
					+"cannot be zero or negative");
		}
		
		int numOfvar = formula.getNumberOfVariables();
		int numOfClauses = formula.getNumberOfClauses();
		
		for(int i =0; i< maxTries; i++){
			MutableBitVector vecStart = new MutableBitVector(formula.getBin((int)(Math.random() * ((Math.pow(2, formula.getNumberOfVariables())-1) + 1))
					, formula.getNumberOfVariables()));
			
			for(int j = 0; j < maxFlips; j++){
				formulaStats.setAssignment(vecStart, false);
				int fit = formulaStats.getNumberOfSatisfied();
				
				if(fit == numOfClauses){
					find = true;
					if(!setVec.contains(vecStart.toString())){
						setVec.add(vecStart.toString());
						System.out.println(vecStart.toString());
					}
					
				}
				
				BitVectorNGenerator generator = new BitVectorNGenerator(vecStart);
		    	MutableBitVector[] vectorNeigh = generator.createNeighborhood();
		    	MutableBitVector[] best = new MutableBitVector[numOfvar];
		    	int fitMax=0;
		    	int numOfBest = 0;
		    	for(int k = 0; k < numOfvar; k++){
			    	formulaStats.setAssignment(vectorNeigh[k], false);
			    	int fitTemp = formulaStats.getNumberOfSatisfied();
			    	if(fitMax<fitTemp){
			    		fitMax = fitTemp;
			    		numOfBest=0;
			    		Arrays.fill(best, null);
			    		best[numOfBest++] = vectorNeigh[k];
			    	}else if(fitMax==fitTemp){
			    		best[numOfBest++] = vectorNeigh[k];
			    	}

		    	}
		    	
		    	vecStart = best[(int)(Math.random() * (numOfBest))];
			}
		}
		if(!find){
			System.out.println("Solution for formula is not found");
		}
		
	}
}
