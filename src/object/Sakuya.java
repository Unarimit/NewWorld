package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public class Sakuya extends Hero {
	//技能1（S1）	The World时间停止 被攻击时启动  持续时间：40  CD：400
	//实现为 技能发动把所有人人眩晕40tick
	private static final int S1TIME = 40;
	private static final int S1CD = 400;

	private boolean s1Flag;	//技能可以用就是true，不能用就是false
	private boolean s1ING;	//技能进行中
	private int s1Clock;	//技能计时器
	
	public Sakuya() {
		super();
		// TODO Auto-generated constructor stub
		this.name = "Sakuya";
		this.maxHp = 120;
		this.hp = 120;
		this.speed = 1.3;
		this.attackRange = 25;
		this.seeRange = 50;
		this.damage = 15;
		this.weaponCD = 25;
		this.headImage = new ImageIcon("image//sakuya.jpg");
		this.vicImage = new ImageIcon("image//vic//sakuya_vic.jpg");
		this.good = 25;
		this.miss = 20;
		this.reHp = 0.5;
		this.maxHungry = 3;
		this.vicClaim = "The World!!!";
		
		s1Clock = 0;
		s1ING = false;
		s1Flag = true;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		theWorld();
	}
	private void theWorld() {
		if(this.isInAttackRange()&&s1Flag) {	//技能发动
			s1ING = true;
			for (Hero hero : Data.heroList) {
				if(!hero.equals(this))
					hero.setShockTick(S1TIME);
			}
			s1Flag = false;
			Data.messageQ.offer(new Message("skill", this, "The World!!"));
		}else if(s1ING){	//技能持续
			s1Clock++;
			if(s1Clock==S1TIME) {
				s1ING = false;
				s1Clock = 0;
			}
		}else if(!s1Flag) {	//技能结束，转cd
			s1Clock++;
			if(s1Clock==S1CD) {
				s1Flag = true;
				s1Clock = 0;
			}
		}
	}

}
