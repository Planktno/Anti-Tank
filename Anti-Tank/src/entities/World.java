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
	float gravity; 
	float windSpeed; // Between 0 and maxWindSpeed
	float maxWindSpeed; // Currently set at 5, easily changed. (needs to be less than gravity or we might have projectiles going off into space :D
	int windAngle; // In degrees
	
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
		maxWindSpeed = 5;
		
		updatePixelMap();
		randomizeWind();
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g, Camera cam) {
		
		Vector2f relPos = cam.getRelFocusPos(new Vector2f(0,0));
		level.draw(relPos.getX(), relPos.getY(), cam.getFocusScale());
		
		
		// Printing for testing
		g.drawString("Current Controls:",300,10);
		g.drawString("Change Weapon - Space", 300, 25);
		g.drawString("Fire Weapon - Enter", 300, 40);
		g.drawString("Launch Speed UP/DOWN - Up/Down Arrows", 300, 55);
		g.drawString("Change Barrel Angle - Right/Left Arrows", 300, 70);
		g.drawString("Randomise Wind - Period", 300, 85);
		g.drawString("Wind:", 190, 10);
		g.drawLine((float)220, (float)70, (float)(220+10*getWindX()), (float)(70+10*getWindY()));
	}
	
	public void updatePixelMap(){
		pixelMap = GameState.getMask(new Vector2f(0,0),level);
	}
	
	public void randomizeWind(){
		windSpeed = (float) (Math.random()*maxWindSpeed);
		windAngle += Math.random()*360;
		
		windAngle = windAngle % 360; // Get it in the 0 - 359 degree range
		if (windSpeed > 5) windSpeed = 5; // Maximum Wind Speed allowed		
	}
	
	public Image getImage() {
		return level;
	}

	public float getGravity() {
		return gravity;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public int getWindAngle() {
		return windAngle;
	}
	
	public float getWindX(){
		return (float) (windSpeed * Math.cos(Math.toRadians(windAngle)));
	}
	
	public float getWindY(){
		return (float) (windSpeed * Math.sin(Math.toRadians(windAngle)));
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

	public void setWindSpeed(int wind) {
		this.windSpeed = wind;
	}

	public void setWindAngle(int windAngle) {
		this.windAngle = windAngle;
	}

}
