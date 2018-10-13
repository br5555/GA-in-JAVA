package hr.fer.zemris.optjava.generic.ga;

/**
 * Class represent job description for parallelisation
 * @author Branko
 *
 */
public class JobDescription {

	protected GASolutionVector[] newPopulation;
	protected int desireNum;

	public JobDescription(GASolutionVector[] newPopulation, int desireNum) {
		super();
		this.newPopulation = newPopulation;
		this.desireNum = desireNum;
	}

}
