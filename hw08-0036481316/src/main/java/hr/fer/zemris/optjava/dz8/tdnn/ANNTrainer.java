package hr.fer.zemris.optjava.dz8.tdnn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.optjava.dz8.df.DifferentialEvolution;
import hr.fer.zemris.optjava.dz8.df.ExponentialCrossover;
import hr.fer.zemris.optjava.dz8.df.Mutation;
import hr.fer.zemris.optjava.dz8.elman.ElmanNetwork;
import hr.fer.zemris.optjava.dz8.elman.Network;
import hr.fer.zemris.optjava.dz8.tdnn.clonalg.ClonAlg;
import hr.fer.zemris.optjava.dz8.tdnn.pso.ErrorFunction;
import hr.fer.zemris.optjava.dz8.tdnn.pso.FFANN;
import hr.fer.zemris.optjava.dz8.tdnn.pso.GlobalNeighborhood;
import hr.fer.zemris.optjava.dz8.tdnn.pso.IData;
import hr.fer.zemris.optjava.dz8.tdnn.pso.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.tdnn.pso.ITransferFunction;
import hr.fer.zemris.optjava.dz8.tdnn.pso.LocalNeighborhood;
import hr.fer.zemris.optjava.dz8.tdnn.pso.PSO;
import hr.fer.zemris.optjava.dz8.tdnn.pso.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.tdnn.pso.SerialData;
import hr.fer.zemris.optjava.dz8.tdnn.pso.TangensHyperbolicus;


/**
 * Class represent demonstrative example of using Elman network and TDNN 
 * 
 * 
 * Dear user,
 *			first argument is path to collection of data
 *	    	second argument is desire algorithm pso-a , pso-b-d where d is size of neighbourhood or clonalg
 *	        third argument is size of population
 *		    fourth is maximal accepted error
 *		    fifth is maximal number of iteartion
 *			sixth is number of data
 *			seventh is desire network and architecture(example tdnn-arh4x10x8 or elman-arh4x10x8)
 * @author Branko
 *
 */
public class ANNTrainer {

	public static void main(String[] args) {
		if (args.length != 7) {
			errorMsg();
		}
		if (!Files.exists(Paths.get(args[0]))) {
			errorMsg();
		}

		Path path = Paths.get(args[0]);
		double merror = 0;
		int maxIterat = 0;
		int sizeOfPopulation = 0;
		int l = 0;
		int numberOfData=0;
		
		String[] nets = args[6].split("-arh");
		String[] designNets = nets[1].split("x");
		int[] design = new int[designNets.length];
		ITransferFunction[] tfs = new ITransferFunction[designNets.length-1];
		
		try {

			merror = Double.parseDouble(args[3]);
			sizeOfPopulation = Integer.parseInt(args[2]);
			maxIterat = Integer.parseInt(args[4]);
			l = Integer.parseInt(designNets[0]);
			numberOfData = Integer.parseInt(args[5]);

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			errorMsg();
		}

		IReadOnlyDataset dataset = loadData(path,l, numberOfData);
		System.out.println(dataset.toString());
		
		
		
		for(int k = 0; k< designNets.length; k++){
			try{
				if(k>0){
					tfs[k-1] = new TangensHyperbolicus();
				}
				design[k] = Integer.parseInt(designNets[k]); 
			}catch(NumberFormatException ex){
				errorMsg();
			}
		}
		
		Network ffann = null;
		if(nets[0].startsWith("elman")){
			ffann = new ElmanNetwork(design,
					tfs, dataset);
		}else if(nets[0].startsWith("tdnn")){
			ffann = new FFANN(design,
					tfs, dataset);
		}
		
		
		ErrorFunction errFunction = new ErrorFunction(convert(dataset.getOutputs(), dataset.numberOfSampleOutputs()), convert(dataset.getInputs(), dataset.numberOfSampleInputs()), ffann);
		double[] vMax = new double[ffann.getWeightsCount()];
		double[] vMin = new double[ffann.getWeightsCount()];
		double[] xMax = new double[ffann.getWeightsCount()];
		double[] xMin = new double[ffann.getWeightsCount()];
		for(int k = 0; k< ffann.getWeightsCount(); k++){
			vMax[k] = 1;
			vMin[k] = -1;
			xMax[k] = 10;
			xMin[k] = -10;
		}
		double[] weights = new double[1];
		
		if (args[1].startsWith("pso-a")) {
			GlobalNeighborhood neigh = new GlobalNeighborhood(sizeOfPopulation, ffann.getWeightsCount(), true);
			PSO pso = new PSO(maxIterat, sizeOfPopulation, ffann.getWeightsCount(), vMax, vMin, xMax, xMin,  neigh,errFunction, merror);
			weights = pso.run();
		} else if (args[1].startsWith("pso-b")) {
			
			String[] parts = args[1].split("\\-");
			int numOfNeigh =0;
			try{
				numOfNeigh = Integer.parseInt(parts[2].trim());
			}catch(NumberFormatException ex){
				ex.printStackTrace();
				errorMsg();
			}
			
			LocalNeighborhood neigh = new LocalNeighborhood(sizeOfPopulation, ffann.getWeightsCount(), true, numOfNeigh);
			PSO pso = new PSO(maxIterat, sizeOfPopulation, ffann.getWeightsCount(), vMax, vMin, xMax, xMin,  neigh,errFunction,merror);
			weights =pso.run();
		} else if (args[1].startsWith("clonalg")) {
			
			ClonAlg clonalg = new ClonAlg(1.3, sizeOfPopulation, (int)(sizeOfPopulation*0.1), ffann.getWeightsCount(), errFunction, maxIterat,merror);
			weights =clonalg.run();
		}else if(args[1].startsWith("df")){
			DifferentialEvolution df = new DifferentialEvolution(maxIterat, new Mutation(xMin, xMax),errFunction, new ExponentialCrossover(), sizeOfPopulation, xMin, xMax, ffann.getWeightsCount(), merror);
			weights =df.run();
		}else {
			errorMsg();
		}
		ArrayList<Double> lastData =new ArrayList<>(dataset.getInputSample(dataset.numberOfSampleInputs()-1));
		
		System.out.println("New sequence is");
		
		
		for(int i = 0; i< 10 ; i++){
						
			double[] output = ffann.calcOutputs(lastData, weights);
			System.out.println(Arrays.toString(output));
			
			for(int k = 0; k < output.length; k++){
				lastData.remove(0);
				lastData.add(output[k]);
			}
		}
	}


	/**
	 * Private static function which converts Double[][] to double[][] array
	 * @param dataset desire Double[][]
	 * @param width number of columns of dataset
	 * @return double[][] array
	 */
	private static double[][] convert(List<List<Double>> dataset, int width) {
		double[][] datasetArray = new double[dataset.size()][width];
		for(int i = 0, size = dataset.size(); i<size; i++){
			for(int j = 0; j< width; j++){
				datasetArray[i][j] = (double) dataset.get(i).get(j);
			}
		}
		return datasetArray;
	}


	/**
	 * Private static method for extracting data from file
	 * @param path path to data
	 * @return instance of {@linkplain IReadOnlyDataset}
	 */
	private static IReadOnlyDataset loadData(Path path, int lengthOfSequnce, int numberOfData) {
		List<IData<Double>> listOfData = new ArrayList<>();
		int index = 0;
		int max  = 0;
		int min = 0 ;
		ArrayList<Integer> dataNotScaled = new ArrayList<>();
		
		try (Stream<String> lines = Files.lines(path)) {
			Iterator<String> iter = lines.iterator();
			while (iter.hasNext()) {
				String line = iter.next();

				
				
				try {
					int int1 = Integer.parseInt(line.trim());
					dataNotScaled.add(int1);					
					
					if(index == 0){
						max =min = int1;
					}
					if(int1 > max){
						max = int1;
					}
					if(int1 < min){
						min = int1;
					}
					
					index++;
					if(index == numberOfData){
						break;
					}

				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					System.exit(0);
				}
				
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		double[] scaledData = new double[dataNotScaled.size()];
		
		for(int i = 0, size = dataNotScaled.size(); i<size; i++){
			scaledData[i] = (double)(2 *( ( dataNotScaled.get(i) - (double)min ) / (double)(max - min) ) - 1);
			System.out.println(dataNotScaled.get(i) +"  "+scaledData[i]);
		}
		
		ArrayList<Double> feature = new ArrayList<>();
		for(int i = 0, size = 600; i<size; i++){
			

			if(i >=lengthOfSequnce){
				SerialData data = new SerialData(lengthOfSequnce, feature, scaledData[i]);
				listOfData.add(data);

				ArrayList<Double> feature1 = new ArrayList<>();
				for(int k = 1; k <lengthOfSequnce; k++){
					feature1.add(feature.get(k));
				}
				feature1.add(scaledData[i]);
				feature = new ArrayList<>(feature1);
				
			}else{
				feature.add(scaledData[i]);
			}
		}
		
		
		ReadOnlyDataset dataset = new ReadOnlyDataset(listOfData);
		return dataset;
	}

	/**
	 * Private static method which display user how to use program.
	 */
	private static void errorMsg() {
		System.out.println("Dear user,");
		System.out.println("	first argument is path to collection of data");
		System.out.println("	second argument is desire algorithm pso-a , pso-b-d where d is size of neighbourhood or clonalg");
		System.out.println("	third argument is size of population");
		System.out.println("	fourth is maximal accepted error");
		System.out.println("	fifth is maximal number of iteartion");
		System.out.println("	sixth is number of data");
		System.out.println("	seventh is desire network and architecture(example tdnn-arh4x10x8 or elman-arh4x10x8)");
		System.exit(0);

	}
}
