package states;

import java.util.ArrayList;
import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Camera;
import entities.Player;
import entities.Projectile;
import entities.Tank;
import entities.World;

public class GameState extends BasicGameState{

	
	private int stateID;
	private World world;
	private static Player[] players;
	private static ArrayList<Projectile> projectiles;
	private int currentPlayer;
	private long timeStarted;
	private int roundsPlayed;
	private int numberOfPlayers;
	private Camera camera;
	
	
	
	public GameState(int id, Camera camera){
		stateID = id;
		this.camera = camera;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		numberOfPlayers = 1; // Placeholder for testing
		world = new World(-1); // Placeholder ID value.
		players = new Player[numberOfPlayers];
		projectiles = new ArrayList<Projectile>();
		currentPlayer = 0;
		timeStarted = System.nanoTime();
		roundsPlayed = 0;
		
		// Quick Fix - for testing.
		players[0] = new Player("Name", new Tank[] {new Tank(1,100,100)});
		
//		world.init(gc, game);
//		for (int i = 0; i < players.size(); i++) players[i].init(gc,game);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
				
		// Render World, then Projectiles, then Players.
		world.render(gc,game,g,camera);
		for (int i = 0; i < projectiles.size(); i++) projectiles.get(i).render(gc,game,g,camera);
		for (int i = 0; i < players.length; i++) players[i].render(gc,game,g,camera);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
				
		// Update World, then Projectiles, then Players.
		world.update(gc,game,delta);
		for (int i = 0; i < projectiles.size(); i++) projectiles.get(i).update(gc, game, delta, world);
		for (int i = 0; i < players.length; i++) players[i].update(gc, game, delta, world);
	}
	
	public static boolean checkCollision(Tank tank, World world){
		HashSet<String> maskTank = getMask(tank.getPos(), tank.getImage());
		HashSet<String> maskWorld = world.getPixelMap();
		maskTank.retainAll(maskWorld); // Only keep those pixels that overlap.
		if (maskTank.size() > 0) return true; // Collides
		return false; // Doesn't Collide
	}

	public static boolean checkCollision(Projectile proj, World world){
		HashSet<String> maskProj = getMask(proj.getPos(), proj.getImage());
		HashSet<String> maskWorld = world.getPixelMap();
		maskProj.retainAll(maskWorld); // Only keep those pixels that overlap.
		if (maskProj.size() > 0) return true; // Collides
		return false; // Doesn't Collide
	}
	
	public static boolean checkCollision(Projectile proj, Tank tank){
		//TODO Implement Collision Detection between Projectile and Tank
		return false;
	}

	
	@Override
	public int getID(){
		return stateID ;
	}

	public static void addProjectile(Projectile proj) {
		projectiles.add(proj);
	}
	
	public static void destroyProjectile(Projectile proj){
		projectiles.remove(proj);
	}
	
	public static HashSet<String> getMask(Vector2f pos, Image img) {
		HashSet<String> mask = new HashSet<String>();
		
		for(int i = 0; i < img.getWidth(); i++) {
			for(int j = 0; j < img.getHeight(); j++) {
				if(img.getColor(i, j).getAlpha() != 0) { //is non transparent
					mask.add((Math.floor(pos.getX())+i) + "," + (Math.round(pos.getY())+j));
				}
			}
		}
		
		return mask;
	}
	
}
