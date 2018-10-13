package hr.fer.zemris.optjava.dz7.pso;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Class represent empirical error functionand implements {@link IFunction}
 * 
 * @author Branko
 *
 */
public class ErrorFunction implements IFunction<Double> {

	/**
	 * real outputs
	 */
	private double[][] realOutputs;
	/**
	 * real inputs
	 */
	private double[][] realInputs;
	/**
	 * private reference to {@linkplain FFANN}
	 */
	private FFANN network;

	/**
	 * Public constructor accepts desire settings
	 * 
	 * @param realOutputs
	 *            real outputs
	 * @param realInputs
	 *            real inputs
	 * @param network
	 *            desire network
	 */
	public ErrorFunction(double[][] realOutputs, double[][] realInputs, FFANN network) {
		super();
		this.realOutputs = realOutputs;
		this.realInputs = realInputs;
		this.network = network;
	}

	@Override
	public double valueAt(Double[] weights) {
		double error = 0;
		double[] weightsPrim = ArrayUtils.toPrimitive(weights);

		for (int k = 0, numberOfData = realInputs.length; k < numberOfData; k++) {
			double[] calcOutput = network.calcOutputs(realInputs[k], weightsPrim);

			for (int i = 0, size = realOutputs[0].length; i < size; i++) {
				error += Math.pow(calcOutput[i] - realOutputs[k][i], 2);
			}
		}

		return error / realInputs.length;
	}

	@Override
	public double valueAt(double[] values) {

		double error = 0;

		for (int k = 0, numberOfData = realInputs.length; k < numberOfData; k++) {
			double[] calcOutput = network.calcOutputs(realInputs[k], values);

			for (int i = 0, size = realOutputs[0].length; i < size; i++) {
				error += Math.pow(calcOutput[i] - realOutputs[k][i], 2);
			}
		}

		return error / realInputs.length;
	}

	/**
	 * Method checks final result
	 */
	public void finalResult(Double[] weights) {
		double[] values = ArrayUtils.toPrimitive(weights);
		int numOfWrong = 0;
		for (int k = 0, numberOfData = realInputs.length; k < numberOfData; k++) {
			double[] calcOutput = network.calcOutputs(realInputs[k], values);
			double[] classifOut = this.classification(calcOutput);
			if (!Arrays.equals(classifOut, realOutputs[k])) {
				System.out.println("Wrong classification");
				System.out.println("Real " + Arrays.toString(realOutputs[k]));
				System.out.println("Model " + Arrays.toString(classifOut));
				System.out.println();
				numOfWrong++;
			}
		}
		System.out.println("Number of wrong classification is " + numOfWrong);
	}

	/**
	 * Method checks final result
	 */
	public void finalResult(double[] weights) {

		int numOfWrong = 0;
		for (int k = 0, numberOfData = realInputs.length; k < numberOfData; k++) {

			double[] calcOutput = network.calcOutputs(realInputs[k], weights);
			double[] classifOut = this.classification(calcOutput);

			if (!Arrays.equals(classifOut, realOutputs[k])) {
				System.out.println("Wrong classification");
				System.out.println("Real " + Arrays.toString(realOutputs[k]));
				System.out.println("Model " + Arrays.toString(classifOut));
				System.out.println();
				numOfWrong++;

			}
		}
		System.out.println("Number of wrong classification is " + numOfWrong);

	}

	/**
	 * Converts probabilities to classification
	 * 
	 * @param calcOutput
	 *            probabilities
	 * @return classification
	 */
	private double[] classification(double[] calcOutput) {
		double[] classifier = new double[calcOutput.length];
		for (int i = 0, size = calcOutput.length; i < size; i++) {
			classifier[i] = calcOutput[i] >= 0.5 ? 1 : 0;
		}
		return classifier;
	}

}
