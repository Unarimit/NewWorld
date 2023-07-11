package object;

import javax.swing.ImageIcon;
//seeRange=80 attackRange=50 hp=60
public class Marine extends Hero{
	public Marine() {
		super();
		this.name = "Marine";
		this.maxHp = 60;
		this.hp = 60;
		this.speed = 1;
		this.attackRange = 50;
		this.seeRange = 80;
		this.damage = 10;
		this.weaponCD = 20;
		this.headImage = new ImageIcon("image//marine.png");
		this.vicImage = new ImageIcon("image//vic//marine_vic.jpg");
		this.good = 25;
		this.miss = 0;
		this.vicClaim ="在狙击镜里我看到了你的一生。。";
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

}
