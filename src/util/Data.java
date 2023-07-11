package util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


import javax.swing.ImageIcon;

import frame.MapPanel;
import object.*;

public class Data {
	//������ɫ���õ���
	public static final Color RED = new Color(220,20,60);
	public static final Color LIGHTPINK = new Color(255,182,193);
	public static final Color DEEPPINK = new Color(255,20,147);
	public static final Color VOILET = new Color(148,0,211);
	public static final Color BLUE = new Color(0,0,205);
	public static final Color SKYBLUE = new Color(0,191,255);
	public static final Color GREEN = new Color(46,139,87);
	public static final Color YELLOW = new Color(255,255,0);
	public static final Color ORANGE = new Color(255,165,0);
	public static final Color OLIVE = new Color(128,128,0);
	public static final Color [] COLORLIST = {RED,LIGHTPINK,DEEPPINK,
			VOILET,BLUE,SKYBLUE,GREEN,YELLOW,ORANGE,OLIVE};
	
	public static final ImageIcon DIEDIMG =  new ImageIcon("image/died.jpg");//hero��������������������Ϊ������ͼ��
	public static final ImageIcon HPIMAGE =  new ImageIcon("image/hp.jpg");//Ѫ������ͼƬ
	public static final ImageIcon MAP =  new ImageIcon("image/map.jpg");//��ͼ
	public static final int MAX_HERO_IN_WAR = 10;//���������ֵ�Ӣ��������Ŀǰ�����ʾ10��
	public static LinkedList<Hero> heroList;	//Ӣ���б�
	public static LinkedList<Message> messageQ;	//��Ϣ�б�
	public static LinkedList<Food> foodList;	//ʳ���б�
	public static int clock;//	��Ϸ��ʱ��
	public static int gameRule;	//��Ϸ���򣬻�����ʱ��ı仯��1���ɵ�2��
	public static Hero[] heroDataBase = {new Anton(),new Aria(),new Arica(), new Boris(),
			new Katia(), new Marine(), new Pavle(), new Roman(), new Sakuya(), new Suzuha()};	//Ӣ�����ݿ�
	
	
	private static int colorcount = 0;
	
	public static void init() {
		gameRule = 1;
		clock = 0;
		heroList = new LinkedList<Hero>();
		messageQ = new LinkedList<Message>();
		foodList = new LinkedList<Food>();
		initHeroDatabase();
		chooseHero();
		//test();
		
	}
	
//	private static void test() {
//		addHero(new ando());
//		addHero(new arica());
//		addHero(new boris());
//		addHero(new kitaliy());
//		addHero(new marine());
//		addHero(new pafoli());
//		addHero(new roman());
//		addHero(new Suzuha());
//		addHero(new Aria());
//		addHero(new Sakuya());
//	}
	//	���hero����
	private static void addHero(Hero hero) {
		Random rand = new Random();
		hero.setColor(COLORLIST[colorcount]);
		hero.setLocation(rand.nextInt(MapPanel.MAP_WIDTH), rand.nextInt(MapPanel.MAP_HEIGHT));
		heroList.add(hero);
		colorcount++;
	}
	// ��HeroDatabase��ѡ��Ҫ��ս��Ӣ��
	private static void chooseHero() {
		LinkedList<Hero> temp = new LinkedList<Hero>();
		//	�����ݿ����Ӣ�ۼ���һ���б�
		for(int i=0; i<heroDataBase.length; i++) {
			temp.add(heroDataBase[i]);
		}
		//	ϴ��
		Collections.shuffle(temp);
		
		//	ѡ��ǰ10������ս��
		int heroCount = 0;
		for (Hero hero : temp) {
			if(heroCount == MAX_HERO_IN_WAR)
				break;
			addHero(hero);
			heroCount++;
		}
		//	��ʼ������
		for (Hero hero : heroList) {
			hero.init();
		}
		
	}
	//	��ʼ��Ӣ�����ݿ⣬��Ҫ����Ӣ�۵���ʷʤ����ֵ��������������Դ����ϵͳ�������޸ĵģ����
	private static void initHeroDatabase() {
		File file = new File("save.txt");
		if(!file.exists())
			return;
		//��ȡ�ļ�����
		FileReader fr = null;
		//���������������
		BufferedReader br = null;
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String lineStr = null;
			int count = 0;
			//���ı��ļ���ȡһ�У���ŵ�lineStr���ж�lineStr�Ƿ�Ϊ��
			while((lineStr = br.readLine()) != null){
				//�ָ�����splitʹ��������ʽ�㷨����Ҫǰ�����ת�����\
				String[] info = lineStr.split("\\|");
				//	����ȡ�����ݸ�ֵ��Ӣ�����ݿ�
				heroDataBase[count].setWinTimes(Integer.parseInt(info[0]));
				heroDataBase[count].setAppearTimes(Integer.parseInt(info[1]));
				count ++ ;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
			br.close();
			fr.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	//	��Ϸ�����ı����������������Դ����ϵͳ�������޸ĵģ����
	public static void saveHeroData() {
		FileOutputStream fos = null;
		try{
			//ʵ���������
			fos= new FileOutputStream("save.txt");
			for(int i = 0; i < heroDataBase.length; i++){
				//��������ϢתΪ�ַ�����������|���
				String temp = String.valueOf(heroDataBase[i].getWinTimes()) + "|" + String.valueOf(heroDataBase[i].getAppearTimes()) + "\r\n";
				try{ 
					//д���ı��ļ�����Ҫ�쳣��׽
					fos.write(temp.getBytes());
				}catch(Exception e){	
					e.printStackTrace();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				//�ر���
				fos.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
