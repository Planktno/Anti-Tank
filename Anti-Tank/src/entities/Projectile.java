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
		float vx = launchSpeed * (float)Math.cos(Math.toRadians(bAngle));
		float vy = launchSpeed * (float)Math.sin(Math.toRadians(bAngle));
		
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
		img.draw(pos.x,pos.y);
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta, World world){
		
		// Check Collisions
		if (GameState.checkCollision(this, world)){ 
			// Damage tanks in BlastRadius by some value relating to base damage
			// Destroy part of the world (maybe)
			GameState.destroyProjectile(this); // Delete the projectile
		}
		
		pos.add(vel); // Update Position
		vel.set(vel.x, vel.y + World.getGravity()); // Update Velocity
		rotation = (float) Math.toDegrees(Math.atan(vel.y/vel.x)); // Update Rotation
		if (vel.x < 0) rotation += 180;
			
	}

}
