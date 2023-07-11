package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//加速回血 hp=120 good=0
public class Arica extends Hero{
	//技能1（S1）	死亡缠绕，被攻击启动  持续时间：0  CD：400
	//所有敌对扣血
	private static final int S1TIME = 0;
	private static final int S1CD = 400;

	private boolean s1Flag;	//技能可以用就是true，不能用就是false
	private boolean s1ING;	//技能进行中
	private int s1Clock;	//技能计时器
	
	public Arica() {
		super();
		this.name = "Arica";
		this.maxHp = 120;
		this.hp = 120;
		this.speed = 1;
		this.attackRange = 20;
		this.seeRange = 50;
		this.damage = 10;
		this.weaponCD = 20;
		this.headImage = new ImageIcon("image/arica.png");
		this.vicImage = new ImageIcon("image/vic/arica_vic.jpg");
		this.reHp = 1.5;
		
		this.good = 25;
		this.miss = 0;
		this.vicClaim ="狭路相逢勇者胜。";
		
		s1Clock = 0;
		s1ING = false;
		s1Flag = true;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		siwangchanrao();
	}
	
	private void siwangchanrao() {
		if(this.isInAttackRange()&&s1Flag) {	//技能发动
			s1ING = true;
			for(Hero h:this.proHeroList){
				h.hp-=30;
			}
			s1Flag = false;
			Data.messageQ.offer(new Message("skill", this, "死亡缠绕！"));
		}else if(s1ING){	//技能持续
			s1Clock++;
			if(s1Clock>=S1TIME) {
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
