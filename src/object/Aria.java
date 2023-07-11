package object;

import javax.swing.ImageIcon;

import util.Data;
import util.Message;

public class Aria extends Hero {
	// ����1 תתת�������й�����Χ���˽��й��������ظ�12��hp���޳���ʱ��
	// ʵ�ַ���
	private static final int S1CD = 70;
	private static final double S1DAMEGE = 20;
	private static final double S1RECUE = 12;
	private static final double DEFALULT_DAMEGE = 10;

	private boolean s1Flag; // ���ܿ����þ���true�������þ���false
	private int s1Clock; // ���ܼ�ʱ��

	public Aria() {
		super();
		// TODO Auto-generated constructor stub
		this.name = "Aria";
		this.maxHp = 100;
		this.hp = 100;
		this.speed = 1;
		this.attackRange = 30;
		this.seeRange = 50;
		this.damage = DEFALULT_DAMEGE;
		this.weaponCD = 15;
		this.headImage = new ImageIcon("image//aria.jpg");
		this.vicImage = new ImageIcon("image//vic//aria_vic.jpg");
		this.good = 15;
		this.miss = 15;
		this.reHp = 0.5;
		this.maxHungry = 3;
		this.vicClaim = "��˲п������..";

		s1Flag = true;
		s1Clock = 0;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		bulletHurricane();
	}
	//����1 תתת
	private void bulletHurricane() {
		if ((!this.canAttackList.isEmpty()) && s1Flag) {	//����е����ڹ�����Χ�ڣ��Ҽ��ܿ���ʹ��
			this.damage = S1DAMEGE;
			for (Hero hero : canAttackList) {
				this.attack(hero);
			}
			this.hp += S1RECUE;
			this.damage = DEFALULT_DAMEGE;
			Data.messageQ.offer(new Message("skill", this, "�ӵ�����"));
			s1Flag = false;
		}else if(s1Flag == false){	//����CD
			s1Clock++;
			if(s1Clock==S1CD) {
				s1Clock = 0;
				s1Flag = true;
			}
		}
	}

}
