package hr.fer.zemris.optjava.dz11;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.Crossover;
import hr.fer.zemris.optjava.generic.ga.Evaluator;
import hr.fer.zemris.optjava.generic.ga.GASolutionVector;
import hr.fer.zemris.optjava.generic.ga.GeneticAlgh;
import hr.fer.zemris.optjava.generic.ga.Mutation;
import hr.fer.zemris.optjava.generic.ga.Selection;
import hr.fer.zemris.optjava.rng.RNG;

public class Proba2 {


	public static void main(String[] args) {
		Path orginalIm = Paths.get("./src/main/resources/11-kuca-200-133.png");
		Path txtFile = Paths.get("./src/main/resources/test.txt");
		Path destIm = Paths.get("./src/main/resources/kuca.png");

		if (!Files.exists(orginalIm)) {
			System.err.println("AA");
		}

		
		
		GrayScaleImage template = null;
		try {
			template = GrayScaleImage.load(orginalIm.toFile());
		} catch (IOException e1) {
			System.err.println("bb");
		}
		Evaluator evaluator = new Evaluator(template);
		RNG rng = new RNG();
		
		
		int[] sol = new int[]{1, 4, 5, 7, 9, 19, 10};
		
		List<String> lines1 = new ArrayList<>();

		for (int vec : sol) {
			lines1.add(Integer.toString(vec));
		}

		try {
			Files.write(txtFile, lines1, Charset.forName("UTF-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GrayScaleImage solIm = template;
		try {
			solIm.save(destIm.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
