package hr.fer.zemris.optjava.dz13;

/**
 * Left function extends {@linkplain Node} and impleemnts function which rotates ant for 90 degress 
 * counter clockwise 
 * @author Branko
 *
 */
public class Left extends Node {

	/**
	 * Public constructor accepts desire settings
	 * @param ant instance of {@linkplain Ant}
	 * @param gameplay instance of {@linkplain GamePlay} 
	 */
	public Left(Ant ant,GamePlay gameplay, MapGenerator map) {
		super();
		this.name = "Left";
		this.map = map;
		if(map.map[ant.postionY][ant.postionX]==1) 	ant.antScore++;
		map.map[ant.postionY][ant.postionX]=0;
		this.ant = ant;
		this.gameplay = gameplay;
	}
	
	/**
	 * Method executes function Left
	 */
	protected void moveLeft(){
		if(!antHasEnergy()) return;
		ant.antEnergy--;
//		System.out.println("Ant energy: "+ant.antEnergy);
		ant.angle =(ant.angle+270)%360;
		
			if(ant.angle == 0){
				ant.dirX = 1;
				ant.dirY = 0;
			}else if(ant.angle == 90){
				ant.dirX = 0;
				ant.dirY = 1;
			}else if(ant.angle == 180){
				ant.dirX = -1;
				ant.dirY = 0;
			}else{
				ant.dirX = 0;
				ant.dirY = -1;
			}
			
			gameplay.rotateCCW();
			
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
		this.moveLeft();
		
	}


}
