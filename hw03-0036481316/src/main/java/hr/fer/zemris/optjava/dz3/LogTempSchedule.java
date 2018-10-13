package hr.fer.zemris.optjava.dz3;

public class LogTempSchedule implements ITempSchedule{

	
	/**
	 * Initial temperature
	 */
	private double tInitial;
	/**
	 * number of calls
	 */
	private int counter;
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
	public LogTempSchedule(double tInitial,int innerLimit,
			int outerLimit) {
		this.tInitial = tInitial;
		this.counter = 0;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
	}
	
	@Override
	public double getNextTemperature() {
		return tInitial/Math.log(counter++);
		
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
