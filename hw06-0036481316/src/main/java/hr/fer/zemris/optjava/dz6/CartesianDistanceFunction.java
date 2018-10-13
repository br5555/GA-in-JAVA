package hr.fer.zemris.optjava.dz6;

import com.eatthepath.jvptree.DistanceFunction;

/**
 * Class that implements {@linkplain DistanceFunction} and calculates euclidean
 * distance
 * 
 * @author Branko
 *
 */
public class CartesianDistanceFunction implements DistanceFunction<CartesianPoint> {

	/**
	 * Public method which calculates euclidean distance between points.
	 * 
	 * @return euclidean distance between points
	 */
	public double getDistance(final CartesianPoint firstPoint, final CartesianPoint secondPoint) {
		final double deltaX = firstPoint.getX() - secondPoint.getX();
		final double deltaY = firstPoint.getY() - secondPoint.getY();

		return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
	}
}