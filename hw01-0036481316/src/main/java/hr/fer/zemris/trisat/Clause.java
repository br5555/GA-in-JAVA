package hr.fer.zemris.trisat;

import java.util.Arrays;
/**
 * Class implements simple clause and containing methods for easier working
 * whit caluses.
 * 
 * @author branko
 *
 */
public class Clause {
	/**
	 * Private array stores values of variables in clauses
	 */
	private int[] indexes;
	
	/**
	 * Public constructor which stores desire values of variables
	 *  
	 * @param indexes desire set of binary vector
	 *  
	 */
	public Clause(int[] indexes) {
		if(indexes == null){
			throw new IllegalArgumentException("Dear user, null is not valid input");
		}
		this.indexes = Arrays.copyOf(indexes, indexes.length);
	}

	/**
	 * Public method returns number of variables in clauses
	 * 
	 * @return number of variables in clauses
	 */
	public int getSize() {
		return indexes.length;
	}

	/**
	 * Public method returns value of i-th variable in clauses
	 * 
	 * @param index desire i-th index
	 * @return value of i-th variable in clauses 
	 * @throws IllegalArgumentException if index is negative or out of range
	 */
	public int getLiteral(int index) {
		if(index < 0 || index >= indexes.length){
			throw new IllegalArgumentException("Dear user, requested index is out of range");

		}
		return indexes[index];
	}

	/**
	 * Method calculates if {@linkplain BitVector} satisfied clause
	 * 
	 * @param assignment desire {@linkplain BitVector}
	 * @return true if {@linkplain BitVector} satisfied otherwise false
	 */
	public boolean isSatisfied(BitVector assignment) {
		for(int i = 0, size = indexes.length; i< size ; i++){
			if(indexes[i]==0)
				continue;
			if((assignment.get(Math.abs(indexes[i])-1) && indexes[i]>0) || (!assignment.get(Math.abs(indexes[i])-1) && indexes[i]<0)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(2*indexes.length +3);
		sb.append("( ");
		for(int i = 0, size = indexes.length; i<size; i++){
			sb.append(Integer.toString(indexes[i])+" ");
		}
		sb.append(")");
		return sb.toString();
		
	}
}
