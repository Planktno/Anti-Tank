package entities;

import game.ResourceManager;

import org.newdawn.slick.geom.Vector2f;

import states.GameState;

public class Weapon {

	private int ammoCount;    // Amount of ammunition left in the weapon
	private int projID;       // The ID of the projectile that the weapon fires
	private int maxSpeed;	  // The maximum muzzle velocity of the weapon
	private Vector2f pos;     // The position the weapon fires from
	
	public Weapon(int id, Vector2f pos){
		this.pos = pos;
		loadResources(id);
	}
	
	private void loadResources(int id) {
		// YAY finally loading from resource manager :D		
		String[] info = ResourceManager.getInstance().getText("WEAPON_" + id + "_INFO").split(",");
	
		ammoCount = Integer.parseInt(info[0]);
		maxSpeed = Integer.parseInt(info[1]); 
		projID = Integer.parseInt(info[2]);
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

	public void updatePosition(float x, float y) {
		pos.set(x,y);
	}
}
