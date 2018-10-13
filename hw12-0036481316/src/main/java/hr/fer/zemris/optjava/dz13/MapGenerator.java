package hr.fer.zemris.optjava.dz13;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

	public int[][] map;
	public int[][] firstMap;
	public int brickWidth;
	public int brickHeight;
	private AntTrailGA antGA; 

	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		firstMap = map;
		brickWidth = 950 / col;
		brickHeight = 950 / row;
	}

	
	
	public MapGenerator(int[][] map) {
		super();
		this.map = copyMap(map);
		firstMap = copyMap(map);
		int col = map[0].length;
		int row = map.length;
		brickWidth = 950 / col;
		brickHeight = 950 / row;
	}
	
	public MapGenerator firstMap(){
		MapGenerator first = new MapGenerator(firstMap);
		return first;
	}

	public int[][] copyMap(int[][] map){
		int[][] temp = new int[map.length][map[0].length];
		
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				temp[i][j] = map[i][j];
			}
		}
		
		
		return temp;
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				g.setColor(Color.white);
				g.fillRect(j*brickWidth + 30, i*brickHeight + 30, brickWidth, brickHeight);
				
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.black);
				g.drawRect(j*brickWidth + 30, i*brickHeight + 30, brickWidth, brickHeight);
	
				if (map[i][j] > 0) {
					
					
					//food
					g.setColor(Color.red);
					g.fillOval(j*brickWidth + 50, i*brickHeight + 50, 8, 8);

					
		}
			}
		}
		
	}
	
	public void setBrickValue(int value, int row, int col ){
		map[row][col] = value;
	}
}
