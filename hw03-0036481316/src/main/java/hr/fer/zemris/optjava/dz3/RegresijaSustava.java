package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;




public class RegresijaSustava {

	public static void main(String[] args) {
		if(args.length!=2){
			
			errorMsg();
		}
		Path path= Paths.get(args[0]);
		if(!Files.exists(path)){
			

			errorMsg();
		}

		Prijenosna sus = new Prijenosna();
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
					sus.setValues(grad);
					
				}
				
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		args[1] = args[1].trim();
		GeometricTempSchedule geoSch = new GeometricTempSchedule(1000, 0.99, 1000, 1000);
		LinearTempSchedule linSch = new LinearTempSchedule(1000, 0.001, 1000, 1000);
		LogTempSchedule logSch = new LogTempSchedule(1000, 1000, 1000);
		SlowTempSchedule slowSch = new SlowTempSchedule(1000, 0.001, 1000, 1000);
		
		if(args[1].startsWith("binary")){
			String[] parts = args[1].split("\\:");
			int size = 0;
			try{
				size = Integer.parseInt(parts[1].trim());
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}
			NaturalBinaryDecoder decoder = new NaturalBinaryDecoder(0, 10, size, sus.numberOfVariables());
			GreyBinaryDecoder decoder1 = new GreyBinaryDecoder(-10, 10, size, sus.numberOfVariables());
			int[] deltas = new int[sus.numberOfVariables()*size];
			for(int k=0; k<deltas.length; k++){
				deltas[k] = 5;
			}
			BitVectorNeighbourhood neighborhood = new BitVectorNeighbourhood(deltas);
			BitvectorSolution startWith = new BitvectorSolution(size, decoder, neighborhood, sus, null);
			startWith.randomize(new Random());
			startWith.updateFitness();
			
			SimulatedAnnealing<BitvectorSolution> algorithm = new SimulatedAnnealing<BitvectorSolution>(decoder, neighborhood, startWith, sus, true, geoSch);
			BitvectorSolution sol = algorithm.run();
			System.out.println("Final result is: " +Arrays.toString(decoder.decode(sol)));
			System.out.println("Error is: "+ sol.value);

		}else if(args[1].equals("decimal")){
			double[] deltas = new double[sus.numberOfVariables()];
			double[] lower = new double[sus.numberOfVariables()];
			double[] upper = new double[sus.numberOfVariables()];

			for(int i =0, numVar = sus.numberOfVariables(); i<numVar; i++){
				deltas[i] = 2;
				lower[i] = -10;
				upper[i] = 10;
			}
			DoubleArrayNormNeighborhood neigh1 = new DoubleArrayNormNeighborhood(deltas);
			DoubleArrayUnifNeighborhood neigh2 = new DoubleArrayUnifNeighborhood(deltas);
			PassThroughDecoder decoder = new PassThroughDecoder();
			DoubleArraySolution startWith = new DoubleArraySolution(sus.numberOfVariables(), decoder, neigh1, sus, null);
			startWith.randomize(new Random(), lower, upper);
			startWith.updateFitness();
			SimulatedAnnealing<DoubleArraySolution> algh = new SimulatedAnnealing<DoubleArraySolution>(decoder, neigh1, startWith, sus, true, geoSch);
			DoubleArraySolution sol = algh.run();
			System.out.println("Final result is: "+ Arrays.toString(sol.values));
			System.out.println("Error is: "+ sol.value);
		}else{
			errorMsg();
		}
		
	}

	private static void errorMsg() {
		System.out.println("Dear user, first argument is path to file and "
				+ "second argument is coding mode");
		System.exit(0);

		
	}
}
