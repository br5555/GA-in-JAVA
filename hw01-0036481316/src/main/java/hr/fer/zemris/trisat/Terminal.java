package hr.fer.zemris.trisat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
/**
 * Class for communication with user.
 * 
 * @author branko
 *
 */
public class Terminal {

	/**
	 * Main method from which program starts to execute
	 * 
	 * @param args inputs from command line
	 */
	public static void main(String[] args) {

		if (args.length == 2) {

			Path path = Paths.get(args[1]);
			
			int algh = -1;
			try {
				algh = Integer.parseInt(args[0]);
			} catch (NumberFormatException ex) {
				System.out.println("Dear user, I need ordinal number of Algorithm");
				System.exit(0);
			}

			if (Files.exists(path)) {

				extractBooleanFunc(path, algh);
			}
			System.out.println("Dear user, file does not exists");
			System.exit(0);
		}

		System.out.println("Dear user, enter ordinal number of desire algorithm and the path to desire document");

	}

	/**
	 * Public static method extract from file need information
	 * 
	 * @param path {@linkplain Path} to desire file
	 * @param algh ordinal number of algorithm used in search
	 */
	private static void extractBooleanFunc(Path path, int algh) {

		try (Stream<String> lines = Files.lines(path, Charset.defaultCharset())) {
			Iterator<String> iterator = lines.iterator();
			Clause[] clauses = new Clause[0];
			int sizeClauses = 0;
			boolean init = false;
			int variables = 0;
			
			while (iterator.hasNext()) {
				String line = iterator.next();
				
				if (line.startsWith("%")) {
					if(clauses.length >0 ){
						callAlgh(clauses, algh, variables);
						System.exit(0);
					}else{
						System.out.println("Dear user, there is no clauses in this document");
						System.exit(0);
					}
					
				} else if (line.startsWith("c")) {
					continue;
				} else if (line.startsWith("p")) {
					if(!init){
						init = true;
					}else{
						throw new IllegalArgumentException("Dear user, multiple initialize of "
								+ "clauses structure is not allowed");
					}
					
					String[] parts = line.split("\\s+");

					if (parts.length == 4) {
						try {
							variables = Integer.parseInt(parts[2]);
							int clausesNum = Integer.parseInt(parts[3]);
		
							if (variables <= 0 || clausesNum <= 0) {
								throw new IllegalArgumentException("Dear user, number of variables "
										+ "and number of clauses cannot be zero or negative");
							}
							
							clauses = new Clause[clausesNum];
						} catch (NumberFormatException ex) {

						}
					}
				}else{
					line = line.trim();
					String[] parts = line.split("\\s+");
					
					if(parts.length ==0 ){
						throw new IllegalArgumentException("Dear user, number of "
								+ "variables is too small");
					}
					
					int numOfVariablesCheck = 0;
					if(variables <= 0){
						throw new IllegalArgumentException("Dear user, number of "
								+ "variables is positive number");
					}
					int[] indexes = new int[variables];
					
					if(Integer.parseInt(parts[parts.length-1]) != 0){
						throw new IllegalArgumentException("Dear user, clause "
								+ "must be terminated with zero");
					
				}
					
					for(int i = 0, size = parts.length; i< size; i++){
						try{
							
							if(Integer.parseInt(parts[i])!=0){
								indexes[(int)Math.abs(Integer.parseInt(parts[i]))-1] = Integer.parseInt(parts[i]);
								continue;
							}else{
								
								Clause clause = new Clause(indexes);
								clauses[sizeClauses++] = clause;
								indexes = new int[variables];
							}
							
							
						}catch(NumberFormatException ex){
							ex.printStackTrace();
						}
					}
					
				}
			}

			if(clauses.length >0 ){
				callAlgh(clauses, algh, variables);
			}else{
				System.out.println("Dear user, there is no clauses in this document");
				System.exit(0);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Private static method calls desire algorithm
	 * 
	 * @param clauses clauses from input file
	 * @param algh desire ordinal number of algorithm
	 * @param numOfVar number of variables used in caluses
	 */
	private static void callAlgh(Clause[] clauses, int algh, int numOfVar) {
		
		switch(algh){
		case 1:
			Algorithm1 algh1= new Algorithm1(new SATFormula(numOfVar, clauses));
			algh1.executeAlgh();
			break;
		case 2:
			Algorithm2 algh2= new Algorithm2(new SATFormula(numOfVar, clauses));
			algh2.executeAlgh();
			break;
		case 3:
			Algorithm3 algh3= new Algorithm3(new SATFormula(numOfVar, clauses));
			algh3.executeAlgh(10000, 10000);
			break;
		case 4:
			Algorithm4 algh4= new Algorithm4(new SATFormula(numOfVar, clauses));
			algh4.executeAlgh(100000, 10000);
			break;
		case 5:
			Algorithm5 algh5= new Algorithm5(new SATFormula(numOfVar, clauses));
			algh5.executeAlgh(100000);
			break;
		case 6:
			Algorithm6 algh6= new Algorithm6(new SATFormula(numOfVar, clauses));
			algh6.executeAlgh(10000000);
			break;
		default:
			throw new IllegalArgumentException("Dear user, ordinal number of "
					+ "algorithm does not exists");
		}
		
	}
}
