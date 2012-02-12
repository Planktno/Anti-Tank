package states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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
		numberOfPlayers = 1;
		world = new World();
		players = new Player[numberOfPlayers];
		projectiles = new ArrayList<Projectile>();
		currentPlayer = 0;
		timeStarted = System.nanoTime();
		roundsPlayed = 0;
		
		// Quick Fix
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
	
	public static boolean checkCollision(Player pl, World w){
		//TODO Implement Collision Detection between Player and World
		return false;
	}

	public static boolean checkCollision(Projectile proj, World w){
		//TODO Implement Collision Detection between Projectile and World
		return false;
	}
	
	public static boolean checkCollision(Projectile proj, Player pl){
		//TODO Implement Collision Detection between Projectile and Player
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
	
}
