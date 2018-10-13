package hr.fer.zemris.optjava.dz9;
/**
 * Interface represents MOOP(MultiObjective optimisation function)
 * @author Branko
 *
 */
public interface MOOPProblem {

	/**
	 * Public getter gets number of objectives
	 * @return number of objectives
	 */
	int getNumberOfObjectives();
	
	/**
	 * Method evaluates for desire solution all functions and write results to objectives.
	 * @param solution desire solution input
	 * @param objectives result of evaluating all functions with desire input
	 */
	void evaluateSolution(double[] solution, double[] objectives);
}
