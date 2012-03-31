package entities;

import game.PixelPos;
import game.ResourceManager;

import java.util.ArrayList;
import java.util.HashSet;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class World {

	ImageBuffer levelBuffer;
	Image level;
	Image background;
	float gravity; 
	float windSpeed; // Between 0 and maxWindSpeed
	float maxWindSpeed; // Currently set at 5, easily changed. (needs to be less than gravity or we might have projectiles going off into space :D
	int windAngle; // In degrees
	
	HashSet<PixelPos> pixelMap;
	
	public World(int id) {
		loadResources(id);
		updatePixelMap();
		randomizeWind();
	}
	
	private void loadResources(int id){
		level = ResourceManager.getInstance().getImage("WORLD_"+id+"_LEVEL");
		setLevelBuffer(level);
		background = ResourceManager.getInstance().getImage("WORLD_"+id+"_BACKGROUND");
		
		String[] info = ResourceManager.getInstance().getText("WORLD_" + id + "_INFO").split(",");
		gravity = Float.parseFloat(info[0]);
		maxWindSpeed = Float.parseFloat(info[1]);
	}
	
	public void setLevelBuffer(Image image) {
		levelBuffer = new ImageBuffer(image.getWidth(), image.getHeight());
		for(int i = 0; i < level.getWidth(); i++) {
			for(int j = 0; j < level.getHeight(); j++) {
				Color c = level.getColor(i, j);
				levelBuffer.setRGBA(i, j, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			}
		}
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g, Camera cam) {
		
		Vector2f relPos = cam.getRelFocusPos(new Vector2f(0,0));
		background.draw(relPos.getX(), relPos.getY(), cam.getMultipliedScale());
		level.draw(relPos.getX(), relPos.getY(), cam.getMultipliedScale());
		
		// Printing for testing
		if (gc.isShowingFPS()) debugRender(g);
	}

	private void debugRender(Graphics g) {
		g.drawString("Current Controls:",300,10);
		g.drawString("Change Weapon - F", 300, 25);
		g.drawString("Fire Weapon - Space", 300, 40);
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
	
	public HashSet<PixelPos> getPixelMap() {
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
	
	public void destroyCircle(float radius, Vector2f pos) {
		ArrayList<PixelPos> toRemove = new ArrayList<PixelPos>();

		float x = (int) pos.getX();
		float y = (int) pos.getY();
		float radiusSquared = radius * radius;
		
		for (int i = (int)Math.floor(y-radius); i < y + radius; i++) {
			for (int j = (int)Math.floor(x-radius); j < x + radius; j++){
				if (i < levelBuffer.getHeight() && (i >= 0)){
					if (j < levelBuffer.getWidth() && (j >= 0)) {
						float distanceSquared = ((y-i)*(y-i)) + ((x-j)*(x-j)); // This is about 2x quicker than the other method.
						if (distanceSquared <= radiusSquared) {
							levelBuffer.setRGBA(j, i, 0, 0, 0, 0); 
							toRemove.add(new PixelPos(j,i));
						}
					}
				}	
			}
		
		}
		level = levelBuffer.getImage();
		pixelMap.removeAll(toRemove);
	}
	
	public void destroyLine(Vector2f pos, float angle, int length, int width) {
		ArrayList<PixelPos> toRemove = new ArrayList<PixelPos>();
		if(length*(Math.cos(angle)) >= 0){
			for (int x = -width; x <= length*(Math.cos(angle)); x++) {
				float y = (float)(x * Math.tan(angle));
				destroyCircleNoUpdate(width/2, new Vector2f(x + pos.getX(),y + pos.getY()), toRemove);
			}
		}else{
			for (int x = width; x >= length*(Math.cos(angle)); x--) {
				float y = (float)(x * Math.tan(angle));
				destroyCircleNoUpdate(width/2, new Vector2f(x + pos.getX(),y + pos.getY()), toRemove);
			}
		}

		level = levelBuffer.getImage();
		pixelMap.removeAll(toRemove);
	}
	public void destroyCircleNoUpdate(float radius, Vector2f pos, ArrayList<PixelPos> toRemove) {
		float x = (int) pos.getX();
		float y = (int) pos.getY();
		float radiusSquared = radius * radius;
		
		for (int i = (int)Math.floor(y-radius); i < y + radius; i++) {
			for (int j = (int)Math.floor(x-radius); j < x + radius; j++){
				if (i < levelBuffer.getHeight() && (i >= 0)){
					if (j < levelBuffer.getWidth() && (j >= 0)) {
						float distanceSquared = ((y-i)*(y-i)) + ((x-j)*(x-j)); // This is about 2x quicker than the other method.
						if (distanceSquared <= radiusSquared) {
							levelBuffer.setRGBA(j, i, 0, 0, 0, 0); 
							toRemove.add(new PixelPos(j,i));
						}
					}
				}	
			}
		
		}
		
	}
}
