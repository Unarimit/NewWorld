package object;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public abstract class Hero {
	private static final int RAND_MOVE_RADIUS = 100;
	private static final double RE_HP_SPEED = 10;	//每几个clock恢复一次生命
	private static final double EAT_FOOD_DISTANCE = 5;	//吃食物的距离
	private static final String DEFAULT_CLAIM = "我赢了";
	
	//	不同类不同的数据
	protected int maxHp; // 最大生命值
	protected double hp; // 生命值
	protected double reHp; // 生命恢复速率
	protected String name; // 名字
	protected double speed; // 英雄速度
	protected double seeRange;	//视野距离
	protected double attackRange;	//攻击距离
	protected double damage;	//可以造成的伤害
	protected ImageIcon headImage; // 头像
	protected ImageIcon vicImage; //胜利展示
	protected int weaponCD;	//武器冷却时间
	protected int good;	//友善度，取值0-50，越高越容易和人合作，判定时是取两个人的友善度的和，随机一个100以内数，如果小于他们之和，就会合作
	protected int miss;	//闪避率，取值0-100，越高越容易闪避攻击
	protected int maxHungry;	//英雄的最大忍耐饥饿度
	protected String vicClaim;
	
	private Color color; // 表示颜色
	private double locationX; // 目前所在地X
	private double locationY; // 目前所在地Y
	private double destinationX; // 需到达地点X
	private double destinationY; // 需到达地点Y
	private boolean moveFlag; // 移动标记
	private Food targetFood;	//要吃的食物
	protected  LinkedList<Hero> coHeroList;	//合作英雄列表
	protected  LinkedList<Hero> proHeroList;	//对抗英雄列表
	private LinkedList<Hero> notFoundList;	//未发现英雄列表
	protected LinkedList<Hero> canAttackList;	//可以攻击到的英雄列表. 改为protected属性, 供子类遍历
	private int hungry;	//英雄的饥饿程度， 越大越饿
	private boolean crazyMode;	//狂暴模式标志旗
	private boolean inAttackRange;	//是否在别人攻击范围旗帜，会在自更新的时候重新设会flag
	private int shockTick;	//眩晕计时器
	
	private int winTimes;	//胜利次数
	private int appearTimes;	//出现次数
	Random rand = new Random();

	public Hero() {
		super();
		this.reHp = 0.5;	//每tick回复生命，默认为0.5
		this.maxHungry = 3;	//正常情况下不用改
		this.vicClaim = DEFAULT_CLAIM;
		
		winTimes = 0;
		appearTimes = 0;
		shockTick = 0;
		hungry = 0;
		crazyMode = false;
		moveFlag = false;
		inAttackRange = false;
		coHeroList = new LinkedList<Hero>();
		proHeroList = new LinkedList<Hero>();
	}
	//	在读取完存档后调用
	public void init() {
		notFoundList = new LinkedList<Hero>();
		notFoundList.addAll(Data.heroList);
		notFoundList.remove(this);
		appearTimes += 1;
	}

	public void upDate() {
		if(shockTick==0) {
			basicAction();
			action();
			endAction();
		}else {
			shockTick--;
		}
		checkStatu();
	}

	//	基本行为
	protected void basicAction() {
		if (moveFlag == true) {
			move();
			checkDestinationLegal();
			//	当规则改变为3时，将目的地定为敌方英雄
			if(Data.gameRule == 3 && (!proHeroList.isEmpty())) {
				destinationX = proHeroList.getFirst().getLocationX();
				destinationY = proHeroList.getFirst().getLocationY();
			}
		} else
			isNeedMove();
		
		findHero();
		doAttack();
		canEat();
		relax();
	}
	//	状态检测
	protected void checkStatu() {
		checkHungry();
	}
	//	特殊hero特殊行为
	abstract public void action();
	//	行为结束收回一些旗帜
	protected void endAction() {
		inAttackRange = false;
	}
	
	//	随机移动函数
	protected void isNeedMove() {
		if (rand.nextInt(100) > 97) {
			moveFlag = true;
			destinationX = locationX + rand.nextInt(RAND_MOVE_RADIUS) - 50;
			destinationY = locationY + rand.nextInt(RAND_MOVE_RADIUS) - 50;
		}
	}

	//	向目的地直线移动
	public void move() {
		// 1.在y上垂直移动
		// 2.在x上垂直移动
		// 3.斜线移动
		if ((destinationX - locationX) == 0) {
			if (Math.abs(destinationY - locationY) < speed) {
				locationY = destinationY;
				moveFlag = false;
			} else {
				if (locationY - destinationY < 0)
					locationY += speed;
				else
					locationY -= speed;
			}
		} else if ((destinationY - locationY) == 0) {
			if (Math.abs(destinationX - locationX) < speed) {
				locationX = destinationX;
				moveFlag = false;
			} else {
				if (locationX - destinationX < 0)
					locationX += speed;
				else
					locationX -= speed;
			}
		} else {
			double distance = Math.sqrt(Math.pow((destinationX - locationX), 2) + Math.pow((destinationY - locationY), 2));
			if (distance <speed) {
				locationX = destinationX;
				locationY = destinationY;
			}else {
				double sin = (destinationX - locationX) / distance;
				double cos = (destinationY - locationY) / distance;
				locationX += speed * sin;
				locationY += speed * cos;
			}
		}
	}
	//	判断单位是否进入攻击范围
	protected boolean isAttack(Hero he) {
		if(betweenDistance(he)<=this.attackRange)
			return true;
		else
			return false;
	}
	//	判断单位是否进入视野范围
	protected boolean isSee(Hero he) {
		if(betweenDistance(he)<=this.seeRange)
			return true;
		else
			return false;
	}
	//	计算与另一个hero的距离
	protected double betweenDistance(Hero he) {
		return Math.sqrt(Math.pow((this.locationX - he.getLocationX()), 2) + Math.pow((this.locationY - he.getLocationY()), 2));
	}
	//	计算与一个food的距离
	protected double betweenDistanceFood(Food he) {
		return Math.sqrt(Math.pow((this.locationX - he.getLocationX()), 2) + Math.pow((this.locationY - he.getLocationY()), 2));
	}
	//	检查目的地合法性
	private void checkDestinationLegal() {
		if(destinationX<10) {
			destinationX = 10;
		}else if(destinationX>940)
			destinationX = 940;
		if(destinationY<10)
			destinationY = 10;
		else if(destinationY>590)
			destinationY = 590;
	}
	//	发现英雄
	private void findHero() {
		for (Hero hero : notFoundList) {
			if(isSee(hero)) {
				if(rand.nextInt(100)<(this.good+hero.getGood())) {
					multico(hero);
					Data.messageQ.offer(new Message("h2h", this, hero, "和", "现在互相合作"));
				}else {
					multipro(hero);
					Data.messageQ.offer(new Message("h2h", this, hero, "和", "现在互相为敌"));
				}
				break;
			}
		}
	}
	//	检查是否有可以攻击的英雄，加入可攻击列表，并攻击第一个加入的
	private void doAttack() {
		canAttackList = new LinkedList<Hero>();
		for (Hero hero : proHeroList) {
			if(this.isAttack(hero)) {
				canAttackList.add(hero);
			}
		}
		if((Data.clock%this.weaponCD==0)&&!canAttackList.isEmpty()) {
			this.attack(canAttackList.poll());
		}
	}
	//	攻击英雄
	protected void attack(Hero he) {
		he.setInAttackRange(true);
		if(rand.nextInt(100)>he.getMiss()) {
			he.setHp(he.getHp()-this.damage);
			Data.messageQ.offer(new Message("h2h", this, he, "对", "造成了"+this.damage+"伤害"));
		}else {
			Data.messageQ.offer(new Message("h2h", he, this, "闪避了来自", "的伤害"));
		}
	}
	//	互相为敌，并删除未发现英雄表的对应项
	private void multipro(Hero he) {
		this.proHeroList.add(he);
		this.notFoundList.remove(he);
		he.beMultipro(this);
	}
	public void beMultipro(Hero he) {
		this.proHeroList.add(he);
		this.notFoundList.remove(he);
	}
	//	互相合作，并删除未发现英雄表的对应项
	private void multico(Hero he) {
		this.coHeroList.add(he);
		this.notFoundList.remove(he);
		he.beMultico(this);
	}
	public void beMultico(Hero he) {
		this.coHeroList.add(he);
		this.notFoundList.remove(he);
	}
	//	其他英雄死亡时调用
	public void allListRemove(Hero he) {
		this.coHeroList.remove(he);
		this.proHeroList.remove(he);
		this.notFoundList.remove(he);
	}
	//zhuyunbo ADD relax
	private void relax(){
		if(Data.clock%RE_HP_SPEED==0)
		{
			if((this.hp+this.reHp)<=this.maxHp)
				this.hp+=reHp;
			else
				this.hp = this.maxHp;
		}
	}
	//	寻找最近的食物，当食物空降时调用
	public void detectFood() {
		if(!Data.foodList.isEmpty()) {
			Food min = Data.foodList.getFirst();
			for (Food food : Data.foodList) {
				if(this.betweenDistanceFood(food)<this.betweenDistanceFood(min))
					min = food;
			}
			this.destinationX = min.getLocationX();
			this.destinationY = min.getLocationY();
			this.moveFlag = true;
			targetFood = min;
		}
		this.hungry += 1;
	}
	//	检查食物能不能吃到，能吃到就吃
	private void canEat() {
		if(targetFood!=null) {
			if(this.betweenDistanceFood(targetFood)<EAT_FOOD_DISTANCE) {
				if(Data.foodList.contains(targetFood)) {
					this.hp += targetFood.getMess();
					Data.foodList.remove(targetFood);
					targetFood = null;
					Data.messageQ.offer(new Message("eat", this));
					this.hungry -= 1;
				}else
					targetFood = null;
			}
		}
	}
	//	检查饥饿度，如果低于就进入狂暴模式
	private void checkHungry() {
		if(hungry>=maxHungry && crazyMode == false) {
			Data.messageQ.offer(new Message("crazy", this));
			for (Hero hero : Data.heroList) {
				if(hero!=this) {
					this.setEnemy(hero);
					hero.setEnemy(this);
				}
			}
			crazyMode = true;
		}
	}
	//	设置敌人
	public void setEnemy(Hero hero) {
		//	如果在合作列表中，删除
		if(this.coHeroList.contains(hero))
			this.coHeroList.remove(hero);
		//	如果在不在对抗列表中，添加
		if(!this.proHeroList.contains(hero))
			this.proHeroList.add(hero);
		//	如果在未发现列表中，删除
		if(this.notFoundList.contains(hero))
			this.notFoundList.remove(hero);
	}
	
	// 无聊但必要的set，get方法
	public void setLocation(double x, double y) {
		locationX = x;
		locationY = y;
	}

	public void setDestination(double x, double y) {
		destinationX = x;
		destinationY = y;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public ImageIcon getHeadImage() {
		return headImage;
	}

	public double getLocationX() {
		return locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isMoveFlag() {
		return moveFlag;
	}

	public void setMoveFlag(boolean moveFlag) {
		this.moveFlag = moveFlag;
	}
	public double getSeeRange() {
		return seeRange;
	}

	public void setSeeRange(double seeRange) {
		this.seeRange = seeRange;
	}

	public double getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(double attackRange) {
		this.attackRange = attackRange;
	}
	public int getGood() {
		return good;
	}
	public int getMiss() {
		return miss;
	}

	public boolean isInAttackRange() {
		return inAttackRange;
	}

	public void setInAttackRange(boolean inAttackRange) {
		this.inAttackRange = inAttackRange;
	}

	public int getHungry() {
		return hungry;
	}

	public int getMaxHungry() {
		return maxHungry;
	}

	public void setShockTick(int shockTick) {
		this.shockTick = shockTick;
	}

	public ImageIcon getVicImage() {
		return vicImage;
	}

	public String getVicClaim() {
		return vicClaim;
	}

	public int getWinTimes() {
		return winTimes;
	}

	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}

	public int getAppearTimes() {
		return appearTimes;
	}

	public void setAppearTimes(int appearTimes) {
		this.appearTimes = appearTimes;
	}
	
}
