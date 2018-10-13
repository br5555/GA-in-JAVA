package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.RealMatrix;

/**
 * Interface extends {@linkplain IFunction} and decorates it with additional 
 * function which computes hesse's matrix of function.
 * 
 * @author Branko
 *
 */

public interface IHFunction extends IFunction{

	/**
	 * Public method which computes hesse's matrix of function at specific point 
	 * 
	 * @param point desire point
	 * @return {@linkplain RealMatrix} which represent hesse's matrix
	 */
	RealMatrix hesseMatrix(double[] point);
}
