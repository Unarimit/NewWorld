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
	
	//	���漰�κε�λ��ϵͳ��ʾ
	public Message(String type, String main) {
		super();
		this.type = type;
		this.main = main;
	}
	//	Ӣ���Լ���״̬��Ϣ
	public Message(String type, Hero heroA) {
		super();
		this.type = type;
		this.heroA = heroA;
	}
	//	�ͷż��ܵ���Ϣ
	public Message(String type, Hero heroA, String skill) {
		super();
		this.type = type;
		this.heroA = heroA;
		this.main = skill;
	}
	//	Ӣ��֮�����Ϣ
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
