package entities;

import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class World {

	Image level;
	int gravity;
	int wind;
	int windAngle;
	
	HashSet<String> pixelMap;
	
	public World(int id) {
		
		// Using the given ID we should be able to load the data from a file
		// Quick fix again...
		
		try {
			level = new Image("data/testlevel.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		gravity = 10;
		wind = 0;
		windAngle = 0;
		pixelMap = GameState.getMask(new Vector2f(0,0),level);
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g, Camera cam) {
		Vector2f relPos = cam.getRelFocusPos(new Vector2f(0,0));
		level.draw(relPos.getX(), relPos.getY(), cam.getFocusScale());
		
		g.drawString("Current Controls:",300,10);
		g.drawString("Change Weapon - Space", 300, 25);
		g.drawString("Fire Weapon - Enter", 300, 40);
		g.drawString("Launch Speed UP/DOWN - Up/Down Arrows", 300, 55);
		g.drawString("Change Barrel Angle - Right/Left Arrows", 300, 70);
	}
	
	public Image getImage() {
		return level;
	}

	public int getGravity() {
		return gravity;
	}

	public int getWind() {
		return wind;
	}

	public int getWindAngle() {
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

	
}
