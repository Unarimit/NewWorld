package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//maxhp=120 good=30
public class Pavle extends Hero{
	//技能1 越野冠军,随时启动 持续时间：100  CD：400
	private static final int S1TIME = 100;
	private static final int S1CD = 400;

	private boolean s1Flag;	//技能可以用就是true，不能用就是false
	private boolean s1ING;	//技能进行中
	private int s1Clock;	//技能计时器
	
	public Pavle() {
		super();
		this.name = "Pavle";
		this.maxHp = 120;
		this.hp = 120;
		this.speed = 1;
		this.attackRange = 20;
		this.seeRange = 50;
		this.damage = 10;
		this.weaponCD = 20;
		this.headImage = new ImageIcon("image//pafoli.png");
		this.vicImage = new ImageIcon("image//vic//pafoli_vic.jpg");
		this.good = 30;
		this.miss = 0;
		this.vicClaim ="还好我跑得快。。";
		
		s1Clock = 0;
		s1ING = false;
		s1Flag = true;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		yueyeguanjun();
	}
	
	private void yueyeguanjun() {
		if(s1Flag) {	//技能发动
			s1ING = true;
			s1Flag = false;
			this.speed=3;
			Data.messageQ.offer(new Message("skill", this, "越野冠军！！"));
		}else if(s1ING){	//技能持续
			s1Clock++;
			if(s1Clock==S1TIME) {
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
