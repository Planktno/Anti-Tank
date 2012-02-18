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
import game.GUI;

public class GameState extends BasicGameState{

	private int stateID;
	private World world;
	private static Player[] players;
	private static ArrayList<Projectile> projectiles;
	private static int currentPlayer;
	private long timeStarted;
	private int roundsPlayed;
	private static int numberOfPlayers;
	private Camera camera;
	private GUI gui;
	
	
	public GameState(int id, Camera camera){
		stateID = id;
		this.camera = camera;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		numberOfPlayers = 1; // Placeholder for testing
		world = new World(0); // ID 0 - Test Level   ID 1 - Possible New Level
		players = new Player[numberOfPlayers];
		projectiles = new ArrayList<Projectile>();
		timeStarted = System.nanoTime();
		roundsPlayed = 0;
		
		// Quick Fix - for testing.
		players[0] = new Player("Name", new Tank[] {new Tank(0,400,100)});
//		players[1] = new Player("Name2", new Tank[] {new Tank(0,100,100)});
		
		currentPlayer = 0;
		players[currentPlayer].setFocus();
		
		gui = new GUI();
		gui.setCamera(camera);
		gui.setGameState(this);
		gui.setPlayers(players);
		gui.setWorld(world);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
				
		// Render World, then Projectiles, then Players.
		world.render(gc,game,g,camera);
		for (int i = 0; i < projectiles.size(); i++) projectiles.get(i).render(gc,game,g,camera);
		for (int i = 0; i < players.length; i++) players[i].render(gc,game,g,camera);
		g.drawString("Current Player: " + currentPlayer, 10, 580);
		
		gui.render(gc, game, g);
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

	
	public static void nextPlayer() {
		players[currentPlayer].nextTank(); // Move to next tank on old player's team
		players[currentPlayer].removeFocus(); // Remove focus from old player
		
		// Move to next player
		if (currentPlayer + 1 == numberOfPlayers) currentPlayer = 0;
		else currentPlayer++; 
		
		players[currentPlayer].setFocus(); // Give focus to the new player
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public long getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(long timeStarted) {
		this.timeStarted = timeStarted;
	}

	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}
	
}
