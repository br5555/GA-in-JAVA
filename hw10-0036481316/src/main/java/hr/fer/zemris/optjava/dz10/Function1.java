package hr.fer.zemris.optjava.dz10;

/**
 * Class represent function f(x)=x1 and implements {@linkplain IFunction}
 * @author Branko
 *
 */
public class Function1 implements IFunction{

	/**
	 * Dimension of inputs
	 */
	private int dimOfInput=2;
	/**
	 * dimensin of outputs
	 */
	private int dimOfOutput=1;

	
	@Override
	public double[] valueAt(double[] point) {
		return new double[]{point[0]};
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
