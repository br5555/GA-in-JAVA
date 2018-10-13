package hr.fer.zemris.optjava.dz10;

/**
 * Class represent MOOP(multiobjective optimization problem) and impleemnts
 * {@linkplain MOOPProblem}.
 * 
 * @author Branko
 *
 */
public class MOOPFunction implements MOOPProblem {

	/**
	 * number of objective functions
	 */
	private int numberOfObjectives;
	/**
	 * array of functions
	 */
	private IFunction[] functions;

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param functions
	 *            desire functions
	 */
	public MOOPFunction(IFunction[] functions) {
		this.functions = functions;
		numberOfObjectives = functions.length;
	}

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {

		int num = getNumberOfObjectives();
		int index = 0;

		for (int i = 0; i < num; i++) {
			double[] rez = functions[i].valueAt(solution);

			for (int j = 0; j < rez.length; j++) {
				objectives[index++] = rez[j];
			}
		}

	}

}
