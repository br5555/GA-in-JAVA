package hr.fer.zemris.optjava.dz8.df;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class represents exponential crossover and implements
 * {@linkplain ICrossover}.
 * 
 * @author Branko
 *
 */
public class ExponentialCrossover implements ICrossover<SolutionVector> {

	@Override
	public SolutionVector crossover(SolutionVector mutante, SolutionVector target, double Cr) {
		Random rand = new Random();
		ArrayList<Double> values = new ArrayList<>();
		ArrayList<Double> mutanteValues = mutante.getValues();
		ArrayList<Double> targetValues = target.getValues();
		int index = rand.nextInt(mutanteValues.size());
		int size = mutanteValues.size();

		for(int i = 0; i< size; i++){
			values.add(new Double(0));
		}
		
		values.set(index,mutanteValues.get(index));
		int count = 1;

		
		while (rand.nextDouble() <= Cr) {
			index++;
			count++;
			values.set((index) % (size),mutanteValues.get((index) % (size)));
		}

		for (int i = 0, size1 = size - count; i < size1; i++) {

			values.set((index + i + 1) % (size),targetValues.get((index + i + 1) % (size)));

		}

		SolutionVector vec = mutante.duplicate();
		vec.setValues(new ArrayList<>(values));
		return vec;
	}
}
