package states;

import java.util.ArrayList;
import java.util.Random;

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
import entities.Tank;
import entities.World;
import game.GunsAndHats;
import game.ResourceManager;

public class PreGameMenu extends BasicGameState {

	// KEEP THESE UP TO DATE
	private static final int numberMenuItems = 3;
	private static final int numberOfWorlds = 4;
	private static final int maxNumTanks = 4;
	private static final int maxNumPlayers = 4;

	
	private int id;
	
	private String[] playerNames;
	private int worldId;
	private int tanksPerPlayer;
	private int numberOfPlayers;
	private int currentMenuItem;
	
	private Player[] players;
	
	Image backgroundImg;
	Image worldImg;
	Image b_start;
//	Image b_start_hover;
	Image left_big;
	Image left_small;
	Image right_big;
	Image right_small;

	private Camera cam;
	
	public PreGameMenu(int id, Camera camera) {
		this.id = id;
		this.cam = camera;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		playerNames = new String[maxNumPlayers];
		for (int i = 1; i <= maxNumPlayers; i++) {
			playerNames[i-1] = "Player " + i;
		}
		
		//TEMPORARY NAME CHANGES
		playerNames[0] = "Gordon";
		playerNames[1] = "Oana";
		playerNames[2] = "Peter";
		playerNames[3] = "Victor";
		            
		worldId = 0;
		tanksPerPlayer = 1;
		numberOfPlayers = 2;
		currentMenuItem = 1;
		
		backgroundImg = ResourceManager.getInstance().getImage("PRE_GAME_MENU_BG");
		updateWorldImg();
		b_start         = new Image ("data/button_start_game.png");
	
	}
	
	private void updateWorldImg() {
		this.worldImg = ResourceManager.getInstance().getImage("WORLD_" + worldId + "_THUMB");
		this.right_small = ResourceManager.getInstance().getImage("RIGHT_SMALL");
		this.right_big = ResourceManager.getInstance().getImage("RIGHT_BIG");
		this.left_big = ResourceManager.getInstance().getImage("LEFT_BIG");

	}


	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		
		float scale = cam.getScale();
		int offset = (int) cam.getOffset().getX();
		backgroundImg.draw(cam.getOffset().getX(),cam.getOffset().getY(),scale);
		b_start.draw(cam.getOffset().getX()+550*cam.getScale(), cam.getOffset().getY()+450*cam.getScale(), cam.getScale());
		
		int lx = (int) (50*scale);
		int spacing = (int) (30*scale);
		g.drawString("Number of players:   " + this.numberOfPlayers, lx + offset, spacing*1);
		g.drawString("Tanks per player:    " + this.tanksPerPlayer, lx + offset, spacing*2);
		g.drawString("Your chosen world: World " + this.worldId, lx + offset, spacing*3);
		
		worldImg.draw(lx + spacing*3 + offset, spacing*4, scale);
		right_small.draw(lx - (40 * scale) + offset, spacing*currentMenuItem);
		right_big.draw(lx + 400 * scale + offset, spacing* 11/2 );
		left_big.draw(lx - 55 * scale + offset, spacing* 11/2);
		
//		g.drawString("To START press ENTER", lx + offset, spacing*15);
//		g.drawString(">", lx - (20*scale) + offset, spacing*currentMenuItem);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input in = gc.getInput();
		checkInputs(in);
		int mouseX = in.getMouseX();
		int mouseY = in.getMouseY();
		
		if(mouseY >= cam.getOffset().getY() + 450*cam.getScale() && mouseY <= cam.getOffset().getY() + 450*cam.getScale() + b_start.getHeight()*cam.getScale()) {
			if(mouseX >= cam.getOffset().getX() + 550*cam.getScale() && mouseX <= cam.getOffset().getX() + 550*cam.getScale() + b_start.getWidth()*cam.getScale()) {
				if (in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					createPlayers();
					GameState gameState = (GameState)game.getState(GunsAndHats.GAMESTATE);
					gameState.init(gc, game);
					startGame(gameState,gc);
					game.enterState(GunsAndHats.GAMESTATE);
				}
			}
		}
		
		
		//Temporary
//		if (in.isKeyPressed(Input.KEY_ENTER)){
//			createPlayers();
//			GameState gameState = (GameState)game.getState(GunsAndHats.GAMESTATE);
//			gameState.init(gc, game);
//			startGame(gameState);
//			game.enterState(GunsAndHats.GAMESTATE);
//		}
		
	}

	private void checkInputs(Input in) {
		if (in.isKeyPressed(Input.KEY_UP)) upMenuItems();
		if (in.isKeyPressed(Input.KEY_DOWN)) downMenuItems();
		if (in.isKeyPressed(Input.KEY_RIGHT)) incrementMenuItem(); // Increment current menu item
		if (in.isKeyPressed(Input.KEY_LEFT)) decrementMenuItem(); // Decrement current menu item
	}

	private void decrementMenuItem() {
		switch (currentMenuItem){
		case 1: numPlayersDown(); break;
		case 2: numTanksDown(); break;
		case 3: worldIdDown(); updateWorldImg(); break;
		}
	}
	private void incrementMenuItem() {
		switch (currentMenuItem){
		case 1: numPlayersUp(); break;
		case 2: numTanksUp(); break;
		case 3: worldIdUp(); updateWorldImg(); break;
		}
		
	}

	private void worldIdUp() {
		if (this.worldId + 1 > numberOfWorlds - 1){
			worldId = numberOfWorlds - 1;
		} else {
			worldId ++;
		}
	}
	private void worldIdDown() {
		if (this.worldId - 1 < 0){
			worldId = 0;
		} else {
			worldId --;
		}
	}

	private void numTanksUp() {
		if (this.tanksPerPlayer + 1 > maxNumTanks){
			tanksPerPlayer = maxNumTanks;
		} else {
			tanksPerPlayer ++;
		}
	}
	private void numTanksDown() {
		if (this.tanksPerPlayer - 1 < 1){
			tanksPerPlayer = 1;
		} else {
			tanksPerPlayer --;
		}
	}

	private void numPlayersUp() {
		if (this.numberOfPlayers + 1 > maxNumPlayers){
			numberOfPlayers = maxNumPlayers;
		} else {
			numberOfPlayers ++;
		}
	}
	private void numPlayersDown() {
		if (this.numberOfPlayers - 1 < 2){
			numberOfPlayers = 2;
		} else {
			numberOfPlayers --;
		}
	}

	private void upMenuItems() {
		if (this.currentMenuItem - 1 < 1){
			currentMenuItem = numberMenuItems;
		} else {
			currentMenuItem --;
		}
	}
	private void downMenuItems() {
		if (this.currentMenuItem + 1 > numberMenuItems){
			currentMenuItem = 1;
		} else {
			currentMenuItem ++;
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void addPlayer() { //adds a Player to the end
		if(playerNames[0] == null) playerNames[0] = "Gordon";
		else if(playerNames[1] == null) playerNames[1] = "Oana";
		else if(playerNames[2] == null) playerNames[2] = "Peter";
		else if(playerNames[3] == null) playerNames[3] = "Victor";
	}
	
	public void removePlayer() { //removes the last Player
		if(playerNames[3] != null) playerNames[3] = null;
		else if(playerNames[2] != null) playerNames[2] = null;
		else if(playerNames[1] != null) playerNames[1] = null;
		else if(playerNames[0] != null) playerNames[0] = null;
	}
	
	public Player[] createPlayers() {
		players = new Player[numberOfPlayers];
		for (int i = 1; i <= numberOfPlayers; i++){
			players[i-1] = new Player(playerNames[i-1], createTanks(i,2)); // 2 = tankId. should be selectable probably..
		}
		return players;
	}
	
	private Tank[] createTanks(int i, int tankType){
		Random rand = new Random();		
		Tank[] playerTanks = new Tank[tanksPerPlayer];
		int worldWidth = ResourceManager.getInstance().getImage("WORLD_" + worldId + "_LEVEL").getWidth();
		
		for (int j = 0; j < tanksPerPlayer; j++){
			int tankX = rand.nextInt(worldWidth - 40) + 20;
			playerTanks[j] = new Tank(tankType, tankX, 200, i); // TODO Change 1 to i when more colours of tanks implemented.
		}
		
		return playerTanks;
	}
	
	public void startGame(GameState gs, GameContainer gc) {
		World world = new World(worldId);
		
		ArrayList<Hat> hats = new ArrayList<Hat>();
		hats = dispenseHats();
		
		gs.startGame(world, players, hats, gc);
	}

	private ArrayList<Hat> dispenseHats() {
		ArrayList<Hat> hats = new ArrayList<Hat>();
		Random rand = new Random();
		int worldWidth = ResourceManager.getInstance().getImage("WORLD_" + worldId + "_LEVEL").getWidth();
		
		int numberOfHats = numberOfPlayers; 
		
		for (int i = 0; i < numberOfHats; i++){
			int hatX = rand.nextInt(worldWidth - 40) + 20;
			hats.add(new Hat(rand.nextInt(4)+1, new Vector2f(hatX,-900), 1)); // Has to be large & negative Y value.. don't ask!
		}
		
		return hats;
	}

}
