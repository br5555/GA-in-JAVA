package hr.fer.zemris.optjava.dz8.df;

import java.util.ArrayList;

import hr.fer.zemris.optjava.dz8.tdnn.pso.IFunction;
/**
 * Class represents solution vector and implements {@linkplain Comparable}.
 *  
 * @author Branko
 *
 */
public class SolutionVector implements Comparable<SolutionVector> {
	
	/**
	 * solution values 
	 */
	protected ArrayList<Double> values;
	/**
	 * Private reference to {@linkplain IFunction}
	 */
	private IFunction<Double> function;
	/**
	 * quality of solution
	 */
	private double fitness;

	@Override
	public int compareTo(SolutionVector arg0) {

		if (this.fitness > arg0.fitness)
			return -1;
		else if (this.fitness == arg0.fitness)
			return 0;
		else
			return 1;
	}

	@Override
	public boolean equals(Object arg0) {
		SolutionVector sol = null;

		if (arg0 instanceof SolutionVector) {
			sol = (SolutionVector) arg0;
		} else {
			throw new IllegalArgumentException("arg0 is not instance of SolutionVector");
		}

		for (int i = 0, size = values.size(); i < size; i++) {
			if (Double.compare(values.get(i), sol.values.get(i)) != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Public constructor accepts desire settings.
	 * @param values solution values
	 * @param function reference to {@linkplain IFunction}
	 */
	public SolutionVector(ArrayList<Double> values, IFunction<Double> function) {
		this.values = new ArrayList<>(values);
		this.function = function;
		updateFitness();
	}

	/**
	 * Method creates duplicate of this instance
	 * @return duplicate of this instance
	 */
	public SolutionVector duplicate() {
		SolutionVector vec = new SolutionVector(values, function);
		return vec;
	}

	/**
	 * Method sets sets desire value
	 * @param values
	 */
	protected void setValues(ArrayList<Double> values) {
		this.values = new ArrayList<>(values);
		updateFitness();
	}

	/**
	 * Public getter method gets fitness
	 * @return fitness
	 */
	public double getFitness() {
		updateFitness();
		return fitness;
	}

	/**
	 * Method updates solution fitness
	 */
	private void updateFitness() {
		Double[] valuesArary = new Double[values.size()];
		for (int i = 0, size = values.size(); i < size; i++) {
			valuesArary[i] = values.get(i);
		}
		fitness = function.valueAt(valuesArary);
		if (Double.isNaN(fitness)) {	
			fitness = 100000;
		}
		if (Double.isInfinite(fitness)) {
			fitness = 100000;
		}
	}

	public ArrayList<Double> getValues() {
		return values;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		SolutionVector vec = new SolutionVector(values, function);
		return vec;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.fitness);
		return sb.toString();
	}

}
