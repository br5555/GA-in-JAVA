package hr.fer.zemris.optjava.dz10;

/**
 * Class represent function f(x)=xi^2 where i is element[1,4] and implements
 * {@linkplain IFunction}
 * 
 * @author Branko
 *
 */
public class Function3 implements IFunction {

	/**
	 * Dimension of input
	 */
	private int dimOfInput = 4;
	/**
	 * Dimension of output
	 */
	private int dimOfOutput = 1;
	/**
	 * index of i
	 */
	private int indexOfPoint;

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param indexOfPoint
	 *            desire index i
	 */
	public Function3(int indexOfPoint) {
		super();
		this.indexOfPoint = indexOfPoint;
	}

	@Override
	public double[] valueAt(double[] point) {
		return new double[] { point[indexOfPoint] * point[indexOfPoint] };
	}

	@Override
	public int dimOdInput() {
		return dimOfInput;
	}

	@Override
	public int dimOfOutput() {
		return dimOfOutput;
	}

}
