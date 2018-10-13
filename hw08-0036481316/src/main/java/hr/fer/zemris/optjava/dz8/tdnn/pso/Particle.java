package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.Arrays;
import java.util.List;

/**
 * Class represent simple particle
 * @author Branko
 *
 * @param <T> generic parameter
 */
public class Particle<T> {

	/**
	 * size of particle
	 */
	private int size;
	/**
	 *present values of particles
	 */
	private T[] values;
	/**
	 * best values of particles
	 */
	private T[] bestValues;
	/**
	 * velocities of particles
	 */
	private T[] velocities;
	/**
	 * old values of particle
	 */
	private T[] oldValues;
	/**
	 * present fitness of particle
	 */
	private double fitness;
	/**
	 * best fitness of particle
	 */
	private double bestFitness;
	/**
	 * private reference of {@linkplain IFunction}
	 */
	private IFunction<T> function;
	/**
	 * is problem to minimize
	 */
	boolean minimize;

	/**
	 * Constructor accepts desire settings
	 * @param size size of solution
	 * @param values values
	 * @param velocities velocities
	 * @param function desire dunction
	 * @param minimize minimize or maximize
	 */
	public Particle(int size, T[] values,T[] velocities, IFunction<T> function, boolean minimize) {
		this.size = size;
		this.values = Arrays.copyOf(values, size);
		this.bestValues = Arrays.copyOf(values, size);
		this.velocities = Arrays.copyOf(velocities, size);
		this.function = function;
		this.fitness = function.valueAt(values);
		this.bestFitness = fitness;
		this.minimize = minimize;
	}
	
	/**
	 * Public setter set desire features 
	 * @param values desire features
	 */
	public void setFeatures(T[] values) {
		this.values = values;
		getFitness();
	}
	
	/**
	 * Public setter set desire velocities 
	 * @param values desire velocities
	 */
	public void setVelocities(T[] velocities) {
		this.velocities = velocities;
	}

	/**
	 * Public getter returns best fitness
	 * @return best fitness
	 */
	public double getBestFitness() {
		return bestFitness;
	}

	/**
	 * Public getter returns best values
	 * @return best values
	 */
	public T[] getBestValues() {
		return bestValues;
	}

	/**
	 * Public getter returns old values 
	 * @return best old values
	 */
	public T[] getOldValues() {
		return oldValues;
	}

	/**
	 * Public getter returns values 
	 * @return best values
	 */
	public T[] getValues() {
		return values;
	}

	/**
	 * Public getter returns velocities 
	 * @return best velocities
	 */
	public T[] getVelocities() {
		return velocities;
	}

	/**
	 * Method makes duplicate of this instance
	 * @return duplicate of this instance
	 */
	public Particle<T> duplicate(){
		Particle<T> temp = new Particle<>(this.size, this.values, this.velocities, this.function, minimize);
		return temp;
	}
	
	/**
	 * Public getter returns fitness 
	 * @return best fitness
	 */
	public double getFitness() {
		this.fitness = function.valueAt(values);
		if(((fitness > bestFitness) && !minimize) ||((fitness < bestFitness) && minimize)){
			bestFitness = fitness;
			bestValues = Arrays.copyOf(values, values.length);
			
		}
		return fitness;
	}

	/**
	 * Public getter returns size 
	 * @return best size
	 */
	public int getSize() {
		return size;
	}

}
