package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public class Anton extends Hero{
	//技能1 冥想,随时启动 持续时间：80  CD：300
	private static final int S1TIME = 80;
	private static final int S1CD = 300;

	private boolean s1Flag;	//技能可以用就是true，不能用就是false
	private boolean s1ING;	//技能进行中
	private int s1Clock;	//技能计时器
	
	public Anton() {
		super();
		this.name = "Anton";
		this.maxHp = 80;
		this.hp = 80;
		this.speed = 1;
		this.attackRange = 20;
		this.seeRange = 50;
		this.damage = 10;
		this.weaponCD = 20;
		this.headImage = new ImageIcon("image/ando.png");
		this.vicImage = new ImageIcon("image/vic/ando_vic.jpg");
		this.good = 25;
		this.miss = 0;
		this.vicClaim ="too young,too naive.";
		
		s1Clock = 0;
		s1ING = false;
		s1Flag = true;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		mingxiang();
	}
	
	private void mingxiang() {
		if(s1Flag) {	//技能发动
			s1ING = true;
			s1Flag = false;
			Data.messageQ.offer(new Message("skill", this, "冥想！"));
		}else if(s1ING){	//技能持续
			s1Clock++;
			if(this.hp<=this.maxHp-2)
				this.hp+=1.5;
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
