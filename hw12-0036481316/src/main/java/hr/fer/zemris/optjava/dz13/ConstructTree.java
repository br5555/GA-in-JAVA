package hr.fer.zemris.optjava.dz13;
import java.util.Random;

/**
 * Class construct trees with two methods full and growth
 * @author Branko
 *
 */
public class ConstructTree {
	/**
	 * private instance of {@linkplain Random}
	 */
	private Random rand;
	/**
	 * instance of {@linkplain Ant}
	 */
	protected Ant ant;
	/**
	 * private instance of {@linkplain MapGenerator}
	 */
	private MapGenerator map;
	/**
	 * desire maximal depth of tree
	 */
	public static int maxDepth;
	/**
	 * desire maximal number of nodes in tree
	 */
	public static int maxNodes;
	/**
	 * maximal depth of tree
	 */
	public static int limitDepth;
	/**
	 * maximal number of nodes in tree
	 */
	public static int limitNodes;

	/**
	 * private instance of {@linkplain GamePlay}
	 */
	private GamePlay gameplay;
	
	private int counter = 0;
	
	
	public void resetCounter(){
		counter = 0;
	}
	
	/**
	 * Public constructor accepts desire settings
	 * @param ant desire instance of {@linkplain Ant}
	 * @param map desire instance of {@linkplain MapGenerator}
	 * @param maxDepth desire maximal depth
	 * @param maxNodes desire maximal number of nodes
	 * @param limitDepth maximal possible depth
	 * @param limitNodes maximal possible number of nodes in tree
	 * @param gameplay desire instance of {@linkplain GamePlay}
	 */
	public ConstructTree(Ant ant, MapGenerator map, int maxDepth, int maxNodes, int limitDepth,int limitNodes,GamePlay gameplay) {
		super();
		this.ant = ant;
		this.map = map;
		this.maxDepth = maxDepth;
		this.maxNodes = maxNodes;
		this.gameplay = gameplay;
		this.limitDepth = limitDepth;
		this.limitNodes = limitNodes;
		rand = new Random();
	}

	
	/**
	 * Method grow construct tree which randomly picks nodes from function and terminal nodes
	 * @param parent parent Node
	 * @param head root Node
	 * @return constructed tree
	 */
	public Node grow(Node parent, Node head) {
		Node root = new Node();
		boolean first = false;
		if(head == null) first = true;

		if (parent == null) {

			root.parent = parent;
			head = root;
			parent = root;
			head.depth = 1;
		} else {

			if (parent != null)
				root.parent = parent;
		}
		Node firstRoot = root;
		int numNodes = head.numOfChild;

		 

		if (first)
			root.depth = 1;
		else
			root.depth = root.parent.depth + 1;

		if (maxDepth < root.depth)
			return null;

		int in;

		if (parent == head)
			in = rand.nextInt(3);
		else if (maxDepth == root.depth || maxNodes - counter <= numNodes)
			in = rand.nextInt(3) + 3;
		else
			in = rand.nextInt(6);

		root.newChild(root.parent);
		switch (in) {
		case 0:
			counter +=2;
			IfFoodAhead food = new IfFoodAhead(ant,  map);
			food.parent = root.parent;
			food.depth = root.depth;
			root = food;
			if(first){
				head = root;
				first = false;
			}
			food.leftChild = grow(root, head);
			food.middleChild = grow(root, head);
			food.rightChild = null;
			food.gameplay = gameplay;

			root.func = food;

			break;
		case 1:
			counter +=2;

			Prog2 prog2 = new Prog2(ant, map);
			prog2.parent = root.parent;
			prog2.depth = root.depth;
			root = prog2;
			if(first){
				head = root;
				first = false;
			}
			prog2.leftChild = grow(root, head);
			prog2.middleChild = grow(root, head);
			prog2.rightChild = null;
			prog2.gameplay = gameplay;

			root.func = prog2;

			break;
		case 2:
			counter +=3;
			Prog3 prog3 = new Prog3(ant, map);
			prog3.parent = root.parent;
			prog3.depth = root.depth;
			root = prog3;
			if(first){
				head = root;
				first = false;
			}
			prog3.leftChild = grow(root, head);
			prog3.middleChild = grow(root, head);
			prog3.rightChild = grow(root, head);
			prog3.gameplay = gameplay;
			root.func = prog3;

			break;
		case 3:
			Move move = new Move(ant, map, gameplay);
			move.parent = root.parent;
			move.depth = root.depth;
			root = move;
			break;
		case 4:
			Right right = new Right(ant, gameplay, map);
			right.parent = root.parent;
			right.depth = root.depth;
			root = right;
			break;
		case 5:
			Left left = new Left(ant, gameplay, map);
			left.parent = root.parent;
			left.depth = root.depth;
			root = left;
			break;

		}
		root.newChild(root.parent);
		return root;
	}
	
	/**
	 * Method full construct tree with maximal depth or limit number of nodes
	 * @param parent parent Node 
	 * @param head root Node
	 * @return constructed tree
	 */
	public Node full(Node parent, Node head) {
		Node root = new Node();
		boolean first = false;
		if(head == null) first = true;

		if (parent == null) {

			root.parent = parent;
			head = root;
			parent = root;
			head.depth = 1;
		} else {

			if (parent != null)
				root.parent = parent;
		}
		Node firstRoot = root;
		int numNodes = head.numOfChild;

		 

		if (first)
			root.depth = 1;
		else
			root.depth = root.parent.depth + 1;

		if (maxDepth < root.depth)
			return null;

		int in;

		if (parent == head)
			in = rand.nextInt(3);
		else if (maxDepth == root.depth || maxNodes - counter <= numNodes)
			in = rand.nextInt(3) + 3;
		else
			in = rand.nextInt(3);

		root.newChild(root.parent);
		switch (in) {
		case 0:
			counter +=2;
			IfFoodAhead food = new IfFoodAhead(ant, map);
			food.parent = root.parent;
			food.depth = root.depth;
			root = food;
			if(first){
				head = root;
				first = false;
			}
			food.leftChild = full(root, head);
			food.middleChild = full(root, head);
			food.rightChild = null;
			food.gameplay = gameplay;

			root.func = food;

			break;
		case 1:
			counter +=2;
			Prog2 prog2 = new Prog2(ant,map);
			prog2.parent = root.parent;
			prog2.depth = root.depth;
			root = prog2;
			if(first){
				head = root;
				first = false;
			}
			prog2.leftChild = full(root, head);
			prog2.middleChild = full(root, head);
			prog2.rightChild = null;
			prog2.gameplay = gameplay;

			root.func = prog2;

			break;
		case 2:
			counter +=3;

			Prog3 prog3 = new Prog3(ant, map);
			prog3.parent = root.parent;
			prog3.depth = root.depth;
			prog3.gameplay = gameplay;

			root = prog3;
			if(first){
				head = root;
				first = false;
			}
			prog3.leftChild = full(root, head);
			prog3.middleChild = full(root, head);
			prog3.rightChild = full(root, head);
			root.func = prog3;

			break;
		case 3:
			Move move = new Move(ant, map, gameplay);
			move.parent = root.parent;
			move.depth = root.depth;
			root = move;
			break;
		case 4:
			Right right = new Right(ant, gameplay, map);
			right.parent = root.parent;
			right.depth = root.depth;
			root = right;
			break;
		case 5:
			Left left = new Left(ant, gameplay, map);
			left.parent = root.parent;
			left.depth = root.depth;
			root = left;
			break;

		}
		

		return root;
	}

}
