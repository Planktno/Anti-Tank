package entities;

import java.awt.Color;
import java.util.HashSet;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class World_old {

	private Camera cam;
	
	private Image level;
	private Tank_old[] tanks;
	
	public World_old() {
		init();
	}
	
	public void init() {
		try {
			level = new Image("data/testlevel.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cam = new Camera(800, 600, 800, 600);
		tanks = new Tank_old[1];
		tanks[0] = new Tank_old();
		this.checkCollision(tanks[0]);
		System.out.println("init End");
	}
	
	public void update() {
		
	}
	
	public void render() {
		level.draw(0, 0); //render the level

		for(int i = 0; i < tanks.length; i++) { //render all tanks
			tanks[i].render(cam);
		}
		
		//render all projectiles
	}
	
	public Camera getCamera() {
		return cam;
	}
	
	/**
	 * Collisions can only be between:
	 * Tanks and World
	 * Tanks and Tanks
	 * Projectiles and Tanks
	 * Projectiles and World
	 */
	public boolean checkCollision(Tank_old tank) { //atm only between tank and world
		HashSet<String> maskTank  = getMask(tank.getPos(), tank.getImage());
		HashSet<String> maskWorld = getMask(new Vector2f(0,0), level);

//		maskTank.retainAll(new HashSet<String>());
		System.out.println("Size of Tank:" + maskTank.size());
		System.out.println("Size of World:" + maskWorld.size());
		maskTank.retainAll(maskWorld);
		System.out.println(maskTank.toString());
		
		if(maskTank.size() > 0) {
			System.out.println("Collision");
			return true;
		}
		
		System.out.println("No Collision");
		
		return false;
	}
	
	public HashSet<String> getMask(Vector2f pos, Image img) {
		HashSet<String> mask = new HashSet<String>();
		
		for(int i = 0; i < img.getWidth(); i++) {
			for(int j = 0; j < img.getWidth(); j++) {
				if(img.getColor(i, j).getAlpha() != 0) { //is non transparent
					mask.add((Math.floor(pos.getX())+i) + "," + (Math.round(pos.getY())+j));
				}
			}
		}
		
		return mask;
	}
	
}
