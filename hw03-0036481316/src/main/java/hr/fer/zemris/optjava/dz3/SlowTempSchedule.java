package hr.fer.zemris.optjava.dz3;

public class SlowTempSchedule implements ITempSchedule{

	/**
	 * private constant of cooling
	 */
	private double alpha;
	/**
	 * Initial temperature
	 */
	private double tInitial;
	/**
	 * current temperature
	 */
	private double tCurrent;
	/**
	 * number of inner iteration
	 */
	private int innerLimit;
	/**
	 * number of outer iteration
	 */
	private int outerLimit;
	
	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param tInitial desire initial temperature
	 * @param alpha desire constant of cooling
	 * @param innerLimit desire number of inner iteration
	 * @param outerLimit desire number of outer iteration
	 */
	public SlowTempSchedule(double tInitial,double alpha,int innerLimit,
			int outerLimit) {
		this.alpha = alpha;
		this.tInitial = tInitial;
		this.tCurrent = tInitial;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
	}
	
	@Override
	public double getNextTemperature() {
		double temp = tCurrent;
		tCurrent = tCurrent/(1+alpha*tCurrent);
		return temp;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}
}
