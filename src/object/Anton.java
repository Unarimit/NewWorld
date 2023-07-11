package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public class Anton extends Hero{
	//����1 ڤ��,��ʱ���� ����ʱ�䣺80  CD��300
	private static final int S1TIME = 80;
	private static final int S1CD = 300;

	private boolean s1Flag;	//���ܿ����þ���true�������þ���false
	private boolean s1ING;	//���ܽ�����
	private int s1Clock;	//���ܼ�ʱ��
	
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
		if(s1Flag) {	//���ܷ���
			s1ING = true;
			s1Flag = false;
			Data.messageQ.offer(new Message("skill", this, "ڤ�룡"));
		}else if(s1ING){	//���ܳ���
			s1Clock++;
			if(this.hp<=this.maxHp-2)
				this.hp+=1.5;
			if(s1Clock==S1TIME) {
				s1ING = false;
				s1Clock = 0;
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
