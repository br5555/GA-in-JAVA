package hr.fer.zemris.optjava.dz7.pso;

import java.util.Arrays;
/**
 * Class implements feedforward network
 * @author Branko
 *
 */
public class FFANN {

	/**
	 * structure of network
	 */
	private int[] structure;
	/**
	 * activation functions
	 */
	private ITransferFunction[] functions;
	/**
	 * number of weights
	 */
	private int numberOfWeights;
	/**
	 * private reference to {@linkplain IReadOnlyDataset}
	 */
	private IReadOnlyDataset dataset;
	
	/**
	 * Public constructor accepts desire settings
	 * @param structure desire structure
	 * @param functions desire activation functions
	 * @param dataset desire dataset
	 */
	public FFANN(int[] structure, ITransferFunction[] functions, IReadOnlyDataset dataset) {
		super();
		this.structure = structure;
		this.functions = functions;
		this.dataset = dataset;
		calcNumberOfWeights();
	}

	/**
	 * Method calculates number of weights
	 */
	private void calcNumberOfWeights() {
		int sum = 0;
		for(int i = 1, size = structure.length; i< size; i++){
			sum += (structure[i-1]+1)*structure[i];
		}
		numberOfWeights = sum;
	}

	/**
	 * Method returns number of weights
	 * @return number of weights
	 */
	public int getWeightsCount() {
		return numberOfWeights;
	}

	/**
	 * Method calculates output of feedforward network
	 * @param inputsOrg inputs
	 * @param weights weights
	 * @return outputs
	 */
	public double[] calcOutputs(double[] inputsOrg, double[] weights){
		if(inputsOrg.length != structure[0]){
			throw new IllegalArgumentException("Dear user, number of inputs is "+structure[0]);
		}
		
		int indexOfWeight = 0;
		double[] inputs = Arrays.copyOf(inputsOrg, inputsOrg.length);
		
		for(int i =1, depth = structure.length; i< depth; i++){
			double[] layer = new double[structure[i]];
			
			for(int j = 0; j<structure[i]; j++){
				for(int k = 0, inputSize = inputs.length; k<inputSize; k++){
						
						layer[j] += inputs[k]*weights[indexOfWeight++];
						
						
				}	
				layer[j] +=weights[indexOfWeight++];
				layer[j] = functions[i-1].valueAt(layer[j]);
			}
			inputs = layer;
		}
		double[] outputs = Arrays.copyOf(inputs, inputs.length);;
		return outputs;

	}
	
	
}
