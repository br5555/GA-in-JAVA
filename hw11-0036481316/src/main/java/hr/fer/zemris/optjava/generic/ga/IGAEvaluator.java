package hr.fer.zemris.optjava.generic.ga;

/**
 * Interface for evaluating GA solution {@linkplain GASolution}
 * @author Branko
 *
 * @param <T> generic parameter
 */
public interface IGAEvaluator<T> {
	public void evaluate(GASolution<T> p);
}