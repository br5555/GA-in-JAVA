package hr.fer.zemris.optjava.dz13;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class represent genetic programming.
 * @author Branko
 *./src/main/resources/SantFe.txt 100 500 89 ./src/main/resources/tree.txt
 */
public class AntTrailGA extends JFrame{

	/**
	 * size of population
	 */
	private static int sizeOfPopulation;
	/**
	 * maximal number of iteration
	 */
	private static int maxIteration;
	/**
	 * private instance of {@linkplain ICrossover}
	 */
	private ICrossover crossover;
	/**
	 * private instance of {@linkplain IMutation}
	 */
	private IMutation mutation;
	/**
	 * percentage for crossover
	 */
	private double percentageOfCrossover = 0.85;
	/**
	 * percentage for mutation
	 */
	private double percentageOfMutation = 0.14;
	/**
	 * percentage for reproduction
	 */
	private double percentageOfReproduction = 0.01;
	/**
	 * maximal fitness
	 */
	private static double maxFitness;
	/**
	 * private instance of {@linkplain ConstructTree}
	 */
	private ConstructTree tree;
	/**
	 * private instance of {@linkplain Random}
	 */
	private Random rand;
	/**
	 * private instance of {@linkplain ISelection}
	 */
	private ISelection selection;
	/**
	 * global best solution
	 */
	private Node globalBest;
	/**
	 * best solution
	 */
	protected static Node bestSol;
	/**
	 * protected reference to {@linkplain GamePlay}
	 */
	protected static GamePlay gameplay;
	/**
	 * protected reference to {@linkplain AntTrailGA}
	 */
	protected static AntTrailGA main ;
	/**
	 * protected reference to {@linkplain JTextField}
	 */
	protected JTextField antsScore;
	
	private double desireWidth = 1020;
	private double desireHeight = 1020;
	
	/**
	 * Main method from which program starts to execute. Method uses arguments from command line.
	 * @param args arguments from command line
	 */
	public static void main(String[] args) {
		if (args.length != 5) {
			errorMsg();
		}
		
		Path input = Paths.get(args[0]);
		if (!Files.exists(input)) {
			errorMsg();
		}
		
		Path output = Paths.get(args[4]);

		try {
			maxIteration = Integer.parseInt(args[1].trim());
			sizeOfPopulation = Integer.parseInt(args[2].trim());
			maxFitness = Double.parseDouble(args[3].trim());
		} catch (NumberFormatException e) {
			errorMsg();
		}
		
		int[][] mapField = null;
		int i = 0 , j = 0;
		
		try (Stream<String> stream = Files.lines(input)) {
			
	        Iterator<String> iterator = stream.iterator();
	        boolean first = true;
	        
	        while(iterator.hasNext()){
	        	String line = iterator.next();
	        	if(first){
	        		first = false;
	        		String[] arraySize = line.split("x");
	        		mapField = new int[Integer.parseInt(arraySize[0].trim())][Integer.parseInt(arraySize[1].trim())];
	        		continue;
	        	}
	        	char[] syms = line.toCharArray();
	        	for(char sym: syms){
	        		if(sym == '1'){
	        			mapField[j][i++] =1;
	        		}else mapField[j][i++] =0;
	        	}
	        	i = 0;
	        	j++;
	        }
	        
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		MapGenerator map = new MapGenerator(mapField);
		Ant ant = new Ant(0, 0, 1, 0, 0, 31, 0, 31, 0);

		gameplay = new GamePlay(map,ant);
		
		ConstructTree conTree = new ConstructTree(ant, map, 7, 200,20,200, gameplay);
		Crossover crossover = new Crossover(conTree);
		Mutation mutation = new Mutation(conTree);
		Selection selection = new Selection();
		
		main = new AntTrailGA(crossover, mutation, 0.85, 0.14, 0.01, conTree, new Random(), selection);
		bestSol = main.run();
		System.out.println("Score "+bestSol.ant.antScore);
		bestSol.turnSimulation();
		main.init();
		
		List<String> lines1 = new ArrayList<>();

		
		lines1.add(bestSol.toString());
		

		try {
			Files.write(output, lines1, Charset.forName("UTF-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * action listener
	 */
	public static ActionListener playAc = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(bestSol == null) return;
			bestSol.resetGame();
			gameplay = bestSol.gameplay;
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			main.init();
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			Thread dretva = new Thread(new Runnable() {
				
				@Override
				public void run() {
					main.executeBest();
					
				}
			});
			dretva.start();
			
		}
	};
	
	/**
	 * Public method executes best solution
	 */
	public  void executeBest(){
		do{
			bestSol.executeFunction();
			bestSol.resetFlage();

		}while(bestSol.antHasEnergy());
	}
	
	/**
	 * Method which construct JFrame for Ant
	 */
	public void init(){
		main.getContentPane().removeAll();
		main.getContentPane().repaint();
		bestSol.resetGame();
//		bestSol.turnSimulation();
		
		gameplay = bestSol.gameplay;
		main.setBounds(10, 10 , (int)desireWidth, (int)desireHeight);
		main.setTitle("Ant");
		antsScore = new JTextField("Score :"+bestSol.ant.antScore);

		JPanel panel = new JPanel(new BorderLayout());

		JPanel westPanel = new JPanel(new BorderLayout());
		
		JButton playButton = new JButton("Play");
		playButton.setSize(20, 20);
		playButton.addActionListener(playAc);
		main.setResizable(false);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(gameplay == null) System.out.println("LOL");
		panel.add(gameplay, BorderLayout.CENTER);
		westPanel.add(playButton, BorderLayout.WEST);
		panel.add(westPanel, BorderLayout.SOUTH);

		main.add(panel);
		main.validate();
		main.repaint();

	}
	
	

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return super.getHeight();
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return super.getWidth();
	}
	
	public double getDesireWidth() {
		return desireWidth;
	}
	
	public double getDesireHeight() {
		return desireHeight;
	}
	
	

	public AntTrailGA(ICrossover crossover, IMutation mutation, double percentageOfCrossover,
			double percentageOfMutation, double percentageOfReproduction, ConstructTree tree, Random rand,
			ISelection selection) {
		super();
		this.crossover = crossover;
		this.mutation = mutation;
		this.percentageOfCrossover = percentageOfCrossover;
		this.percentageOfMutation = percentageOfMutation;
		this.percentageOfReproduction = percentageOfReproduction;
		this.tree = tree;
		this.rand = rand;
		this.selection = selection;
	}

	public AntTrailGA(ICrossover crossover, IMutation mutation,  ConstructTree tree, 
			ISelection selection) {
		super();
		this.crossover = crossover;
		this.mutation = mutation;
		this.tree = tree;
		this.rand = new Random();
		this.selection = selection;
	}



	/**
	 * Public method which display users how to use program.
	 */
	public static void errorMsg() {
		System.out.println("Dear user,");
		System.out.println("         * first argument is path to desire document,");
		System.out.println("         * second argument is maximal allowed number of generations,");
		System.out.println("         * third argument is size of population,");
		System.out.println(
				"         * fourth argumnet is satisfied fitness after which program stops searching for better solutions,");
		System.out.println("         * fifth argument is path to destination document where solution will be written");

		System.exit(1);

	}

	/**
	 * Public method which executes genetic programming
	 * @return best tree
	 */
	private Node run() {
		ArrayList<Node> population = new ArrayList<>();
		for (int i = 0; i < sizeOfPopulation; i++) {
			
			if (i % 2 == 0 && i != 0) {
				tree.maxDepth--;
				if(tree.maxDepth <= 3) tree.maxDepth =7;
			}
			
			tree.ant = new Ant(0, 0, 1, 0, 0, 31, 0, 31, 0);
			
			if (i % 2 == 0)
				population.add(tree.full(null, null));
			else
				population.add(tree.grow(null, null));
			tree.resetCounter();
			
		}
		evaluate(population);
		globalBest = selection.select(population).copy();

		for (int i = 0; i < maxIteration; i++) {

			ArrayList<Node> newPopulation = new ArrayList<>();

			for (int j = 0; j < sizeOfPopulation; j++) {
				ArrayList<Node> forSelc = giveNodes(population);

				float f = rand.nextFloat();
				if (f < percentageOfCrossover) {

					Node parent1 = selection.select(giveNodes(population));
					Node parent2 = selection.select(giveNodes(population));
					Node child = crossover.crossover(parent1, parent2);

					while(child.ant.antEnergy>0){
						child.executeFunction();
						child.resetFlage();
					}
					if(child.ant.antScore == parent1.ant.antScore) child.childCrib();
					newPopulation.add(child);

				} else if ((f - percentageOfCrossover) < percentageOfMutation) {
					Node parent1 = selection.select(giveNodes(population));
					Node child = mutation.mutate(parent1);

					while(child.ant.antEnergy>0){
						child.executeFunction();

						child.resetFlage();

					}

					if(child.ant.antScore == parent1.ant.antScore) child.childCrib();

					newPopulation.add(child);

				} else {
					Node child = selection.select(giveNodes(population)).copy();
					child.resetGame();
					newPopulation.add(child);

				}
			}
			evaluate(newPopulation);
			Node best = selection.select(population).copy();
			if(globalBest.ant.antScore < best.ant.antScore) globalBest = best;
			System.out.println("Global best "+globalBest.ant.antScore);
			if(globalBest.ant.antScore >= maxFitness) return globalBest;;
			population = new ArrayList<>(newPopulation);
		}
		return globalBest;
	}

	/**
	 * Method randomly picks seven trees
	 * @param population population of trees
	 * @return randomly picked seven trees
	 */
	private ArrayList<Node> giveNodes(ArrayList<Node> population) {
		ArrayList<Integer> indexes = new ArrayList<>();
		ArrayList<Node> forSel = new ArrayList<>();

		int size = population.size();

		for (int i = 0; i < 7; i++) {
			while (indexes.size() - 1 != i) {
				Integer iii = rand.nextInt(size);
				if (!indexes.contains(iii)) {
					indexes.add(iii);
					forSel.add(population.get(iii.intValue()));
				}
			}
		}
		return forSel;

	}

	/**
	 * Method evaluate list of trees 
	 * @param population list of trees
	 */
	private void evaluate(ArrayList<Node> population) {
		int size = population.size();

		for (int i = 0; i < size; i++) {
			while(population.get(i).ant.antEnergy>0){
				population.get(i).executeFunction();

				population.get(i).resetFlage();

			}
		}

	}
}
