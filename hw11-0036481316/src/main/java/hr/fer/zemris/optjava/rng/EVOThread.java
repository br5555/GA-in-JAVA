package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

/**
 * Class implements {@linkplain IRNGProvider} and extends {@linkplain Thread}
 * @author Branko
 *
 */
public class EVOThread extends Thread implements IRNGProvider {
	
	/**
	 * Private instance of {@linkplain RNGRandomImpl}
	 */
	private IRNG rng = new RNGRandomImpl();

	/**
	 * Public constructor 
	 */
	public EVOThread() {
	}

	/**
	 * Public constructor accepts desire settings
	 * @param target desire job
	 */
	public EVOThread(Runnable target) {
		super(target);
	}

	/**
	 * Public constructor accepts desire settings
	 * @param name desire name
	 */
	public EVOThread(String name) {
		super(name);
	}

	/**
	 * Public constructor accepts desire settings
	 * @param group desire {@linkplain ThreadGroup}
	 * @param target desire job which is instance of {@linkplain Runnable}
	 */
	public EVOThread(ThreadGroup group, Runnable target) {
		super(group, target);
	}

	/**
	 * Public constructor accepts desire settings
	 * @param group desire {@linkplain ThreadGroup}
	 * @param name desire name
	 */
	public EVOThread(ThreadGroup group, String name) {
		super(group, name);
	}

	/**
	 * Public constructor accepts desire settings
	 * @param target desire job which is instance of {@linkplain Runnable}
	 * @param name desire name
	 */
	public EVOThread(Runnable target, String name) {
		super(target, name);
	}

	/**
	 * Public constructor accepts desire settings
	 * @param group desire {@linkplain ThreadGroup}
	 * @param target desire job which is instance of {@linkplain Runnable}
	 * @param name desire name
	 */
	public EVOThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
	}

	/**
	 * Public constructor accepts desire settings
	 * @param group desire {@linkplain ThreadGroup}
	 * @param target desire job which is instance of {@linkplain Runnable}
	 * @param name desire name 
	 * @param stackSize desire stack size
	 */
	public EVOThread(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
	}

	@Override
	public IRNG getRNG() {
		return rng;
	}
}