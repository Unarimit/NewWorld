package object;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public abstract class Hero {
	private static final int RAND_MOVE_RADIUS = 100;
	private static final double RE_HP_SPEED = 10;	//ÿ����clock�ָ�һ������
	private static final double EAT_FOOD_DISTANCE = 5;	//��ʳ��ľ���
	private static final String DEFAULT_CLAIM = "��Ӯ��";
	
	//	��ͬ�಻ͬ������
	protected int maxHp; // �������ֵ
	protected double hp; // ����ֵ
	protected double reHp; // �����ָ�����
	protected String name; // ����
	protected double speed; // Ӣ���ٶ�
	protected double seeRange;	//��Ұ����
	protected double attackRange;	//��������
	protected double damage;	//������ɵ��˺�
	protected ImageIcon headImage; // ͷ��
	protected ImageIcon vicImage; //ʤ��չʾ
	protected int weaponCD;	//������ȴʱ��
	protected int good;	//���ƶȣ�ȡֵ0-50��Խ��Խ���׺��˺������ж�ʱ��ȡ�����˵����ƶȵĺͣ����һ��100�����������С������֮�ͣ��ͻ����
	protected int miss;	//�����ʣ�ȡֵ0-100��Խ��Խ�������ܹ���
	protected int maxHungry;	//Ӣ�۵�������ͼ�����
	protected String vicClaim;
	
	private Color color; // ��ʾ��ɫ
	private double locationX; // Ŀǰ���ڵ�X
	private double locationY; // Ŀǰ���ڵ�Y
	private double destinationX; // �赽��ص�X
	private double destinationY; // �赽��ص�Y
	private boolean moveFlag; // �ƶ����
	private Food targetFood;	//Ҫ�Ե�ʳ��
	protected  LinkedList<Hero> coHeroList;	//����Ӣ���б�
	protected  LinkedList<Hero> proHeroList;	//�Կ�Ӣ���б�
	private LinkedList<Hero> notFoundList;	//δ����Ӣ���б�
	protected LinkedList<Hero> canAttackList;	//���Թ�������Ӣ���б�. ��Ϊprotected����, ���������
	private int hungry;	//Ӣ�۵ļ����̶ȣ� Խ��Խ��
	private boolean crazyMode;	//��ģʽ��־��
	private boolean inAttackRange;	//�Ƿ��ڱ��˹�����Χ���ģ������Ը��µ�ʱ���������flag
	private int shockTick;	//ѣ�μ�ʱ��
	
	private int winTimes;	//ʤ������
	private int appearTimes;	//���ִ���
	Random rand = new Random();

	public Hero() {
		super();
		this.reHp = 0.5;	//ÿtick�ظ�������Ĭ��Ϊ0.5
		this.maxHungry = 3;	//��������²��ø�
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
	//	�ڶ�ȡ��浵�����
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

	//	������Ϊ
	protected void basicAction() {
		if (moveFlag == true) {
			move();
			checkDestinationLegal();
			//	������ı�Ϊ3ʱ����Ŀ�ĵض�Ϊ�з�Ӣ��
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
	//	״̬���
	protected void checkStatu() {
		checkHungry();
	}
	//	����hero������Ϊ
	abstract public void action();
	//	��Ϊ�����ջ�һЩ����
	protected void endAction() {
		inAttackRange = false;
	}
	
	//	����ƶ�����
	protected void isNeedMove() {
		if (rand.nextInt(100) > 97) {
			moveFlag = true;
			destinationX = locationX + rand.nextInt(RAND_MOVE_RADIUS) - 50;
			destinationY = locationY + rand.nextInt(RAND_MOVE_RADIUS) - 50;
		}
	}

	//	��Ŀ�ĵ�ֱ���ƶ�
	public void move() {
		// 1.��y�ϴ�ֱ�ƶ�
		// 2.��x�ϴ�ֱ�ƶ�
		// 3.б���ƶ�
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
	//	�жϵ�λ�Ƿ���빥����Χ
	protected boolean isAttack(Hero he) {
		if(betweenDistance(he)<=this.attackRange)
			return true;
		else
			return false;
	}
	//	�жϵ�λ�Ƿ������Ұ��Χ
	protected boolean isSee(Hero he) {
		if(betweenDistance(he)<=this.seeRange)
			return true;
		else
			return false;
	}
	//	��������һ��hero�ľ���
	protected double betweenDistance(Hero he) {
		return Math.sqrt(Math.pow((this.locationX - he.getLocationX()), 2) + Math.pow((this.locationY - he.getLocationY()), 2));
	}
	//	������һ��food�ľ���
	protected double betweenDistanceFood(Food he) {
		return Math.sqrt(Math.pow((this.locationX - he.getLocationX()), 2) + Math.pow((this.locationY - he.getLocationY()), 2));
	}
	//	���Ŀ�ĵغϷ���
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
	//	����Ӣ��
	private void findHero() {
		for (Hero hero : notFoundList) {
			if(isSee(hero)) {
				if(rand.nextInt(100)<(this.good+hero.getGood())) {
					multico(hero);
					Data.messageQ.offer(new Message("h2h", this, hero, "��", "���ڻ������"));
				}else {
					multipro(hero);
					Data.messageQ.offer(new Message("h2h", this, hero, "��", "���ڻ���Ϊ��"));
				}
				break;
			}
		}
	}
	//	����Ƿ��п��Թ�����Ӣ�ۣ�����ɹ����б���������һ�������
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
	//	����Ӣ��
	protected void attack(Hero he) {
		he.setInAttackRange(true);
		if(rand.nextInt(100)>he.getMiss()) {
			he.setHp(he.getHp()-this.damage);
			Data.messageQ.offer(new Message("h2h", this, he, "��", "�����"+this.damage+"�˺�"));
		}else {
			Data.messageQ.offer(new Message("h2h", he, this, "����������", "���˺�"));
		}
	}
	//	����Ϊ�У���ɾ��δ����Ӣ�۱�Ķ�Ӧ��
	private void multipro(Hero he) {
		this.proHeroList.add(he);
		this.notFoundList.remove(he);
		he.beMultipro(this);
	}
	public void beMultipro(Hero he) {
		this.proHeroList.add(he);
		this.notFoundList.remove(he);
	}
	//	�����������ɾ��δ����Ӣ�۱�Ķ�Ӧ��
	private void multico(Hero he) {
		this.coHeroList.add(he);
		this.notFoundList.remove(he);
		he.beMultico(this);
	}
	public void beMultico(Hero he) {
		this.coHeroList.add(he);
		this.notFoundList.remove(he);
	}
	//	����Ӣ������ʱ����
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
	//	Ѱ�������ʳ���ʳ��ս�ʱ����
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
	//	���ʳ���ܲ��ܳԵ����ܳԵ��ͳ�
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
	//	��鼢���ȣ�������ھͽ����ģʽ
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
	//	���õ���
	public void setEnemy(Hero hero) {
		//	����ں����б��У�ɾ��
		if(this.coHeroList.contains(hero))
			this.coHeroList.remove(hero);
		//	����ڲ��ڶԿ��б��У����
		if(!this.proHeroList.contains(hero))
			this.proHeroList.add(hero);
		//	�����δ�����б��У�ɾ��
		if(this.notFoundList.contains(hero))
			this.notFoundList.remove(hero);
	}
	
	// ���ĵ���Ҫ��set��get����
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
