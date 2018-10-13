package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class implements version of iterated local search algorithm known as repeated
 * local search. Repeated local search implies that the knowledge obtained
 * during the previous local search phases is not used. Learning implies that
 * the previous history, for example the memory about the previously found local
 * minimum, is mined to produce better and better starting points for local
 * search.
 * 
 * @author Branko
 *
 */
public class Algorithm5 {
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
	public Algorithm5(SATFormula formula) {
		this.formula = formula;
		formulaStats = new SATFormulaStats(formula);
		setVec = new ArrayList<>();

	}

	/**
	 * Private method implements repeated local search algorithm.
	 * 
	 * @param maxTries number of iteration of outer loop
	 */
	protected void executeAlgh(int maxTries) {

		double percentage = 0.16;
		MutableBitVector vecStart = new MutableBitVector(
				formula.getBin((int) (Math.random() * ((Math.pow(2, formula.getNumberOfVariables()) - 1))),
						formula.getNumberOfVariables()));
		for (int j = 0; j < maxTries; j++) {

			if (!localSearch(vecStart)) {
				int size = (int) Math.ceil(percentage * formula.getNumberOfVariables());
				ArrayList<Integer> pastIndex = new ArrayList<>();

				for (int i = 0; i < size; i++) {
					while (true) {
						int tempInd = (int) (Math.random() * ((formula.getNumberOfVariables() - 1) ));
						if (pastIndex.contains(tempInd)) {
							continue;
						}
						pastIndex.add(tempInd);
						break;
					}

				}
				for (Integer i : pastIndex) {
					vecStart.set(i, vecStart.get(i) ^ true);
				}

			} else {
				vecStart = new MutableBitVector(
						formula.getBin((int) (Math.random() * ((Math.pow(2, formula.getNumberOfVariables()) - 1))),
								formula.getNumberOfVariables()));
			}
		}

	}

	/**
	 * Private method which implements local search of repeated local search algorithm.
	 * 
	 * @param vecStart initial vector {@link MutableBitVector}
	 * @return false if algorithm stuck in local optimum otherwise true. 
	 */
	private boolean localSearch(MutableBitVector vecStart) {
		int numOfvar = formula.getNumberOfVariables();
		int numOfClauses = formula.getNumberOfClauses();

		for (int i = 0; i < 100000; i++) {

			formulaStats.setAssignment(vecStart, false);
			int fitStart = formulaStats.getNumberOfSatisfied();
			if (fitStart == numOfClauses) {
				if(!setVec.contains(vecStart.toString())){
					setVec.add(vecStart.toString());
					System.out.println(vecStart.toString());
				}
			}
			BitVectorNGenerator generator = new BitVectorNGenerator(vecStart);
			MutableBitVector[] vectorNeigh = generator.createNeighborhood();
			MutableBitVector[] best = new MutableBitVector[numOfvar];
			int numOfBest = 0;
			int fitMax = fitStart;

			for (int j = 0; j < numOfvar; j++) {
				formulaStats.setAssignment(vectorNeigh[j], false);
				int fitTemp = formulaStats.getNumberOfSatisfied();
				if (fitMax < fitTemp) {
					fitMax = fitTemp;
					Arrays.fill(best, null);
					numOfBest=0;
					best[numOfBest++] = vectorNeigh[j];
				} else if (fitMax == fitTemp) {
					best[numOfBest++] = vectorNeigh[j];
				}

			}
			if (numOfBest == 0) {
				System.out.println("Dear user, algorithm stuck in local optimum");
				return false;
			}
			vecStart = best[(int) (Math.random() * (numOfBest-1))];
		}
		return true;
	}
}
