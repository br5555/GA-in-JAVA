package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.jzy3d.plot3d.builder.Mapper;

/**
 * Class implements {@linkplain IHFunction} for working
 * with function f(x1,x2) =(x1-1)^2 +10*(x2-2)^2.
 *  
 * @author Branko
 *
 */
public class Function2 implements IDrawFunction{
	
	@Override
	public  Mapper getMapper(){
		 Mapper mapper = new Mapper() {
	            @Override
	            public double f(double x, double y) {
	                return Math.pow(x-1,2) + 10*Math.pow(x-2,2);
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
		return Math.pow(point[0]-1, 2)+10*Math.pow((point[1]-2), 2);
	}

	@Override
	public double[] gradientOfFunction(double[] point) {
		if(point.length != 2){
			throw new IllegalArgumentException("Dear user, function has 2 variables");
		}
		double[] grad= new double[numberOfVariables()];
		grad[0] = 2*(point[0]-1);
		grad[1] = 20*(point[1]-2);
		return grad;
	}

	@Override
	public RealMatrix hesseMatrix(double[] point) {
		double[][] array = {{2,0},{0,20}};
		return MatrixUtils.createRealMatrix(array);
	}

}
