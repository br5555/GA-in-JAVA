package hr.fer.zemris.trisat;

/**
 * Class implements different methods which helps realizing different variation
 * of SAT Algorithm. 
 * 
 * @author Branko
 *
 */
public class SATFormulaStats {
	/**
	 * Private SATFormula which stores complete boolean expression
	 */
	private SATFormula formula;
	/**
	 * Private integer which counts how many clauses are true.
	 */
	private int numberOfSatisfied;
	/**
	 * Private double array which stores percentage for each clauses
	 */
	private double[] post;
	/**
	 * Private boolean array which stores which clauses are true
	 */
	private boolean[] clauses;
	/**
	 * Private integer constant which tells how many the most likely clauses are 
	 * store
	 */
	private final int numberOfBest = 2;
	/**
	 * Private integer constant which is added to clauses percentage if clauses is true
	 */
	private final double percentageConstantUp = 0.01;
	/**
	 * Private integer constant which is subtract of clauses percentage if clauses is false
	 */
	private final double percentageConstantDown = 0.1;
	/**
	 * Private integer constant used for evaluating percentage of expression
	 */
	private final int percentageUnitAmount = 50;

	/**
	 * Public constructor which stores desire expression.
	 * 
	 * @param formula desire expression.
	 */
	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		numberOfSatisfied = 0;
		post = new double[formula.getNumberOfClauses()];
		clauses = new boolean[formula.getNumberOfClauses()];
	}

	/**
	 * Analyse stored expression and updates all statistical variables according
	 * to the result of expression.
	 * 
	 * @param assignment initial solution
	 * @param updatePercentages flags which indicates if specific variables are 
	 * 			updated or not
	 */
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		numberOfSatisfied = 0;
		for (int i = 0, size = formula.getNumberOfClauses(); i < size; i++) {
			
			
			if (formula.getClause(i).isSatisfied(assignment)) {
				numberOfSatisfied++;
				clauses[i] = true;
				if (updatePercentages)
					post[i] += (1 - post[i]) * percentageConstantUp;
			} else {
				clauses[i] = false;
				if (updatePercentages)
					post[i] += (0 - post[i]) * percentageConstantDown;
			}
		}
	}

	/**
	 * Method returns how many clauses are true.
	 * 
	 * @return number of satisfied clauses
	 */
	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}

	/**
	 * Method returns if whole expression is true.
	 * 
	 * @return true if it is
	 */
	public boolean isSatisfied() {
		if (numberOfSatisfied == formula.getNumberOfClauses()) {
			return true;
		}
		return false;
	}

	/**
	 * Method calculates correction clause sum as a result what method
	 *  {@link #setAssignment(BitVector, boolean)} stores.
	 *  
	 * @return double which represent correction clause sum
	 */
	public double getPercentageBonus() {
		double percent = (double) numberOfSatisfied;
		for (int i = 0, size = formula.getNumberOfClauses(); i < size; i++) {
			if (clauses[i]) {
				percent += percentageUnitAmount * (1 - post[i]);
			} else {
				percent += -percentageUnitAmount * (1 - post[i]);
			}
		}
		return percent;
	}

	/**
	 * Method returns percentage for desire clause  as a result what method
	 *  {@link #setAssignment(BitVector, boolean)} stores.
	 * @param index   ordinal number of desire clause
	 * @return double which represent goodness of desire clause
	 */
	public double getPercentage(int index) {
		if (index < 0 || index >= post.length) {
			throw new IllegalArgumentException("Dear user, requested index is out of range");

		}
		return post[index];
	}


}