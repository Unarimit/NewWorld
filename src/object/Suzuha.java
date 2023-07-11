package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public class Suzuha extends Hero{
	//	����ʯ֮�ŵĴ�սʿ
	//	����1��S1����͸������ ������ʱ������miss��Ϊ100 ����ʱ�䣺25	cd��100
	private static final int S1TIME = 25;
	private static final int S1CD = 100;
	private static final int DEFAULT_MISS = 30;
	private static final int SKILL_MISS = 100;
	
	private boolean s1Flag;	//���ܿ����þ���true�������þ���false
	private boolean s1ING;	//���ܽ�����
	private int s1Clock;	//���ܼ�ʱ��
	
	public Suzuha() {
		super();
		// TODO Auto-generated constructor stub
		this.name = "Suzuha";
		this.maxHp = 80;
		this.hp = 80;
		this.speed = 1.1;
		this.attackRange = 30;
		this.seeRange = 60;
		this.damage = 6;
		this.weaponCD = 12;
		this.headImage = new ImageIcon("image//suzuha.jpg");
		this.vicImage = new ImageIcon("image//vic//suzuha_vic.jpg");
		this.good = 15;
		this.miss = DEFAULT_MISS;
		this.reHp = 0.5;
		this.maxHungry = 3;
		this.vicClaim = "�������";
		
		s1Flag = true;
		s1Clock = 0;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		transparentFied();
	}
	//	͸������  cd:100 ����ʱ�䣺10
	private void transparentFied() {
		if(this.isInAttackRange()&&s1Flag) {	//���ܷ���
			s1ING = true;
			this.miss = SKILL_MISS;
			s1Flag = false;
			Data.messageQ.offer(new Message("skill", this, "͸������"));
		}else if(s1ING){	//���ܳ���
			s1Clock++;
			if(s1Clock==S1TIME) {
				this.miss = DEFAULT_MISS;
				s1ING = false;
				s1Clock = 0;
			}
		}else if(!s1Flag) {	//���ܽ�����תcd
			s1Clock++;
			if(s1Clock==S1CD){
				s1Clock = 0;
				s1Flag = true;
			}
		}
	}
	
}
