package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class BgmThread implements Runnable {

	@Override
	public void run() {
		LinkedList<PlayMusic> play = new LinkedList<PlayMusic>();
		play.add(new PlayMusic());
		play.add(new PlayMusic());
		play.add(new PlayMusic());
		Iterator<PlayMusic> it = play.iterator();
		while(it.hasNext())
		{
			it.next();
			try {
				new Thread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
class PlayMusic
{
	public PlayMusic() {
		File file = new File("music/bgm.mp3");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		BufferedInputStream stream = new BufferedInputStream(fis);
		Player player = null;
		
		try {
			player = new Player(stream);
		} catch (JavaLayerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
