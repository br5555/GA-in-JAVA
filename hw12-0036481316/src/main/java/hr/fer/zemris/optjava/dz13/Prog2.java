package hr.fer.zemris.optjava.dz13;

/**
 * Class represents prog2 function and extends {@linkplain Node}
 * @author Branko
 *
 */
public class Prog2  extends Node {

	
	/**
	 * Public constructor accepts desire settings
	 * @param ant desire {@linkplain Ant}
	 * @param map desire {@linkplain MapGenerator}
	 */
	public Prog2(Ant ant, MapGenerator map) {
		super();
		this.map = map;
		if(map.map[ant.postionY][ant.postionX]==1) 	ant.antScore++;
		map.map[ant.postionY][ant.postionX]=0;
		this.name = "Prog2";
		this.isFunction = true;
		this.ant = ant;

	}
	
	/**
	 * Method executes prog2 function
	 */
	public void execute(){
		this.leftChild.executeFunction();
		if(this.fromStart){
			if(this.parent != null) this.parent.fromStart = true;
			return;
		}
		this.middleChild.executeFunction();
		if(this.fromStart){
			if(this.parent != null) this.parent.fromStart = true;
			return;
		}
	}

	@Override
	public void executeFunction() {
		this.execute();
		
	}
	
	
//	@Override
//	public void setFunctions() {
//		this.a = this.leftChild;
//		this.b = this.middleChild;
//	}

}
