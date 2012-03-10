package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Camera;
import entities.Player;
import entities.Tank;
import entities.World;
import game.GunsAndHats;

public class PreGameMenu extends BasicGameState {

	private int id;
	
	String[] playerNames;
	
	public PreGameMenu(int id, Camera camera) {
		this.id = id;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		playerNames = new String[4];
		playerNames[0] = "Player1";
		playerNames[1] = "Player2";
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
		//Temporary
		GameState gameState = (GameState)game.getState(GunsAndHats.GAMESTATE);
		gameState.init(gc, game);
		startGame(gameState);
		game.enterState(GunsAndHats.GAMESTATE);
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void addPlayer() { //adds a Player to the end
		if(playerNames[0] == null) playerNames[0] = "Player1";
		else if(playerNames[1] == null) playerNames[1] = "Player2";
		else if(playerNames[2] == null) playerNames[2] = "Player3";
		else if(playerNames[3] == null) playerNames[3] = "Player4";
	}
	
	public void removePlayer() { //removes the last Player
		if(playerNames[3] != null) playerNames[3] = null;
		else if(playerNames[2] != null) playerNames[2] = null;
		else if(playerNames[1] != null) playerNames[1] = null;
		else if(playerNames[0] != null) playerNames[0] = null;
	}
	
	public Player[] createPlayers() {
		//TODO
		return null;
	}
	
	public void startGame(GameState gameState) {
		World world = new World(0); // ID 0 - Test Level   ID 1 - Possible New Level
		// Quick Fix - for testing.
		Player[] players =  new Player[2];
		players[0] = new Player("Player1", new Tank[] {new Tank(1,600,200),new Tank(1,500,200)});
		players[1] = new Player("Player2", new Tank[] {new Tank(1,200,200),new Tank(1,100,200)});
		
		gameState.startGame(world, players);
	}

}
