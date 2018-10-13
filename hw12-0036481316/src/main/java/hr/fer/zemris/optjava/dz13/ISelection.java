package hr.fer.zemris.optjava.dz13;

import java.util.ArrayList;
/**
 * Interface represents tournament selection fro genetoc programming
 * @author Branko
 *
 */
public interface ISelection {

	Node select(ArrayList<Node> minPop);
	
}
