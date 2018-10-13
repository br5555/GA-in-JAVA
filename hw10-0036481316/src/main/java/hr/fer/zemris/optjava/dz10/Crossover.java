package hr.fer.zemris.optjava.dz10;

import java.util.Random;
/**
 * Class represent simple genetic algorithm crossover 
 * @author Branko
 *
 */
public class Crossover {

	/**
	 * Method crossover two parents and creates child
	 * @param parent1 first parent
	 * @param parent2 second parent
	 * @return child
	 */
	public SolutionVector crossover(SolutionVector parent1, SolutionVector parent2){
		double[] parent1Values = parent1.getValues();
		double[] parent2Values = parent2.getValues();
		double[] childValues = new double[parent1Values.length];
		
		Random rand = new Random();
		int index = rand.nextInt(parent1Values.length);
		
		
		for(int i = 0, size = parent1Values.length; i< size; i++){
			if(i<=index){
				childValues[i]= parent1Values[i];
			}else{
				childValues[i]= parent2Values[i];

			}
		}
		SolutionVector child = new SolutionVector(childValues);
		return child;
	}
}
