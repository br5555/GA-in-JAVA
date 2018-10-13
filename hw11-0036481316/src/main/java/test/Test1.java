package test;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;
import hr.fer.zemris.optjava.rng.provimpl.ThreadBoundRNGProvider;

/**
 * Class test working of {@linkplain ThreadBoundRNGProvider}
 * @author Branko
 *
 */
public class Test1 {
	
	/**
	 * Method from which test starts to execute. Program does not used arguments form command line.
	 * @param args arguments form command line
	 * 
	 */
	public static void main(String[] args) {
		IRNG rng = RNG.getRNG();
		for (int i = 0; i < 20; i++) {
			System.out.println(rng.nextInt(-5, 5));
		}
	}
}