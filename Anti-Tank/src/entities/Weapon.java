package entities;

import org.newdawn.slick.geom.Vector2f;

import states.GameState;

public class Weapon {

	private int ammoCount;    // Amount of ammunition left in the weapon
	private int projID;       // The ID of the projectile that the weapon fires
	private int maxSpeed;	  // The maximum muzzle velocity of the weapon
	private Vector2f pos;     // The position the weapon fires from
	
	public Weapon(int id, float x, float y){
		pos = new Vector2f(x,y);
				
		//TODO Using the given weapon id, we should be able to load all of this data from somewhere...
		//For now, another quick fix...
		ammoCount = 20;
		maxSpeed = 10;
		projID = 0;
	}
	
	public void updatePosition(Vector2f vel){
		pos.add(vel);
	}
	
	public void shoot(float launchSpeed, float bAngle){
		Projectile proj = new Projectile(projID,pos.x,pos.y,launchSpeed*maxSpeed,bAngle);
		GameState.addProjectile(proj);
		ammoCount -= 1;
	}
	
	public int getAmmoCount(){
		return ammoCount;
	}
	
	public int getMaxSpeed(){
		return maxSpeed;
	}
}
