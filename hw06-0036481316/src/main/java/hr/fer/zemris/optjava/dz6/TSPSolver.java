package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.eatthepath.jvptree.VPTree;

/**
 * Class that implements Ant min-max algorithm. The ant colony optimization
 * algorithm (ACO) is a probabilistic technique for solving computational
 * problems which can be reduced to finding good paths through graphs. First
 * argument must be path to file,second argument is size of candidate list,
 * third is number of ants and forth is maximal number of generations
 * 
 * @author Branko
 *
 */
public class TSPSolver {

	/**
	 * alpha hyper parameter
	 */
	private double alpha;
	/**
	 * beta hyper parameter
	 */
	private static double beta;
	/**
	 * maximal amount of pheromone
	 */
	private static double tauMax;
	/**
	 * minimal amount of pheromone
	 */
	private static double tauMin;
	/**
	 * Map that stores distance to neighbour
	 */
	private HashMap<Integer, ArrayList<Double>> neighboursDist;
	/**
	 * Map that stores distance to neighbour at minus beta
	 */
	private HashMap<Integer, ArrayList<Double>> neighboursDistwithBeta;
	/**
	 * Map that stores ID of neighbours
	 */
	private HashMap<Integer, ArrayList<Integer>> neighboursInd;
	/**
	 * Map that stores amount of pheromone at path that leading to neighbour
	 */
	private HashMap<Integer, ArrayList<Double>> neighboursPhero;
	/**
	 * Map that connects ID to instance of {@linkplain City}
	 */
	private HashMap<Integer, City> neighboursCity;
	/**
	 * Private instance of {@linkplain Random}
	 */
	private Random rand;
	/**
	 * Hyper parameter for evaporation
	 */
	private double rho;
	/**
	 * number of neighbours that will me memorised
	 */
	private int numberOfNearestNeigh;
	/**
	 * Maximal radius of neighbourhood
	 */
	private double radius;
	/**
	 * Hyper paremeter number of ants
	 */
	private int numberOfAnts;
	/**
	 * Number of cities
	 */
	private int numberOfCities;
	/**
	 * Global best {@linkplain Ant}
	 */
	private Ant globalBest;
	/**
	 * Flag that tells if we have globally best Ant
	 */
	private boolean haveBest;
	/**
	 * Flag for restarting anthill to zero
	 */
	private boolean resetAnts;
	/**
	 * Hyper parameter maximal iteration
	 */
	private int maxIteration;
	/**
	 * Hyper parameter
	 */
	private double a;

	/**
	 * Public static method which calculates euclidean distance
	 * 
	 * @param city1
	 *            first city
	 * @param city2
	 *            second city
	 * @return distance between them
	 */
	public static double euclidianDistance(City city1, City city2) {

		return Math.sqrt(Math.pow(city1.getX() - city2.getX(), 2) + Math.pow(city1.getY() - city2.getY(), 2));
	}

	/**
	 * Method for brute force calculating nearest neighbours
	 * 
	 * @param cities
	 *            list of cities
	 */
	public void nearestNeighbours(List<City> cities) {

		ArrayList<Double> distances = new ArrayList<>();
		ArrayList<Integer> indexes = new ArrayList<>();
		ArrayList<Double> phero = new ArrayList<>();

		for (int i = 0, size = cities.size(); i < size; i++) {
			City c1 = cities.get(i);
			TreeMap<Double, Integer> myMap = new TreeMap<>();
			TreeMap<Double, Integer> sorted = new TreeMap();
			for (int j = 0; j < size; j++) {
				City c2 = cities.get(j);
				if (c1.getName() == c2.getName()) {
					continue;
				}
				double dist = TSPSolver.euclidianDistance(c1, c2);

				myMap.put(dist, j);

			}
			sorted.putAll(myMap);
			int k = 0;
			for (Double dist : myMap.keySet()) {
				if (k >= numberOfNearestNeigh) {
					break;
				}
				k++;
				distances.add(Math.pow(1 / Math.sqrt(dist), beta));
				indexes.add(sorted.get(dist));
				phero.add(tauMax);
			}
			neighboursDist.put(i, distances);
			neighboursInd.put(i, indexes);
			neighboursPhero.put(i, phero);

		}

	}

	/**
	 * Public constructor which accepts desire settings
	 * 
	 * @param numberOfAnts
	 *            number of ants
	 * @param numOfCandidates
	 *            number of near neighbours
	 * @param maxIteration
	 *            maximal number of iteration
	 * @param neighboursDist
	 *            map of distances to neighbours
	 * @param neighboursInd
	 *            map of neighbours IDs
	 * @param neighboursPhero
	 *            map of amount of pheromon on path
	 * @param neighboursCity
	 *            map of ID and their instance of {@linkplain City}
	 * @param cities
	 *            list of cities
	 * @param numberOfCities
	 *            number of cities
	 * @param neighboursDistwithBeta
	 *            map of distances at minus beta
	 */
	public TSPSolver(int numberOfAnts, int numOfCandidates, int maxIteration,
			HashMap<Integer, ArrayList<Double>> neighboursDist, HashMap<Integer, ArrayList<Integer>> neighboursInd,
			HashMap<Integer, ArrayList<Double>> neighboursPhero, HashMap<Integer, City> neighboursCity,
			ArrayList<City> cities, int numberOfCities, HashMap<Integer, ArrayList<Double>> neighboursDistwithBeta) {
		this.rho = 0.02;
		this.alpha = 1;
		this.beta = 3;
		this.tauMax = 5;
		this.tauMin = 1;
		this.numberOfAnts = numberOfAnts;
		this.numberOfCities = numberOfCities;
		this.numberOfNearestNeigh = numOfCandidates;
		this.maxIteration = maxIteration;
		this.neighboursInd = neighboursInd;
		this.neighboursDist = neighboursDist;
		this.neighboursPhero = neighboursPhero;
		this.neighboursCity = neighboursCity;
		this.neighboursDistwithBeta = neighboursDistwithBeta;
		rand = new Random();
		this.a = 1.1;
	}

	/**
	 * Main method from which program starts to execute. Method uses inputs from
	 * command line
	 * 
	 * @param args
	 *            inputs form command line
	 */
	public static void main(String[] args) {
		if (args.length != 4) {
			errorMsg();
		}
		Path path = Paths.get(args[0]);
		if (!Files.exists(path)) {
			errorMsg();
		}

		int numberOfAnts = 0;
		int numOfCandidates = 0;
		int maxIteration = 0;
		try {
			numberOfAnts = Integer.parseInt(args[2]);
			numOfCandidates = Integer.parseInt(args[1]);
			maxIteration = Integer.parseInt(args[3]);
		} catch (NumberFormatException ex) {
			errorMsg();
		}

		HashMap<Integer, ArrayList<Double>> neighboursDist = new HashMap<>();
		HashMap<Integer, ArrayList<Integer>> neighboursInd = new HashMap<>();
		HashMap<Integer, ArrayList<Double>> neighboursPhero = new HashMap<>();
		HashMap<Integer, ArrayList<Double>> neighboursDistwithBeta = new HashMap<>();
		HashMap<Integer, City> neighboursCity = new HashMap<>();
		ArrayList<City> cities = new ArrayList<>();
		boolean first = true;
		int numberOfCities = 0;

		try (Stream<String> lines = Files.lines(path)) {
			Iterator<String> iter = lines.iterator();

			while (iter.hasNext()) {
				String line = iter.next();
				if (line.equals("EOF")) {
					continue;
				}
				if (first) {
					first = false;
					numberOfCities = Integer.parseInt(line);
					continue;
				}
				String[] parts = line.split("\\s+");
				try {
					City city = new City(Integer.parseInt(parts[0]) - 1, Double.parseDouble(parts[1]),
							Double.parseDouble(parts[2]));
					cities.add(city);
					neighboursCity.put(Integer.parseInt(parts[0]) - 1, city);
				} catch (NumberFormatException ex) {
					System.out.println("Dear user, file is wrongly structured");
					System.exit(0);
				}

			}

			for (City city : cities) {
				ArrayList<City> cities2 = new ArrayList<>(cities);
				cities2.remove(city);
				final VPTree<CartesianPoint, City> vpTree = new VPTree<CartesianPoint, City>(
						new CartesianDistanceFunction(), cities2);
				final List<City> nearestCities = vpTree.getNearestNeighbors(city, numOfCandidates);

				ArrayList<Double> distance = new ArrayList<>();
				ArrayList<Double> distancewithBeta = new ArrayList<>();

				ArrayList<Integer> indexes = new ArrayList<>();
				ArrayList<Double> phero = new ArrayList<>();

				for (City city1 : cities) {
					phero.add(tauMax);

				}

				for (City neighbor : nearestCities) {
					indexes.add(neighbor.getName());
					double dist = euclidianDistance(city, neighbor);
					distance.add(dist);
					distancewithBeta.add(Math.pow(1 / dist, beta));
				}
				neighboursDistwithBeta.put(city.getName(), distancewithBeta);
				neighboursDist.put(city.getName(), distance);
				neighboursInd.put(city.getName(), indexes);
				neighboursPhero.put(city.getName(), phero);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		TSPSolver algh = new TSPSolver(numberOfAnts, numOfCandidates, maxIteration, neighboursDist, neighboursInd,
				neighboursPhero, neighboursCity, cities, numberOfCities, neighboursDistwithBeta);
		algh.run();
	}

	/**
	 * Method which display user how to use program.
	 */
	private static void errorMsg() {
		System.out.println("Dear user, first argument must be path to file,"
				+ " second argument is size of candidate list, third is number"
				+ " of ants and forth is maximal number of generations");
		System.exit(0);

	}

	/**
	 * Method which executes algorithm.
	 */
	public void run() {
		ArrayList<Ant> ants = new ArrayList<>();

		for (int i = 0; i < numberOfAnts; i++) {
			ants.add(new Ant(rand.nextInt(numberOfCities)));
		}

		for (int iter = 0; iter < maxIteration; iter++) {

			for (int i = 0; i < numberOfAnts; i++) {
				ants.get(i).resetCities();
				ants.get(i).setStartIndex(rand.nextInt(numberOfCities));
				constructSolution(ants.get(i));
			}

			Collections.sort(ants, Collections.reverseOrder());

			if (!haveBest) {
				haveBest = true;
				globalBest = ants.get(0).duplicate();
				updateTauMax();

			} else if (globalBest.compareTo(ants.get(0)) <= 0) {

				globalBest = ants.get(0).duplicate();

			}

			evaporation();

			ArrayList<Integer> bestCities;
			if (iter >= 0.8 * maxIteration) {
				bestCities = globalBest.getCities();
			} else {
				bestCities = ants.get(0).duplicate().getCities();
			}
			for (int k = 0, max = bestCities.size() - 1; k < max; k++) {

				ArrayList<Double> newPheros = neighboursPhero.get(bestCities.get(k));
				double tau = newPheros.get(bestCities.get(k + 1)) * (1 + numberOfAnts);

				if (tau > tauMax) {
					tau = tauMax;
				}
				newPheros.set(bestCities.get(k + 1), tau);

			}
			System.out.println("The best ant is " + globalBest.getDistance() + " " + globalBest.toString());

			if (resetAnts) {
				System.out.println("Restarto sam na " + iter);
				tauMax = 1 / (rho * globalBest.distance);
				tauMin = tauMin / a;
				for (int i = 0; i < numberOfAnts; i++) {
					ants.get(i).resetCities();
					ants.get(i).setStartIndex(rand.nextInt(numberOfCities));
					// iter = -1;
				}
				for (Integer pheroIndex : neighboursPhero.keySet()) {
					ArrayList<Double> maxPheros = new ArrayList<>(neighboursPhero.get(pheroIndex));
					for (int k = 0, sizeList = maxPheros.size(); k < sizeList; k++) {
						maxPheros.set(k, tauMax);
					}
					neighboursPhero.put(pheroIndex, maxPheros);
				}
			}

		}
		System.out.println("The best ant is " + globalBest.getDistance() + " " + globalBest.toString());
	}

	private void updateTauMax() {
		tauMax = 1 / (rho * globalBest.distance);
		tauMin = tauMin;
		for (Integer pheroIndex : neighboursPhero.keySet()) {
			ArrayList<Double> maxPheros = new ArrayList<>(neighboursPhero.get(pheroIndex));
			for (int k = 0, sizeList = maxPheros.size(); k < sizeList; k++) {
				maxPheros.set(k, tauMax);
			}
			neighboursPhero.put(pheroIndex, maxPheros);
		}

	}

	private void evaporation() {
		resetAnts = false;
		int numOfMin = 0;
		for (Integer indPhe : neighboursPhero.keySet()) {
			ArrayList<Double> temPheros = neighboursPhero.get(indPhe);
			for (int i = 0, sizePheros = temPheros.size(); i < sizePheros; i++) {
				double tau = temPheros.get(i) * (1 - rho);
				if (tau < tauMin) {
					numOfMin++;
					tau = tauMin;
				}
				temPheros.set(i, tau);
			}
			neighboursPhero.put(indPhe, temPheros);
		}
		if (numberOfCities * numberOfCities - numOfMin <= 2 * numberOfCities) {
			resetAnts = true;
		}
	}

	/**
	 * Method which construct solution for one Ant
	 * @param ant instance of {@linkplain Ant}
	 */
	private void constructSolution(Ant ant) {
		for (int i = 0, size = neighboursInd.keySet().size(); i < size; i++) {
			ArrayList<Integer> antCities = ant.getCities();
			int cityIndex = this.nextCity(antCities.get(antCities.size() - 1), antCities);
			int lastIndex = antCities.get(antCities.size() - 1);
			double dist = 0;
			int index = -1;

			if (neighboursInd.get(lastIndex).contains(cityIndex)) {
				index = neighboursInd.get(lastIndex).indexOf(cityIndex);
				dist = neighboursDist.get(lastIndex).get(index);
			} else {
				City city1 = neighboursCity.get(lastIndex);
				City city2 = neighboursCity.get(cityIndex);
				dist = euclidianDistance(city1, city2);

			}
			ant.addDistance(dist);
			ant.addCity(cityIndex);

		}

		ArrayList<Integer> antCities = ant.getCities();
		ant.addDistance(this.euclidianDistance(neighboursCity.get(antCities.get(0)),
				neighboursCity.get(antCities.get(numberOfCities - 1))));
	}

	private int nextCity(int tempCity, ArrayList<Integer> seenCities) {
		double[] probabilities = new double[neighboursInd.get(tempCity).size()];

		ArrayList<Double> dist = neighboursDistwithBeta.get(tempCity);
		ArrayList<Double> phero = neighboursPhero.get(tempCity);
		double sum = 0;
		for (int i = 0, size = neighboursInd.get(tempCity).size(); i < size; i++) {
			sum += Math.pow(phero.get(i), alpha) * dist.get(i);
		}

		for (int i = 0, size = neighboursInd.get(tempCity).size(); i < size; i++) {
			probabilities[i] = Math.pow(phero.get(i), alpha) * dist.get(i) / sum;

		}
		if (seenCities.containsAll(neighboursInd.get(tempCity))) {
			Set<Integer> neighSet = new HashSet<>(neighboursInd.get(tempCity));
			Set<Integer> allCities = new HashSet<>(neighboursDist.keySet());
			allCities.removeAll(neighSet);
			ArrayList<Integer> restCities = new ArrayList<>(allCities);
			return restCities.get(rand.nextInt(restCities.size()));

		}

		double roullete = rand.nextDouble();
		double temp = 0;
		for (int i = 0, size = probabilities.length; i < size; i++) {
			temp += probabilities[i];
			if (temp >= roullete) {
				int index = neighboursInd.get(tempCity).get(i);

				if (!seenCities.contains(index)) {
					return index;
				} else {
					i = -1;
					roullete = rand.nextDouble();
					temp = 0;
				}

			}
		}
		int index = 0;
		do {
			if (!seenCities.contains(index)) {
				return index;
			}
			index++;
			index %= numberOfCities;
		} while (true);
	}
}
