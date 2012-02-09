package states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GunsAndHats extends StateBasedGame {

	
	
	public GunsAndHats() {
		super("Guns and Hats");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new StartScreen());
		
		this.enterState(1);
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer( new GunsAndHats() );
		 
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(60);
        app.start();
	}

}
