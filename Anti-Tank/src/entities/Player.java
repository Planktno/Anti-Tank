package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class Player {

	private String playerName; // ...name of the player ;)
	private Tank[] tanks;	   // all tanks associated with this player
	private int currentTank;   // Index of current playing tank in array tanks
	private boolean hasFocus;  // True if this player is currently playing
	
	
	public Player(String playerName, Tank[] tanks){
		this.playerName = playerName;
		this.tanks = tanks;
		currentTank = 0;
		hasFocus = false;
	}
	
	public void update(GameContainer gc, StateBasedGame game , int delta, World world){
		// For testing only
		Input input = gc.getInput();
		tanks[currentTank].update(gc, game, delta, world, input); 
		if(input.isKeyPressed(Input.KEY_PERIOD)) world.randomizeWind();
		
		if (hasFocus){
			//Get Inputs
			//Update Active Tank with regard to input
			//If shot fired tell GameState to move to next player and remove focus on this player
			//nextTank();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g, Camera cam){
		for (int i = 0; i < tanks.length; i++) tanks[i].render(gc,game,g,cam);
	}

	public void nextTank(){
		if (currentTank + 1 == tanks.length) currentTank = 0;
		else currentTank += 1;
	}
	
	public void setFocus(){
		hasFocus = true;
	}
	
	public void removeFocus(){
		hasFocus = false;
	}
}
