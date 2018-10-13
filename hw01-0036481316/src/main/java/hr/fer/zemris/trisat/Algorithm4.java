package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class implements WalkSAT algorithm. WalkSAT algorithm is local search
 * algorithm. WalkSAT first picks a clause which is unsatisfied by the current
 * assignment, then flips a variable within that clause. The clause is picked at
 * random among unsatisfied clauses.The variable is picked that will result in
 * the fewest previously satisfied clauses becoming unsatisfied, with some
 * probability of picking one of the variables at random. When picking at
 * random, WalkSAT is guaranteed at least a chance of one out of the number of
 * variables in the clause of fixing a currently incorrect assignment. When
 * picking a guessed to be optimal variable, WalkSAT has to do less calculation
 * than GSAT because it is considering fewer possibilities.
 * 
 * @author Branko
 *
 */
public class Algorithm4 {
	/**
	 * Private list for storing already seen results
	 */
	private List<String> setVec;
	/**
	 * Private reference to clauses expression {@link SATFormula}
	 */
	private SATFormula formula;
	/**
	 * Private reference to {@link SATFormulaStats}
	 */
	private SATFormulaStats formulaStats;

	/**
	 * Public constructor which stores desire clauses expression.
	 * 
	 * @param formula
	 *            desire clauses expression
	 */
	public Algorithm4(SATFormula formula) {
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
	protected void executeAlgh(int maxFlips, int maxTries) {
		boolean find = false;
		double p = 0.2;

		if (maxFlips <= 0 || maxTries <= 0) {
			throw new IllegalArgumentException("Dear user, max flips and max tries" + "cannot be zero or negative");
		}

		int numOfvar = formula.getNumberOfVariables();
		int numOfClauses = formula.getNumberOfClauses();
		Random r = new Random();

		for (int i = 0; i < maxTries; i++) {
			MutableBitVector vecStart = new MutableBitVector(
					formula.getBin((int) (Math.random() * ((Math.pow(2, formula.getNumberOfVariables()) - 1) + 1)),
							numOfvar));

			for (int j = 0; j < maxFlips; j++) {
				formulaStats.setAssignment(vecStart, false);
				int fit = formulaStats.getNumberOfSatisfied();

				if (fit == numOfClauses) {
					find = true;
					if(!setVec.contains(vecStart.toString())){
						setVec.add(vecStart.toString());
						System.out.println(vecStart.toString());
					}
					break;
				}
				int numOfNegClauses = numOfClauses - formulaStats.getNumberOfSatisfied();
				Clause[] negClauses = new Clause[numOfNegClauses];
				int numNegClauses = 0;
				Clause tempClause;

				for (int k = 0; k < numOfClauses; k++) {
					tempClause = formula.getClause(k);
					if (!tempClause.isSatisfied(vecStart)) {
						negClauses[numNegClauses++] = tempClause;
					}
				}
				tempClause = negClauses[(int) (Math.random() * (numNegClauses))];
				float chance = r.nextFloat();

				if (chance <= p) {
					int index =0;
					do{
						index= -1 * tempClause.getLiteral((int) (Math.random() * ((tempClause.getSize() - 1) + 1)));

					}while(index ==0);
					
					vecStart.set(((int) Math.abs(index))-1, (index >= 0)^true);
				} else {
					int fitMax = 0;
					int indexMax = -1;
					MutableBitVector tempVec;
					for (int d = 0, clauseSize = tempClause.getSize(); d < clauseSize; d++) {
						int index = -1 * tempClause.getLiteral(d);
						tempVec = vecStart;
						if(index == 0){
							continue;
						}
						tempVec.set(((int) Math.abs(index))-1, (index >= 0)^true);
						formulaStats.setAssignment(tempVec, false);
						int fitTemp = formulaStats.getNumberOfSatisfied();
						if (fitTemp > fitMax) {
							indexMax = index;
						}
					}
					vecStart.set(((int) Math.abs(indexMax))-1, (indexMax >= 0)^true);

				}

			}
		}
		if (!find) {
			System.out.println("Solution for formula is not found");
		}
	}
}
