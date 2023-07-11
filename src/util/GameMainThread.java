package util;

import java.util.LinkedList;

import frame.*;
import object.*;

public class GameMainThread implements Runnable {
	MainFrame mainFrame;
	private final int FOOD_CD = 400;
	private int foodCount; // 空降的食物数量会随着空投次数而减少
	private boolean run;

	public GameMainThread(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
		foodCount = 8;
		run = true;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Data.messageQ.offer(new Message("sys", "欢迎来到饥饿岛，在这里只有一个人能够幸存"));
		messageUpDate();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (run) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 如果只剩一个人，进入结算界面
			if (Data.heroList.size() == 1) {
				result(Data.heroList.poll());
				run = false;
			}
			upData();
		}
	}

	private void upData() {
		//	foreach遍历heroList更新他们，如果hero的hp小于0，调用死亡函数，删除相关更新组
		for (int i = 0; i < Data.heroList.size(); i++) {
			if (Data.heroList.get(i).getHp() <= 0) {
				heroDied(Data.heroList.get(i));
				i--;
			} else {
				Data.heroList.get(i).upDate();
			}
		}
		mainFrame.upDate();	//mainframe更新
		messageUpDate();	//检测message队列有没有信息，有就弹出给mainframe让它更新
		haveSomeFood();	//过一定时间，配给食物
		Data.clock++;	//时间流动
	}

	// 英雄死亡调用
	private void heroDied(Hero hero) {
		mainFrame.myPaint(new Message("die", hero));
		Data.heroList.remove(hero);
		for (Hero otherhero : Data.heroList) {
			otherhero.allListRemove(hero);
		}
	}

	// 更新信息时调用
	private void messageUpDate() {
		while (!Data.messageQ.isEmpty()) {
			mainFrame.myPaint(Data.messageQ.poll());
		}
	}

	// 空降食物调用
	private void haveSomeFood() {
		if (Data.clock % FOOD_CD == 0 && foodCount != 0) {
			Data.foodList = new LinkedList<Food>();
			for (int i = 0; i < this.foodCount; i++) {
				Data.foodList.add(new Food());
			}
			for (Hero hero : Data.heroList) {
				hero.detectFood();
			}
			foodCount -= 2;
			Data.messageQ.offer(new Message("sys", "空降了一些食物，并把距离这些人最近的食物坐标发给了他们"));
			if(foodCount == 0) 
				Data.gameRule = 2;
			
		} else if (Data.clock % FOOD_CD == 0 && Data.gameRule == 2) {
			Data.foodList = new LinkedList<Food>();
			Data.messageQ.offer(new Message("sys_warning", "最后的时刻就要降临了，每个人都将获得他的敌人的坐标"));
			Data.gameRule = 3;
		
		} else if (Data.clock % FOOD_CD == 0 && Data.gameRule == 3) {
			for (Hero hero : Data.heroList) {
				hero.detectFood();
			}
		}
	}

	// 结算界面调用
	private void result(Hero hero) {
		hero.setWinTimes(hero.getWinTimes()+1);//	胜利英雄胜场+1
		
		Data.messageQ.offer(new Message("sys", "GAME OVER!, 今天的饥饿岛，幸存一人..."));
		Data.messageQ.offer(new Message("sys", "顺便说一下，数据已保存，可以关闭了"));
		Data.saveHeroData();
		mainFrame.result(hero);
	}
//	private void upDateEarly() {
//		for (Hero hero : Data.heroList) {
//		if(hero.getHp()<=0) {
//			heroDied(hero);
//		}else {
//			hero.upDate();
//			hero.setHp(hero.getHp()-1);
//		}}
//		mainFrame.upDate();
//		迭代器不能再遍历途中删除元素
//	}
}
