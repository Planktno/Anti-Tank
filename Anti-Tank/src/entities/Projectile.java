package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class Projectile {

	private Vector2f pos;		// Position of the projectile
	private Vector2f vel;		// Velocity of the projectile
	private Image img;			// Projectile image
	private float rotation;		// Current angle of rotation of projectile (in degrees)
	private float blastRadius;	// Area of effect of explosion
	private float baseDamage;	// Base amount of hitpoint damage done to tanks
	
	public Projectile(int id, float x, float y, float launchSpeed, float bAngle) {
		float vx = launchSpeed * (float)Math.cos(Math.toRadians(bAngle)); // x component of initial velocity
		float vy = launchSpeed * (float)Math.sin(Math.toRadians(bAngle)); // y component of initial velocity
		
		pos = new Vector2f(x,y);
		vel = new Vector2f(vx,vy);
		rotation = bAngle;
		
		//TODO Using the given projectile id, we should be able to load all of this data from somewhere...
		//For now, another quick fix...
		try {
			img = new Image("data/projectiles/proj.png");
		} catch (SlickException e) {
			System.out.println("Image of PROJECTILE not found..");
			e.printStackTrace();
		}
		blastRadius = 10;
		baseDamage = 20;
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g, Camera cam){
		img.setRotation(rotation);
		img.draw(cam.getRelFocusPos(pos).x,cam.getRelFocusPos(pos).y,cam.getScale());
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta, World world){
		// Check Collisions
		if (GameState.checkCollision(this, world)){ 
			// TODO Damage tanks in BlastRadius by some value relating to base damage
			// TODO Destroy part of the world (maybe)
			GameState.destroyProjectile(this); // Delete the projectile
		}
		
		 // Update Position
		pos.set(pos.x+(vel.x*delta/100),pos.y+(vel.y*delta/100));
		
		 // Update Velocity
		vel.set(vel.x, vel.y + world.getGravity()*delta/100);

		// Update Rotation
		rotation = (float) Math.toDegrees(Math.atan(vel.y/vel.x)); 
		if (vel.x < 0) rotation += 180;	
	}

	public Vector2f getPos() {
		return pos;
	}

	public Image getImage() {
		return img;
	}

}
