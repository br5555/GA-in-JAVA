package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Class thet represent container of boxes.
 * 
 * @author Branko
 *
 */
public class Boxes {

	/**
	 * Private list which stores boxes which contains
	 */
	private ArrayList<Box> boxes;
	/**
	 * Number of boxes in the container
	 */
	private int numberOfBoxes;
	/**
	 * Private reference of {@linkplain Random}
	 */
	private Random rand;
	/**
	 * quality of the container
	 */
	public double fitness;
	/**
	 * Private reference of {@linkplain BoxFunction}
	 */
	private BoxFunction func;
	
	/**
	 * Public method which return number of sticks in the container
	 * @return number of sticks in container.
	 */
	public int numberOfSticksInBox(){
		int sum = 0;
		for(Box box : boxes){
			sum+=box.getNumberOfSticks();
		}
		return sum;
	}
	
	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param boxes list of {@linkplain Box}
	 * @param func reference to {@linkplain BoxFunction}
	 */
	public Boxes(List<Box> boxes, BoxFunction func) {
		 
		this.boxes = new ArrayList();
		for(int i =0; i<boxes.size(); i++){
			this.boxes.add(new Box(boxes.get(i).getSticks(), boxes.get(i).getSize()));
		}
		this.numberOfBoxes = boxes.size();
		rand = new Random();
		this.func = func;
		this.fitness = func.valueAt(this);
	}
	
	/**
	 * Public getter which returns list of boxes which container holds. 
	 * @return list of boxes which container holds
	 */
	public List<Box> getBoxes() {
		return new ArrayList(boxes);
	}
	
	/**
	 * Public getter which returns number of boxes in container
	 * @return
	 */
	public int getNumberOfBoxes() {
		return boxes.size();
	}
	
	/**
	 * Public method which returns desire Box
	 * @param i index of desire box
	 * @return desire box
	 */
	public Box returnBox(int i){
		return boxes.get(i);
	}
	
	/**
	 * Public method which take out desire box from conatiner
	 * @param i index of desire box
	 * @return desire box
	 */
	public Box getBox(int i){
		Box temp = boxes.get(i);
		this.boxes.remove(i);
		this.fitness = func.valueAt(this);
		return temp;
	}
	
	/**
	 * Public method which adds desire boxes into container making sure that 
	 * consistency is satisfied 
	 * @param boxes list of desire boxes
	 */
	public void addBoxes(List<Box> boxes){
		
		ArrayList<Box> myBoxes = new ArrayList<>();
		for(int i = 0; i< this.boxes.size(); i++){
			myBoxes.add(new Box(this.returnBox(i).getSticks(), this.returnBox(i).getSize()));
		}
		

		
		
		ArrayList<Stick> redundantSticks = new ArrayList<>();
		for(Box box : boxes){
			for(Stick stick: box.getSticks()){
				redundantSticks.add(new Stick(stick.getId(), stick.getSize()));
			}
		}
		
		ArrayList<Box> redundantBoxes = new ArrayList<>();
		for(Box box: myBoxes){
			for(Stick stick: redundantSticks){
				if(box.containsStick(stick)){
					redundantBoxes.add(box);
					break;
				}
			}
		}
		ArrayList<Stick> mySticks = new ArrayList<>();
		for(Box box : redundantBoxes){
			for(Stick stick: box.getSticks()){
				mySticks.add(new Stick(stick.getId(), stick.getSize()));
			}
		}
		
		myBoxes.removeAll(redundantBoxes);
		for(Box box : boxes){
			myBoxes.add(new Box(box.getSticks(), box.getSize()));
		}
		for(Stick redundantStick: redundantSticks){
			for(int i = 0; i<mySticks.size(); i++){
				if(redundantStick.getId()== mySticks.get(i).getId()){
					mySticks.remove(i);
					i--;
					break;
				}
			}
		}

		Collections.sort(mySticks, new Comparator<Stick>() {
    	    @Override
    	    public int compare(Stick o1, Stick o2) {
    	        if(o1.getSize() > o2.getSize()){
    	        	return -1;
    	        	}
    	        else if(o1.getSize() == o2.getSize()){
    	        	return 0;
    	        	}
    	        return 1;
    	        	
    	        
    	    }
    	});
		boolean putted = false;
		for(Stick stick: mySticks){
			int size = 0;
			putted = false;
			for(Box box: myBoxes){
				size = box.getSize();
				if(box.putTheStick(new Stick(stick.getId(), stick.getSize()))){
					putted = true;
					break;
				}
			}
			if(!putted){
				ArrayList<Stick> tempStick = new ArrayList<>();
				tempStick.add(new Stick(stick.getId(), stick.getSize()));
				myBoxes.add(new Box(tempStick, size));
			}
		}
		this.boxes = new ArrayList<>();
		for(Box myBox : myBoxes){
			this.boxes.add(new Box(myBox.getSticks(), myBox.getSize()));
		}
		this.fitness = func.valueAt(this);

	}

	/**
	 * Public method which shuffles content of container
	 * @param sigma number of shuffles
	 */
	public void randomize(int sigma){
		

		for(int i = 0; i<sigma; i++){
			Box temp = this.getBox(rand.nextInt(boxes.size()));
			Stick stick = temp.getStick();
			boolean putted = false;


			for(int j = 0; j<this.boxes.size(); j++){
				if(this.boxes.get(j).putTheStick(stick)){
					putted = true;
					break;
				}
			}
			if(!putted){
				ArrayList<Stick> temp1 = new ArrayList<>();
				temp1.add(stick);
				this.boxes.add(new Box(temp1, 20));
			}
			if(temp.getSticks().size()!=0)
				this.boxes.add(temp);
		}
		this.fitness = func.valueAt(this);

	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for(Box box: boxes){
			sb.append(box.toString()).append("\n");
		}
		return sb.toString();
	}
}
