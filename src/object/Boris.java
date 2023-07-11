package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//speed=1.5f miss=30
public class Boris extends Hero{
		//技能1 绝地反杀,低于30血,且被攻击启动 持续时间：40  CD：400
		//伤害加为20 攻速翻倍
		private static final int S1TIME = 40;
		private static final int S1CD = 400;
		
		private boolean s1Flag;	//技能可以用就是true，不能用就是false
		private boolean s1ING;	//技能进行中
		private int s1Clock;	//技能计时器
		
	public Boris() {
		super();
		this.name = "Boris";
		this.maxHp = 100;
		this.hp = 100;
		this.speed = 1.5f;
		this.attackRange = 20;
		this.seeRange = 50;
		this.damage = 10;
		this.weaponCD = 20;
		this.headImage = new ImageIcon("image/boris.png");
		this.vicImage = new ImageIcon("image/vic/boris_vic.jpg");
		this.good = 25;
		this.miss = 30;
		this.vicClaim ="我的大刀早已饥渴难耐了。";
		
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		juedifansha();
	}
	private void juedifansha() {
		if(this.hp<=30&&s1Flag) {	//技能发动
			s1ING = true;
			s1Flag = false;
			this.damage=20;
			this.weaponCD=10;
			Data.messageQ.offer(new Message("skill", this, "绝地反杀！"));
		}else if(s1ING){	//技能持续
			s1Clock++;
			if(s1Clock==S1TIME) {
				s1ING = false;
				s1Clock = 0;
				this.damage=10;
				this.weaponCD=20;
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
