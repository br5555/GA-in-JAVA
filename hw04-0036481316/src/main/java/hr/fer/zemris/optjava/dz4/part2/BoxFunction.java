package hr.fer.zemris.optjava.dz4.part2;

/**
 * Class represents container function which calculates quality of the conatiner.
 * 
 * @author Branko
 *
 */
public class BoxFunction{

	/**
	 * Number of boxes 
	 */
	private int numberOfBoxes;
	/**
	 * hyper parameter of function
	 */
	private double alpha;
	
	/**
	 * Public constructor which accepts desire settings
	 * @param numberOfBoxes number of boxes
	 * @param alpha desire hyper parameter 
	 */
	public BoxFunction(int numberOfBoxes, double alpha ) {
		this.numberOfBoxes = numberOfBoxes;
		this.alpha = alpha;
	}
	
	/**
	 * Public method calculates quality of the container
	 * @param boxes desire container
	 * @return quality of the container
	 */
	public double valueAt(Boxes boxes) {
		this.setNumberOfBoxes(this.numberOfBoxes);
		double value = 0;
		for(int i = 0, size =boxes.getNumberOfBoxes() ; i<size ; i++){
			value += Math.pow(boxes.returnBox(i).boxQuality(),alpha);
		}
		return value/boxes.getNumberOfBoxes();
	}

	/**
	 * Private setter which sets number of variables in the function
	 * @param numberOfBoxes number of boxes in the container
	 */
	private void setNumberOfBoxes(int numberOfBoxes) {
		this.numberOfBoxes = numberOfBoxes;
	}
	/**
	 * Public getter returns number of variables in the function.
	 * @return number of variables in the function
	 */
	public int numberOfVariables() {
		
		return numberOfBoxes;
	}

}
