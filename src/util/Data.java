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
	//分配颜色会用到的
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
	
	public static final ImageIcon DIEDIMG =  new ImageIcon("image/died.jpg");//hero死亡后会在他的面板上作为背景的图像
	public static final ImageIcon HPIMAGE =  new ImageIcon("image/hp.jpg");//血量条的图片
	public static final ImageIcon MAP =  new ImageIcon("image/map.jpg");//地图
	public static final int MAX_HERO_IN_WAR = 10;//场上最大出现的英雄数量，目前最多显示10个
	public static LinkedList<Hero> heroList;	//英雄列表
	public static LinkedList<Message> messageQ;	//信息列表
	public static LinkedList<Food> foodList;	//食物列表
	public static int clock;//	游戏计时器
	public static int gameRule;	//游戏规则，会随着时间的变化从1过渡到2。
	public static Hero[] heroDataBase = {new Anton(),new Aria(),new Arica(), new Boris(),
			new Katia(), new Marine(), new Pavle(), new Roman(), new Sakuya(), new Suzuha()};	//英雄数据库
	
	
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
	//	添加hero方法
	private static void addHero(Hero hero) {
		Random rand = new Random();
		hero.setColor(COLORLIST[colorcount]);
		hero.setLocation(rand.nextInt(MapPanel.MAP_WIDTH), rand.nextInt(MapPanel.MAP_HEIGHT));
		heroList.add(hero);
		colorcount++;
	}
	// 从HeroDatabase中选择将要参战的英雄
	private static void chooseHero() {
		LinkedList<Hero> temp = new LinkedList<Hero>();
		//	将数据库里的英雄加入一个列表
		for(int i=0; i<heroDataBase.length; i++) {
			temp.add(heroDataBase[i]);
		}
		//	洗牌
		Collections.shuffle(temp);
		
		//	选择前10个加入战场
		int heroCount = 0;
		for (Hero hero : temp) {
			if(heroCount == MAX_HERO_IN_WAR)
				break;
			addHero(hero);
			heroCount++;
		}
		//	初始化他们
		for (Hero hero : heroList) {
			hero.init();
		}
		
	}
	//	初始化英雄数据库，主要进行英雄的历史胜场赋值操作，从人力资源管理系统抄来后修改的，舒服
	private static void initHeroDatabase() {
		File file = new File("save.txt");
		if(!file.exists())
			return;
		//读取文件对象
		FileReader fr = null;
		//缓存流，提高性能
		BufferedReader br = null;
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String lineStr = null;
			int count = 0;
			//从文本文件读取一行，存放到lineStr，判断lineStr是否为空
			while((lineStr = br.readLine()) != null){
				//分隔符，split使用正则表达式算法，需要前面加上转意符号\
				String[] info = lineStr.split("\\|");
				//	将读取的数据赋值给英雄数据库
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
	//	游戏结束的保存操作，从人力资源管理系统抄来后修改的，舒服
	public static void saveHeroData() {
		FileOutputStream fos = null;
		try{
			//实例化输出流
			fos= new FileOutputStream("save.txt");
			for(int i = 0; i < heroDataBase.length; i++){
				//将对象信息转为字符串，属性用|间隔
				String temp = String.valueOf(heroDataBase[i].getWinTimes()) + "|" + String.valueOf(heroDataBase[i].getAppearTimes()) + "\r\n";
				try{ 
					//写入文本文件，需要异常捕捉
					fos.write(temp.getBytes());
				}catch(Exception e){	
					e.printStackTrace();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				//关闭流
				fos.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
