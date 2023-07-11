package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//���ٻ�Ѫ hp=120 good=0
public class Arica extends Hero{
	//����1��S1��	�������ƣ�����������  ����ʱ�䣺0  CD��400
	//���ежԿ�Ѫ
	private static final int S1TIME = 0;
	private static final int S1CD = 400;

	private boolean s1Flag;	//���ܿ����þ���true�������þ���false
	private boolean s1ING;	//���ܽ�����
	private int s1Clock;	//���ܼ�ʱ��
	
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
		this.vicClaim ="��·�������ʤ��";
		
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
		if(this.isInAttackRange()&&s1Flag) {	//���ܷ���
			s1ING = true;
			for(Hero h:this.proHeroList){
				h.hp-=30;
			}
			s1Flag = false;
			Data.messageQ.offer(new Message("skill", this, "�������ƣ�"));
		}else if(s1ING){	//���ܳ���
			s1Clock++;
			if(s1Clock>=S1TIME) {
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
