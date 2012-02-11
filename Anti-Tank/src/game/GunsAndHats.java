package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.EndGameScreen;
import states.GameState;
import states.HistoryScreen;
import states.InGameMenu;
import states.StartScreen;
import entities.Camera;

public class GunsAndHats extends StateBasedGame {

	private static int FRAMEWIDTH = 1024;
	private static int FRAMEHEIGHT = 768;
	private static int FRAMERATE = 60;
	
	private Camera camera;
	
	public GunsAndHats() {
		super("Guns and Hats");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new StartScreen());
		this.addState(new PreGameState());
		this.addState(new GameState());
		this.addState(new InGameMenu());
		this.addState(new EndGameScreen());
		this.addState(new HistoryScreen());
		
		this.enterState(1);
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
