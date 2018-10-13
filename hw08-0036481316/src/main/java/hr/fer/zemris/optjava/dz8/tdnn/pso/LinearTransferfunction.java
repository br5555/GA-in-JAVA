package hr.fer.zemris.optjava.dz8.tdnn.pso;


/**
 * Class represents linear function and implements {@linkplain ITransferFunction}
 * @author Branko
 *
 */
public class LinearTransferfunction implements ITransferFunction{

	@Override
	public double valueAt(double point) {
		
		return point;
	}

}
