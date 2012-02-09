package states;


import javax.swing.JComponent;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Animation extends JComponent{
	
	private Image[] imgs;
	int currentImg;
	int timePerFrame; //in ms
	int deltaSum;
	int x;
	int y;
	int[] sequence = {0, 1, 2, 3, 2, 1};
	int i = 0;
	
	public Animation() {

		
		currentImg = 0;
		try {
			imgs = new Image[2];
			imgs[0] = new Image("data/Tank1.png");
			imgs[1] = new Image("data/Tank2.png");
			imgs[2] = new Image("data/Tank3.png");
			imgs[3] = new Image("data/Tank4.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Animation(Image[] imgs, int timePerFrame, int x, int y) {
		this.imgs = imgs;
		currentImg = 0;
		this.timePerFrame = timePerFrame;
		this.deltaSum = 0;
		this.x = x;
		this.y = y;
	}
	
	public void update(int delta) {
		
		
		deltaSum += delta;
		deltaSum -= timePerFrame; 
		if (i < sequence.length) {
			
			i++;
		} else {
			i = 0;
		}
			
		
	}
		//add delta to deltaSum
		//for every timePerFrame in deltaSum, jump one img forward
		//subtract multiples of timePerFrame from deltaSum

	
	public void render() {
		
		imgs[sequence[i]].draw(60, 60);
	}
	
}
