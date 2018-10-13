package hr.fer.zemris.optjava.dz8.df;

import java.util.ArrayList;
import java.util.Random;
/**
 * Class implements random crossover and implements {@linkplain ICrossover}
 * @author Branko
 *
 */
public class RandomCrossover implements ICrossover<SolutionVector> {

	@Override
	public SolutionVector crossover(SolutionVector mutante, SolutionVector target, double Cr) {
		Random rand = new Random();
		ArrayList<Double> values = new ArrayList<>();
		ArrayList<Double> mutanteValues = mutante.getValues();
		ArrayList<Double> targetValues = target.getValues();
		int index = rand.nextInt(mutanteValues.size());
		
		values.add(mutanteValues.get(index));
		
		for(int i =0, size = mutanteValues.size()-1; i<size; i++){
			
			if(rand.nextDouble()<=Cr){
				
				values.add(mutanteValues.get((index+i+1)%(size+1)));
			
			}else{
				
				values.add(targetValues.get((index+i+1)%(size+1)));
			}
			
		}
		
		SolutionVector vec = mutante.duplicate();
		vec.setValues(values);
		return vec;
	}
	

}
