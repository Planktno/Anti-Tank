package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import entities.World;

public class GunsAndHats_simple extends BasicGame {

	World world;
	
	public GunsAndHats_simple() {
		super("Guns and Hats");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub
		world.render();
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		world = new World();
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer( new GunsAndHats_simple() );
		 
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(60);
        app.start();
	}
}
