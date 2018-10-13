package hr.fer.zemris.optjava.dz13;

/**
 * Right function extends {@linkplain Node} and implements function which rotates ant for 90 degrees 
 * clockwise 
 * @author Branko
 *
 */
public class Right  extends Node {

	/**
	 * Public constructor accepts desire settings
	 * @param ant desire {@linkplain Ant}
	 * @param gameplay desire {@linkplain GamePlay}
	 */
	public Right(Ant ant, GamePlay gameplay, MapGenerator map) {
		super();
		this.name = "Right";
		this.map = map;

		if(map.map[ant.postionY][ant.postionX]==1) 	ant.antScore++;
		map.map[ant.postionY][ant.postionX]=0;
		this.ant = ant;
		this.gameplay = gameplay;
	}
	
	/**
	 * Method which executes right function
	 */
	protected void moveRight(){
		if(!antHasEnergy()) return;
		
		ant.antEnergy--;
//		System.out.println("Ant energy: "+ant.antEnergy);
		ant.angle =(ant.angle+90)%360;
		
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
			
		gameplay.rotateCW();
		
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
		this.moveRight();
		
	}
	
}
