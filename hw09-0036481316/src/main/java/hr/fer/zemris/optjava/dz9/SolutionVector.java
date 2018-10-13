package hr.fer.zemris.optjava.dz9;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class which represent solution vector
 * @author Branko
 *
 */
public class SolutionVector {

	/**
	 * list of subordinate solutions
	 */
	protected ArrayList<SolutionVector> subordinate;
	/**
	 *  number of superior solutions
	 */
	protected int numberOfSuperior;
	/**
	 * solution vector
	 */
	protected double[] solutions;
	/**
	 * input vector
	 */
	protected double[] values;
	/**
	 * solution ID
	 */
	protected int ID;
	/**
	 * divided fitness
	 */
	protected double dividedFitness;
	/**
	 * fitness
	 */
	protected double fitness;
	
	/**
	 * Public getter gets fitness
	 * @return fitness
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Public setter sets desire fitness
	 * @param fitness desire fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	
	/**
	 * Public setter sets desire divided fitness
	 * @param dividedFitness divided fitness
	 */
	public void setDividedFitness(double dividedFitness) {
		this.dividedFitness = dividedFitness;
	}
	
	/**
	 * Public getter gets divided fitness
	 * @return divided fitness
	 */
	public double getDividedFitness() {
		return dividedFitness;
	}
	
	/**
	 * Public constructor accepts desire settings
	 * @param values desire values
	 */
	public SolutionVector(double[] values) {
		this.subordinate = new ArrayList<>();
		numberOfSuperior = 0;
		this.values = Arrays.copyOf(values, values.length);
	}
	
	/**
	 * Public getter gets solutions
	 * @return  solutions
	 */
	public double[] getSolutions() {
		return solutions;
	}
	
	/**
	 * Public getter gets values
	 * @return values
	 */
	public double[] getValues() {
		return values;
	}
	
	/**
	 * Method which restarts all subordinate and superior solutions
	 */
	public void reset(){
		this.subordinate = new ArrayList<>();
		numberOfSuperior = 0;
	}
	
	/**
	 * Method which increment number of superior solutions
	 */
	public void newSuperior(){
		numberOfSuperior++;
	}
	
	/**
	 * Method which accepts new subordinate solution
	 * @param vect subordinate solution
	 */
	public void newSubordinate(SolutionVector vect){
		subordinate.add(vect);
	}
	
	/**
	 * Public getter gets number of superior solutions
	 * @return number of superior solutions
	 */
	public int getNumberOfSuperior() {
		return numberOfSuperior;
	}
	
	/**
	 * Public getter gets all subordinate solutions
	 * @return all subordinate solutions
	 */
	public ArrayList<SolutionVector> getSubordinate() {
		return subordinate;
	}
	
	/**
	 * Public method which decrement number of superior solutions
	 */
	public void removeOneOfSuperior(){
		numberOfSuperior--;
	}
	
	/**
	 * Public getter gets solution ID
	 * @return solution ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Method creates duplicate from this instance
	 * @return duplicate from this instance
	 */
	public SolutionVector duplicate(){
		SolutionVector temp = new SolutionVector(this.getValues());
		temp.subordinate= new ArrayList<>(this.subordinate);
		temp.numberOfSuperior= this.numberOfSuperior;
		temp.solutions= Arrays.copyOf(this.solutions, this.solutions.length);
		temp.values= Arrays.copyOf(this.values, this.values.length);
		temp.ID= this.ID;
		temp.dividedFitness=this.dividedFitness;
		temp.fitness= this.fitness;
		return temp;
	}
	
	/**
	 * Method creates string array of values from values array
	 * @return string array of values 
	 */
	public String valuesToString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< values.length; i++){
			sb.append(values[i]);
			if(i!=values.length-1){
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Method creates string array of solutions from values solutions
	 * @return string array of solutions
	 */
	public String solutionToString(){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i< solutions.length; i++){
			sb.append(solutions[i]);
			if(i!=solutions.length-1){
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for(int i = 0; i< solutions.length; i++){
			sb.append(solutions[i]);
			if(i!=solutions.length-1){
				sb.append(", ");
			}
		}
		sb.append(" ]");
		return sb.toString();
	}
}
