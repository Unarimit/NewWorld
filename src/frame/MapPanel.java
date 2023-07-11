package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import object.*;
import util.*;

// 地图大小 宽950 高600
public class MapPanel extends JPanel{
	public static final int MAP_WIDTH = 950;
	public static final int MAP_HEIGHT = 600;
	private static final int POINT_SIZE = 10;
	private static final int FOOD_SIZE = 8;

	
	
	public MapPanel() {
		super();
		// TODO Auto-generated constructor stub
		this.setLayout(null);
		this.setSize(MAP_WIDTH, MAP_HEIGHT);
		this.setBackground(Color.gray);
		this.setOpaque(false);
	}
	// 画出英雄组所有英雄位置
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		for (Hero hero : Data.heroList) {
			int x = (int)hero.getLocationX();
			int y = (int)hero.getLocationY();
			g.setColor(hero.getColor());
			g.drawOval(x, y, POINT_SIZE, POINT_SIZE);
			g.fillOval(x, y, POINT_SIZE, POINT_SIZE);
		}
		for (Food food : Data.foodList) {
			int x = food.getLocationX();
			int y = food.getLocationY();
			g.setColor(Color.green);
			g.drawRect(x, y, FOOD_SIZE, FOOD_SIZE);
			g.fillRect(x, y, FOOD_SIZE, FOOD_SIZE);
		}
		for (Message m : Data.messageQ) {
			if(m.getType()=="h2h") {
				g.setColor(m.getHeroA().getColor());
				g.drawLine((int)m.getHeroA().getLocationX(), (int)m.getHeroA().getLocationY()
						, (int)m.getHeroB().getLocationX(), (int)m.getHeroB().getLocationY());
				
			}
		}
	}

	public void upDate() {
		this.repaint();
	}
}
