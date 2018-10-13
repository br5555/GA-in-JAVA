package hr.fer.zemris.optjava.dz13;
/**
 * Class represents prog3 function and extends {@linkplain Node}
 * @author Branko
 *
 */
public class Prog3  extends Node {

	
	/**
	 * Public constructor accepts desire settings
	 * @param ant desire {@linkplain Ant}
	 * @param map desire {@linkplain MapGenerator}
	 */
	public Prog3(Ant ant, MapGenerator map) {
		super();
		this.map = map;
		if(map.map[ant.postionY][ant.postionX]==1) 	ant.antScore++;
		map.map[ant.postionY][ant.postionX]=0;
		this.name = "Prog3";

		this.isFunction = true;
		this.ant = ant;



	}
	
	/**
	 * Method executes prog3 function
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
		
		this.rightChild.executeFunction();
		
		if(this.fromStart){
			if(this.parent != null) this.parent.fromStart = true;
			return;
		}
	}
	
//	@Override
//	public void setFunctions() {
//		this.a = this.leftChild;
//		this.b = this.middleChild;
//		this.c = this.rightChild;
//	}


	
	@Override
	public void executeFunction() {
		this.execute();
		
	}
}
