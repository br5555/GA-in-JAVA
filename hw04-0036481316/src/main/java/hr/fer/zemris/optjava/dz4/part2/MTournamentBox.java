package hr.fer.zemris.optjava.dz4.part2;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.optjava.dz4.part1.Selection;

/**
 * Tournament selection is a method of selecting an individual from a population
 * of individuals in a genetic algorithm. The winner of each tournament (the one
 * with the best fitness) is selected for crossover.
 * 
 * @author Branko
 *
 */
public class MTournamentBox {

	/**
	 * Public static method which returns the worst from the inputed container.
	 * @param containers inputed container
	 * @return the worst from the inputed container
	 */
	public static Boxes mTournamentBox(List<Boxes> containers) {
		Collections.sort(containers, new Comparator<Boxes>() {
			@Override
			public int compare(Boxes o1, Boxes o2) {
				if (o1.fitness > o2.fitness) {
					return 1;
				} else if (o1.fitness == o2.fitness) {
					return 0;
				}
				return -1;

			}
		});
		return containers.get(0);
	}

	/**
	 * Public static method which returns the best from the inputed container.
	 * @param containers inputed container
	 * @return the best from the inputed container
	 */
	public static Boxes nTournamentBox(List<Boxes> containers) {
		Collections.sort(containers, new Comparator<Boxes>() {
			@Override
			public int compare(Boxes o1, Boxes o2) {
				if (o1.fitness > o2.fitness) {
					return -1;
				} else if (o1.fitness == o2.fitness) {
					return 0;
				}
				return 1;

			}
		});
		return containers.get(0);
	}
}
