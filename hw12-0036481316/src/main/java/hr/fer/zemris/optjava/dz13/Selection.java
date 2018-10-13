package hr.fer.zemris.optjava.dz13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Method which implements {@linkplain ISelection} and represents selection in genetic programming
 * @author Branko
 *
 */
public class Selection implements ISelection{

	@Override
	public Node select(ArrayList<Node> minPop) {
		
		Collections.sort(minPop, new Comparator<Node>() {
			public int compare(Node o1, Node o2) {
				if(o1.ant.antScore > o2.ant.antScore) return -1;
				else if(o1.ant.antScore == o2.ant.antScore) return 0;
				else return 1;
			};
		});
		return minPop.get(0).copy();
	}

}
