package hr.fer.zemris.optjava.dz2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * Class implements methods for iterative searching of local optimum. Methods
 * are gradient descent and Newton method. Gradient descent is a first-order
 * iterative optimization algorithm for finding the minimum of a function.
 * Newton's method is applied to the derivative f ′ of a twice-differentiable
 * function f to find the roots of the derivative (solutions to f ′(x)=0), also
 * known as the stationary points of f.
 * 
 * @author Branko
 *
 */
public class NumOptAlgorithms extends AbstractAnalysis {
	/**
	 * private static list remember seen points for drawing
	 */
	private static List<double[]> pointsDraw = new ArrayList<double[]>();
	/**
	 * Private reference to {@linkplain IDrawFunction}
	 */
	private static IDrawFunction funcDraw;

	/**
	 * Public setter getter
	 * 
	 * @return reference to {@linkplain IDrawFunction}
	 */
	public static IDrawFunction getFuncDraw() {
		return funcDraw;
	}

	/**
	 * Public static setter
	 * 
	 * @param funcDraw
	 *            desire reference to {@linkplain IDrawFunction}
	 */
	public static void setFuncDraw(IDrawFunction funcDraw) {
		NumOptAlgorithms.funcDraw = funcDraw;
	}

	/**
	 * Public static getter
	 * 
	 * @return list of seen points
	 */
	public static List<double[]> getPointsDraw() {
		return pointsDraw;
	}

	/**
	 * Public static setter
	 * 
	 * @param pointsDraw
	 *            seen points
	 */
	public static void setPointsDraw(List<double[]> pointsDraw) {
		NumOptAlgorithms.pointsDraw = pointsDraw;
	}

	@Override
	public void init() {
		// Define a function to plot
		Mapper mapper = NumOptAlgorithms.getFuncDraw().getMapper();

		// Define range and precision for the function to plot
		Range range = new Range(-10, 10);
		int steps = 80;

		// Create the object to represent the function over the given range.
		final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
		surface.setColorMapper(new ColorMapper(new Red(), surface.getBounds().getZmin(), surface.getBounds().getZmax(),
				new Color(1, 1, 1, .5f)));
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(false);

		Coord3d[] points = new Coord3d[NumOptAlgorithms.getPointsDraw().size()];
		Color[] colors = new Color[NumOptAlgorithms.getPointsDraw().size()];
		// Create scatter points
		int index = 0;
		for (double[] point : NumOptAlgorithms.getPointsDraw()) {
			float x = (float) point[0];
			float y = (float) point[1];
			float z = (float) point[2];
			points[index] = new Coord3d(x, y, z);
			colors[index++] = new Color(0.5f, 0.5f, 0.5f, 1.0f);
		}

		// Create a drawable scatter with a colormap
		Scatter scatter = new Scatter(points, colors); // Create a chart
		chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
		chart.getScene().getGraph().add(surface);
		chart.getScene().getGraph().add(scatter);

	}

	/**
	 * Public static method which implements gradient descent
	 * 
	 * @param func
	 *            desire function
	 * @param maxIter
	 *            maximum number of iteration
	 * @param startPoint
	 *            start point
	 * @param endPoint
	 *            upper boundary point
	 * @param draw
	 *            true plot function
	 */
	public static void gradientDownhill(IFunction func, int maxIter, double[] startPoint, double[] endPoint,
			boolean draw) {
		List<double[]> points = new ArrayList<double[]>();
		double scalar = 0.1;
		boolean minimum = true;
		for (int i = 0; i < maxIter; i++) {
			minimum = true;
			double[] gradient = func.gradientOfFunction(startPoint);
			for (double d : gradient) {
				if (d != 0) {
					minimum = false;
					break;
				}
			}
			if (minimum) {

				System.out.println("Minimum is " + Arrays.toString(startPoint) + "." + " Number of iteration is " + i);
				if (draw) {
					NumOptAlgorithms.setPointsDraw(points);
					NumOptAlgorithms.setFuncDraw((IDrawFunction) func);
					NumOptAlgorithms.draw();
				}

			}

			do {
				double[] tempPoint = Arrays.copyOf(startPoint, startPoint.length);
				if (draw) {
					double[] point = new double[tempPoint.length + 1];
					for (int t = 0, size = tempPoint.length; t < size; t++) {
						point[t] = startPoint[t];
					}
					point[point.length - 1] = func.functionValue(tempPoint);
					points.add(point);
				}

				for (int j = 0; j < startPoint.length; j++) {
					startPoint[j] = startPoint[j] - gradient[j] * scalar;
				}
				if (func.functionValue(startPoint) > func.functionValue(tempPoint)) {
					if (func.functionValue(startPoint) < func.functionValue(endPoint)) {
						endPoint = Arrays.copyOf(startPoint, startPoint.length);
					}
					startPoint = Arrays.copyOf(tempPoint, tempPoint.length);
					break;
				}
			} while (true);

		}
		System.out.println("Point where iteration finished is " + Arrays.toString(startPoint));

		if (draw) {
			NumOptAlgorithms.setPointsDraw(points);
			NumOptAlgorithms.setFuncDraw((IDrawFunction) func);
			NumOptAlgorithms.draw();
		}

	}

	/**
	 * Public static method for drawing 3D image
	 */
	public static void draw() {
		System.out.println(pointsDraw.size());
		try {
			AnalysisLauncher.open(new NumOptAlgorithms());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Public static method which implements Newton method for approximation of
	 * function
	 * 
	 * @param func
	 *            desire function
	 * @param maxIter
	 *            maximum number of iteration
	 * @param startPoint
	 *            start point
	 * @param endPoint
	 *            upper boundary point
	 * @param draw
	 *            true plot function
	 */
	public static void newtonMethod(IHFunction func, int maxIter, double[] startPoint, double[] endPoint,
			boolean draw) {
		boolean minimum = true;
		List<double[]> points = new ArrayList<double[]>();

		for (int i = 0; i < maxIter; i++) {

			double[] gradient = func.gradientOfFunction(startPoint);
			RealMatrix hesse = func.hesseMatrix(startPoint);
			minimum = true;
			for (double d : gradient) {
				if (d != 0) {
					minimum = false;
					break;
				}
			}
			if (minimum) {

				System.out.println("Minimum is " + Arrays.toString(startPoint) + "." + " Number of iteration is " + i);
				if (draw) {
					NumOptAlgorithms.setPointsDraw(points);
					NumOptAlgorithms.setFuncDraw((IDrawFunction) func);
					NumOptAlgorithms.draw();
				}
			}
			double[] vector = (new LUDecomposition(hesse).getSolver().getInverse()).preMultiply(gradient);
			do {
				double[] tempPoint = Arrays.copyOf(startPoint, startPoint.length);
				if (draw) {
					double[] point = new double[tempPoint.length + 1];
					for (int t = 0, size = tempPoint.length; t < size; t++) {
						point[t] = startPoint[t];
					}
					point[point.length - 1] = func.functionValue(tempPoint);
					points.add(point);
				}

				for (int j = 0; j < startPoint.length; j++) {
					startPoint[j] = startPoint[j] - vector[j];
				}
				if (func.functionValue(startPoint) > func.functionValue(tempPoint)) {
					if (func.functionValue(startPoint) < func.functionValue(endPoint)) {
						endPoint = Arrays.copyOf(startPoint, startPoint.length);
					}
					startPoint = Arrays.copyOf(tempPoint, tempPoint.length);
					break;
				}
			} while (true);
		}

		System.out.println("Point where iteration finished is " + Arrays.toString(startPoint));
		if (draw) {
			NumOptAlgorithms.setPointsDraw(points);
			NumOptAlgorithms.setFuncDraw((IDrawFunction) func);
			NumOptAlgorithms.draw();
		}
	}
}
