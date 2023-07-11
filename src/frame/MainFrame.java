package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import object.Hero;
import util.Data;
import util.Message;


public class MainFrame extends JFrame{
	//configure
	public static final int MAPIN_WIDTH = 950;
	public static final int MAPIN_HEIGHT = 600;
	
	public static final int CPNANEL_WIDTH = 250;
	public static final int CPNANEL_HEIGHT = 800;
	
	public static final int SCROLLTEXT_WIDTH = 970;//�ѹ���������ȥ����̫����
	public static final int SCROLLTEXT_HEIGHT = 200;
	
	public static final Color GRAY = new Color(105,105,105);
	public static final Color BLACKPRO = new Color(45,45,45);
	
	private JPanel mapIn;	//��ͼ����
	private JPanel cPanelIn;	//��ɫ��������
	private JPanel scrollIn;	//���ֹ�������
	
	private LinkedList<CharacterPanel> cPanelList;	//�����ǩ��
	
	private ScrollText scrollText;	//����
	private MapPanel map; //��ͼ
	private JLabel mapbg; //��ͼ����

	public MainFrame() throws HeadlessException {
		super("New World");
		// TODO Auto-generated constructor stub
		this.setLayout(null);	//���Բ���
		this.setBounds(200, 100, 1213, 840);	//���ڴ�С1200*800 = 1187*760+(�߿�)
		//�������ʼ��
		cPanelList = new LinkedList<CharacterPanel>();
		InitMapin();
		InitCPanelin();
		InitScrollText();
		this.setVisible(true);
		//��д�ر�
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
		try {
			scrollText.SystemMessage(new Message("sys", "�������"));
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			System.out.println("���������");
			e1.printStackTrace();
		}
	}
	// ��ʼ����ͼ����
	private void InitMapin(){
		mapIn = new JPanel();
		mapIn.setBackground(Color.white);
		mapIn.setLocation(250, 0);
		mapIn.setSize(MAPIN_WIDTH, MAPIN_HEIGHT);
		mapIn.setLayout(null);
		this.add(mapIn);
		
		map = new MapPanel();
		mapIn.add(map);

		mapbg = new JLabel(Data.MAP);
		mapbg.setSize(MAPIN_WIDTH, MAPIN_HEIGHT);
		mapbg.setOpaque(false);
		mapIn.add(mapbg);
		
		
	}
	// ��ʼ����ɫ��ͼ����
	private void InitCPanelin(){
		cPanelIn = new JPanel();
		cPanelIn.setBackground(Color.black);
		cPanelIn.setLocation(0, 0);
		cPanelIn.setSize(CPNANEL_WIDTH, CPNANEL_HEIGHT);
		cPanelIn.setLayout(null);
		this.add(cPanelIn);
		// ��data�е�Ӣ�ۿ����Cpanelin
		for (Hero hero : Data.heroList) {
			this.addCPanel(new CharacterPanel(hero));
		}
	}
	// ��ʼ����������
	private void InitScrollText() {
		scrollIn = new JPanel();
		scrollIn.setLocation(250, 600);
		scrollIn.setSize(SCROLLTEXT_WIDTH, SCROLLTEXT_HEIGHT);
		this.add(scrollIn);
		
		scrollText = new ScrollText();
		scrollIn.setLayout(null);
		scrollIn.add(scrollText.scroll);
	}
	// ���ӽ�ɫ����Ľ�ɫ�ķ���
	public void addCPanel(CharacterPanel cPanel) {
		cPanelIn.add(cPanel);
		cPanelList.add(cPanel);
	}
	public void upDate() {
		this.repaint();
		map.repaint();
		for (CharacterPanel characterPanel : cPanelList) {
			characterPanel.upDate();
		}
	}
	//	�����
	public void removeUpData() {
		
	}
	//	��ӡ������������
	public void myPaint(Message message) {
		try {
			scrollText.SystemMessage(message);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			System.out.println("���������");
			e.printStackTrace();
		}
	}
	//	�������
	public void result(Hero hero) {
		this.mapIn.remove(map);
		this.mapIn.remove(mapbg);
		JLabel vicImage = new JLabel(hero.getVicImage());
		vicImage.setSize(MAPIN_WIDTH, MAPIN_HEIGHT);
		
		JLabel vicClaim = new JLabel(hero.getVicClaim());
		vicClaim.setForeground(Color.white);
		vicClaim.setFont(new Font("΢���ź�", 0, 20));
		vicClaim.setBounds(MAPIN_WIDTH/2, MAPIN_HEIGHT/3*2, 200, 40);
		
		JLabel vicTimes = new JLabel("�Ҵ������"+Integer.toString(hero.getWinTimes()));
		vicTimes.setForeground(Color.white);
		vicTimes.setFont(new Font("΢���ź�", 0, 20));
		vicTimes.setBounds(MAPIN_WIDTH/5*3, MAPIN_HEIGHT/3, 200, 40);
		
		JLabel appearTimes = new JLabel("���������"+Integer.toString(hero.getAppearTimes()));
		appearTimes.setForeground(Color.white);
		appearTimes.setFont(new Font("΢���ź�", 0, 20));
		appearTimes.setBounds(MAPIN_WIDTH/5*2, MAPIN_HEIGHT/3, 200, 40);
		
		this.mapIn.add(vicClaim);
		this.mapIn.add(vicTimes);
		this.mapIn.add(appearTimes);
		this.mapIn.add(vicImage);
	}
}
