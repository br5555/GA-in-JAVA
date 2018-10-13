package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * Class repreents one box of {@linkplain Stick}
 * @author Branko
 *
 */
public class Box {

	/**
	 * Height of the box
	 */
	private int size;
	/**
	 * Private list of {@linkplain Stick}
	 */
	private ArrayList<Stick> sticks;
	/**
	 * Private reference of {@linkplain Random} 
	 */
	private Random rand;
	
	/**
	 * Public constructor which accepts desire settings.
	 * 
	 * @param sticks list of {@linkplain Stick}
	 * @param size height of the box
	 */
	public Box(ArrayList<Stick>  sticks,int size) {
		this.size = size;
		this.sticks = new ArrayList<Stick>();
		for(int i = 0; i< sticks.size(); i++){
			Stick temp = new Stick(sticks.get(i).getId(), sticks.get(i).getSize());
			this.sticks.add(temp);
		}
		rand = new Random();
	}
	
	/**
	 * Public getter which returns {@linkplain Box#size}
	 * @return  {@linkplain Box#size}
	 */
	public int getSize() {
		return size;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0,size =sticks.size(); i<size; i++){
			sb.append(sticks.get(i).getSize());
			if(i<size-1){
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Public method which return height of the filament  
	 * @return  height of the filament
	 */
	public int boxFilling(){
		int sum = 0;
		for(int i=0; i< sticks.size(); i++){
			sum+=sticks.get(i).getSize();
		}
		return sum;
	}
	
	/**
	 * Public method return number of sticks in box
	 * @return number of sticks in box
	 */
	public int getNumberOfSticks(){
		return this.sticks.size();
	}
	
	/**
	 * Public method return random stick from the box
	 * @return {@linkplain Stick}
	 */
	public Stick getStick(){
		if(getNumberOfSticks()==0){
			return null;
		}
		int index = rand.nextInt(getNumberOfSticks());
		Stick temp = sticks.get(index);
		sticks.remove(index);
		return temp;
	}
	
	/**
	 * Public method returns all sticks in the box as a list
	 * @return all sticks in the box as a list
	 */
	public ArrayList<Stick> getSticks() {
		return new ArrayList<Stick>(sticks);
	}
	
	/**
	 * Public method which checks if inputed stick is in the box 
	 * @param otherStick inputed stick
	 * @return true if it is in the box  otherwise false.
	 */
	public boolean containsStick(Stick otherStick){
		for(Stick stick: this.getSticks()){
			if(stick.getId()==otherStick.getId()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Public method which tries to put stick in box.
	 * 
	 * @param stick desire {@linkplain Stick}
	 * @return true if it fits in the box otherwise false.
	 */
	public boolean putTheStick(Stick stick){
		if(stick == null){
			System.exit(0);
		}
		if(stick.getSize()+boxFilling()>size){
			return false;
		}
		sticks.add(stick);
		return true;
	}
	
	/**
	 * Method which return box quality as a percentage of filament of the box
	 * 
	 * @return box quality as a percentage of filament of the box
 	 */
	public double boxQuality(){
		return boxFilling()/size;
	}
}
