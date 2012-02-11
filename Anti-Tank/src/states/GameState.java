package states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Player;
import entities.Projectile;
import entities.World;

public class GameState extends BasicGameState{

	
	private int stateID;
	private World world;
	private Player[] players;
	private ArrayList<Projectile> projectiles;
	private int currentPlayer;
	private long timeStarted;
	private int roundsPlayed;
	private int numberOfPlayers;
	
	
	
	public GameState(int id){
		stateID = id;
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
				
//		world.init();
//		for (int i = 0; i < players.length; i++) players[i].init();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		
		
		// Render World, then players, then projectiles.
//		world.render();
//		for (int i = 0; i < players.length; i++) players[i].render();
//		for (int i = 0; i < projectiles.size(); i++) projectiles[i].render();
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		
		
		// Update World, then players, then projectiles.
//		world.update();
//		for (int i = 0; i < players.length; i++) players[i].update();
//		for (int i = 0; i < projectiles.size(); i++) projectiles[i].update();

		
	}
	
	public boolean checkCollision(Player pl, World w){
		return false;
	}

	public boolean checkCollision(Projectile proj, World w){
		return false;
	}
	
	public boolean checkCollision(Projectile proj, Player pl){
		return false;
	}

	
	@Override
	public int getID(){
		return stateID ;
	}

}
