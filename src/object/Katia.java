package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//good=40 hp=60
public class Katia extends Hero{
	//技能1 战地医生,有队友启动 持续时间：0  CD：400
	//全体队友回20血
	private static final int S1TIME = 0;
	private static final int S1CD = 400;
	
	private boolean s1Flag;	//技能可以用就是true，不能用就是false
	private boolean s1ING;	//技能进行中
	private int s1Clock;	//技能计时器
	
	public Katia() {
		super();
		this.name = "Katia";
		this.maxHp = 60;
		this.hp = 60;
		this.speed = 1;
		this.attackRange = 20;
		this.seeRange = 50;
		this.damage = 10;
		this.weaponCD = 20;
		this.headImage = new ImageIcon("image/kitaliy.png");
		this.vicImage = new ImageIcon("image/vic/kitaliy_vic.jpg");
		this.good = 50;
		this.miss = 0;
		
		s1Clock = 0;
		s1ING = false;
		s1Flag = true;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
			zhandiyisheng();
	}
	
	private void zhandiyisheng() {
		if(this.coHeroList.size()!=0&&s1Flag) {	//技能发动
			s1ING = true;
			s1Flag = false;
			for(Hero h:this.coHeroList){
				if(h.hp>=h.maxHp-20)
					h.hp=h.maxHp;
				else
					h.hp+=20;
			}
			Data.messageQ.offer(new Message("skill", this, "战地医生！"));
		}else if(s1ING){	//技能持续
			s1Clock++;
			if(s1Clock>=S1TIME) {
				s1ING = false;
				s1Clock = 0;
				this.speed=1;
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
