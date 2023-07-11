package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//maxhp=120 good=30
public class Pavle extends Hero{
	//����1 ԽҰ�ھ�,��ʱ���� ����ʱ�䣺100  CD��400
	private static final int S1TIME = 100;
	private static final int S1CD = 400;

	private boolean s1Flag;	//���ܿ����þ���true�������þ���false
	private boolean s1ING;	//���ܽ�����
	private int s1Clock;	//���ܼ�ʱ��
	
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
		this.vicClaim ="�������ܵÿ졣��";
		
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
		if(s1Flag) {	//���ܷ���
			s1ING = true;
			s1Flag = false;
			this.speed=3;
			Data.messageQ.offer(new Message("skill", this, "ԽҰ�ھ�����"));
		}else if(s1ING){	//���ܳ���
			s1Clock++;
			if(s1Clock==S1TIME) {
				s1ING = false;
				s1Clock = 0;
				this.speed=1;
			}
		}else if(!s1Flag) {	//���ܽ�����תcd
			s1Clock++;
			if(s1Clock==S1CD) {
				s1Flag = true;
				s1Clock = 0;
			}
		}
	}

}
