package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//good=40 hp=60
public class Katia extends Hero{
	//����1 ս��ҽ��,�ж������� ����ʱ�䣺0  CD��400
	//ȫ����ѻ�20Ѫ
	private static final int S1TIME = 0;
	private static final int S1CD = 400;
	
	private boolean s1Flag;	//���ܿ����þ���true�������þ���false
	private boolean s1ING;	//���ܽ�����
	private int s1Clock;	//���ܼ�ʱ��
	
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
		if(this.coHeroList.size()!=0&&s1Flag) {	//���ܷ���
			s1ING = true;
			s1Flag = false;
			for(Hero h:this.coHeroList){
				if(h.hp>=h.maxHp-20)
					h.hp=h.maxHp;
				else
					h.hp+=20;
			}
			Data.messageQ.offer(new Message("skill", this, "ս��ҽ����"));
		}else if(s1ING){	//���ܳ���
			s1Clock++;
			if(s1Clock>=S1TIME) {
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
