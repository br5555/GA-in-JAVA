package hr.fer.zemris.optjava.dz7.pso;

public interface IFunction<T> {

	double valueAt(T[] values);
	double valueAt(double[] values);
	void finalResult(Double[] weights);
	void finalResult(double[] weights);
}
