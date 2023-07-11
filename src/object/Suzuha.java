package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public class Suzuha extends Hero{
	//	命运石之门的打工战士
	//	技能1（S1）：透明立场 被攻击时启动，miss变为100 持续时间：25	cd：100
	private static final int S1TIME = 25;
	private static final int S1CD = 100;
	private static final int DEFAULT_MISS = 30;
	private static final int SKILL_MISS = 100;
	
	private boolean s1Flag;	//技能可以用就是true，不能用就是false
	private boolean s1ING;	//技能进行中
	private int s1Clock;	//技能计时器
	
	public Suzuha() {
		super();
		// TODO Auto-generated constructor stub
		this.name = "Suzuha";
		this.maxHp = 80;
		this.hp = 80;
		this.speed = 1.1;
		this.attackRange = 30;
		this.seeRange = 60;
		this.damage = 6;
		this.weaponCD = 12;
		this.headImage = new ImageIcon("image//suzuha.jpg");
		this.vicImage = new ImageIcon("image//vic//suzuha_vic.jpg");
		this.good = 15;
		this.miss = DEFAULT_MISS;
		this.reHp = 0.5;
		this.maxHungry = 3;
		this.vicClaim = "不过如此";
		
		s1Flag = true;
		s1Clock = 0;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		transparentFied();
	}
	//	透明立场  cd:100 持续时间：10
	private void transparentFied() {
		if(this.isInAttackRange()&&s1Flag) {	//技能发动
			s1ING = true;
			this.miss = SKILL_MISS;
			s1Flag = false;
			Data.messageQ.offer(new Message("skill", this, "透明立场"));
		}else if(s1ING){	//技能持续
			s1Clock++;
			if(s1Clock==S1TIME) {
				this.miss = DEFAULT_MISS;
				s1ING = false;
				s1Clock = 0;
			}
		}else if(!s1Flag) {	//技能结束，转cd
			s1Clock++;
			if(s1Clock==S1CD){
				s1Clock = 0;
				s1Flag = true;
			}
		}
	}
	
}
