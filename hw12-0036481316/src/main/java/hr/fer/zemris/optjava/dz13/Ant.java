package hr.fer.zemris.optjava.dz13;
/**
 * Class represents simple ant.
 * @author Branko
 *
 */
public class Ant {
	/**
	 * x position of ant
	 */
	protected int postionX;
	/**
	 * y position of ant
	 */
	protected int postionY;
	/**
	 * x direction of ant
	 */
	protected int dirX;
	/**
	 * y direction of ant
	 */
	protected int dirY;
	/**
	 * angle of ant with respect to x-axis
	 */
	protected int angle;
	/**
	 * maximal x position
	 */
	protected int maxX;
	/**
	 * minimal x position
	 */
	protected int minX;
	/**
	 * maximal y position
	 */
	protected int maxY;
	/**
	 * minimal x position
	 */
	protected int minY;
	/**
	 * energy of ant
	 */
	protected int antEnergy;
	/**
	 * fitness of ant
	 */
	protected int antScore;

	
	
	/**
	 * Public constructor accepts desire settings
	 * @param postionX ants position x
	 * @param postionY ants position y
	 * @param dirX ants direction x
	 * @param dirY ants direction y
	 * @param angle ants angle with respect to x-axis
	 * @param maxX maximal x position
	 * @param minX minimal x position
	 * @param maxY maximal y position
	 * @param minY minimal y position
	 */
	public Ant(int postionX, int postionY, int dirX, int dirY, int angle, int maxX, int minX, int maxY, int minY) {
		super();
		this.postionX = postionX;
		this.postionY = postionY;
		this.dirX = dirX;
		this.dirY = dirY;
		this.angle = angle;
		this.maxX = maxX;
		this.minX = minX;
		this.maxY = maxY;
		this.minY = minY;
		this.antEnergy = 600;
		this.antScore = 0;
	}


}
