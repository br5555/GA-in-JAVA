package hr.fer.zemris.trisat;

import java.util.Arrays;
/**
 * Class implements boolean expression and methods for easier usage
 * 
 * @author branko
 *
 */
public class SATFormula {
	/**
	 * Private int which stores number of variables used in expression
	 */
	private int numberOfVariables;
	/**
	 * Private {@linkplain Clause} array which contains clauses of expression
	 */
	private Clause[] clauses;

	/**
	 * Public constructor store desire clauses and number of variables
	 * 
	 * @param numberOfVariables number of variables used in expression
	 * @param clauses clauses of expression
	 */
	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = new Clause[clauses.length];
		for (int i = 0, size = clauses.length; i < size; i++) {
			this.clauses[i] = clauses[i];
		}
	}

	/**
	 * Public method returns number of variables in expression
	 * 
	 * @return {@link #numberOfVariables}
	 */
	public int getNumberOfVariables() {
		return numberOfVariables;
	}

	/**
	 * Public method returns number of clauses in expression
	 * 
	 * @return number of caluses in expression
	 */
	public int getNumberOfClauses() {
		return clauses.length;
	}

	/**
	 * Method returns i-th clauses of expression
	 * 
	 * @param index desire i-th index
	 * @return i-th {@linkplain  Clause} of expression
	 * @throws IllegalArgumentException if index is negative or out of range
	 */
	public Clause getClause(int index) {
		if (index < 0 || index >= clauses.length) {
			throw new IllegalArgumentException("Dear user, requested index is out of range");

		}
		return clauses[index];
	}

	/**
	 * Public method returns if desire {@linkplain BitVector} satisfied expression
	 * 
	 * @param assignment desire {@linkplain BitVector}
	 * @return true if satisfied otherwise false
	 */
	public boolean isSatisfied(BitVector assignment) {
		for (int i = 0, size = clauses.length; i < size; i++) {
			if (!clauses[i].isSatisfied(assignment)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Public method calculates binary vector from input whole number and number of bits
	 * 
	 * @param num desire whole number
	 * @param size number of bits
	 * @return binary vector
	 */
	public static boolean[] getBin(int num, int size) {
		boolean[] bits = new boolean[size];
		for (int i = size - 1; i >= 0; i--) {
			bits[i] = (num & (1 << i)) != 0;
		}
		return bits;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(clauses.length * (2 * numberOfVariables + 3));
		for (int i = 0, size = clauses.length; i < size; i++) {
			sb.append(clauses[i].toString());
		}

		return sb.toString();
	}
}