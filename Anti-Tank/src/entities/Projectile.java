package entities;

import game.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class Projectile {

	private Vector2f pos;		// Position of the projectile (centre of the image)
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

		loadResources(id); // Using ResourceManager and id.	
	}
	
	private void loadResources(int id){
		// YAY finally loading from resource manager :D	
		img = ResourceManager.getInstance().getImage("PROJECTILE_"+id+"_IMAGE");
		img.setCenterOfRotation(img.getWidth()/2, img.getHeight()/2); // Set at centre of projectile image.
		
		String[] info = ResourceManager.getInstance().getText("TANK_" + id + "_INFO").split(",");
		blastRadius = Float.parseFloat(info[0]);
		baseDamage = Float.parseFloat(info[1]);
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g, Camera cam){
		float scale = cam.getScale();
		Vector2f relpos = cam.getRelFocusPos(pos);
		float halfwidth = img.getWidth()/2;
		float halfheight = img.getHeight()/2;
		
		img.setRotation(rotation);
		img.draw(relpos.x - halfwidth*scale,relpos.y - halfheight*scale,scale); // Draw projectile around the centre 
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
		float windX = world.getWindX()*delta/100;
		float windY = world.getWindY()*delta/100;
		float grav = world.getGravity()*delta/100;
		vel.set(vel.x + windX, vel.y + windY + grav);

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
