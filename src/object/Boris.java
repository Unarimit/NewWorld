package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;
//speed=1.5f miss=30
public class Boris extends Hero{
		//����1 ���ط�ɱ,����30Ѫ,�ұ��������� ����ʱ�䣺40  CD��400
		//�˺���Ϊ20 ���ٷ���
		private static final int S1TIME = 40;
		private static final int S1CD = 400;
		
		private boolean s1Flag;	//���ܿ����þ���true�������þ���false
		private boolean s1ING;	//���ܽ�����
		private int s1Clock;	//���ܼ�ʱ��
		
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
		this.vicClaim ="�ҵĴ����Ѽ��������ˡ�";
		
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		juedifansha();
	}
	private void juedifansha() {
		if(this.hp<=30&&s1Flag) {	//���ܷ���
			s1ING = true;
			s1Flag = false;
			this.damage=20;
			this.weaponCD=10;
			Data.messageQ.offer(new Message("skill", this, "���ط�ɱ��"));
		}else if(s1ING){	//���ܳ���
			s1Clock++;
			if(s1Clock==S1TIME) {
				s1ING = false;
				s1Clock = 0;
				this.damage=10;
				this.weaponCD=20;
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
