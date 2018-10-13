package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import hr.fer.zemris.optjava.dz8.elman.Network;

/**
 * Class implements feedforward network
 * @author Branko
 *
 */
public class FFANN extends Network{

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

	@Override
	public int getWeightsCount() {
		return numberOfWeights;
	}

	@Override
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
	
	@Override
	public double[] calcOutputs(List<Double> inputsOrg, double[] weights){
		Double[] inputs = new Double[inputsOrg.size()];
		inputsOrg.toArray(inputs);
		return this.calcOutputs(ArrayUtils.toPrimitive(inputs), weights);
	}
	
}
