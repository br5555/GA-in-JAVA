package hr.fer.zemris.optjava.dz8.tdnn.pso;

/**
 * Class represents tangens hyperbolic function and implements {@linkplain ITransferFunction}
 * @author Branko
 *
 */
public class TangensHyperbolicus implements ITransferFunction{

	@Override
	public double valueAt(double point) {
		return ( 1 - Math.exp(-point) ) / ( 1 + Math.exp(-point) );
		
	}

}
