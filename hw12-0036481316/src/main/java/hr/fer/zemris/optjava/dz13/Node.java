package hr.fer.zemris.optjava.dz13;

/**
 * Class represents tree node and implements {@linkplain IFunction}
 * 
 * @author Branko
 *
 */
public class Node implements IFunction {
	/**
	 * simulation flag
	 */
	protected boolean simulation = false;
	protected int timeDelay = 700;
	/**
	 * Name of function
	 */
	protected String name;
	/**
	 * flage tells if move function is executed
	 */
	protected boolean fromStart = false;
	/**
	 * flag tells if node is function node
	 */
	protected boolean isFunction = false;
	/**
	 * parent node
	 */
	protected Node parent = null;
	/**
	 * left child
	 */
	protected Node leftChild = null;
	/**
	 * right child
	 */
	protected Node rightChild = null;
	/**
	 * middle child
	 */
	protected Node middleChild = null;
	/**
	 * depth of node in tree
	 */
	protected int depth;
	/**
	 * instance of {@linkplain IFunction}
	 */
	protected IFunction func = null;
	/**
	 * number of children
	 */
	protected int numOfChild = 0;
	/**
	 * reference to instance of {@linkplain Ant}
	 */
	protected Ant ant;
	/**
	 * reference to instance of {@linkplain GamePlay}
	 */
	protected GamePlay gameplay;
	/**
	 * reference to instance of {@linkplain MapGenerator}
	 */
	protected MapGenerator map;

	/**
	 * public method checks if ant has energy
	 * 
	 * @return true if ant has energy otherwise false
	 */
	public boolean antHasEnergy() {
		if (ant.antEnergy < 0) {
			throw new IllegalAccessError("Ant cannot have negative energy");
		}
		return ant.antEnergy > 0;
	}

	/**
	 * Public getter returns function of node
	 * 
	 * @return
	 */
	public IFunction getFunc() {
		return func;
	}

	/**
	 * Method calculates maximal tree depth
	 * 
	 * @param root
	 *            desire node
	 * @return depth of that tree starting form desire node
	 */
	public static int depthOfSubtree(Node root) {
		if (root.leftChild == null && root.rightChild == null && root.middleChild == null) {
			return root.depth;
		}
		int max = -1;
		if (root.leftChild != null) {
			int temp = depthOfSubtree(root.leftChild);
			if (temp > max) {
				max = temp;
			}
		}
		if (root.middleChild != null) {
			int temp = depthOfSubtree(root.middleChild);
			if (temp > max) {
				max = temp;
			}
		}
		if (root.rightChild != null) {
			int temp = depthOfSubtree(root.rightChild);
			if (temp > max) {
				max = temp;
			}
		}
		return max;
	}

	/**
	 * Method update number of children
	 * 
	 * @param parent
	 *            parent node
	 */
	public void newChild(Node parent) {
		if (parent == null)
			return;

		parent.numOfChild++;
		parent.newChild(parent.parent);
	}

	public void setFunctions() {

	}

	/**
	 * Method updates depth of tree
	 * 
	 * @param root
	 *            desire root
	 */
	protected static void setDepth(Node root) {
		if (root.parent != null)
			root.depth = root.parent.depth + 1;
		else
			root.depth = 1;

		if (root.leftChild != null) {
			setDepth(root.leftChild);
		}

		if (root.middleChild != null) {
			setDepth(root.middleChild);
		}

		if (root.rightChild != null) {
			setDepth(root.rightChild);
		}
	}

	/**
	 * Method deep copy of a tree
	 * 
	 * @return copy of a tree
	 */
	protected Node copy() {
		Node leftChild = null;
		Node rightChild = null;
		Node middleChild = null;

		if (this.leftChild != null) {
			leftChild = this.leftChild.copy();
		}
		if (this.rightChild != null) {
			rightChild = this.rightChild.copy();
		}
		if (this.middleChild != null) {
			middleChild = this.middleChild.copy();
		}

		Node temp = null;
		if (this instanceof Prog2) {
			temp = new Prog2(this.ant, this.map);
		} else if (this instanceof Prog3) {
			temp = new Prog3(this.ant, this.map);
		} else if (this instanceof IfFoodAhead) {
			temp = new IfFoodAhead(this.ant, this.map);
		} else if (this instanceof Left) {
			temp = new Left(this.ant, gameplay, this.map);
		} else if (this instanceof Right) {
			temp = new Right(this.ant, gameplay, this.map);
		} else if (this instanceof Move) {
			temp = new Move(this.ant, this.map, gameplay);
		}
		temp.leftChild = leftChild;
		temp.middleChild = middleChild;
		temp.rightChild = rightChild;
		temp.func = this.func;
		temp.depth = this.depth;
		temp.numOfChild = numOfChild;
		temp.fromStart = this.fromStart;

		if (temp.middleChild != null)
			temp.middleChild.parent = temp;
		if (temp.rightChild != null)
			temp.rightChild.parent = temp;
		if (temp.leftChild != null)
			temp.leftChild.parent = temp;
		temp.setFunctions();
		return temp;
	}

	@Override
	public void executeFunction() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method reset game memory
	 */
	public void resetGame() {

		if (this.parent == null) {

			this.map = this.map.firstMap();
			this.ant = new Ant(0, 0, 1, 0, 0, 31, 0, 31, 0);

			this.gameplay = new GamePlay(this.map, this.ant);
			if (map.map[this.ant.postionX][this.ant.postionY] == 1)
				this.ant.antScore++;
			map.setBrickValue(0, this.ant.postionY, this.ant.postionX);
			this.resetFlage();
		}

		if (this.leftChild != null) {

			this.leftChild.ant = this.ant;
			this.leftChild.map = this.map;
			this.leftChild.gameplay = this.gameplay;

			this.leftChild.resetGame();
		}

		if (this.middleChild != null) {

			this.middleChild.ant = this.ant;
			this.middleChild.map = this.map;
			this.middleChild.gameplay = this.gameplay;

			this.middleChild.resetGame();
		}

		if (this.rightChild != null) {

			this.rightChild.ant = this.ant;
			this.rightChild.map = this.map;
			this.rightChild.gameplay = this.gameplay;

			this.rightChild.resetGame();
		}

	}

	/**
	 * Reset a flag which indicates if move function was executed
	 */
	public void resetFlage() {
		if (this.parent == null) {
			this.fromStart = false;
		}
		if (this.leftChild != null) {
			this.leftChild.fromStart = false;
			this.leftChild.resetFlage();
		}
		if (this.middleChild != null) {
			this.middleChild.fromStart = false;
			this.middleChild.resetFlage();
		}
		if (this.rightChild != null) {
			this.rightChild.fromStart = false;
			this.rightChild.resetFlage();
		}

	}

	/**
	 * Simulation flag turn on
	 */
	public void turnSimulation() {
		if (this.parent == null) {
			this.simulation = true;
		}
		if (this.leftChild != null) {
			this.leftChild.simulation = true;
			this.leftChild.turnSimulation();
			this.leftChild.resetFlage();
		}
		if (this.middleChild != null) {
			this.middleChild.simulation = true;
			this.middleChild.turnSimulation();
			this.middleChild.resetFlage();
		}
		if (this.rightChild != null) {
			this.rightChild.simulation = true;
			this.rightChild.turnSimulation();
			this.rightChild.resetFlage();
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (name == null)
			return null;
		sb.append(name).append("( ");

		if (this.leftChild != null) {
			String leftStr = this.leftChild.toString();
			if (leftStr != null)
				sb.append(leftStr);
		}

		if (this.middleChild != null) {
			String middStr = this.middleChild.toString();

			if (middStr != null)
				sb.append(", ").append(middStr);
		}
		if (this.rightChild != null) {
			String rightStr = this.rightChild.toString();

			if (rightStr != null)
				sb.append(", ").append(rightStr);
		}
		sb.append(" )");
		return sb.toString();
	}

	/**
	 * Method punish rewriting
	 */
	public void childCrib() {
		if (this.parent == null) {
			this.ant.antScore *= 0.9;
		}
		if (this.leftChild != null) {
			this.leftChild.ant.antScore = this.ant.antScore;
			this.leftChild.childCrib();
		}
		if (this.middleChild != null) {
			this.middleChild.ant.antScore = this.ant.antScore;
			this.middleChild.childCrib();
		}
		if (this.rightChild != null) {
			this.rightChild.ant.antScore = this.ant.antScore;
			this.rightChild.childCrib();
		}
	}
}
