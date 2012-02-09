package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartScreen extends BasicGameState{

	private static int STARTSCREENSTATE = 1;
	
	Image background;
	
	public StartScreen() {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		background = new Image("data/testlevel.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
			throws SlickException {
		gr.drawString("LOL",0,0);
		background.draw(0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return STARTSCREENSTATE;
	}

}
