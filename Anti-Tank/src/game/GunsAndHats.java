package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.EndGameScreen;
import states.GameState;
import states.HistoryScreen;
import states.InGameMenu;
import states.PreGameMenu;
import states.StartScreen;
import entities.Camera;

public class GunsAndHats extends StateBasedGame {

	private static int FRAMEWIDTH = 1024;
	private static int FRAMEHEIGHT = 768;
	private static int FRAMERATE = 60;
	
	private static int STARTSCREEN = 1;
	private static int PREGAMESTATE = 2;
	private static int GAMESTATE = 3;
	private static int INGAMEMENU = 4;
	private static int ENDGAMESCREEN = 5;
	private static int HISTORYSCREEN = 6;
	
	private Camera camera;
	
	public GunsAndHats() {
		super("Guns and Hats");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new StartScreen(STARTSCREEN));
		this.addState(new PreGameMenu(PREGAMESTATE));
		this.addState(new GameState(GAMESTATE));
		this.addState(new InGameMenu(INGAMEMENU));
		this.addState(new EndGameScreen(ENDGAMESCREEN));
		this.addState(new HistoryScreen(HISTORYSCREEN));
		
		this.enterState(STARTSCREEN);
	}
	
	public void setCamera(int frameWidth, int frameHeight, int screenWidth, int screenHeight) {
		camera = new Camera(frameWidth, frameHeight, screenWidth, screenHeight);
	}
	
	public static void main(String[] args) throws SlickException {
		GunsAndHats game = new GunsAndHats();
		AppGameContainer app = new AppGameContainer(game);
//		game.setCamera(FRAMEWIDTH, FRAMEHEIGHT, app.getScreenWidth(), app.getScreenHeight());
//		app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
		game.setCamera(FRAMEWIDTH, FRAMEHEIGHT, FRAMEWIDTH, FRAMEHEIGHT);
		app.setDisplayMode(FRAMEWIDTH, FRAMEHEIGHT, true);
		app.setTargetFrameRate(FRAMERATE);
		app.setShowFPS(false);
		app.start();
	}

}
