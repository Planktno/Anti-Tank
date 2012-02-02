package LevelSlick;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class World {

	private Image level;
	private Tank[] tanks;
	
	public World() {
		init();
	}
	
	public void init() {
		try {
			level = new Image("data/testlevel.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		level.draw(0, 0);
	}
	
}
