package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Camera;

public class StartScreen extends BasicGameState{

	private int id;
	Camera camera;
	
	Image background;
	Image b_start;
	Image b_history;
	Image b_exit;
	
	public StartScreen(int id, Camera camera) {
		this.id = id;
		this.camera = camera;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		background = new Image("data/startscreen.JPG");
		b_start    = new Image("data/button_new_game.png");
		b_history  = new Image("data/button_history.png");
		b_exit     = new Image("data/button_exit.png");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
			throws SlickException {
		gr.drawString("LOL",0,0);
		background.draw(camera.getOffset().getX(), camera.getOffset().getY(), camera.getScale());
		b_start.draw(camera.getOffset().getX()+50*camera.getScale(), camera.getOffset().getY()+450*camera.getScale(), camera.getScale());
		b_history.draw(camera.getOffset().getX()+330*camera.getScale(), camera.getOffset().getY()+450*camera.getScale(), camera.getScale());
		b_exit.draw(camera.getOffset().getX()+590*camera.getScale(), camera.getOffset().getY()+450*camera.getScale(), camera.getScale());
		// +n*camera.getScale()
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return id;
	}

}
