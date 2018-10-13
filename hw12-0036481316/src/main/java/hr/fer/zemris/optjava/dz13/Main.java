package hr.fer.zemris.optjava.dz13;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame{
	
	protected static Node bestSol;
	protected static GamePlay gameplay;
	protected static Ant ant;
	protected static Main main = new Main();
	protected JTextField antsScore;
	
	public static ActionListener playAc = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(bestSol == null) return;
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
	

	
	public  void executeBest(){
		do{
			bestSol.executeFunction();
			bestSol.resetFlage();
			
		}while(bestSol.antHasEnergy());
	}
	
	public void init(){
		main.getContentPane().removeAll();
		main.getContentPane().repaint();
//		bestSol.resetGame();
//		bestSol.turnSimulation();
		
		
//		gameplay = bestSol.gameplay;
		main.setBounds(10, 10 , 1020, 1020);
		main.setTitle("Ant");
		JPanel panel = new JPanel(new BorderLayout());
//		antsScore = new JTextField("Score :"+ant.postionX + " "+ant.postionY);

		JPanel westPanel = new JPanel(new BorderLayout());
		
		JButton playButton = new JButton("Play");
		playButton.setSize(20, 20);
		playButton.addActionListener(playAc);
		main.setResizable(false);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(gameplay, BorderLayout.CENTER);
		westPanel.add(playButton, BorderLayout.WEST);
//		westPanel.add(antsScore, BorderLayout.CENTER);
		panel.add(westPanel, BorderLayout.SOUTH);

		main.add(panel);
		main.validate();
		main.repaint();

	}
	
	public static void main(String[] args) {

		MapGenerator map = new MapGenerator(32, 32);
		ant = new Ant(0, 0, 1, 0, 0, 31, 0, 31, 0);

		gameplay = new GamePlay(map,ant);
		
		ConstructTree conTree = new ConstructTree(ant, map, 6, 200,20,200, gameplay);
		
		bestSol = conTree.grow(null, null);
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		main.init();
		System.out.println(bestSol.toString());

		
	}

}
