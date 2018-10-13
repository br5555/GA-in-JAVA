package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.List;
/**
 * Interface which represent read data from file
 * @author Branko
 *
 */
public interface IReadOnlyDataset {

	/**
	 * Method which returns number of samples
	 * @return number of samples
	 */
	public int numberOfSamples();
	
	/**
	 * Method which returns number of inputs
	 * @return number of inputs
	 */
	public int numberOfSampleInputs();	
	
	/**
	 * Method which returns number of outputs
	 * @return number of outputs
	 */
	public int numberOfSampleOutputs();
	
	/**
	 * Method which returns desire input
	 * @param index index of desire input
	 * @return desire input
	 */
	public List<Double> getInputSample(int index);
	
	/**
	 * Method which returns desire output
	 * @param index index of desire output
	 * @return desire output
	 */
	public List<Double> getOutputSample(int index);
	
	/**
	 * Method which returns all inputs
	 * @return all inputs
	 */
	public List<List<Double>> getInputs();
	
	/**
	 * Method which returns all outputs
	 * @return all outputs
	 */
	public List<List<Double>> getOutputs();
}
