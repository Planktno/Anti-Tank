package entities;

import game.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class Projectile {

	private Vector2f pos;		// Position of the projectile (centre of the image)
	private Vector2f vel;		// Velocity of the projectile
	private Image img;			// Projectile image
	private float rotation;		// Current angle of rotation of projectile (in degrees)
	private float blastLength;
	private float blastRadius;	// Area of effect of explosion
	private int baseDamage;	    // Base amount of hitpoint damage done to tanks
	private boolean laser;
	private Sound sound;
	
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
		
		String[] info = ResourceManager.getInstance().getText("PROJECTILE_" + id + "_INFO").split(",");
		blastLength = Float.parseFloat(info[0]);
		blastRadius = Float.parseFloat(info[1]);
		baseDamage = Integer.parseInt(info[2]);
		
		if(blastLength != 0) {
			laser = true;
			float vx = 100 * (float)Math.cos(Math.toRadians(rotation)); // x component of initial velocity
			float vy = 100 * (float)Math.sin(Math.toRadians(rotation)); // y component of initial velocity
			vel = new Vector2f(vx,vy);
		}
		else laser = false;
		
		if(!laser) {
			sound = ResourceManager.getInstance().getSound("PROJECTILE_" + id + "_SOUND");
		}
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g, Camera cam){
		float scale = cam.getMultipliedScale();
		Vector2f relpos = cam.getRelFocusPos(pos);
		float halfwidth = img.getWidth()/2;
		float halfheight = img.getHeight()/2;
		
		img.setRotation(rotation);
		img.draw(relpos.x - halfwidth*scale,relpos.y - halfheight*scale,scale); // Draw projectile around the centre 
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta, World world, GameState gs){
		// Check Collisions	(projectile, tanks)	
		for (int i = 0; i < gs.getPlayers().length; i++){ // for each player
			for (int j = 0; j < gs.getPlayer(i).getTanks().length; j++){ //for each tank
				Tank currentTank = gs.getPlayer(i).getTank(j);
				if (GameState.checkCollision(this, currentTank)){ // if the tank and projectile collide
					if(!laser) {
						sound.play();
					}
					currentTank.damageBy(baseDamage); // Damage the current tank by full damage of projectile
					gs.destroyProjectile(this); // Destroy the projectile
					world.destroyCircle(blastRadius, pos); // Destroy part of the world
				}
			}
		}
		
		// Check Collisions	(projectile, world)	
		if (GameState.checkCollision(this, world)){ 
			gs.destroyProjectile(this); // Delete the projectile
			if(laser) {
				//world.destroyLine(pos, rotation, 200, 20);
				world.destroyLine(pos, (float)Math.toRadians(rotation), (int)Math.floor(blastLength), (int)Math.floor(blastRadius) + 4);
				gs.damagePlayers(blastLength, blastRadius, (float)Math.toRadians(rotation), pos, baseDamage);
			} else {
				sound.play();
				world.destroyCircle(blastRadius, pos);// Destroy part of the world
				gs.damagePlayers(blastRadius, pos, baseDamage);// Damage all tanks of all players in BlastRadius by some value relating to base damage
			}
		}
		
		// Check projectile is in the level, if not: delete it.
		int worldheight = world.getImage().getHeight();
		if (pos.getY() > worldheight) gs.destroyProjectile(this);
		
//		Used to speed up and/or slow down projectile flight.. one complaint I
//		had when testing out the game on my flat mates was that it was too quick too see what was going on.
		int speedFactor = 15;
		
		 // Update Position
		pos.set(pos.x+(vel.x*delta/(speedFactor*10)),pos.y+(vel.y*delta/(speedFactor*10)));
		
		 // Update Velocity
		float windX = world.getWindX()*delta/(speedFactor*10);//150150;
		float windY = world.getWindY()*delta/(speedFactor*10);
		float grav = world.getGravity()*delta/(speedFactor*10);
		if(!laser) vel.set(vel.x + windX * delta/speedFactor, vel.y + (windY + grav)*delta/speedFactor);
		else {
			blastLength -= new Vector2f(vel.x*delta/(speedFactor*10),vel.y*delta/(speedFactor*10)).length();
			if(blastLength < 0) {
				gs.destroyProjectile(this);
			}
		}

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
