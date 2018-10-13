package hr.fer.zemris.optjava.dz8.tdnn.clonalg;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz8.tdnn.pso.IFunction;

/**
 * Class represents Antigen and implements {@linkplain Comparable}
 * 
 * @author Branko
 *
 * @param <T>
 *            generic type
 */
public class Antigen<T> implements Comparable<Antigen<T>>{
	/**
	 * fitness of antigen
	 */
	private double fitness;
	/**
	 * array of values
	 */
	private T[] values;
	/**
	 * private instance of {@linkplain IFunction}
	 */
	private IFunction<T> function;	
	
	/**
	 * Public constructor which accepts desire settings.
	 * 
	 * @param values
	 *            desire values
	 * @param function
	 *            desire function
	 */
	public Antigen(T[] values, IFunction<T> function) {
		super();
		this.values = Arrays.copyOf(values,values.length);
		this.function = function;
		updateFitness();
	}

	/**
	 * Public getter which gets fitness
	 * 
	 * @return fitness of antigen
	 */
	public double getFitness() {
		//updateFitness();
		return fitness;
	}

	/**
	 * Private method for refreshing values of fitness
	 */
	private void updateFitness() {
		this.fitness = function.valueAt(values);
	}
	
	/**
	 * Public getter which gets values of antigen
	 * 
	 * @return values of antigen
	 */
	public T[] getValues() {
		return values;
	}

	/**
	 * Public method which makes duplicate of this instance.
	 * 
	 * @return duplicate of this instance
	 */
	public Antigen<T> duplicate(){
		Antigen<T> temp = new Antigen<>(values, function);
		return temp;
	}
	
	/**
	 * Public setter which sets desire values
	 * 
	 * @param values
	 *            desire values
	 */
	public void setValues(T[] values) {
		this.values = values;
		updateFitness();
	}
	
	@Override
	public int compareTo(Antigen<T> o) {
		if(this.fitness>o.fitness){
			return 1;
			
		}else if(o.fitness == this.fitness){
			return 0;
		}else{
			return -1;
		}
	}
	
	
}
