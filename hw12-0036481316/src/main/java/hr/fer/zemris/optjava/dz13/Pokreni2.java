package hr.fer.zemris.optjava.dz13;

import java.util.Random;

public class Pokreni2 {

	public static void main(String[] args) {
		MapGenerator map = new MapGenerator(32, 32);
		Ant ant = new Ant(0, 0, 1, 0, 0, 31, 0, 31, 0);

		GamePlay gameplay = new GamePlay(map, ant);
		ConstructTree conTree = new ConstructTree(ant, map, 3, 200,10,200, gameplay);


		Node root = conTree.grow(null, null);
		conTree.ant = new Ant(0, 0, 1, 0, 0, 31, 0, 31, 0);
		Node root2 = conTree.grow(null, null);

		System.out.println(root.numOfChild);
		do{
			root.executeFunction();
			root.resetFlage();
		}while(root.antHasEnergy());
		System.out.println("Prije reseta" +  root.toString());

		root.resetGame();
		System.out.println("Poslije reseta" +root.toString());

		System.out.println("Nova igra");
		do{
			root.executeFunction();
			root.resetFlage();
		}while(root.antHasEnergy());

		do{
			root2.executeFunction();
			root2.resetFlage();
		}while(root2.antHasEnergy());
		
		Crossover crossO = new Crossover(conTree);
		Node child = crossO.crossover(root, root2);
		System.out.println("Dijete Prije " +child.toString());

		System.out.println("Dijete");
		
		int i = 0;
		do{
			child.executeFunction();
			child.resetFlage();
			i++;
		}while(child.antHasEnergy() && i<100);
		System.out.println("Dijete polsije " +child.toString());

		System.out.println(i);
		if(i == 100){
			do{
				child.executeFunction();
				child.resetFlage();
				i++;
			}while(child.antHasEnergy());
		}
		System.out.println(root.toString());
	}

}
