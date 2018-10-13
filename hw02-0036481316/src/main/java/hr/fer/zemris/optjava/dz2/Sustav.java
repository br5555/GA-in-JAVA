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
 * with function f(x1,x2,x3,x4,x5) =(n1x1 + n2x2 + n3x3 + n4x4 + n5x5)^2
 * +(n6x1 + n7x2 + n8x3 + n9x4 + n10x5)^2+ etc..
 * @author Branko
 *
 */
public class Sustav implements IHFunction{

	public static void main(String[] args) {
		if(args.length!=3 || (!args[0].equals("grad") && !args[0].equals("newton"))){
			errorMsg();
		}
		
		int maxIter = 0;
		try{
			maxIter = Integer.parseInt(args[1]);
		}catch(NumberFormatException ex){
			errorMsg();
		}
		
		Path path= Paths.get(args[2]);
		if(!Files.exists(path)){
			errorMsg();
		}
		Sustav sus = new Sustav();

		try(Stream<String> lines = Files.lines(path)){
			Iterator<String> iter = lines.iterator();
			
			while(iter.hasNext()){
				String line = iter.next();
				if(line.startsWith("[")){
					line = line.replaceAll("\\[", "").replaceAll("\\]", "");
					line = line.replaceAll("\\,", " ");
					line = 	line.trim();
					String[] values = line.split("\\s+");
					
					if(values.length != 11){
						System.out.println("Dear user, file is wrongly structured");
						System.exit(0);
					}
					double[] grad = new double[11];
					for(int i = 0; i < 11 ; i++){
						grad[i]= Double.parseDouble(values[i]);
					}
					sus.setParts(grad);
					
				}
				
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		double[] point1 = new double[10];
		double[] point2 = new double[10];
		Random r = new Random();
		for(int k =0; k<10; k++){
			point1[k] = -100 + (100 +100) * r.nextDouble();
			point2[k] = -100 + (100 +100) * r.nextDouble();
		}
		if(sus.functionValue(point1)>=sus.functionValue(point2)){
			double[] temp = Arrays.copyOf(point1, point1.length);
			point1 = Arrays.copyOf(point2, point2.length);
			point2 = Arrays.copyOf(temp, temp.length);
		}
		if(args[0].trim().equals("grad")){
			NumOptAlgorithms.gradientDownhill(sus, maxIter,point1, point2, false);

		}else{
			NumOptAlgorithms.newtonMethod(sus, maxIter,point1, point2, false);

		}
	}
	public static void errorMsg(){
		System.out.println("Dear user, first argument must be one of the methods"
				+ "(grad, newton), second argument is max iterations,"
				+ "third is path to file with assigment");
		System.exit(0);
	}
	
	int index, index1;
	double[][] parts;
	
	public Sustav() {
		parts = new double[10][11];
		index=0;
		index1=0;
	}
	@Override
	public int numberOfVariables() {
		
		return 10;
	}

	@Override
	public double functionValue(double[] point) {

		double sum = 0;
		for(int i=0; i<parts.length; i++){
			double part = 0;
			for(int j=0; j<parts[0].length-1; j++){
				part += parts[i][j]*point[i];
			}
			part -= parts[i][parts[0].length-1];
			sum+=Math.pow(part, 2);
		}
		return sum;
	}

	@Override
	public double[] gradientOfFunction(double[] point) {
		double[] grad = new double[numberOfVariables()];
		double[] mParts = new double[numberOfVariables()];
		
		
			for(int i=0; i<parts.length; i++){
				double part = 0;
				for(int j=0; j<parts[0].length-1; j++){
					part += parts[i][j];
				}
				part -= parts[i][parts[0].length-1];
				mParts[i]=2*part;
			}
			
			for(int i=0; i<parts.length; i++){
				double part = 0;
				for(int j=0; j<parts[0].length-1; j++){
					part += mParts[j]*parts[j][i];
				}
				
				grad[i]=part;
			}
			return grad;
		
	}
	public void setParts(double[] parts) {
		this.parts[index++] = parts;
	}
	

	@Override
	public RealMatrix hesseMatrix(double[] point) {
		double[][] hesse = new double[numberOfVariables()][numberOfVariables()];
		for(int i=0; i<parts.length; i++){
			for(int j=0; j<parts[0].length-1; j++){
				if(j>i){
					double sum = 0;
					for(int k = 0; k< parts.length; k++){
						sum+=parts[k][i]*parts[k][j];
					}
					hesse[i][j]= 2*sum;
					hesse[j][i] = hesse[i][j];
				}else if(j==i){
					double sum = 0;
					for(int k = 0; k< parts.length; k++){
						sum+=parts[k][j]*parts[k][j];
					}
					hesse[i][j]= 2*sum;
					
				}
				
			}			
			
		}
		return MatrixUtils.createRealMatrix(hesse);
	}
}
