package util;

import frame.MainFrame;

public class GameLaunch {
	public static void main(String[] args) {
		Data.init();
		MainFrame mainFrame = new MainFrame();	//frame����
		new Thread(new GameMainThread(mainFrame)).start();	//��Ϸ���߳�����
		new Thread(new BgmThread()).start();	//��������
	}
}
