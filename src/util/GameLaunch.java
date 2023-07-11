package util;

import frame.MainFrame;

public class GameLaunch {
	public static void main(String[] args) {
		Data.init();
		MainFrame mainFrame = new MainFrame();	//frame载入
		new Thread(new GameMainThread(mainFrame)).start();	//游戏主线程启动
		new Thread(new BgmThread()).start();	//来点音乐
	}
}
