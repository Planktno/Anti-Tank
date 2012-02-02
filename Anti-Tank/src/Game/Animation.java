package Game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class Animation extends JComponent{
	
	private Image[] imgs;
	int currentImg;
	int timePerFrame; //in ms
	int deltaSum;
	int x;
	int y;

	public Animation(Image[] imgs, int timePerFrame, int x, int y) {
		this.imgs = imgs;
		currentImg = 0;
		this.timePerFrame = timePerFrame;
		this.deltaSum = 0;
		this.x = x;
		this.y = y;
	}
	
	public void update(int delta) {
		//add delta to deltaSum
		//for every timePerFrame in deltaSum, jump one img forward
		//subtract multiples of timePerFrame from deltaSum
	}
	
	public void render() {
		//paint the current img at the respective x,y coordinates
	}
	
}
