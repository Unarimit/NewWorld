package frame;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.*;

import object.Hero;
import util.Data;

public class CharacterPanel extends JPanel {
	private static final int CPNANEL_WIDTH = 250;
	private static final int CPNANEL_HEIGHT = 75; // 这里是指单个的高度
	private static final int IMAGE_X = 10;
	private static final int IMAGE_PIX = 60;
	private static final int NAME_X = 80;
	private static final int HP_X = 80;
	private static final int HP_MAX_LENGTH = 160;
	private static final int COLOR_X = 220;
	private static final Color NOT_TOO_BLACK = new Color(40, 40, 40);
	private static final Color NOT_TOO_GREEN = new Color(60, 179, 113);
	private static int TopY = 0;
	private static int BeginY = 7;

	public JLabel labelImage;
	private JLabel labelName;
	private JLabel labelHp;
	private JLabel labelHpText;
	private LinkedList<JPanel> hungryBuffer;
	private Hero hero;
	private boolean deathFlag = false;

	public CharacterPanel(Hero hero) {
		super();
		// TODO Auto-generated constructor stub
		this.hero = hero;
		this.setLocation(0, TopY);
		this.setSize(CPNANEL_WIDTH, CPNANEL_HEIGHT);
		this.setLayout(null);
		this.setBackground(NOT_TOO_BLACK);

		hungryBuffer = new LinkedList<JPanel>();

		showHeadImage();
		showName();
		showHpText();
		showHp();
		showColor();
		
		TopY += CPNANEL_HEIGHT + 5;
	}

	// 显示头像
	private void showHeadImage() {
		labelImage = new JLabel(hero.getHeadImage());
		labelImage.setBounds(IMAGE_X, BeginY, IMAGE_PIX, IMAGE_PIX);
		this.add(labelImage);
		
		
	}

	// 显示名字
	private void showName() {
		labelName = new JLabel(hero.getName());
		labelName.setForeground(Color.white);
		labelName.setBounds(NAME_X, BeginY + 10, 60, 10);
		this.add(labelName);
	}

	// 显示血条
	private void showHp() {
		labelHp = new JLabel(Data.HPIMAGE);
		labelHp.setBackground(NOT_TOO_GREEN);
		labelHp.setBounds(HP_X, BeginY + 30, (int) (HP_MAX_LENGTH * (hero.getHp() / hero.getMaxHp())), 20);
		labelHp.setOpaque(false);
		this.add(labelHp);
	}

	// 显示血条文字
	private void showHpText() {
		String s = "" + hero.getHp();
		labelHpText = new JLabel(s);
		labelHpText.setForeground(Color.white);
		labelHpText.setBounds(HP_X + 5, BeginY + 35, 60, 10);
		this.add(labelHpText);
	}

	// 显示指代颜色
	private void showColor() {
		JPanel labelColor = new JPanel();
		labelColor.setBounds(COLOR_X, BeginY + 5, 20, 10);
		labelColor.setBackground(hero.getColor());
		this.add(labelColor);
	}

	// 显示饥饿度
	private void showHungry() {
		int start = 0;
		for (int i = 1; i < hero.getHungry(); i++) {
			JPanel temp = new JPanel();
			temp.setBounds(HP_X + start, BeginY + 50, 10, 7);
			temp.setBackground(Color.orange);
			this.add(temp);
			hungryBuffer.add(temp);
			start += 12;
		}

		if (hero.getHungry() >= hero.getMaxHungry()) {
			for (JPanel jLabel : hungryBuffer) {
				jLabel.setBackground(Color.red);
			}
		}

	}

	// 读取hero类信息，更新显示
	public void upDate() {
		if(hero.getHp()<25) {
			this.setBackground(new Color(100,0,0));
		}else if(hero.getHp()<50) {
			this.setBackground(new Color(50,0,0));
		}else {
			this.setBackground(NOT_TOO_BLACK);
		}
		
		if (deathFlag) {
			
		} else if (hero.getHp() <= 0) {
			died();
		} else {
			String s = "" + hero.getHp();
			labelHpText.setText(s);
			double temp = HP_MAX_LENGTH * (((double) hero.getHp()) / hero.getMaxHp());
			labelHp.setBounds(HP_X, BeginY + 30, (int) temp, 20);
			this.showHungry();
		}

	}

	// 执行角色死亡函数
	public void died() {
		this.remove(labelHp);
		this.remove(labelHpText);
		JLabel died = new JLabel(Data.DIEDIMG);
		died.setOpaque(false);
		died.setBounds(0, 0, CPNANEL_WIDTH, CPNANEL_HEIGHT);
		this.add(died);
		deathFlag = true;
		for (JPanel jPanel : hungryBuffer) {
			this.remove(jPanel);
		}
	}

}
