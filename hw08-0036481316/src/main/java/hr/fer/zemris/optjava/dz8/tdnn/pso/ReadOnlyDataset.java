package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implements {@linkplain IReadOnlyDataset}
 * @author Branko
 *
 */
public class ReadOnlyDataset implements IReadOnlyDataset {

	/**
	 * list of data
	 */
	List<IData<Double>> listOfData = new ArrayList<>();

	/**
	 * Public constructor accepts desire settings
	 * @param listOfData list of data
	 */
	public ReadOnlyDataset(List<IData<Double>> listOfData) {
		super();
		this.listOfData = listOfData;
	}

	@Override
	public int numberOfSamples() {
		return listOfData.size();
	}

	@Override
	public int numberOfSampleInputs() {
		return listOfData.get(0).getNumberOfFeatures();
	}

	@Override
	public int numberOfSampleOutputs() {
		return listOfData.get(0).getNumberOfOutputs();
	}

	@Override
	public List<Double> getInputSample(int index) {
		return listOfData.get(index).getFeatures();
	}

	@Override
	public List<Double> getOutputSample(int index) {
		return listOfData.get(index).getOutputs();
	}

	@Override
	public List<List<Double>> getInputs() {

		List<List<Double>> inputs = new ArrayList<>();
		
		for (int i = 0, size = listOfData.size(); i < size; i++) {
			
			inputs.add(new ArrayList<>(this.getInputSample(i)));
			
		}
		
		return inputs;

	}

	@Override
	public List<List<Double>> getOutputs() {
		
		List<List<Double>> outputs = new ArrayList<>();
		
		for (int i = 0, size = listOfData.size(); i < size; i++) {
			
			outputs.add(new ArrayList<>(this.getOutputSample(i)));
			
		}
		
		return outputs;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(IData<Double> data: listOfData){
			sb.append(data.toString()).append("\n");
		}
		return sb.toString();
	}
}
