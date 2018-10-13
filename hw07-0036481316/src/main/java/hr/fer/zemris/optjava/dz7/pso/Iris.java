package hr.fer.zemris.optjava.dz7.pso;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent one iris flower and implements {@linkplain IData}
 * @author Branko
 *
 */
public class Iris implements IData<Double> {

	/**
	 * number of features
	 */
	private int numberOfFeatures;
	/**
	 * number of outputs
	 */
	private int numberOfOutputs;
	/**
	 * Gender of flower
	 */
	private String family;
	/**
	 * length of sepals
	 */
	private double lengthOfSepals;
	/**
	 * width of sepals
	 */
	private double widthOfSepals;
	/**
	 * length of petals
	 */
	private double lengthOfPetals;
	/**
	 * width of petals
	 */
	private double widthOfPetals;

	
	/**
	 * Public constructor which accepts desire settings
	 * @param family gender of flower
	 * @param lengthOfSepals length of sepals
	 * @param widthOfSepals  width of sepals
	 * @param lengthOfPetals length of petals
	 * @param widthOfPetals width of petals
	 */
	public Iris(String family, double lengthOfSepals, double widthOfSepals, double lengthOfPetals,
			double widthOfPetals) {
		super();
		this.family = family;
		this.lengthOfSepals = lengthOfSepals;
		this.widthOfSepals = widthOfSepals;
		this.lengthOfPetals = lengthOfPetals;
		this.widthOfPetals = widthOfPetals;
		numberOfFeatures = 4;
		numberOfOutputs = 3;
	}

	/**
	 * Public getter which returns flowers gender 
	 * @return flowers gender
	 */
	public String getFamily() {
		return family;
	}

	/**
	 * Public getter which returns length of petals 
	 * @return flowers length of petals 
	 */
	public double getLengthOfPetals() {
		return lengthOfPetals;
	}

	/**
	 * Public getter which returns length of sepals 
	 * @return flowers length of sepals 
	 */
	public double getLengthOfSepals() {
		return lengthOfSepals;
	}

	/**
	 * Public getter which returns number of features
	 * @return number of features 
	 */
	public int getNumberOfFeatures() {
		return numberOfFeatures;
	}

	/**
	 * Public getter which returns number of outputs
	 * @return number of outputs 
	 */
	public int getNumberOfOutputs() {
		return numberOfOutputs;
	}

	/**
	 * Public getter which returns width of petals 
	 * @return flowers width of petals
	 */
	public double getWidthOfPetals() {
		return widthOfPetals;
	}

	/**
	 * Public getter which returns width of sepals 
	 * @return flowers width of sepals 
	 */
	public double getWidthOfSepals() {
		return widthOfSepals;
	}

	@Override
	public Double getFeature(int index) {
		switch (index) {
		case 0:
			return getLengthOfSepals();
		case 1:
			return getWidthOfSepals();
		case 2:
			return getLengthOfPetals();
		case 3:
			return getWidthOfPetals();
		default:
			throw new IllegalArgumentException("Dear user, that feature does not exists");
		}
	}

	@Override
	public Double getOutput(int index) {
		switch (index) {
		case 0:
			if(family.equals("Iris setosa")) return Double.valueOf(1);
			else return Double.valueOf(0);
		case 1:
			if(family.equals("Iris versicolor")) return Double.valueOf(1);
			else return Double.valueOf(0);
		case 2:
			if(family.equals("Iris virginica")) return Double.valueOf(1);
			else return Double.valueOf(0);
		default:
			throw new IllegalArgumentException("Dear user, this output does not exists");
		}
	}

	@Override
	public List<Double> getFeatures() {
		List<Double> list= new ArrayList<>();
		for(int i = 0, size = numberOfFeatures; i < size; i++){
			list.add(this.getFeature(i));
		}
		return list;
	}

	@Override
	public List<Double> getOutputs() {
		List<Double> list= new ArrayList<>();
		for(int i = 0, size = numberOfOutputs; i < size; i++){
			list.add(this.getOutput(i));
		}
		return list;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("length of sepals: ").append(lengthOfSepals).append(",width of sepals: ").append(widthOfSepals)
		.append(",length of petals: ").append(lengthOfPetals).append(",width of petals: ").append(widthOfPetals)
		.append(",family: ").append(family);
		return sb.toString();
		
	}

}
