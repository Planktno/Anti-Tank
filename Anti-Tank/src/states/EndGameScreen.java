package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Camera;
import game.GunsAndHats;
import game.ResourceManager;

public class EndGameScreen extends BasicGameState {

	private int id;
	Camera camera;
	
	Image background1;
	Image background2;
	Image background3;
	Image background4;
	Image b_start;
	Image b_start_hover;
	Image b_history;
	Image b_history_hover;
	Image b_exit;
	Image b_exit_hover;
	
	public EndGameScreen(int id, Camera camera) {
		this.id = id;
		this.camera = camera;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		background1 = new Image ("data/GameOverCian.jpg");
		background2 = new Image ("data/GameOverGreen.jpg");
		background3 = new Image ("data/GameOverPurple.jpg");
		background4 = new Image ("data/GameOverRed.jpg");
		b_start         = ResourceManager.getInstance().getImage("SS_BUTTON_NEW_GAME");
		b_start_hover   = ResourceManager.getInstance().getImage("SS_BUTTON_NEW_GAME_HOVER");
		b_history       = ResourceManager.getInstance().getImage("SS_BUTTON_HISTORY");
		b_history_hover = ResourceManager.getInstance().getImage("SS_BUTTON_HISTORY_HOVER");
		b_exit          = ResourceManager.getInstance().getImage("SS_BUTTON_EXIT");
		b_exit_hover    = ResourceManager.getInstance().getImage("SS_BUTTON_EXIT_HOVER");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
			throws SlickException {
		background1.draw(camera.getOffset().getX(), camera.getOffset().getY(), camera.getScale());
		b_start.draw(camera.getOffset().getX()+50*camera.getScale(), camera.getOffset().getY()+450*camera.getScale(), camera.getScale());
		b_history.draw(camera.getOffset().getX()+310*camera.getScale(), camera.getOffset().getY()+450*camera.getScale(), camera.getScale());
		b_exit.draw(camera.getOffset().getX()+550*camera.getScale(), camera.getOffset().getY()+450*camera.getScale(), camera.getScale());
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if(mouseY >= camera.getOffset().getY() + 450*camera.getScale() && mouseY <= camera.getOffset().getY() + 450*camera.getScale() + b_start.getHeight()*camera.getScale()) {
			if(mouseX >= camera.getOffset().getX() + 50*camera.getScale() && mouseX <= camera.getOffset().getX() + 50*camera.getScale() + b_start.getWidth()*camera.getScale()) {
				b_start=b_start_hover.copy();
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) sb.enterState(GunsAndHats.GAMESTATE);
			} else {
				b_start = ResourceManager.getInstance().getImage("SS_BUTTON_NEW_GAME");
			}
		} else {
			b_start = ResourceManager.getInstance().getImage("SS_BUTTON_NEW_GAME");
		}
		
		if(mouseY >= camera.getOffset().getY() + 450*camera.getScale() && mouseY <= camera.getOffset().getY() + 450*camera.getScale() + b_start.getHeight()*camera.getScale()) {
			if(mouseX >= camera.getOffset().getX() + 310*camera.getScale() && mouseX <= camera.getOffset().getX() + 310*camera.getScale() + b_start.getWidth()*camera.getScale()) {
				b_history=b_history_hover.copy();
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) sb.enterState(GunsAndHats.HISTORYSCREEN);
			} else {
				b_history = ResourceManager.getInstance().getImage("SS_BUTTON_HISTORY");  
			}
		} else {
			b_history = ResourceManager.getInstance().getImage("SS_BUTTON_HISTORY");
		}
		
		if(mouseY >= camera.getOffset().getY() + 450*camera.getScale() && mouseY <= camera.getOffset().getY() + 450*camera.getScale() + b_start.getHeight()*camera.getScale()) {
			if(mouseX >= camera.getOffset().getX() + 550*camera.getScale() && mouseX <= camera.getOffset().getX() + 550*camera.getScale() + b_start.getWidth()*camera.getScale()) {
				b_exit=b_exit_hover.copy();
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) gc.exit();
			} else {
				b_exit = ResourceManager.getInstance().getImage("SS_BUTTON_EXIT"); 
			}
		} else {
			b_exit = ResourceManager.getInstance().getImage("SS_BUTTON_EXIT");
		}
		
	}

	@Override
	public int getID() {
		return id;
	}

}
