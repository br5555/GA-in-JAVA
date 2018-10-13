package hr.fer.zemris.optjava.dz8.tdnn.pso;

import java.util.List;

public interface IANN {

	int getWeightsCount();
	double[] calcOutputs(double[] inputsOrg, double[] weights);
	double[] calcOutputs(List<Double> inputsOrg, double[] weights);
}
