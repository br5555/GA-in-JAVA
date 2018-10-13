package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.jzy3d.plot3d.builder.Mapper;

/**
 * Class implements {@linkplain IHFunction} for working
 * with function f(x1,x2) =x1^2 +(x2-1)^2.
 *  
 * @author Branko
 *
 */
public class Function1 implements IDrawFunction{

	@Override
	public  Mapper getMapper(){
		 Mapper mapper = new Mapper() {
	            @Override
	            public double f(double x, double y) {
	                return Math.pow(x,2) + Math.pow(x-1,2);
	            }
	        };
	      return mapper;
	}
	
	@Override
	public int numberOfVariables() {
		return 2;
	}

	@Override
	public double functionValue(double[] point) {
		if(point.length != 2){
			throw new IllegalArgumentException("Dear user, function has 2 variables");
		}
		return Math.pow(point[0], 2)+Math.pow((point[1]-1), 2);
	}

	@Override
	public double[] gradientOfFunction(double[] point) {
		if(point.length != 2){
			throw new IllegalArgumentException("Dear user, function has 2 variables");
		}
		double[] grad= new double[numberOfVariables()];
		grad[0] = 2*point[0];
		grad[1] = 2*(point[1]-1);
		return grad;
	}

	@Override
	public RealMatrix hesseMatrix(double[] point) {
		double[][] array = {{2,0},{0,2}};
		return MatrixUtils.createRealMatrix(array);
	}

}
