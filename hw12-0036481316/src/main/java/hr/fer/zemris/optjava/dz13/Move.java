package hr.fer.zemris.optjava.dz13;

/**
 * Class represent move function which moves ant forward and extends {@linkplain Node}
 * @author Branko
 *
 */
public class Move extends Node {

	/**
	 * Public constructor accepts desire settings
	 * @param ant desire {@linkplain Ant}
	 * @param map desire {@linkplain MapGenerator}
	 * @param gameplay desire {@linkplain GamePlay}
	 */
	public Move(Ant ant,MapGenerator map,GamePlay gameplay) {
		super();
		this.name = "Move";
		this.map = map;

		if(map.map[ant.postionY][ant.postionX]==1) 	ant.antScore++;
		map.map[ant.postionY][ant.postionX]=0;
		this.ant = ant;
		this.map = map;
		this.gameplay = gameplay;
	}
	
	/**
	 * Method which executes function move
	 */
	protected void move(){
		if(!antHasEnergy()) return;
		
		this.parent.fromStart = true;
		this.fromStart= true;
		
		ant.antEnergy--;
//		System.out.println("Ant energy " +ant.antEnergy);

		ant.postionX +=ant.dirX;
		ant.postionY +=ant.dirY;
		
		if(ant.postionX > ant.maxX){
			ant.postionX = ant.minX;
		}else if(ant.postionX < ant.minX){
			ant.postionX = ant.maxX;
		}
		if(ant.postionY > ant.maxY){
			ant.postionY = ant.minY;
		}else if(ant.postionY < ant.minY){
			ant.postionY = ant.maxY;
		}
		if(map.map[ant.postionY][ant.postionX]==1){
			ant.antScore++;
			map.map[ant.postionY][ant.postionX]=0;
		}
		if(ant.dirX != 0){
			if(ant.dirX >0) gameplay.moveRight();
			else gameplay.moveLeft();
		}else{
			if(ant.dirY >0) gameplay.moveUp();
			else gameplay.moveDown();
		}
		if(simulation){

			try {
				Thread.currentThread().sleep(timeDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void executeFunction() {
		this.move();
		
	}
	
	
}
