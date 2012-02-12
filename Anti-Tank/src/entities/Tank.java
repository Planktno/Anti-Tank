package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Tank {

	private Vector2f pos;		// Position of the Tank
	private Vector2f vel;		// Velocity of the Tank
	private Image body;			// Image for the body of the Tank
	private Image barrel;	    // Image for the barrel of the Tank
	private float bAngle;		// Angle of the barrel relative to horizontal
	private float bx;			// Position of the barrel in x direction w.r.t body
	private float by;			// Position of the barrel in y direction w.r.t body
	private float launchSpeed;  // Percentage of weapon maximum muzzle velocity
	private int hitpoints;		// Health of tank remaining
	private Weapon[] weapons;	// Weapons associated with the tank
	
	
	public Tank(int id, float x, float y){
		pos = new Vector2f(x,y);
		vel = new Vector2f(0,0);
		launchSpeed = 1;
		bAngle = 0;
		
		//TODO Using the given tank id, we should be able to load all of this data from somewhere...
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
		weapons = new Weapon[] {new Weapon(-1,pos.x+bx, pos.y+by)};
	}
	
	public void render (GameContainer gc, StateBasedGame game, Graphics g, Camera cam){
		
		// Not implemented properly yet, below is for testing purposes.
//		barrel.setRotation(bAngle);
//		barrel.draw(pos.x+bx, pos.y+by);
//		body.draw(pos.x, pos.y);
//		g.drawString(Integer.toString(weapons[0].getAmmoCount()),10,10);
	}
	
	public void update (GameContainer gc, StateBasedGame game, int delta){
		// Check Inputs
		// Check Collisions
		// Update Position
		// Update Velocity
		
		// For Testing only
//		Input in = gc.getInput();
//		if(in.isKeyPressed(Input.KEY_ENTER)) weapons[0].shoot(launchSpeed, bAngle);
	}
	
	
	
}

