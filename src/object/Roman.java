package object;

import javax.swing.ImageIcon;
//maxhp=150 seeRange=70 damage=20 good=0 miss=30 speed=1.5f
public class Roman extends Hero{
	public Roman() {
		super();
		this.name = "Roman";
		this.maxHp = 150;
		this.hp = 150;
		this.speed = 1.5;
		this.attackRange = 20;
		this.seeRange = 70;
		this.damage = 20;
		this.weaponCD = 20;
		this.headImage = new ImageIcon("image/roman.png");
		this.vicImage = new ImageIcon("image/vic/roman_vic.jpg");
		this.good = -20;
		this.miss = 30;
		this.vicClaim ="人不为己，天诛地灭！";
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

}
