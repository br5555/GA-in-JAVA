package hr.fer.zemris.optjava.dz13;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Gamplay extends {@link JPanel} and implements {@link KeyListener} and {@link ActionListener} and
 * represent simple game of ant in torodional world
 * @author Branko
 *
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener {

	/**
	 * flage is game started
	 */
	private boolean play = false;
	/**
	 * ants score
	 */
	private int score = 0;
	/**
	 * total number of bricks
	 */
	private int totalBricks = 32*32;

	/**
	 * private instance of  {@linkplain Timer}
	 */
	private Timer timer;
	/**
	 * delay in seconds
	 */
	private int delay = 8;

	/**
	 * x position of ant
	 */
	private int ballposX = 40;
	/**
	 * y position of ant
	 */
	private int ballposY = 40;
	/**
	 * x direction of ant
	 */
	private int ballxDir = 1;
	/**
	 * y direction of ant
	 */
	private int ballyDir = 0;
	/**
	 * angle of ant with respect to x-axis
	 */
	private int angle = 0;

	/**
	 * protected instance of {@linkplain MapGenerator}
	 */
	protected MapGenerator map;

	protected Ant myAnt;
	
	/**
	 * Public constructor which accepts desire settings
	 * @param map desire {@linkplain MapGenerator}
	 */
	public GamePlay(MapGenerator map, Ant ant) {
		
		this.map = map;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		myAnt = ant;
	}
	


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 1020, 1020);

		map.draw((Graphics2D) g);

		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("Score: "+myAnt.antScore, 400, 30);

		// the ant
		g.setColor(Color.green);
		g.fillOval(ballposX, ballposY, 10, 10);
		g.setColor(Color.ORANGE);
		g.fillOval(ballposX+6*ballxDir, ballposY+6*ballyDir, 10, 10);
		g.setColor(Color.BLUE);
		g.fillOval(ballposX+4+5*ballyDir, ballposY+4+5*ballxDir, 3, 3);
		g.fillOval(ballposX+4-5*ballyDir, ballposY+4-5*ballxDir, 3, 3);
		g.fillOval(ballposX+4+6*ballxDir+5*ballyDir, ballposY+4+6*ballyDir+5*ballxDir, 3, 3);
		g.fillOval(ballposX+4+6*ballxDir-5*ballyDir, ballposY+4+6*ballyDir-5*ballxDir, 3, 3);
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
//		if (play) {
////			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(playerX, 550, 100, 8)) {
////				ballyDir = -ballyDir;
////
////			}
////			boolean touch = false;
////			for (int i = 0; i < map.map.length && !touch; i++) {
////				for (int j = 0; j < map.map[0].length && !touch; j++) {
////					if (map.map[i][j] > 0) {
////						int brickX = j * map.brickWidth + 80;
////						int brickY = i * map.brickHeight + 50;
////						int brickWidth = map.brickWidth;
////						int brickHeight = map.brickHeight;
////
////						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
////						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
////						Rectangle brickRect = rect;
////
////					}
////				}
//			}
//
//			
//		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//			 
//				if(ballxDir == 1) moveRight();
//				else if(ballxDir == -1){
//					rotateCW();
//					rotateCW();
//				}else if(ballyDir == -1) rotateCW();
//				else rotateCCW();
//
//			
//		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//				if(ballxDir== -1) moveLeft();
//				else if(ballxDir == 1){
//					rotateCW();
//					rotateCW();
//				}else if(ballyDir == -1) rotateCCW();
//				else rotateCW();
//
//		}
//		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//				
//				if(ballyDir == 1) moveUp();
//				else if(ballyDir == -1){
//					rotateCW();
//					rotateCW();
//				}else if(ballxDir == -1) rotateCCW();
//				else rotateCW();
//
//		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
//			
//			if(ballyDir == -1) moveDown();
//			else if(ballyDir == 1){
//				rotateCW();
//				rotateCW();
//			}else if(ballxDir == -1) rotateCW();
//			else rotateCCW();
//
//			
//		}
//		
		
	}

	/**
	 * Public method which rotates ant for 90 degrees clockwise
	 */
	public void rotateCW(){
		angle =(angle+90)%360;
//		myAnt.angle = angle;

		setDir();
		
	}
	
	/**
	 * Public method which rotates ant for 90 degrees counter clockwise
	 */
	public void rotateCCW(){
		angle =(angle+270)%360;
//		myAnt.angle = angle;
		setDir();
		
	}
	
	/**
	 * Public method updates direction of ant
	 */
	private void setDir() {
		if(angle == 0){
			ballxDir = 1;
			ballyDir = 0;
		}else if(angle == 90){
			ballxDir = 0;
			ballyDir = 1;
		}else if(angle == 180){
			ballxDir = -1;
			ballyDir = 0;
		}else{
			ballxDir = 0;
			ballyDir = -1;
		}
//		myAnt.dirX = ballxDir;
//		myAnt.dirY = ballyDir;
		repaint();
		
	}

	/**
	 * Public method moves ant to right
	 */
	public void moveRight() {
		play = true;
		ballposX += map.brickWidth;
		if (ballposX >= 950) {
			ballposX = 40;
		}
//		myAnt.postionX++;
//		if(myAnt.postionX>31) myAnt.postionX=0;
		repaint();

	}

	/**
	 * Public method moves ant to left
	 */
	public void moveLeft() {
		play = true;
		ballposX -= map.brickWidth;
		if (ballposX <= 35) {
			ballposX = 940;
		}
//		myAnt.postionX--;
//		if(myAnt.postionX<0) myAnt.postionX=31;
		repaint();

	}

	/**
	 * Public method moves ant up
	 */
	public void moveUp() {
		play = true;
		ballposY += map.brickHeight;
		if (ballposY >= 960) {
			ballposY = 40;
		} 
//		myAnt.postionY++;
//		if(myAnt.postionY>31) myAnt.postionY=0;
		repaint();

	}

	/**
	 * Public method moves ant down
	 */
	public void moveDown() {
		play = true;
		ballposY -= map.brickHeight;
		if (ballposY <= 40) {
			ballposY = 940;
		}
//		myAnt.postionY--;
//		if(myAnt.postionY<0) myAnt.postionY=31;
		repaint();

	}
	
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
