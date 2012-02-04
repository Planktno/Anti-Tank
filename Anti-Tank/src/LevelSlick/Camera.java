package LevelSlick;

import org.newdawn.slick.geom.Vector2f;

public class Camera {

	private World world;
	private Tank tank;
	private Projectile p;
	
	private float scale;
	private int movingSpeed = 10;
	
	public Camera() {
		world = null;
		tank = null;
		p = null;
		
		scale = 1;
		movingSpeed = 10;
	}
	
	public void setFocus(World world) {
		
	}
	
	public void setFocus(Tank tank) {
		
	}
	
	public void setFocus(Projectile p) {
		
	}
	
	public void setScale(float scale) {
		
	}
	
	public Vector2f getRelativePos(Vector2f pos) {
		return pos;
	}
	
	public float getScale() {
		return scale;
	}
	
}
