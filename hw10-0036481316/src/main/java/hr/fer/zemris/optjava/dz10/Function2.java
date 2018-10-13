package hr.fer.zemris.optjava.dz10;

/**
 * Class represent function f(x)=(x2+1)/x1 and implements {@linkplain IFunction}
 * @author Branko
 *
 */
public class Function2 implements IFunction{

	/**
	 * Dimension of input
	 */
	private int dimOfInput=2;
	/**
	 * Dimension of outputs
	 */
	private int dimOfOutput=1;
	
	@Override
	public double[] valueAt(double[] point) {
		return new double[]{(point[1]+1)/(point[0])};
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
