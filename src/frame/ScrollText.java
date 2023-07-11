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
	Document docs; //���ڲ�������
	
	public ScrollText() {
		super();
		// TODO Auto-generated constructor stub
		this.setEditable(false);	//���ò��ɱ༭
		scroll = new JScrollPane(this);
		scroll.setSize(970,200);
		this.setBackground(BLACKPRO);
		docs = this.getDocument();
	}
	
	public void SystemMessage(Message m) throws BadLocationException {
		SimpleAttributeSet a = new SimpleAttributeSet();
		StyleConstants.setForeground(a, AYONERED);
		docs.insertString(docs.getLength(), " [system]: ", a);
		
		
		//	���ݲ�ͬtype�����ʽ��ͬ
		switch (m.getType()) {
		case "sys":
			StyleConstants.setForeground(a, Color.white);//�ı���ɫ
			docs.insertString(docs.getLength(), m.getMain()+"\n", a);//��������
			
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
			docs.insertString(docs.getLength()," "+ "����"+"\n", a);
			
			break;
		
		case "eat":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength()," "+ "������һ�ٴ��"+"\n", a);
			
			break;
		
		case "crazy":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength()," "+ "��Ϊ�����ȹ��ͣ�ʧȥ�����ԣ�����"+" ", a);
			
			StyleConstants.setForeground(a, Color.red);
			docs.insertString(docs.getLength(),"��������Ϊ��"+"\n", a);
			
			break;
		case "skill":
			StyleConstants.setForeground(a, m.getHeroA().getColor());
			docs.insertString(docs.getLength(), m.getHeroA().getName(), a);
			
			StyleConstants.setForeground(a, Color.white);
			docs.insertString(docs.getLength()," "+ "�����˼���: ", a);
			
			StyleConstants.setForeground(a, SKYBLUE);
			docs.insertString(docs.getLength(),m.getMain()+"\n", a);
			
			break;
			
		case "sys_warning":
			StyleConstants.setForeground(a, Color.red);//�ı���ɫ
			docs.insertString(docs.getLength(), m.getMain()+"\n", a);//��������
			
			break;
		default:
			break;
		}
		this.setCaretPosition(docs.getLength());
		
	}
}
