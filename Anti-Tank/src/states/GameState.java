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
	private Player[] players;
	private ArrayList<Projectile> projectiles;
	private int currentPlayer;
	private long timeStarted;
	private int roundsPlayed;
	private int numberOfPlayers;
	private Camera camera;
	private GUI gui;
	
	
	public GameState(int id, Camera camera){
		stateID = id;
		this.camera = camera;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		numberOfPlayers = 2; // Placeholder for testing
		world = new World(0); // ID 0 - Test Level   ID 1 - Possible New Level
		players = new Player[numberOfPlayers];
		projectiles = new ArrayList<Projectile>();
		timeStarted = System.nanoTime();
		roundsPlayed = 0;
		
		// Quick Fix - for testing.
		players[0] = new Player("Name", new Tank[] {new Tank(0,400,100)});
		players[1] = new Player("Name2", new Tank[] {new Tank(0,100,100)});
		
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
		for (int i = 0; i < projectiles.size(); i++) projectiles.get(i).update(gc, game, delta, world, this);
		for (int i = 0; i < players.length; i++) players[i].update(gc, game, delta, world, this);
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
		float tx1 = tank.getPos().getX();
		float tx2 = tank.getPos().getX() + tank.getImage().getWidth();
		float ty1 = tank.getPos().getY();
		float ty2 = tank.getPos().getY() + tank.getImage().getHeight();
		
		float px = proj.getPos().getX();
		float py = proj.getPos().getY();
		
		if (px > tx1 && px < tx2 && py > ty1 && py < ty2){
			HashSet<String> maskProj = getMask(proj.getPos(), proj.getImage());
			HashSet<String> maskTank = getMask(tank.getPos(), tank.getImage());
			maskProj.retainAll(maskTank); // Only keep those pixels that overlap.
			if (maskProj.size() > 0) return true; // Collides
			return false; // Doesn't Collide
		}
		return false;
	}

	@Override
	public int getID(){
		return stateID ;
	}

	public void addProjectile(Projectile proj) {
		projectiles.add(proj);
	}
	
	public void destroyProjectile(Projectile proj){
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
	
	public void nextPlayer() {
		players[currentPlayer].nextTank(); // Move to next tank on old player's team
		players[currentPlayer].removeFocus(); // Remove focus from old player
		
		// Move to next player
		if (currentPlayer + 1 == numberOfPlayers) {
			currentPlayer = 0;
//			roundsPlayed++;
//			world.randomizeWind();
		}
		else currentPlayer++; 
		
		players[currentPlayer].setFocus(); // Give focus to the new player
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player[] getPlayers(){
		return players;
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

	public void damagePlayers(float blastRadius, Vector2f pos, int baseDamage) {
		for (int i = 0; i < players.length; i++) players[i].damageTanks(blastRadius, pos, baseDamage);
	}

	public Player getPlayer(int i) {
		return players[i];
	}
	
}
