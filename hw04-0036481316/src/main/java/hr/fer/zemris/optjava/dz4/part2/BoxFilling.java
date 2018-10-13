package hr.fer.zemris.optjava.dz4.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import hr.fer.zemris.optjava.dz4.part1.DoubleArraySolution;
import hr.fer.zemris.optjava.dz4.part1.IDecoder;
import hr.fer.zemris.optjava.dz4.part1.IFunction;
import hr.fer.zemris.optjava.dz4.part1.INeighborhood;

/**
 * Class implements genetic algorithm for storing sticks into minimal number of
 * boxes.
 * 
 * @author Branko
 *
 */
public class BoxFilling {

	/**
	 * Size of population
	 */
	private static int sizeOfPopulation;
	/**
	 * Parameter for tournament(finding the best) selection
	 */
	private static int n;
	/**
	 * Parameter for tournament(finding the worst) selection
	 */
	private static int m;
	/**
	 * flag which tells are the child and worst from tournament are
	 * compared(true) or child replace worst without comparing(false)
	 */
	private static boolean p;
	/**
	 * Maximal number of iteration
	 */
	private static int maxIteration;
	/**
	 * Maximal size of the tank which satisfied
	 */
	private static double sizeOfTank;
	/**
	 * Hyper parameter for mutation
	 */
	private static int sigma;
	/**
	 * Private reference to {@linkplain IFunction}
	 */
	private IFunction function;
	/**
	 * Hyper paramter for {@linkplain BoxFunction}
	 */
	private static double alpha = 1.3;
	/**
	 * Private reference to {@linkplain IDecoder}
	 */
	private IDecoder<DoubleArraySolution> decoder;
	/**
	 * Private reference of {@linkplain INeighborhood}
	 */
	private INeighborhood<DoubleArraySolution> neighborhood;
	/**
	 * Starting solution
	 */
	private Boxes startBoxes;

	/**
	 * Main method from which program starts to execute. Program use arguments
	 * form command line
	 * 
	 * @param args
	 *            first argument is path to folder, second argument is size of
	 *            population,third argument is n, fourth argument is m, fifth
	 *            argument is p, sixth argument is maximal number of iteration
	 *            and seventh is acceptable size of tank
	 */
	public static void main(String[] args) {
		if ((args == null) || (args.length == 0)) {
			errorMsg();

		}
		if (args.length != 7 && args.length != 8) {
			errorMsg();

		}
		Path path = Paths.get(args[0]);
		if (!Files.exists(path)) {
			errorMsg();
		}
		try {
			sizeOfPopulation = Integer.parseInt(args[1].trim());
			n = Integer.parseInt(args[2].trim());
			m = Integer.parseInt(args[3].trim());
			p = Boolean.parseBoolean(args[4].trim());
			maxIteration = Integer.parseInt(args[5].trim());
			sizeOfTank = Double.parseDouble(args[6].trim());
			if (args.length == 8) {
				sigma = Integer.parseInt(args[7].trim());

			} else {
				sigma = 3;
			}

		} catch (NumberFormatException ex) {
			errorMsg();
		}
		List<Stick> sticks = new ArrayList<>();
		int count = 0;
		try (Stream<String> lines = Files.lines(path)) {
			Iterator<String> iter = lines.iterator();

			while (iter.hasNext()) {
				String line = iter.next();
				if (line.startsWith("[")) {
					line = line.replaceAll("\\[", "").replaceAll("\\]", "");
					line = line.replaceAll("\\,", " ");
					line = line.trim();
					String[] values = line.split("\\s+");

					for (String num : values) {
						try {
							sticks.add(new Stick(count++, Integer.parseInt(num)));
						} catch (NumberFormatException ex) {
							ex.printStackTrace();
						}
					}

				}

			}
			List<Box> boxes = new ArrayList<>();

			for (int i = 0, size = sticks.size(); i < size; i++) {
				ArrayList<Stick> temp = new ArrayList<>();
				temp.add(sticks.get(i));
				boxes.add(new Box(temp, 20));
			}

			BoxFilling.run(new Boxes(boxes, new BoxFunction(boxes.size(), alpha)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Public static method which tells user how to call program
	 * from command line
	 */
	private static void errorMsg() {

		System.out.println("Dear user, first argument is path to folder,"
				+ " second argument is size of population,third argument is n,"
				+ " fourth argument is m, fifth argument is p,"
				+ "sixth argument is maximal number of iteration and seventh is " + "acceptable size of tank");
		System.exit(0);
	}

	/**
	 * Method which executes algorithm
	 * @param startBoxes starting solution 
	 * @return final solution from algorithm
	 */
	public static Boxes run(Boxes startBoxes) {

		Random rand = new Random();
		List<Boxes> parents = new ArrayList<>();

		for (int i = 0; i < sizeOfPopulation; i++) {
			Boxes temp = new Boxes(startBoxes.getBoxes(), new BoxFunction(startBoxes.getNumberOfBoxes(), alpha));
			temp.randomize(400);
			parents.add(temp);

		}

		for (int k = 0; k < maxIteration; k++) {
			List<Boxes> nTor = new ArrayList<>();
			List<Boxes> mTor = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				nTor.add(parents.get(rand.nextInt(sizeOfPopulation)));
			}
			Boxes parent1 = MTournamentBox.nTournamentBox(nTor);
			nTor = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				nTor.add(parents.get(rand.nextInt(sizeOfPopulation)));
			}

			Boxes parent2 = MTournamentBox.nTournamentBox(nTor);

			Boxes temp1 = new Boxes(parent1.getBoxes(), new BoxFunction(parent1.getNumberOfBoxes(), alpha));
			Boxes temp2 = new Boxes(parent2.getBoxes(), new BoxFunction(parent2.getNumberOfBoxes(), alpha));

			List<Box> boxes2 = new ArrayList<>();
			boxes2.add(temp2.getBox(rand.nextInt(temp2.getNumberOfBoxes())));
			boxes2.add(temp2.getBox(rand.nextInt(temp2.getNumberOfBoxes())));
			boxes2.add(temp2.getBox(rand.nextInt(temp2.getNumberOfBoxes())));

			temp1.addBoxes(boxes2);
			Boxes child = new Boxes(temp1.getBoxes(), new BoxFunction(temp1.getNumberOfBoxes(), alpha));

			child.randomize(sigma);

			for (int i = 0; i < m; i++) {
				mTor.add(parents.get(rand.nextInt(sizeOfPopulation)));
			}

			Boxes worst = MTournamentBox.mTournamentBox(mTor);
			int index = parents.indexOf(worst);
			if (p && child.fitness >= worst.fitness) {
				parents.set(index, child);
			} else {
				parents.set(index, child);
			}
			Boxes res = MTournamentBox.nTournamentBox(parents);
			System.out.println(k + " : " + res.fitness + " " + res.toString());
			if (res.getNumberOfBoxes() <= sizeOfTank) {
				System.out
						.println("Final result : " + res.fitness + " " + res.getNumberOfBoxes() + " " + res.toString());
				return res;
			}

		}
		Boxes finalRes = MTournamentBox.nTournamentBox(parents);
		System.out.println(
				"Final result : " + finalRes.fitness + " " + finalRes.getNumberOfBoxes() + " " + finalRes.toString());

		return finalRes;
	}
}
