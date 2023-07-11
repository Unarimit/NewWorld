package frame;

import java.awt.Color;

import javax.swing.*;
import javax.swing.text.*;

import object.Hero;
import util.Message;

public class ScrollText extends JTextPane{
	//my color
	private static final Color BLACKPRO = new Color(45,45,45);
	private static final Color AYONERED = new Color(255,102,102);
	public static final Color SKYBLUE = new Color(0,191,255);
	JScrollPane scroll;
	Document docs; //用于插入文字
	
	public ScrollText() {
		super();
		// TODO Auto-generated constructor stub
		this.setEditable(false);	//设置不可编辑
		scroll = new JScrollPane(this);
		scroll.setSize(970,200);
		this.setBackground(BLACKPRO);
		docs = this.getDocument();
	}
	
	public void SystemMessage(Message m) throws BadLocationException {
		SimpleAttributeSet a = new SimpleAttributeSet();
		StyleConstants.setForeground(a, AYONERED);
		docs.insertString(docs.getLength(), " [system]: ", a);
		
		
		//	根据不同type输出方式不同
		switch (m.getType()) {
		case "sys":
			StyleConstants.setForeground(a, Color.white);//改变颜色
			docs.insertString(docs.getLength(), m.getMain()+"\n", a);//插入文字
			
			break;
		case "h2h":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength(), " "+m.getPrep(), a);
			
			StyleConstants.setForeground(a, m.getHeroB().getColor());
			docs.insertString(docs.getLength()," "+ m.getHeroB().getName(), a);
			
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength()," "+ m.getMain()+"\n", a);
			
			break;
		case "die":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			StyleConstants.setForeground(a, Color.red);
			docs.insertString(docs.getLength()," "+ "死亡"+"\n", a);
			
			break;
		
		case "eat":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength()," "+ "享用了一顿大餐"+"\n", a);
			
			break;
		
		case "crazy":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength()," "+ "因为饥饿度过低，失去了理性，现在"+" ", a);
			
			StyleConstants.setForeground(a, Color.red);
			docs.insertString(docs.getLength(),"以所有人为敌"+"\n", a);
			
			break;
		case "skill":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength()," "+ "发动了技能: ", a);
			
			StyleConstants.setForeground(a, SKYBLUE);
			docs.insertString(docs.getLength(),m.getMain()+"\n", a);
			
			break;
			
		case "sys_warning":
			StyleConstants.setForeground(a, Color.red);//改变颜色
			docs.insertString(docs.getLength(), m.getMain()+"\n", a);//插入文字
			
			break;
		default:
			break;
		}
		this.setCaretPosition(docs.getLength());
		
	}
}
