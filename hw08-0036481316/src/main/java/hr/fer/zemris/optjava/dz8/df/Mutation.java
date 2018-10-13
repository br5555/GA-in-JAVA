package hr.fer.zemris.optjava.dz8.df;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class represents mutaion and implements {@linkplain IMutation}
 * 
 * @author Branko
 *
 */
public class Mutation implements IMutation<SolutionVector> {

	/**
	 * Minimal values of dimensions
	 */
	private double[] mins;
	/**
	 * Maximal values of dimensions
	 */
	private double[] maxs;

	private Random rand;
	
	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param mins
	 *            minimal values for dimension
	 * @param maxs
	 *            maximal values for dimension
	 */
	public Mutation(double[] mins, double[] maxs) {
		super();
		this.mins = mins;
		this.maxs = maxs;
		rand = new Random();
	}

	@Override
	public SolutionVector mutate(ArrayList<SolutionVector> orginal, double[] F) {
		
		ArrayList<Double> values = new ArrayList<>(orginal.get(0).getValues());
		for (int i = 0, size = values.size(); i < size; i++) {
//			int i = rand.nextInt(values.size());

			double temp = values.get(i);
			for (int j = 0, sizeF = F.length; j < sizeF; j++) {

				ArrayList<Double> vec1 = orginal.get(2 * j + 1).getValues();
				ArrayList<Double> vec2 = orginal.get(2 * j + 2).getValues();
				double oldTemp = temp;
				temp += F[j] * (vec1.get(i) - vec2.get(i));
				if(oldTemp == temp){
					temp += rand.nextGaussian();
				}
			}
//			if (temp > maxs[i]) {
//				temp = maxs[i];
//			} else if (temp < mins[i]) {
//				temp = mins[i];
//			}
			values.set(i, temp);

		}

		SolutionVector sol = orginal.get(0).duplicate();
		sol.setValues(values);

		return sol;
	}

}
