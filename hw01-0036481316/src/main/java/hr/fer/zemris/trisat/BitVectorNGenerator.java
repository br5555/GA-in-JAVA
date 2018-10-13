package hr.fer.zemris.trisat;

import java.util.Iterator;

/**
 * Class computes neighborhood from input binary vector.
 * 
 * @author branko
 *
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	/**
	 * Private {@linkplain BitVector} stores center of neighborhood
	 */
	private BitVector assignment;
	
	/**
	 * Public constructor store desire center of neighborhood
	 * 
	 * @param assignment desire center of neighborhood
	 */
	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
	}


	@Override
	public Iterator<MutableBitVector> iterator() {
		return new Neighborhood();
	}

	/**
	 * Public method creates neighborhood from root binary vector {@link #assignment}
	 * 
	 * @return {@linkplain MutableBitVector} array of neighbors 
	 */
	public MutableBitVector[] createNeighborhood() {
		MutableBitVector[] neighborhood = new MutableBitVector[assignment.getSize()];
		for(int i =0, size = assignment.getSize(); i< size; i++){
			neighborhood[i] = assignment.copy();
			neighborhood[i].set(i, assignment.get(i)^true);
		}
		return neighborhood;
	}
	
	/**
	 * Private class implements {@linkplain Iterator}.
	 * 
	 * @author branko
	 *
	 */
	private class Neighborhood implements Iterator<MutableBitVector>{
		/**
		 * Private counter which remembers last seen index
		 */
		private int count = 0;
		@Override
		public boolean hasNext() {
			if(count < assignment.getSize())
				return true;
			return false;
		}

		@Override
		public MutableBitVector next() {
			if(hasNext()){
				MutableBitVector nextVec = assignment.copy();
				nextVec.set(count, assignment.get(count)^true);
				count++;
			}
			return null;
			
		}
		
		@Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
	}
}