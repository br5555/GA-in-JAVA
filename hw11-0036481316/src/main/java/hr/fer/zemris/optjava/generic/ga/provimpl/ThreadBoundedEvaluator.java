package hr.fer.zemris.optjava.generic.ga.provimpl;

import hr.fer.zemris.optjava.generic.ga.GASolution;
import hr.fer.zemris.optjava.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.rng.IRNGProvider;

/**
 * 
 * @author Branko
 *
 */
public class ThreadBoundedEvaluator implements IGAEvaluator<int[]>{

	@Override
	public void evaluate(GASolution<int[]> p) {
		( (IRNGProvider) Thread.currentThread() ).getRNG();
		
	}

}
