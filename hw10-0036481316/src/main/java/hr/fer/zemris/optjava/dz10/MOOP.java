package hr.fer.zemris.optjava.dz10;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class tests NSGA algorithm for specific problem.
 * Class uses argumnets form command line:
 * 						first argument is ordinal number of problem(1 for first and 2 for second
 * 						second argument is size of population
 * 						third is maximal number of iterations
 * @author Branko
 *
 */
public class MOOP {

	/**
	 * Main method from which program starts to execute. 
	 * Method uses argument from command line.
	 * @param args arguments from command line
	 */
	public static void main(String[] args) {
		if(args.length != 3){
			errorMsg();
		}
		int fja = 0;
		int sizeOfPopulation = 0;
		int maxiter = 0;
		
		try{
			fja = Integer.parseInt(args[0]);
			sizeOfPopulation = Integer.parseInt(args[1]);
			maxiter = Integer.parseInt(args[2]);
		}catch (NumberFormatException ex) {
			errorMsg();
		}
		
		if(maxiter<= 0 || sizeOfPopulation <= 0|| (fja!=1 && fja!=2) ){
			errorMsg();
		}
		
		double[] maxs=null;
		double[] mins=null;
		IFunction[] functions=null;
		
		if(fja ==1){
			maxs = new double[4];
			mins = new double[4];
			functions = new IFunction[4];
			for(int i = 0; i<4; i++){
				functions[i] = new Function3(i);
				maxs[i] = 5;
				mins[i] = -5;
			}
			
		}else if(fja ==2){
			maxs = new double[2];
			mins = new double[2];
			functions = new IFunction[2];
			functions[0] = new Function1();
			functions[1] = new Function2();
			mins[0]=0.1;
			mins[1]=0;
			maxs[0]=1;
			maxs[1]=5;
		}
		MOOPFunction moop = new MOOPFunction(functions);
		NSGA2 nsga = new NSGA2(maxiter, maxs, mins, sizeOfPopulation, moop);
		ArrayList<ArrayList<SolutionVector>> pareto = nsga.run();
		
		
		
		List<String> lines = new ArrayList<>();
		List<String> lines1 = new ArrayList<>();
		int index = 1;
		for(ArrayList<SolutionVector> front: pareto){
			for(SolutionVector vec : front){
				lines.add(vec.valuesToString()+"	" + index);
				lines1.add(vec.solutionToString()+"	"  + index);
			}
			index++;
		}

		Path file = Paths.get("izlaz-dec.txt");
		Path file1 = Paths.get("izlaz-obj.txt");

		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			Files.write(file1, lines1, Charset.forName("UTF-8"));


		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method which display user how to use program.
	 */
	private static void errorMsg() {
		System.out.println("Dear user,");
		System.out.println("	first argument is ordinal number of problem(1 for first and 2 for second)");
		System.out.println("	second argument is size of population");
		System.out.println("	third is maximal number of iterations");
		System.exit(0);
		
	}
	
}
