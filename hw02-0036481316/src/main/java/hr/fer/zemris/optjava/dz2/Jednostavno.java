package hr.fer.zemris.optjava.dz2;

import java.util.Random;

import org.jzy3d.analysis.AbstractAnalysis;

/**
 * Class depending of terminal input choose which function user wants
 * 1 stands for {@linkplain Function1} and 2 stands for {@linkplain Function2}
 * and what iterative search algorithm a stands for gradient descent and b for
 * Newton's method. Acceptable inputs are shapes 1a, 1b, 2a and 2b.
 * 
 * @author Branko
 *
 */
public class Jednostavno {
	
	
	
	/**
	 * Main method from which program starts to execute.
	 * 
	 * @param args inputs form terminal
	 */
	public static void main(String[] args) {
		double[] point1 =new double[2];
		double[] point2 =new double[2];
		int maxIter = 0;
		if(args.length!=4 && args.length!=2){
			errorMsg();
		}
		try{
			maxIter = Integer.parseInt(args[1]);
		}catch(NumberFormatException ex){
			errorMsg();
		}
		if(args.length == 2){
			Random r = new Random();
			for(int k =0; k<2; k++){
				point1[k] = -5 + (5 +5) * r.nextDouble();
				point2[k] = -5 + (5 +5) * r.nextDouble();
			}
		}else{
			for(int i =2; i<4;i++){
				args[i].replaceAll("[", "");
				args[i].replaceAll(",", " ");
				args[i].trim();
				String[] values = args[i].split("\\s+");
				for(int k =0; k<2; k++){
					try{
						point1[k] = Double.parseDouble(args[i]);
						point2[k] = Double.parseDouble(args[i]);
					}catch(NumberFormatException ex){
						
						errorMsg();
					}
					
				}
			}
			
		}
		switch(args[0].trim()){
		case "1a":
			NumOptAlgorithms.gradientDownhill(new Function1(), maxIter, point1, point2,true);
			break;
		case "1b":
			NumOptAlgorithms.newtonMethod(new Function1(), maxIter, point1, point2,true);
			break;
		case "2a":
			NumOptAlgorithms.gradientDownhill(new Function2(), maxIter, point1, point2,true);
			break;
		case "2b":
			NumOptAlgorithms.newtonMethod(new Function2(), maxIter, point1, point2,true);
			break;
		default:
			errorMsg();
		}
	}
	
	/**
	 * Public static method which display message to user if terminal input is incorrect.
	 */
	public static void errorMsg(){
		System.out.println("Dear user, first argument must be one of the expression"
				+ "(1a, 1b, 1c, 1d), second argument is max iterations,"
				+ "third and fourth argumnet are arbitrary. Third is lower boundery"
				+ "Forth is upper boundery");
		System.exit(0);
	}
}
