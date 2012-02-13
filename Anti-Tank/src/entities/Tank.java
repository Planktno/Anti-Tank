package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class Tank {

	private Vector2f pos;		// Position of the Tank
	private Vector2f vel;		// Velocity of the Tank
	private Vector2f bPos; 		// Position of the Barrel of the tank
	private Image body;			// Image for the body of the Tank
	private Image barrel;	    // Image for the barrel of the Tank
	private float bAngle;		// Angle of the barrel relative to horizontal (in degrees)
	private float bx;			// Position of the barrel in x direction w.r.t body
	private float by;			// Position of the barrel in y direction w.r.t body
	private float launchSpeed;  // Percentage of weapon maximum muzzle velocity
	private int hitpoints;		// Health of tank remaining
	private Weapon[] weapons;	// Weapons associated with the tank
	private int currentWeapon;	// Index of currently selected weapon
	
	
	
	public Tank(int id, float x, float y){
		pos = new Vector2f(x,y);
		vel = new Vector2f(0,0);
		launchSpeed = 0.6f;
		bAngle = 330;
		currentWeapon = 0;
			
		//TODO Using the given tank id, we should be able to load most of this data from somewhere...
		//For now, another quick fix...
		try{
			body = new Image("data/tanks/body.png");
			barrel = new Image("data/tanks/barrel.png");
		} catch (SlickException e){
			System.out.println("Images Could not be found for TANK");
			e.printStackTrace();
		}
		bx = 15;
		by = 5; 
		hitpoints = 100;
		bPos = new Vector2f(pos.x+bx, pos.y+by);
		barrel.setCenterOfRotation(0, barrel.getHeight()/2); // So that it rotates about its end.
		weapons = new Weapon[] {new Weapon(-1,bPos), new Weapon(-1,bPos)}; // ID of -1 is a placeholder
		
	}
	
	public void render (GameContainer gc, StateBasedGame game, Graphics g, Camera cam){
		
		// Not implemented properly yet, below is for testing purposes.
		barrel.setRotation(bAngle);
		barrel.draw(cam.getRelFocusPos(bPos).x, cam.getRelFocusPos(bPos).y, cam.getScale());
		body.draw(cam.getRelFocusPos(pos).x, cam.getRelFocusPos(pos).y, cam.getScale());
		
		
		//For Debugging
		g.drawString("Current Weapon: " + currentWeapon,10,25);
		g.drawString("Ammo Count: " + Integer.toString(weapons[currentWeapon].getAmmoCount()),10,40);
		g.drawString("launchSpeed: " + launchSpeed,10,55);
		g.drawString("bAngle: " + bAngle,10,70);
	}
	
	public void update (GameContainer gc, StateBasedGame game, int delta, World world, Input in){
		// Check Inputs
		checkInputs(in);
		
		// Keep old position
		Vector2f old_pos = new Vector2f(pos.x, pos.y);
		Vector2f old_bPos = new Vector2f(bPos.x,bPos.y);
		
		// Update Position (body and barrel)
		pos.add(new Vector2f((vel.x*delta/100),(vel.y*delta/100)));
		bPos.add(new Vector2f((vel.x*delta/100),(vel.y*delta/100)));
		
		// Check Collisions
				if (GameState.checkCollision(this, world)){ 
					vel.set(vel.x,0); // TODO Not properly implemented
					pos.set(old_pos);
					bPos.set(old_bPos);
				}
		
		// Update Velocity
		vel.set(vel.x, vel.y + world.getGravity()*delta/100);
		
		
	}
	
	private void checkInputs(Input in) {
		if(in.isKeyPressed(Input.KEY_UP)) launchSpeedUp();
		if(in.isKeyPressed(Input.KEY_DOWN)) launchSpeedDown();
		if(in.isKeyPressed(Input.KEY_LEFT)) barrelRotateAnticlockwise();
		if(in.isKeyPressed(Input.KEY_RIGHT)) barrelRotateClockwise();
		
		// TODO Tank Movement Inputs
		
		if(in.isKeyPressed(Input.KEY_SPACE)) changeWeapon();
		
		if(in.isKeyPressed(Input.KEY_ENTER)) if (weapons[currentWeapon].getAmmoCount() > 0) weapons[currentWeapon].shoot(launchSpeed, bAngle);
	}

	private void changeWeapon() {
		if (currentWeapon + 1 == weapons.length) currentWeapon = 0;
		else currentWeapon += 1;
		
	}

	private void barrelRotateAnticlockwise() {
		bAngle -= 5;
		if (bAngle <= 0) bAngle += 360;
		if (bAngle == 175) bAngle = 180;
	}

	private void barrelRotateClockwise() {
		bAngle += 5;
		if (bAngle >= 360) bAngle -= 360;
		if (bAngle == 5) bAngle = 0;
	}

	private void launchSpeedUp() {
		if (launchSpeed >= 1) launchSpeed = 1;
		else launchSpeed += 0.05;
	}
	
	private void launchSpeedDown() {
		if (launchSpeed <= 0) launchSpeed = 0;
		else launchSpeed -= 0.05;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Image getImage() {
		return body;
	}
	
	
}

