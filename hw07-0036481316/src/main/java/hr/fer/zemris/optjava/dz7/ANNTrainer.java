package hr.fer.zemris.optjava.dz7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.optjava.dz7.clonalg.ClonAlg;
import hr.fer.zemris.optjava.dz7.pso.ErrorFunction;
import hr.fer.zemris.optjava.dz7.pso.FFANN;
import hr.fer.zemris.optjava.dz7.pso.GlobalNeighborhood;
import hr.fer.zemris.optjava.dz7.pso.IData;
import hr.fer.zemris.optjava.dz7.pso.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.pso.ITransferFunction;
import hr.fer.zemris.optjava.dz7.pso.Iris;
import hr.fer.zemris.optjava.dz7.pso.LocalNeighborhood;
import hr.fer.zemris.optjava.dz7.pso.PSO;
import hr.fer.zemris.optjava.dz7.pso.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.pso.SigmoidTransferFunction;

/**
 * Class represent demonstrative example of using Immunological Algorithm (CLONALG)
 * and Particle Swarm Optimization.
 * 
 * Dear user,
 *			first argument is path to collection of data
 *	    	second argument is desire algorithm pso-a , pso-b-d where d is size of neighbourhood or clonalg
 *	        third argument is size of population
 *		    fourth is maximal accepted error
 *		    fifth is maximal number of iteartion
 * @author Branko
 *
 */
public class ANNTrainer {

	/**
	 * Main method from which program starts to execute. 
	 * Program uses arguments from command line
	 * @param args arguments form command line
	 */
	public static void main(String[] args) {
		if (args.length != 5) {
			errorMsg();
		}
		if (!Files.exists(Paths.get(args[0]))) {
			errorMsg();
		}

		Path path = Paths.get(args[0]);
		double maxError = 0;
		int maxIterat = 0;
		int sizeOfPopulation = 0;

		try {

			maxError = Double.parseDouble(args[3]);
			sizeOfPopulation = Integer.parseInt(args[2]);
			maxIterat = Integer.parseInt(args[4]);

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			errorMsg();
		}

		IReadOnlyDataset dataset = loadData(path);
		System.out.println(dataset.toString());
		FFANN ffann = new FFANN(new int[]{4, 5,3,3},
				new ITransferFunction[]{new SigmoidTransferFunction(),
										new SigmoidTransferFunction(),
										new SigmoidTransferFunction()
										}, dataset);
		
		double[][] ajmo = convert(dataset.getOutputs(), dataset.numberOfSampleOutputs());
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
		if (args[1].startsWith("pso-a")) {
			GlobalNeighborhood neigh = new GlobalNeighborhood(sizeOfPopulation, ffann.getWeightsCount(), true);
			PSO pso = new PSO(maxIterat, sizeOfPopulation, ffann.getWeightsCount(), vMax, vMin, xMax, xMin,  neigh,errFunction,maxError);
			pso.run();
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
			PSO pso = new PSO(maxIterat, sizeOfPopulation, ffann.getWeightsCount(), vMax, vMin, xMax, xMin,  neigh,errFunction,maxError);
			pso.run();
		} else if (args[1].startsWith("clonalg")) {
			
			ClonAlg clonalg = new ClonAlg(1.3, sizeOfPopulation, (int)(sizeOfPopulation*0.1), ffann.getWeightsCount(), errFunction, maxIterat,maxError);
			clonalg.run();
		} else {
			errorMsg();
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
	private static IReadOnlyDataset loadData(Path path) {
		List<IData<Double>> listOfIrises = new ArrayList<>();
		
		try (Stream<String> lines = Files.lines(path)) {
			Iterator<String> iter = lines.iterator();

			while (iter.hasNext()) {
				String line = iter.next();

				String[] featureAndClass = line.split("\\:");
				String feature = featureAndClass[0].trim().replaceAll("\\(", "");
				feature = feature.replaceAll("\\)", "");
				feature = feature.replaceAll("\\,", " ");
				String classifi = featureAndClass[1].trim().replaceAll("\\(", "");
				classifi = classifi.replaceAll("\\)", "");
				classifi = classifi.replaceAll("\\,", " ");
				String[] features = feature.trim().split("\\s+");
				String[] classes = classifi.trim().split("\\s+");

				String family = "";
				double lengthOfSepals=0;
				double widthOfSepals=0;
				double lengthOfPetals=0;
				double widthOfPetals=0;
				
				try {
					int int1 = Integer.parseInt(classes[0].trim());
					int int2 = Integer.parseInt(classes[1].trim());
					int int3 = Integer.parseInt(classes[2].trim());
					lengthOfSepals=Double.parseDouble(features[0].trim());
					widthOfSepals=Double.parseDouble(features[1].trim());
					lengthOfPetals=Double.parseDouble(features[2].trim());
					widthOfPetals=Double.parseDouble(features[3].trim());
					
					if (int1 == 1 && int2 == 0 && int3 == 0) {
						family = "Iris setosa";
					} else if (int2 == 1 && int1 == 0 && int3 == 0) {
						family = "Iris versicolor";
					} else if (int3 == 1 && int1 == 0 && int2 == 0) {
						family = "Iris virginica";
					}

				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					System.exit(0);
				}
				Iris iris = new Iris(family, lengthOfSepals, widthOfSepals, lengthOfPetals, widthOfPetals);
				listOfIrises.add(iris);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		ReadOnlyDataset dataset = new ReadOnlyDataset(listOfIrises);
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
		System.exit(0);

	}
}
