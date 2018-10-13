package hr.fer.zemris.optjava.dz13;
import java.util.Random;

/**
 * Class implements ICrossover and represents crossover of genetic programming
 * @author Branko
 *
 */
public class Crossover implements ICrossover {

	/**
	 * private instance of {@linkplain Random}
	 */
	private Random rand = new Random();
	/**
	 * private instance of {@linkplain ConstructTree}
	 */
	private ConstructTree tree;

	
	
	
	public Crossover(ConstructTree tree) {
		super();
		this.tree = tree;
	}




	@Override
	public Node crossover(Node parent1, Node parent2) {

		Node parent1Copy = parent1.copy();
		Node parent2Copy = parent2.copy();

		int num = rand.nextInt(tree.limitDepth)+1;//+4;
		float randPercent = rand.nextFloat();

		Node temp1 = null;
		int randInt = num;

		for (int i = 0; i < num; i++, randInt += 5) {
			int child = randInt % 3;
			if (child == 0) {
				if (parent1Copy.leftChild != null) {
					parent1Copy = parent1Copy.leftChild;
					temp1 = parent1Copy;
				} else {
					temp1 = parent1Copy;
					break;
				}

			} else if (child == 1) {
				if (parent1Copy.middleChild != null) {
					parent1Copy = parent1Copy.middleChild;
					temp1 = parent1Copy;
				} else {
					temp1 = parent1Copy;
					break;
				}

			} else {
				if (parent1Copy.rightChild != null) {
					parent1Copy = parent1Copy.rightChild;
					temp1 = parent1Copy;
				} else {
					if (parent1Copy.leftChild != null) {
						parent1Copy = parent1Copy.leftChild;
						temp1 = parent1Copy;
					} else {
						temp1 = parent1Copy;
						break;
					}
				}
			}
		}
		temp1 = parent1Copy;
		if(!temp1.isFunction && temp1.parent != null && temp1.parent.parent != null && randPercent <= 0.9f){
			temp1 = temp1.parent;
		}
		
		Node temp2 = null;

		for (int i = 0; i < num; i++, randInt += 7) {
			int child = randInt % 3;
			if (child == 0) {
				if (parent2Copy.leftChild != null) {
					parent2Copy = parent2Copy.leftChild;
					temp2 = parent2Copy;
				} else {
					temp2 = parent2Copy;
					break;
				}

			} else if (child == 1) {
				if (parent2Copy.middleChild != null) {
					parent2Copy = parent2Copy.middleChild;
					temp2 = parent2Copy;
				} else {
					temp2 = parent2Copy;
					break;
				}

			} else {
				if (parent2Copy.rightChild != null) {
					parent2Copy = parent2Copy.rightChild;
					temp2 = parent2Copy;
				} else {
					temp2 = parent2Copy;
					break;
				}
			}
		}
		temp2 = parent2Copy;

		if(!temp2.isFunction && temp2.parent != null && temp2.parent.parent != null && randPercent <= 0.9f){
			temp2 = temp2.parent;
		}
		
		int limit1 = temp1.depth - 1 + temp2.depthOfSubtree(temp2);

		if (limit1 > tree.limitDepth) {
			while (parent1Copy.parent != null) {
				parent1Copy = parent1Copy.parent;
			}
			return parent1Copy;
		}

		int limit2 = temp2.depth - 1 + temp1.depthOfSubtree(temp1);

		if (limit2 > tree.limitDepth) {
			while (parent2Copy.parent != null) {
				parent2Copy = parent2Copy.parent;
			}
			return parent2Copy;
		}
		
		
		Node child = temp1.parent;
		int nn= child.numOfChild;
		int nn1 = temp2.numOfChild;
		int nn2 = temp2.numOfChild;

		child.numOfChild += temp2.numOfChild - temp1.numOfChild + 1;

		tree.resetCounter();
		
		
		if(child.leftChild == temp1){
			child.leftChild = temp2;
			temp2.parent = child;
			temp2.setDepth(temp2);
			while (child.parent != null) {
				child = child.parent;
			}
			child.resetGame();

			return child;
		}else if(child.middleChild == temp1){
			child.middleChild = temp2;
			temp2.parent = child;
			temp2.setDepth(temp2);

			while (child.parent != null) {
				child = child.parent;
			}
			child.resetGame();

			return child;
		}else{
			child.rightChild = temp2;
			temp2.parent = child;
			temp2.setDepth(temp2);


			while (child.parent != null) {
				child = child.parent;
			}
			child.resetGame();

			return child;
		}
	}

}
