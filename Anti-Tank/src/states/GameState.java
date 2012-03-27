package states;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Camera;
import entities.Hat;
import entities.Player;
import entities.Projectile;
import entities.Tank;
import entities.World;
import game.GUI;
import game.GunsAndHats;
import game.History;
import game.PixelPos;
import game.ResourceManager;

public class GameState extends BasicGameState{

	private int stateID;
	private World world;
	private Player[] players;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Hat> hats;
	private int currentPlayer;
	private long timeStarted;
	private int roundsPlayed;
	private int numberOfPlayers;
	private int tanksPerPlayer;
	private Camera cam;
	private GUI gui;
	private String winner; // Only non-empty if there is actually a winner
	private int winnerInt;
	private boolean winnerChosen; // True if and only if there is a winner
	private int currentTank; // Keeps track of how many tanks have been played - only used in keeping track of how many rounds have been played.
	private boolean goToNextPlayer;
	private int frameCount;
	private String loserStr;
	
	
	public GameState(int id, Camera camera){
		stateID = id;
		this.cam = camera;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.white); // So that all text in the game is rendered in white
	
		// Render World, then Projectiles, then Players.
		world.render(gc,game,g,cam);
		for (int i = 0; i < projectiles.size(); i++) projectiles.get(i).render(gc,game,g,cam);
		for(Hat hat : hats) hat.render(gc, game, g, cam);
		for (int i = 0; i < players.length; i++) players[i].render(gc,game,g,cam, gui);
		gui.render(gc, game, g, this);
		
		if (winnerChosen) displayWinner(winner, g);
		
		if (gc.isShowingFPS()) debugRender(g);
	}
	
	public void startGame(World world, Player[] players, ArrayList<Hat> hats){
		this.world = world;
		this.players = players;
		
		projectiles = new ArrayList<Projectile>();
		this.hats = hats;
	
		timeStarted = System.nanoTime();
		roundsPlayed = 0;
		winner = "";
		winnerChosen = false;
		frameCount = 0;
		goToNextPlayer = false;
		
		numberOfPlayers = players.length; // Missing and causing crashes!!
		tanksPerPlayer = players[0].getTanks().length;
		currentPlayer = 0;
		currentTank = 1;//shouldn't this be 0?
		players[currentPlayer].setFocus(this);
		
		gui = new GUI();
		gui.setCamera(cam);
		gui.setGameState(this);
		gui.setPlayers(players);
		gui.setWorld(world);
		
		cam.setFocus(world);
		cam.setSmooth(true);
		cam.setFocus(players[0].getCurrentTank());
		
		loserStr = "";
	}

	private void addToHistory(GameContainer gc) {
		try{
			History h = new History();
			long time = gc.getTime()/1000 - this.getTimeStarted()/1000000000;
			int min=(int)time/60;
			int s=(int)time%60;
			String str = winner + ", " + loserStr;
			if(min < 10) str = str + "0" + min + ":";
			else str = str + min + ";";
			
			if(s < 10) str = str + "0" + s;
			else str = str + s;
			h.addMatch(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void displayWinner(String win, Graphics g) {
		g.drawString(win + " is the WINNER!", cam.getScreenWidth()/2 - 50, cam.getScreenHeight()/2 - 50);
		g.drawString("Press ENTER to exit", cam.getScreenWidth()/2 - 50, cam.getScreenHeight()/2 - 35);
	}

	private void debugRender(Graphics g) {
		g.drawString("Rounds Played: " + roundsPlayed, 10, 550);
		g.drawString("Current Player: " + currentPlayer, 10, 565);	
		g.drawString("Current Tank: " + players[currentPlayer].getCurrentTankNo(), 10,580);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input in = gc.getInput();
		if (winner != "") {// Check to see if there is a winner yet
			winnerChosen = true; 
		}
		
		if (!winnerChosen) { // If there is no winner yet..
			// Update World, then Projectiles, then Players.
			world.update(gc, game, delta);
			for (int i = 0; i < projectiles.size(); i++) projectiles.get(i).update(gc, game, delta, world, this);
			for (int i = 0; i < players.length; i++) players[i].update(gc, game, delta, world, this);
			
			if (players[currentPlayer].getCurrentTank().hasShot()){
				if (projectiles.isEmpty()) {
					goToNextPlayer = true;
				}		
			}
			
			// If any player has no tanks left alive, set them as a loser.
			for (int i = 0; i < players.length; i++) {
				if (players[i].getAliveTanks() == 0) {
					if(!players[i].isLoser()){
						loserStr = players[i].getPlayerName() + ", " + loserStr;
						players[i].setLoser();
					}
				}
			}

			// If only one player isn't a loser, display them as the winner, then end the game.
			int losers = 0;
			for(int i = 0; i < players.length; i++) {
				if(players[i].isLoser()) losers++;
			}
			if(losers == players.length-1) {
				for(int i = 0; i < players.length; i++) {
					if(!players[i].isLoser()) {
						winner = players[i].getPlayerName();
						winnerInt = i+1;
						nextPlayer();
					}
				}
			}
		} else { // If there is a winner!
			cam.setFocus(world);
			if (in.isKeyPressed(Input.KEY_ENTER)) {
				addToHistory(gc); // Add the game to history.txt
				((EndGameScreen)game.getState(GunsAndHats.ENDGAMESCREEN)).setWinner(winnerInt);
				game.enterState(GunsAndHats.ENDGAMESCREEN);
			}
		}
		
		// Added this to avoid major unwanted jump when camera refocused.
		// Just waits one frame so the delta value is decent again after the terrain destruction
		// before it goes to the next player and moves the camera. - Peter
		if (goToNextPlayer){
			if (frameCount == 0){
				frameCount++;
			} else {
				frameCount = 0;
				goToNextPlayer = false;
				nextPlayer();
			}
		}
		
		for(Hat hat : hats) hat.update(gc, game, delta, world, in, this, cam);
		
		if(in.isKeyPressed(Input.KEY_P)) cam.setFocusScale(cam.getFocusScale()*1.1f);
		if(in.isKeyPressed(Input.KEY_O)) cam.setFocusScale(cam.getFocusScale()*0.9f);
		if(in.isKeyPressed(Input.KEY_ESCAPE)) gc.exit();
		
		cam.update(delta);
		
		// Animates all tanks - Peter
		for (int i = 0; i < this.numberOfPlayers; i++){
			players[i].getCurrentTank().getAnim().update(delta); 
		}
		
		
		// Debug Mode Toggle
		if (in.isKeyPressed(Input.KEY_F12)) gc.setShowFPS(!gc.isShowingFPS());
	}

	public static boolean checkCollision(Tank tank, World world){
//		HashSet<String> maskTank = getMask(tank.getPos(), tank.getImage());
//		HashSet<String> maskWorld = world.getPixelMap();
		HashSet<PixelPos> maskTank = getMask(tank.getPos(), tank.getImage());
		HashSet<PixelPos> maskWorld = world.getPixelMap();
		maskTank.retainAll(maskWorld); // Only keep those pixels that overlap.
		if (maskTank.size() > 0) return true; // Collides
		return false; // Doesn't Collide
	}

	public static boolean checkCollision(Projectile proj, World world){
//		HashSet<String> maskProj = getMask(proj.getPos(), proj.getImage());
//		HashSet<String> maskWorld = world.getPixelMap();
		HashSet<PixelPos> maskProj = getMask(proj.getPos(), proj.getImage());
		HashSet<PixelPos> maskWorld = world.getPixelMap();
		maskProj.retainAll(maskWorld); // Only keep those pixels that overlap.
		if (maskProj.size() > 0) return true; // Collides
		return false; // Doesn't Collide
	}
	
	public static boolean checkCollision(Projectile proj, Tank tank){
		float tx1 = tank.getPos().getX();
		float tx2 = tank.getPos().getX() + tank.getImage().getWidth();
		float ty1 = tank.getPos().getY();
		float ty2 = tank.getPos().getY() + tank.getImage().getHeight();
		
		float px1 = proj.getPos().getX();
		float px2 = px1 + proj.getImage().getWidth();
		float py1 = proj.getPos().getY();
		float py2 = py1 + proj.getImage().getHeight();
		
		if (((px1 > tx1 && px1 < tx2)||(px2 > tx1 && px2 < tx2)) &&  ((py1 > ty1 && py1 < ty2)||(py2 > ty1 && py2 < ty2))){
//			HashSet<String> maskProj = getMask(proj.getPos(), proj.getImage());
//			HashSet<String> maskTank = getMask(tank.getPos(), tank.getImage());
			HashSet<PixelPos> maskProj = getMask(proj.getPos(), proj.getImage());
			HashSet<PixelPos> maskTank = getMask(tank.getPos(), tank.getImage());
			maskProj.retainAll(maskTank); // Only keep those pixels that overlap.
			if (maskProj.size() > 0) return true; // Collides
			return false; // Doesn't Collide
		}
		return false;
	}
	
	public static boolean checkCollision(Hat hat, World world){
//		HashSet<String> maskTank = getMask(tank.getPos(), tank.getImage());
//		HashSet<String> maskWorld = world.getPixelMap();
		HashSet<PixelPos> maskHat = getMask(hat.getPosition(), hat.getImage());
		HashSet<PixelPos> maskWorld = world.getPixelMap();
		maskHat.retainAll(maskWorld); // Only keep those pixels that overlap.
		if (maskHat.size() > 0) return true; // Collides
		return false; // Doesn't Collide
	}
	
	public static boolean checkCollision(Hat hat, Tank tank){
		float tx1 = tank.getPos().getX();
		float tx2 = tank.getPos().getX() + tank.getImage().getWidth();
		float ty1 = tank.getPos().getY();
		float ty2 = tank.getPos().getY() + tank.getImage().getHeight();
		
		float hx1 = hat.getPosition().getX();
		float hx2 = hx1 + hat.getImage().getWidth();
		float hy1 = hat.getPosition().getY();
		float hy2 = hy1 + hat.getImage().getHeight();
		
		if (((hx1 > tx1 && hx1 < tx2)||(hx2 > tx1 && hx2 < tx2)) &&  ((hy1 > ty1 && hy1 < ty2)||(hy2 > ty1 && hy2 < ty2))){
//			HashSet<String> maskProj = getMask(proj.getPos(), proj.getImage());
//			HashSet<String> maskTank = getMask(tank.getPos(), tank.getImage());
			HashSet<PixelPos> maskHat = getMask(hat.getPosition(), hat.getImage());
			HashSet<PixelPos> maskTank = getMask(tank.getPos(), tank.getImage());
			maskHat.retainAll(maskTank); // Only keep those pixels that overlap.
			if (maskHat.size() > 0) return true; // Collides
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
		cam.setFocus(proj);
	}
	
	public void destroyProjectile(Projectile proj){
		projectiles.remove(proj);
	}
	
	public static HashSet<PixelPos> getMask(Vector2f pos, Image img) {
		HashSet<PixelPos> mask = new HashSet<PixelPos>();
		
		for(int i = 0; i < img.getWidth(); i++) {
			for(int j = 0; j < img.getHeight(); j++) {
				if(img.getColor(i, j).getAlpha() != 0) { //is non transparent
					//mask.add((Math.floor(pos.getX())+i) + "," + (Math.round(pos.getY())+j));
					mask.add(new PixelPos((int)Math.floor(pos.getX()+i), (int)Math.floor(pos.getY()+j)));
				}
			}
		}
		
		return mask;
	}
	
	public void nextPlayer() {
		
		players[currentPlayer].getCurrentTank().setHasShot(false);
		
		players[currentPlayer].nextTank(); // Move to next tank on old player's team
		players[currentPlayer].removeFocus(); // Remove focus from old player
		
		// Move to next player
		currentPlayer++;
		if(currentPlayer == numberOfPlayers) {
			currentPlayer = 0;
			roundsPlayed++;
			world.randomizeWind();
		}
		
		if(players[currentPlayer].isLoser()) {
			nextPlayer();
		} else {
			players[currentPlayer].setFocus(this); // Give focus to the new player
			cam.setFocus(players[currentPlayer].getCurrentTank());
		}
	}

	public Player getCurrentPlayer() {
		return players[currentPlayer];
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

	public int getCurrentPlayerNo() {
		return currentPlayer;
	}

	public void changeFocus() {
		cam.setFocus(players[currentPlayer].getCurrentTank());
	}
	
}
