package hr.fer.zemris.optjava.dz10;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class implements genetic algorithm selection
 * 
 * @author Branko
 *
 */
public class ProportionalSelection {

	/**
	 * Method select entity from inputed population
	 * 
	 * @param solutions
	 *            inputed population
	 * @return selected entity
	 */
	public SolutionVector selection(ArrayList<SolutionVector> solutions) {
		Random rand = new Random();
		SolutionVector vec1 = solutions.get(rand.nextInt(solutions.size()));
		SolutionVector vec2 = solutions.get(rand.nextInt(solutions.size()));
		
		if(vec1.numOfFront < vec2.numOfFront){
			return vec1.duplicate();
		}else if(vec2.numOfFront < vec1.numOfFront){
			return vec2.duplicate();
		}else{
			if(vec1.dividedDistance > vec2.dividedDistance){
				return vec1.duplicate();
			}else{
				return vec2.duplicate();
			}
		}

	}
}
