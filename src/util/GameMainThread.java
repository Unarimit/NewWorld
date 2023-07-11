package util;

import java.util.LinkedList;

import frame.*;
import object.*;

public class GameMainThread implements Runnable {
	MainFrame mainFrame;
	private final int FOOD_CD = 400;
	private int foodCount; // �ս���ʳ�����������ſ�Ͷ����������
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
		Data.messageQ.offer(new Message("sys", "��ӭ������������������ֻ��һ�����ܹ��Ҵ�"));
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
			// ���ֻʣһ���ˣ�����������
			if (Data.heroList.size() == 1) {
				result(Data.heroList.poll());
				run = false;
			}
			upData();
		}
	}

	private void upData() {
		//	foreach����heroList�������ǣ����hero��hpС��0����������������ɾ����ظ�����
		for (int i = 0; i < Data.heroList.size(); i++) {
			if (Data.heroList.get(i).getHp() <= 0) {
				heroDied(Data.heroList.get(i));
				i--;
			} else {
				Data.heroList.get(i).upDate();
			}
		}
		mainFrame.upDate();	//mainframe����
		messageUpDate();	//���message������û����Ϣ���о͵�����mainframe��������
		haveSomeFood();	//��һ��ʱ�䣬���ʳ��
		Data.clock++;	//ʱ������
	}

	// Ӣ����������
	private void heroDied(Hero hero) {
		mainFrame.myPaint(new Message("die", hero));
		Data.heroList.remove(hero);
		for (Hero otherhero : Data.heroList) {
			otherhero.allListRemove(hero);
		}
	}

	// ������Ϣʱ����
	private void messageUpDate() {
		while (!Data.messageQ.isEmpty()) {
			mainFrame.myPaint(Data.messageQ.poll());
		}
	}

	// �ս�ʳ�����
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
			Data.messageQ.offer(new Message("sys", "�ս���һЩʳ����Ѿ�����Щ�������ʳ�����귢��������"));
			if(foodCount == 0) 
				Data.gameRule = 2;
			
		} else if (Data.clock % FOOD_CD == 0 && Data.gameRule == 2) {
			Data.foodList = new LinkedList<Food>();
			Data.messageQ.offer(new Message("sys_warning", "����ʱ�̾�Ҫ�����ˣ�ÿ���˶���������ĵ��˵�����"));
			Data.gameRule = 3;
		
		} else if (Data.clock % FOOD_CD == 0 && Data.gameRule == 3) {
			for (Hero hero : Data.heroList) {
				hero.detectFood();
			}
		}
	}

	// ����������
	private void result(Hero hero) {
		hero.setWinTimes(hero.getWinTimes()+1);//	ʤ��Ӣ��ʤ��+1
		
		Data.messageQ.offer(new Message("sys", "GAME OVER!, ����ļ��������Ҵ�һ��..."));
		Data.messageQ.offer(new Message("sys", "˳��˵һ�£������ѱ��棬���Թر���"));
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
//		�����������ٱ���;��ɾ��Ԫ��
//	}
}
