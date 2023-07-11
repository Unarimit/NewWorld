package object;

import java.util.Random;

import frame.MapPanel;

public class Food {
	private final static int MESS_LIMIT = 20;
	private int locationX;	//坐标
	private int locationY;	//坐标
	private int mess;	//质量，吃到会+多少血
	Random rand = new Random();
	public Food() {
		super();
		// TODO Auto-generated constructor stub
		locationX = rand.nextInt(MapPanel.MAP_WIDTH);
		locationY = rand.nextInt(MapPanel.MAP_HEIGHT);
		mess = rand.nextInt(MESS_LIMIT);
	}
	public int getLocationX() {
		return locationX;
	}
	public int getLocationY() {
		return locationY;
	}
	public int getMess() {
		return mess;
	}
	
}
