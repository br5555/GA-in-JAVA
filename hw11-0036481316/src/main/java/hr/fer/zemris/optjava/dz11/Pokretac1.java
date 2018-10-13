package hr.fer.zemris.optjava.dz11;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.Crossover;
import hr.fer.zemris.optjava.generic.ga.Evaluator;
import hr.fer.zemris.optjava.generic.ga.GASolutionVector;
import hr.fer.zemris.optjava.generic.ga.GeneticAlgh;
import hr.fer.zemris.optjava.generic.ga.Mutation;
import hr.fer.zemris.optjava.generic.ga.Selection;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * Class test impleemntation of {@linkplain GeneticAlgh}.Program uses argument from command line
 * - first argument is path to original PNG image
 * - second argument is number of squares for approximating image
 * - third argument is size of population
 * - fourth argument is maximal number of generation
 * - fifth argumnet is minimal accepted fitness
 * - sixth argumnet is path to txt file where soultion will be written
 * - seventh argument is path to destination image
 * @author Branko
 *
 */
public class Pokretac1 {

	/**
	 * Method from which program starts to execute. Method uses arguments form command line
	 * @param args arguments from command line
	 */
	public static void main(String[] args) {
		if (args.length != 7) {
			errorMsg();
		}

		Path orginalIm = Paths.get(args[0]);
		Path txtFile = Paths.get(args[5]);
		Path destIm = Paths.get(args[6]);

		if (!Files.exists(orginalIm)) {
			errorMsg();
		}

		int numOfSquares = Integer.parseInt(args[1]);
		int sizeOfPop = Integer.parseInt(args[2]);
		int maxIterat = Integer.parseInt(args[3]);
		double minError = Double.parseDouble(args[4]);
		
		GrayScaleImage template = null;
		try {
			template = GrayScaleImage.load(orginalIm.toFile());
		} catch (IOException e1) {
			errorMsg();
		}
		Evaluator evaluator = new Evaluator(template);
//		RNG rng = new RNG();
		
		GeneticAlgh algh = new GeneticAlgh(sizeOfPop, numOfSquares, RNG.getRNG(), maxIterat, 4, new Selection(), new Crossover(2, RNG.getRNG()), new Mutation(2, RNG.getRNG()), template);
		
		GASolutionVector solVec = algh.run();
		int[] sol = solVec.getData();
		
		List<String> lines1 = new ArrayList<>();

		for (int vec : sol) {
			lines1.add(Integer.toString(vec));
		}

		try {
			Files.write(txtFile, lines1, Charset.forName("UTF-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GrayScaleImage solIm = evaluator.draw(solVec, null);
		try {
			solIm.save(destIm.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method display to user how to use program
	 */
	public static void errorMsg() {
		System.out.println("Dear user,");
		System.out.println("           - first argument is path to original PNG image");
		System.out.println("           - second argument is number of squares for approximating image");
		System.out.println("           - third argument is size of population");
		System.out.println("           - fourth argument is maximal number of generation");
		System.out.println("           - fifth argumnet is minimal accepted fitness");
		System.out.println("           - sixth argumnet is path to txt file where soultion will be written");
		System.out.println("           - seventh argument is path to destination image");
		
		System.exit(1);

	}
}
