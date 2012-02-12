package entities;

import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class World {

	Image level;
	static int gravity;
	static int wind;
	static int windAngle;
	
	HashSet<String> pixelMap;
	
	public World() {
		try {
			level = new Image("data/testlevel.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gravity = 1;
		wind = 0;
		windAngle = 0;
	}

	public Image getImage() {
		return level;
	}

	public static int getGravity() {
		return gravity;
	}

	public static int getWind() {
		return wind;
	}

	public static int getWindAngle() {
		return windAngle;
	}
	
	public HashSet<String> getPixelMap() {
		return pixelMap;
	}

	public void setImage(Image level) {
		this.level = level;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public void setWind(int wind) {
		this.wind = wind;
	}

	public void setWindAngle(int windAngle) {
		this.windAngle = windAngle;
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Camera cam) {
		Vector2f relPos = cam.getRelFocusPos(new Vector2f(0,0));
		level.draw(relPos.getX(), relPos.getY(), cam.getFocusScale());
	}
}
