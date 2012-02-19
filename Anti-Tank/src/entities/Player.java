package entities;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
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
		if (hasFocus){
			Input input = gc.getInput(); //Get Inputs
			tanks[currentTank].update(gc, game, delta, world, input); //Update Active Tank with regard to input
			for (int i = 0; i < tanks.length; i++) if (i != currentTank) tanks[i].updateInBackground(gc, game, delta, world); // Update all other tanks in background
			if(input.isKeyPressed(Input.KEY_PERIOD)) world.randomizeWind();// For testing only
		} else {
			for (int i = 0; i < tanks.length; i++) tanks[i].updateInBackground(gc, game, delta, world);
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

	public String getPlayerName() {
		return playerName;
	}

	public int getCurrentTankNo() {
		return currentTank;
	}
	
	public Tank getCurrentTank() {
		return tanks[currentTank];
	}

	public void damageTanks(float blastRadius, Vector2f pos, int baseDamage) {
		int x = (int) pos.getX();
		int y = (int) pos.getY();
							
		for (int i = 0; i < tanks.length; i++){
			int tx = (int) tanks[i].getPos().getX() + (tanks[i].getImage().getWidth()/2); 
			int ty = (int) tanks[i].getPos().getY() + (tanks[i].getImage().getHeight()); // Middle-Bottom of current tank's body.
			
			float distance = (float)Math.sqrt(Math.pow((y-ty),2)+ Math.pow((x-tx), 2));
			
			if (distance <= blastRadius) {
				int dmg = (int) (baseDamage * (1 -(distance/blastRadius))); // 
				tanks[i].damageBy(dmg);
			}
		}
	}

	public Tank getTank(int j) {
		return tanks[j];
	}

	public Tank[] getTanks() {
		return tanks;
	}
}
