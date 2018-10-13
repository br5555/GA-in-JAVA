package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.ArrayList;
import java.util.List;

public class SerialData implements IData<Double>{

	private int lengthOfSequence;
	private ArrayList<Double> features;
	private Double output;
	
	
	
	public SerialData(int lengthOfSequence, ArrayList<Double> features, Double output) {
		super();
		this.lengthOfSequence = lengthOfSequence;
		this.features = new ArrayList<>(features);
		this.output = output;
	}

	public SerialData(int lengthOfSequence, ArrayList<Double> features, double output) {
		super();
		this.lengthOfSequence = lengthOfSequence;
		this.features = features;
		this.output = new Double(output);
	}
	
	@Override
	public int getNumberOfFeatures() {
		
		return features.size();
	}

	@Override
	public int getNumberOfOutputs() {
		
		return 1;
	}

	@Override
	public Double getFeature(int index) {
		if(index < 0 || index >= this.getNumberOfFeatures()){
			throw new IllegalArgumentException("Dear user, data has only one output");
		}
		return features.get(index);
	}

	@Override
	public Double getOutput(int index) {
		if(index != 0){
			throw new IllegalArgumentException("Dear user, data has only one output");
		}
		return output;
	}

	@Override
	public List<Double> getFeatures() {
		
		return new ArrayList<>(features);
	}

	@Override
	public List<Double> getOutputs() {
		ArrayList<Double> temp =  new ArrayList<>();
		temp.add(output);
		return temp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		
		for(int i = 0, size = this.getNumberOfFeatures(); i< size;i++){
			
			sb.append(features.get(i));
			if(i!=size-1)
				sb.append(", ");
				
		}
		sb.append(" ] = ").append(output);
		return sb.toString();
	}
}
