package hr.fer.zemris.optjava.dz13;
import java.util.Random;
/**
 * Class implements {@linkplain IMutation} and represents mutation in genetic programming
 * @author Branko
 *
 */
public class Mutation implements IMutation{

	/**
	 * private instance of {@linkplain Random}
	 */ 
	private Random rand;
	/**
	 * private instance of {@linkplain ConstructTree}
	 */
	private ConstructTree tree;
	
	/**
	 * Public constructor which accepts desire settings
	 * @param tree desire {@linkplain ConstructTree}
	 */
	public Mutation(ConstructTree tree) {
		super();
		this.tree = tree;
		rand = new Random();
	}


	@Override
	public Node mutate(Node parent1) {
		Node parent1Copy = parent1.copy();
		parent1Copy.resetGame();
		int num = rand.nextInt(tree.limitDepth)+1;
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
		
		int desireDepth = tree.limitDepth - temp1.depth;
		
		Node root = temp1;
		while (root.parent != null) {
			root = root.parent;
		}
		int desireNodes = tree.limitNodes-root.numOfChild;
		if(desireNodes <= 0) desireNodes =1;
		tree.maxNodes = rand.nextInt(desireNodes);
		if(tree.maxNodes== 0) tree.maxNodes = 1;
		if(desireDepth <= 0) desireDepth =1;

		tree.maxDepth = rand.nextInt(desireDepth);
		if(tree.maxDepth< 3) tree.maxDepth = 3;

		Node temp = tree.grow(null, null);
		
		Node child = temp1.parent;
		child.numOfChild += temp.numOfChild - temp1.numOfChild+1;
		
		tree.resetCounter();

		
		if(child.leftChild == temp1){
			child.leftChild = temp;
			temp.parent = child;
			temp.setDepth(temp);
			
			while (child.parent != null) {
				child = child.parent;
			}
			child.resetGame();
			return child;
			
		}else if(child.middleChild == temp1){
			child.middleChild = temp;
			temp.parent = child;
			temp.setDepth(temp);

			while (child.parent != null) {
				child = child.parent;
			}
			child.resetGame();

			return child;
		}else{
			child.rightChild = temp;
			temp.parent = child;
			temp.setDepth(temp);


			while (child.parent != null) {
				child = child.parent;
			}
			child.resetGame();

			return child;
		}
	}

}
