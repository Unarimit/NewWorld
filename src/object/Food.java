package object;

import java.util.Random;

import frame.MapPanel;

public class Food {
	private final static int MESS_LIMIT = 20;
	private int locationX;	//����
	private int locationY;	//����
	private int mess;	//�������Ե���+����Ѫ
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
