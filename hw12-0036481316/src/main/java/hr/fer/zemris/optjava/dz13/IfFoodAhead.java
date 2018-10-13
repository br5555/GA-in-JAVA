package hr.fer.zemris.optjava.dz13;

/**
 * Class represnts If food ahead function and extends {@linkplain Node}
 * @author Branko
 *
 */
public class IfFoodAhead extends Node{
	
	/**
	 * Public constructor accepts desire settings
	 * @param ant desire {@linkplain Ant}
	 * @param map desire {@linkplain MapGenerator}
	 */
	public IfFoodAhead(Ant ant, MapGenerator map) {
		super();
		if(map.map[ant.postionY][ant.postionX]==1) 	ant.antScore++;
		map.map[ant.postionY][ant.postionX]=0;
		this.isFunction = true;
		this.name = "IfFoodAhead";

		this.ant = ant;
		this.map = map;
	}
	
	/**
	 * Method which executes if food ahead function
	 */
	private void ifFoodAhead(){
		int tempX = ant.postionX +ant.dirX;
		int tempY =ant.postionY +ant.dirY;
		
		if(tempX > ant.maxX){
			tempX = ant.minX;
		}else if(tempX < ant.minX){
			tempX = ant.maxX;
		}
		if(tempY > ant.maxY){
			tempY = ant.minY;
		}else if(tempY < ant.minY){
			tempY = ant.maxY;
		}
		
		if(map.map[tempY][tempX] == 1){
			this.leftChild.executeFunction(); 
		}else{
			this.middleChild.executeFunction(); 
		}
		if(this.fromStart){
			if(this.parent != null) this.parent.fromStart = true;
			return;
		}
	}

//	@Override
//	public void setFunctions() {
//		this.a = this.leftChild;
//		this.b = this.middleChild;
//	}
	
	
	@Override
	public void executeFunction() {
		this.ifFoodAhead();
		
	}

}
