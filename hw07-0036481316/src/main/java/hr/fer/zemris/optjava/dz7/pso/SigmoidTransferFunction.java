package hr.fer.zemris.optjava.dz7.pso;

/**
 * Class implements Sigmoid function and implements {@linkplain ITransferFunction}
 * @author Branko
 *
 */
public class SigmoidTransferFunction implements ITransferFunction{

	@Override
	public double valueAt(double point) {
		return 1/(1 + Math.exp(-1*point));
	}

}
