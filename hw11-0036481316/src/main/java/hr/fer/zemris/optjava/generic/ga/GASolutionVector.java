package hr.fer.zemris.optjava.generic.ga;

/**
 * Class exteds {@linkplain GASolution} 
 * @author Branko
 *
 */
public class GASolutionVector extends GASolution<int[]>{

	@Override
	public GASolution<int[]> duplicate() {
		GASolution<int[]> duplicate = new GASolutionVector();
		duplicate.fitness = this.fitness;
		duplicate.data = new int[this.data.length];
		
		for(int i = 0, size = this.data.length; i < size; i++){
			duplicate.data[i] = this.data[i];
		}
		
		return duplicate;
	}

}
