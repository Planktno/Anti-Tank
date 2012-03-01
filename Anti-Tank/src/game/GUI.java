package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;
import entities.Camera;
import entities.Player;
import entities.Tank;
import entities.Weapon;
import entities.World;

public class GUI {
	
	Camera camera;
	
	GameState gs;
	World world;
	Player[] players;
	
	public GUI() {
		
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public void setGameState(GameState gs) {
		this.gs = gs;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		gr.drawString("Testing GUI", 0, 0);
		//********* The Player Section ************
		int currentPlayer = gs.getCurrentPlayerNo();
		for(int i = 0; i < players.length; i++) {
			int currentTank = players[i].getCurrentTankNo();
			if(i == currentPlayer) {
				//Draw the ith Player as the currentPlayer
				//Display currentTank
			} else {
				//Draw the ith Player
			}
		}
		
		//********** The General Information Section *************
		//Draw the current round
		int rounds = gs.getRoundsPlayed();
		//Draw the current time
		long time = gc.getTime()-gs.getTimeStarted();
		
		//********** The Current Round Section **************
		//Draw the wind + windAngle
		float wind = world.getWindSpeed();
		float windAngle = world.getWindAngle();
		//Draw the shooting Angle
		Tank currentTank = players[currentPlayer].getCurrentTank();
		float shootingAngle = currentTank.getbAngle();
		//Draw the shooting strength
		float shootingStrength = currentTank.getLaunchSpeed();
		//Draw the weapon(selection) + Ammo
		Weapon[] weapons = currentTank.getWeapons();
		int currentWeapon = currentTank.getCurrentWeapon();
	}
	
	

}
