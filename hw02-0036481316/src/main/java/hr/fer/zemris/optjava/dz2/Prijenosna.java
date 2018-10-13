package hr.fer.zemris.optjava.dz2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Class implements {@linkplain IHFunction} for working
 * with function y (x1, x2, x3, x4, x5)=a⋅x1+b⋅x1^3*x2+c*e^(d⋅x3)*(1+cos(e*x4))+ f*x4*x5^2
 * and finding coefficients a,b,c,d,e and f
 * @author Branko
 *
 */
public class Prijenosna implements IHFunction{

	/**
	 * Main method from which program starts to execute.
	 * 
	 * @param args input form terminal
	 */
	public static void main(String[] args) {
		
		if(args.length!=3 || (!args[0].trim().equals("grad") && !args[0].trim().equals("newton"))){
			System.out.println(args[2]);

			errorMsg();
		}
		
		int maxIter = 0;
		try{
			maxIter = Integer.parseInt(args[1]);
		}catch(NumberFormatException ex){
			System.out.println("ex");

			errorMsg();
		}
		
		Path path= Paths.get(args[2]);
		if(!Files.exists(path)){
			System.out.println("File");
			errorMsg();
		}
		Prijenosna prij = new Prijenosna();
		try(Stream<String> lines = Files.lines(path)){
			Iterator<String> iter = lines.iterator();
			
			
			while(iter.hasNext()){
				String line = iter.next();
				if(line.startsWith("[")){
					line = line.replaceAll("\\[", "").replaceAll("\\]", "");
					line = line.replaceAll("\\,", " ");
					line = 	line.trim();
					String[] values = line.split("\\s+");
					
					if(values.length != 6){
						System.out.println("Dear user, file is wrongly structured");
						System.exit(0);
					}
					double[] grad = new double[6];
					for(int i = 0; i < 6 ; i++){
						grad[i]= Double.parseDouble(values[i]);
					}
					prij.setValues(grad);
					
				}
				
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		double[] point1 = new double[6];
		double[] point2 = new double[6];
		Random r = new Random();
		for(int k =0; k<6; k++){
			point1[k] = -100 + (100 +100) * r.nextDouble();
			point2[k] = -100 + (100 +100) * r.nextDouble();
		}
		if(prij.functionValue(point1)>=prij.functionValue(point2)){
			double[] temp = Arrays.copyOf(point1, point1.length);
			point1 = Arrays.copyOf(point2, point2.length);
			point2 = Arrays.copyOf(temp, temp.length);
		}
		if(args[0].trim().equals("grad")){
			NumOptAlgorithms.gradientDownhill(prij, maxIter,point1, point2, false);

		}else{
			NumOptAlgorithms.newtonMethod(prij, maxIter,point1, point2, false);

		}
	}
	
	/**
	 * Public static method display user how to call this class from terminal
	 */
	public static void errorMsg(){
		System.out.println("Dear user, first argument must be one of the methods"
				+ "(grad, newton), second argument is max iterations,"
				+ "third is path to file with assigment");
		System.exit(0);
	}
	
	
	/**
	 * Private 2D double array which stores values of x1, x2, x3, x4, x5.
	 */
	private double[][] values;
	private int index;
	
	/**
	 * Public constructor which creates instance of {@linkplain Prijenosna}.
	 */
	public Prijenosna() {
		values = new double[20][6];
		index = 0;
	}
	

	@Override
	public int numberOfVariables() {
		return 5;
	}

	@Override
	public double functionValue(double[] point) {
		double sum = 0;
		for(int i = 0; i<20; i++){
			sum+=Math.pow(point[0]*values[i][0] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1), 2);
		}
		return sum;
	}

	@Override
	public double[] gradientOfFunction(double[] point) {
		double[] grad = new double[6];
		for(int i = 0; i< 20; i++){
			grad[0]+=2*values[i][0]*(point[0]*values[i][0] -values[i][5]  + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			grad[1]+=2*Math.pow(values[i][0],3)*values[i][1]*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			grad[2]+=2*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			grad[3]+=2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			grad[4]+=-2*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1))*(point[0]*values[i][0] -values[i][5]+ point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			grad[5]+=2*values[i][3]*Math.pow(values[i][4],2)*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));

		}
		return grad;
	}

	@Override
	public RealMatrix hesseMatrix(double[] point) {
	
		
		double[][] hesse =  new double[6][6];
		for(int i =0; i< 20; i++){
			hesse[0][0]+=2*Math.pow(values[i][0],2);
			hesse[1][0]+=2*Math.pow(values[i][0],4)*values[i][1];
			hesse[2][0]+=2*Math.pow(point[4],(point[3]*values[i][2]))*values[i][0]*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[3][0]+=2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][0]*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[4][0]+=-2*Math.pow(values[i][0],(point[2]*Math.pow(point[4],(point[3]*values[i][2])))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[5][0]+=2*values[i][0]*values[i][3]*Math.pow(values[i][4],2);
			hesse[0][1]+=2*Math.pow(values[i][0],4)*values[i][1];
			hesse[1][1]+=2*Math.pow(values[i][0],6)*Math.pow(values[i][1],2);
			hesse[2][1]+=2*Math.pow(point[4],(point[3]*values[i][2]))*Math.pow(values[i][0],3)*values[i][1]*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[3][1]+=2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*Math.pow(values[i][0],3)*values[i][1]*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[4][1]+=-2*Math.pow(values[i][0],3)*values[i][1]*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[5][1]+=2*Math.pow(values[i][0],3)*values[i][1]*values[i][3]*Math.pow(values[i][4],2);
			hesse[0][2]+=2*Math.pow(point[4],(point[3]*values[i][2]))*values[i][0]*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[1][2]+=2*Math.pow(point[4],(point[3]*values[i][2]))*Math.pow(values[i][0],3)*values[i][1]*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[2][2]+=2*Math.pow(point[4],(2*point[3]*values[i][2]))*Math.pow((Math.cos(point[4]*values[i][3]) + 1),2);
			hesse[3][2]+=2*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)) + 2*point[2]*Math.pow(point[4],(2*point[3]*values[i][2]))*values[i][2]*Math.log(point[4])*Math.pow((Math.cos(point[4]*values[i][3]) + 1),2);
			hesse[4][2]+=- 2*(Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1))*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)) - 2*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[5][2]+=2*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.pow(values[i][4],2)*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[0][3]+=2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][0]*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[1][3]+=2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*Math.pow(values[i][0],3)*values[i][1]*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[2][3]+=2*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0] -values[i][5]+ point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)) + 2*point[2]*Math.pow(point[4],(2*point[3]*values[i][2]))*values[i][2]*Math.log(point[4])*Math.pow((Math.cos(point[4]*values[i][3]) + 1),2);
			hesse[3][3]+=2*Math.pow(point[2],2)*Math.pow(point[4],(2*point[3]*values[i][2]))*Math.pow(values[i][2],2)*Math.pow(Math.log(point[4]),2)*Math.pow((Math.cos(point[4]*values[i][3]) + 1),2) + 2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*Math.pow(values[i][2],2)*Math.pow(Math.log(point[4]),2)*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0] -values[i][5]+ point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[4][3]+=2*(point[2]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1) - point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*values[i][3]*Math.sin(point[4]*values[i][3])*Math.log(point[4]) + point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*Math.pow(values[i][2],2)*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1))*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)) - 2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1)*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[5][3]+=2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*values[i][3]*Math.pow(values[i][4],2)*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[0][4]+=-2*values[i][0]*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[1][4]+=-2*Math.pow(values[i][0],3)*values[i][1]*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[2][4]+=2*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)) - 2*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3])*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)) - 2*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[3][4]+=(2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)))/point[4] - 2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1)*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1)) - 2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*values[i][3]*Math.sin(point[4]*values[i][3])*Math.log(point[4])*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1)) + 2*point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*Math.pow(values[i][2],2)*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1)*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[4][4]+=2*Math.pow((point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1)),2) - 2*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*Math.pow(values[i][3],2)*Math.cos(point[4]*values[i][3]) + 2*point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 2))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1)*(point[3]*values[i][2] - 1))*(point[0]*values[i][0]-values[i][5] + point[1]*Math.pow(values[i][0],3)*values[i][1] + point[5]*values[i][3]*Math.pow(values[i][4],2) + point[2]*Math.pow(point[4],(point[3]*values[i][2]))*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[5][4]+=-2*values[i][3]*Math.pow(values[i][4],2)*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[0][5]+=2*values[i][0]*values[i][3]*Math.pow(values[i][4],2);
			hesse[1][5]+=2*Math.pow(values[i][0],3)*values[i][1]*values[i][3]*Math.pow(values[i][4],2);
			hesse[2][5]+=2*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.pow(values[i][4],2)*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[3][5]+=2*point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][2]*values[i][3]*Math.pow(values[i][4],2)*Math.log(point[4])*(Math.cos(point[4]*values[i][3]) + 1);
			hesse[4][5]+=-2*values[i][3]*Math.pow(values[i][4],2)*(point[2]*Math.pow(point[4],(point[3]*values[i][2]))*values[i][3]*Math.sin(point[4]*values[i][3]) - point[2]*point[3]*Math.pow(point[4],(point[3]*values[i][2] - 1))*values[i][2]*(Math.cos(point[4]*values[i][3]) + 1));
			hesse[5][5]+=2*Math.pow(values[i][3],2)*Math.pow(values[i][4],4);


			
		}
		return MatrixUtils.createRealMatrix(hesse);
	
	}
	
	/**
	 * Public setter
	 * @param values known data of function
	 */
	public void setValues(double[] values) {
		this.values[index++] = values;
	}

}
