package game;


import javax.swing.JComponent;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Animation extends JComponent{
	
	private Image[] imgs;
	int currentImg;
	int timePerFrame; //in ms
	int deltaSum;
	int[] sequence;
	int i;
	
	public Animation(Image[] imgs, int timePerFrame, int[] sequence) {
		this.imgs = imgs;
		currentImg = 0;
		this.timePerFrame = timePerFrame;
		this.deltaSum = 0;
		this.sequence = sequence;
		int i = 0;
	}
	
	public void update(int delta) {

		deltaSum += delta;
		
		if(deltaSum >= timePerFrame) {
			deltaSum -= timePerFrame; 
			if (i < sequence.length-1) {
				i++;
			} else {
				i = 0;
			}
		}
		

	}
		//add delta to deltaSum
		//for every timePerFrame in deltaSum, jump one img forward
		//subtract multiples of timePerFrame from deltaSum

	
	public void render(float x, float y, float scale) {
		
		imgs[sequence[i]].draw(x, y, scale);
	}
	
	public Image getCurrentImage() {
		return imgs[sequence[i]];
	}
	
	public Image getImage(int j){
		return imgs[j];
	}
	
}
