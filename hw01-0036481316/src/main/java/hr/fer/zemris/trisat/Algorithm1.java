package hr.fer.zemris.trisat;

import java.util.Arrays;

/**
 * Class implements brute force search algorithm. Brute-force search or
 * exhaustive search, also known as generate and test, is a very general
 * problem-solving technique that consists of systematically enumerating all
 * possible candidates for the solution and checking whether each candidate
 * satisfies the problem's statement.
 * 
 * @author Branko
 *
 */
public class Algorithm1 {
	/**
	 * Private reference to clauses expression {@link SATFormula}
	 */
	private SATFormula formula;

	/**
	 * Public constructor which stores desire clauses expression.
	 * 
	 * @param formula desire clauses expression
	 */
	public Algorithm1(SATFormula formula) {
		this.formula = formula;

	}

	/**
	 * Private method which implements brute-force algorithm.
	 */
	protected void executeAlgh() {

		boolean[] temp;
		int var = formula.getNumberOfVariables();
		for (int i = 0, size = (int) Math.pow(2, var); i < size; i++) {

			temp = SATFormula.getBin(i, var);


			MutableBitVector vec = new MutableBitVector(temp);

			if (formula.isSatisfied(vec)) {
				System.out.println(vec.toString());
			}

		}
	}

}
