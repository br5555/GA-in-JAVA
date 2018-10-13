package hr.fer.zemris.optjava.dz7.pso;

import java.util.List;

/**
 * Class which represent one sample of data
 * 
 * @author Branko
 *
 * @param <T> generic parameter
 */
public interface IData<T> {
	
	/**
	 * Method which returns number of features
	 * @return number of features
	 */
	int getNumberOfFeatures();
	
	/**
	 * Method which returns number of outputs
	 * @return number of outputs
	 */
	int getNumberOfOutputs();
	
	/**
	 * Method gets desire feature 
	 * @param index index of desire feature
	 * @return desire feature
	 */
	T getFeature(int index);
	
	/**
	 * Method gets desire output 
	 * @param index index of desire output
	 * @return desire output
	 */
	T getOutput(int index);
	
	/**
	 * Method returns all samples features
	 * @return  all samples features
	 */
	List<T> getFeatures();
	
	/**
	 * Method returns all samples outputs
	 * @return  all samples outputs
	 */
	List<T> getOutputs();

}
