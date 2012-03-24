package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.EndGameScreen;
import states.GameState;
import states.HistoryScreen;
import states.PreGameMenu;
import states.StartScreen;
import entities.Camera;

public class GunsAndHats extends StateBasedGame {

	private static final int FRAMEWIDTH = 800;
	private static final int FRAMEHEIGHT = 600;
	private static int SCREENWIDTH = 800;//1024;
	private static int SCREENHEIGHT = 600;//768;
	private static int FRAMERATE = 60;
	private static boolean FULLSCREEN = false; 
	// If FULLSCREEN is true, we use the desktop resolution, not SCREENWIDTH and SCREENHEIGHT as above.

	
	// Id codes for the different states in the game.
	public static int STARTSCREEN = 1;
	public static int PREGAMESTATE = 2;
	public static int GAMESTATE = 3;
	public static int ENDGAMESCREEN = 4;
	public static int HISTORYSCREEN = 5; 

	private Camera camera;

	public static boolean isFULLSCREEN() {
		return FULLSCREEN;
	}
	
	public GunsAndHats() {
		super("Guns and Hats");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		File f = new File("data/resources.xml");
		try {
			InputStream in = new FileInputStream(f);
			ResourceManager.getInstance().loadResources(in, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.addState(new StartScreen(STARTSCREEN, camera));
		this.addState(new PreGameMenu(PREGAMESTATE, camera));
		this.addState(new GameState(GAMESTATE, camera));
		this.addState(new EndGameScreen(ENDGAMESCREEN, camera));
		this.addState(new HistoryScreen(HISTORYSCREEN, camera));

		this.enterState(STARTSCREEN);
	}

	public void setCamera(int frameWidth, int frameHeight, int screenWidth,
			int screenHeight) {
		camera = new Camera(frameWidth, frameHeight, screenWidth, screenHeight);
	}

	public static void main(String[] args) throws SlickException {

		// If we are running in fullscreen, get the current
		// desktop resolution and output at that. Otherwise,
		// use the above values.
		if (FULLSCREEN) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension dim = toolkit.getScreenSize();

			SCREENWIDTH = dim.width;
			SCREENHEIGHT = dim.height;
		}

		GunsAndHats game = new GunsAndHats();
		AppGameContainer app = new AppGameContainer(game);
		game.setCamera(FRAMEWIDTH, FRAMEHEIGHT, SCREENWIDTH, SCREENHEIGHT);
		app.setDisplayMode(SCREENWIDTH, SCREENHEIGHT, FULLSCREEN);
		app.setVSync(true); // Keepin' it smooooooothhh...
		app.setTargetFrameRate(FRAMERATE);
		app.setShowFPS(false);
		app.start();
	}

}
