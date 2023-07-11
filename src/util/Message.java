package util;

import object.Hero;

public class Message {
	private String type;
	private Hero heroA;
	private Hero heroB;
	private String main;
	private String prep;
	//	type=sys output:main
	//	type=h2h output:heroA + prep + heroB + main
	
	//	不涉及任何单位的系统提示
	public Message(String type, String main) {
		super();
		this.type = type;
		this.main = main;
	}
	//	英雄自己的状态信息
	public Message(String type, Hero heroA) {
		super();
		this.type = type;
		this.heroA = heroA;
	}
	//	释放技能的信息
	public Message(String type, Hero heroA, String skill) {
		super();
		this.type = type;
		this.heroA = heroA;
		this.main = skill;
	}
	//	英雄之间的信息
	public Message(String type, Hero heroA, Hero heroB, String prep, String main) {
		super();
		this.type = type;
		this.heroA = heroA;
		this.heroB = heroB;
		this.prep = prep;
		this.main = main;
	}
	public String getType() {
		return type;
	}
	public Hero getHeroA() {
		return heroA;
	}
	public Hero getHeroB() {
		return heroB;
	}
	public String getMain() {
		return main;
	}
	public String getPrep() {
		return prep;
	}
}
