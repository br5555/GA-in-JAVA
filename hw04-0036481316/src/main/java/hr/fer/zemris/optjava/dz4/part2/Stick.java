package hr.fer.zemris.optjava.dz4.part2;

/**
 * Class that implements {@linkplain Comparable}.
 * Class represnts stick.
 * @author Branko
 *
 */
public class Stick implements Comparable<Stick>{

	/**
	 * Id of the stick
	 */
	private int id;
	/**
	 * Length of the stick
	 */
	private int size;
	
	/**
	 * Public constructor which accepts desire settings
	 * @param id id of the stick
	 * @param size length of the stick
	 */
	public Stick(int id, int size) {
		this.id = id;
		this.size = size;
	}
	
	/**
	 * Public setter desire id
	 * @param id desire id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Public setter sets desire length of teh stick
	 * @param size desire length
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * Public getter returns id of the stick
	 * @return id of the stick
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Public getter returns length of the stick
	 * @return length of the stick
	 */
	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//sb.append("ID is :").append(this.getId()).append(", size is ").append(this.getSize());
		sb.append(this.getSize());
		return sb.toString();
	}
	@Override
	public int compareTo(Stick o) {
		if(this.id == o.id){
			return 0;
		}else if(this.size >= o.size){
			return 1;
		}
		return -1;
	}
	
	
}
